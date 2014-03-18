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
package org.jclouds.docker.compute.strategy;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.Volume;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.docker.DockerApi;
import org.jclouds.docker.domain.Config;
import org.jclouds.docker.domain.Container;
import org.jclouds.docker.domain.HostConfig;
import org.jclouds.docker.domain.Image;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.logging.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.contains;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.find;

/**
 * defines the connection between the {@link org.jclouds.docker.DockerApi} implementation and
 * the jclouds {@link org.jclouds.compute.ComputeService}
 */
@Singleton
public class DockerComputeServiceAdapter implements
        ComputeServiceAdapter<Container, Hardware, Image, Location> {

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final DockerApi api;

   @Inject
   public DockerComputeServiceAdapter(DockerApi api) {
      this.api = checkNotNull(api, "api");
   }

   @Override
   public NodeAndInitialCredentials<Container> createNodeWithGroupEncodedIntoName(String group, String name,
                                                                                  Template template) {
      checkNotNull(template, "template was null");
      checkNotNull(template.getOptions(), "template options was null");

      String imageId = checkNotNull(template.getImage().getId(), "template image id must not be null");
      String loginUser = template.getOptions().getLoginUser() == null ? "root" : template.getOptions().getLoginUser();
      String loginUserPassword = template.getOptions().getLoginPassword() == null ? "password" : template.getOptions()
              .getLoginUser();
      boolean volumeBindings = !template.getHardware().getVolumes().isEmpty();

      Map<String, Object> exposedPorts = Maps.newHashMap();
      int[] inboundPorts = template.getOptions().getInboundPorts();
      for (int inboundPort : inboundPorts) {
         exposedPorts.put(inboundPort + "/tcp", Maps.newHashMap());
      }

      Map<String, Object> volumes = Maps.newLinkedHashMap();
      volumes.put("/root", Maps.newHashMap());

      Config config = Config.builder()
              .image(imageId)
              .cmd(ImmutableList.of("/usr/sbin/sshd", "-D"))
              .attachStdout(true)
              .attachStderr(true)
              .volumes(volumes)
              .workingDir("")
              .exposedPorts(exposedPorts)
              .build();

      logger.debug(">> creating new container (%s)", "newContainer");
      Container container = api.getRemoteApi().createContainer(config);
      logger.trace("<< container(%s)", container.getId());

      // set up for port bindings
      Map<String, List<Map<String, String>>> portBindings = Maps.newHashMap();
      HostConfig.Builder hostConfigBuilder = HostConfig.builder()
              .portBindings(portBindings)
              .publishAllPorts(true)
              .privileged(true);

      // TODO improve volumes management
      if (volumeBindings) {
         for (Volume v : template.getHardware().getVolumes()) {
            hostConfigBuilder.binds(ImmutableList.of(v.getDevice() + ":/root"));
         }
      } else {
         hostConfigBuilder.binds(ImmutableList.of("/var/lib/docker:/root"));
      }
      HostConfig hostConfig = hostConfigBuilder.build();

      api.getRemoteApi().startContainer(container.getId(), hostConfig);
      container = api.getRemoteApi().inspectContainer(container.getId());
      return new NodeAndInitialCredentials<Container>(container, container.getId() + "",
              LoginCredentials.builder().user(loginUser).password(loginUserPassword).build());
   }

   @Override
   public Iterable<Hardware> listHardwareProfiles() {
      Set<Hardware> hardware = Sets.newLinkedHashSet();
      // todo they are only placeholders at the moment
      hardware.add(new HardwareBuilder().ids("micro").hypervisor("lxc").name("micro").ram(512).build());
      hardware.add(new HardwareBuilder().ids("small").hypervisor("lxc").name("small").ram(1024).build());
      hardware.add(new HardwareBuilder().ids("medium").hypervisor("lxc").name("medium").ram(2048).build());
      hardware.add(new HardwareBuilder().ids("large").hypervisor("lxc").name("large").ram(3072).build());
      return hardware;
   }

   @Override
   public Set<Image> listImages() {
      return api.getRemoteApi().listImages(true);
   }

   @Override
   public Image getImage(final String id) {
      return find(listImages(), new Predicate<Image>() {

         @Override
         public boolean apply(Image input) {
            return input.getId().equals(id);
         }

      }, null);
   }

   @Override
   public Iterable<Container> listNodes() {
      return api.getRemoteApi().listContainers(false);
   }

   @Override
   public Iterable<Container> listNodesByIds(final Iterable<String> ids) {
      return filter(listNodes(), new Predicate<Container>() {

         @Override
         public boolean apply(Container server) {
            return contains(ids, server.getId());
         }
      });
   }

   @Override
   public Iterable<Location> listLocations() {
      return ImmutableSet.of();
   }

   @Override
   public Container getNode(String id) {
      return api.getRemoteApi().inspectContainer(id);
   }

   @Override
   public void destroyNode(String id) {
      api.getRemoteApi().stopContainer(id);
      api.getRemoteApi().removeContainer(id, true);
   }

   @Override
   public void rebootNode(String id) {
      api.getRemoteApi().startContainer(id);
   }

   @Override
   public void resumeNode(String id) {
      api.getRemoteApi().startContainer(id);
   }

   @Override
   public void suspendNode(String id) {
      api.getRemoteApi().stopContainer(id);
   }

}
