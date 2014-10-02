/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.docker.compute;

import static com.google.common.collect.Iterables.getOnlyElement;
import static java.lang.String.format;
import static java.util.logging.Logger.getAnonymousLogger;
import static org.jclouds.compute.options.RunScriptOptions.Builder.nameTask;
import static org.jclouds.compute.options.TemplateOptions.Builder.runAsRoot;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.jclouds.compute.JettyStatements;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.internal.BaseComputeServiceLiveTest;
import org.jclouds.docker.DockerApi;
import org.jclouds.docker.compute.options.DockerTemplateOptions;
import org.jclouds.docker.domain.Container;
import org.jclouds.docker.features.ImageApi;
import org.jclouds.docker.options.CreateImageOptions;
import org.jclouds.docker.options.DeleteImageOptions;
import org.jclouds.scriptbuilder.domain.Statement;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.net.HostAndPort;
import com.google.inject.Module;

/**
 * Live tests for the {@link org.jclouds.compute.ComputeService} integration.
 */
@Test(groups = "live", singleThreaded = true, testName = "DockerComputeServiceLiveTest")
public class DockerComputeServiceLiveTest extends BaseComputeServiceLiveTest {

   private static final String SSHABLE_IMAGE = "tutum/ubuntu";
   private static final String SSHABLE_IMAGE_TAG = "trusty";
   private Image defaultImage;

   public DockerComputeServiceLiveTest() {
      provider = "docker";
   }

   @Override
   protected Module getSshModule() {
      return new SshjSshClientModule();
   }

   @Override
   protected void initializeContext() {
      super.initializeContext();

      String imageName = SSHABLE_IMAGE + ":" + SSHABLE_IMAGE_TAG;
      org.jclouds.docker.domain.Image image = imageApi().inspectImage(imageName);
      if (image == null) {
         CreateImageOptions options = CreateImageOptions.Builder.fromImage(SSHABLE_IMAGE).tag(SSHABLE_IMAGE_TAG);
         imageApi().createImage(options);
      }
      image = imageApi().inspectImage(imageName);
      defaultImage = client.getImage(image.getId());
      assertNotNull(defaultImage);
   }

   @AfterClass
   @Override
   protected void tearDownContext() {
      super.tearDownContext();
      if (defaultImage != null) {
         imageApi().deleteImage(SSHABLE_IMAGE + ":" + SSHABLE_IMAGE_TAG, DeleteImageOptions.Builder.force(true));
      }
   }

   private ImageApi imageApi() {
      return client.getContext().unwrapApi(DockerApi.class).getImageApi();
   }

   @Override
   protected Template buildTemplate(TemplateBuilder templateBuilder) {
      template = templateBuilder.imageId(defaultImage.getId()).build();
      DockerTemplateOptions options = template.getOptions().as(DockerTemplateOptions.class);
      options.env(ImmutableList.of("ROOT_PASS=password"));
      return template;
   }

   @Override
   public void testImageById() {
      Template defaultTemplate = buildTemplate(client.templateBuilder());
      assertEquals(view.getComputeService().getImage(defaultTemplate.getImage().getId()), defaultTemplate.getImage());
   }

   @Override
   protected void createAndRunAServiceInGroup(String group) throws RunNodesException {
      // note that some cloud providers do not support mixed case tag names
      ImmutableMap<String, String> userMetadata = ImmutableMap.<String, String> of("test", group);

      ImmutableSet<String> tags = ImmutableSet.of(group);
      Stopwatch watch = Stopwatch.createStarted();

      template = buildTemplate(client.templateBuilder());
      template.getOptions().inboundPorts(22, 8080).blockOnPort(22, 300).userMetadata(userMetadata).tags(tags);

      NodeMetadata node = getOnlyElement(client.createNodesInGroup(group, 1, template));
      long createSeconds = watch.elapsed(TimeUnit.SECONDS);

      final String nodeId = node.getId();

      checkUserMetadataContains(node, userMetadata);
      checkTagsInNodeEquals(node, tags);

      getAnonymousLogger().info(
              format("<< available node(%s) os(%s) in %ss", node.getId(), node.getOperatingSystem(), createSeconds));

      watch.reset().start();

      client.runScriptOnNode(nodeId, JettyStatements.install(), nameTask("configure-jetty"));

      long configureSeconds = watch.elapsed(TimeUnit.SECONDS);

      getAnonymousLogger().info(
              format(
                      "<< configured node(%s) with %s and jetty %s in %ss",
                      nodeId,
                      exec(nodeId, "java -fullversion"),
                      exec(nodeId, JettyStatements.version()), configureSeconds));

      trackProcessOnNode(JettyStatements.start(), "start jetty", node);

      client.runScriptOnNode(nodeId, JettyStatements.stop(), runAsRoot(false).wrapInInitScript(false));

      trackProcessOnNode(JettyStatements.start(), "start jetty", node);
   }

   protected void trackProcessOnNode(Statement process, String processName, NodeMetadata node) {
      ServiceStats stats = new ServiceStats();
      Stopwatch watch = Stopwatch.createStarted();
      ExecResponse exec = client.runScriptOnNode(node.getId(), process, runAsRoot(false).wrapInInitScript(false));
      stats.backgroundProcessMilliseconds = watch.elapsed(TimeUnit.MILLISECONDS);

      Container container = client.getContext().unwrapApi(DockerApi.class).getContainerApi().inspectContainer(node.getId());
      Map<String, List<Map<String, String>>> ports = container.getNetworkSettings().getPorts();
      int port = Integer.parseInt(getOnlyElement(ports.get("8080/tcp")).get("HostPort"));

      watch.reset().start();
      HostAndPort socket;
      try {
         socket = openSocketFinder.findOpenSocketOnNode(node, port, 600, TimeUnit.SECONDS);
      } catch (NoSuchElementException e) {
         throw new NoSuchElementException(format("%s%n%s%s", e.getMessage(), exec.getOutput(), exec.getError()));
      }
      stats.socketOpenMilliseconds = watch.elapsed(TimeUnit.MILLISECONDS);
      getAnonymousLogger().info(format("<< %s on node(%s)[%s] %s", processName, node.getId(), socket, stats));
   }

   static class ServiceStats {
      long backgroundProcessMilliseconds;
      long socketOpenMilliseconds;

      @Override
      public String toString() {
         return format("[backgroundProcessMilliseconds=%s, socketOpenMilliseconds=%s]",
                 backgroundProcessMilliseconds, socketOpenMilliseconds);
      }
   }

}
