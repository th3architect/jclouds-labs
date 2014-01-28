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
package org.jclouds.docker.compute.functions;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadataBuilder;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.docker.DockerApi;
import org.jclouds.docker.domain.Container;
import org.jclouds.docker.domain.Port;
import org.jclouds.domain.LocationBuilder;
import org.jclouds.domain.LocationScope;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.logging.Logger;
import org.jclouds.rest.ApiContext;
import org.jclouds.ssh.SshClient;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.jclouds.compute.util.ComputeServiceUtils.parseOsFamilyOrUnrecognized;
import static org.jclouds.docker.compute.functions.ContainerToSshClient.readRsaIdentity;

/**
 * @author Andrea Turli
 */
public class ContainerToNodeMetadata implements Function<Container, NodeMetadata> {

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final Function<Container, SshClient> sshClientForContainer;
   private final ApiContext<DockerApi> context;

   @Inject
   public ContainerToNodeMetadata(Function<Container, SshClient> sshClientForContainer, ApiContext<DockerApi> context) {
      this.sshClientForContainer = sshClientForContainer;
      this.context = context;
   }

   @Override
   public NodeMetadata apply(Container container) {
      // TODO extract group and name from description?
      String group = "jclouds";
      String name = "container";

      NodeMetadataBuilder nodeMetadataBuilder = new NodeMetadataBuilder();
      nodeMetadataBuilder.id(container.getId())
              .name(name)
              .group(group);
      // TODO Set up location properly
      LocationBuilder locationBuilder = new LocationBuilder();
      locationBuilder.description("");
      locationBuilder.id("");
      locationBuilder.scope(LocationScope.HOST);
      nodeMetadataBuilder.location(locationBuilder.build());
      // TODO setup hardware and hostname properly
      if (container.getStatus() != null) {
         nodeMetadataBuilder.status(container.getStatus().contains("Up") ? NodeMetadata.Status.RUNNING : NodeMetadata.Status.SUSPENDED);
      } else {
         nodeMetadataBuilder.status(container.getState().isRunning() ? NodeMetadata.Status.RUNNING : NodeMetadata.Status.SUSPENDED);
      }
      nodeMetadataBuilder.imageId(container.getImage());
      nodeMetadataBuilder.loginPort(getLoginPort(container));
      nodeMetadataBuilder.publicAddresses(getPublicIpAddresses());
      nodeMetadataBuilder.privateAddresses(getPrivateIpAddresses(container));
      nodeMetadataBuilder.operatingSystem(getOs(container));
      nodeMetadataBuilder.credentials(getCredentials(container));
      return nodeMetadataBuilder.build();
   }

   private Iterable<String> getPrivateIpAddresses(Container container) {
      if (container.getNetworkSettings() == null) return ImmutableList.of();
      return ImmutableList.of(container.getNetworkSettings().getIpAddress());
   }

   private List<String> getPublicIpAddresses() {
      String dockerIpAddress = URI.create(context.getProviderMetadata().getEndpoint()).getHost();
      return ImmutableList.of(dockerIpAddress);
   }

   private LoginCredentials getCredentials(Container container) {
      // todo first time is root/password then it's AdminAccess
      LoginCredentials.Builder credsBuilder = LoginCredentials.builder();
      String userName = System.getProperty("user.name");
      SshClient client = sshClientForContainer.apply(container);
      if (client.getUsername().equals(userName)) {
         String privateKey = readRsaIdentity();
         credsBuilder.user(userName)
                 .privateKey(privateKey);
      } else if (client.getUsername().equals("root")) {
         credsBuilder.user("root")
                 .password("password")
                 .authenticateSudo(true);
      }
      return credsBuilder.build();
   }

   private OperatingSystem getOs(Container container) {
      SshClient client = sshClientForContainer.apply(container);
      try {
         client.connect();
         URL url = Resources.getResource("os-details.sh");
         String script = Resources.toString(url, Charsets.UTF_8);
         ExecResponse osResponse = client.exec(script);
         Map<String, String> osInfo = Splitter.on(";").trimResults().withKeyValueSeparator(":").split(osResponse.getOutput());
         // todo probably it is worth to cache the result
         OsFamily family = parseOsFamilyOrUnrecognized(osInfo.get("os"));
         return OperatingSystem.builder().description(osResponse.getOutput().trim())
                 .family(family)
                 .arch(osInfo.get("arch"))
                 .version(osInfo.get("version"))
                 .is64Bit(osInfo.get("arch").equals("64"))
                 .build();
      } catch (IOException e) {
         throw Throwables.propagate(e);
      } finally {
         if (client != null)
            client.disconnect();
      }
   }

   private int getLoginPort(Container container) {
      if (container.getNetworkSettings() != null) {
         return Integer.parseInt(getOnlyElement(container.getNetworkSettings().getPorts().get("22/tcp")).get("HostPort"));
      } else if (container.getPorts() != null) {
         for (Port port : container.getPorts()) {
            if (port.getPrivatePort() == 22) {
               return port.getPublicPort();
            }
         }
      }
      throw new IllegalStateException("Cannot determine the login port for " + container.getId());
   }
}
