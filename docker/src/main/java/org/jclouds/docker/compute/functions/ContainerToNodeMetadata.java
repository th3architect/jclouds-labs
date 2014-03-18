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

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
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
import org.jclouds.logging.Logger;
import org.jclouds.rest.ApiContext;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;
import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;

/**
 * @author Andrea Turli
 */
public class ContainerToNodeMetadata implements Function<Container, NodeMetadata> {

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final ApiContext<DockerApi> context;

   @Inject
   public ContainerToNodeMetadata(ApiContext<DockerApi> context) {
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
      nodeMetadataBuilder.operatingSystem(OperatingSystem.builder().description("my description")
                                                                   .family(OsFamily.UNRECOGNIZED)
                                                                   .build());
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
