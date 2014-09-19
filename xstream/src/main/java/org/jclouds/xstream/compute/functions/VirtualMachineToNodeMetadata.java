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
package org.jclouds.xstream.compute.functions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.getOnlyElement;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.jclouds.collect.Memoized;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeMetadataBuilder;
import org.jclouds.compute.domain.Processor;
import org.jclouds.compute.functions.GroupNamingConvention;
import org.jclouds.xstream.domain.VirtualMachine;
import org.jclouds.xstream.domain.Port;
import org.jclouds.xstream.domain.State;
import org.jclouds.domain.Location;
import org.jclouds.providers.ProviderMetadata;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.inject.Singleton;

@Singleton
public class VirtualMachineToNodeMetadata implements Function<VirtualMachine, NodeMetadata> {

   private final ProviderMetadata providerMetadata;
   private final Function<State, NodeMetadata.Status> toPortableStatus;
   private final GroupNamingConvention nodeNamingConvention;
   private final Supplier<Map<String, ? extends Image>> images;
   private final Supplier<Set<? extends Location>> locations;

   @Inject
   public VirtualMachineToNodeMetadata(ProviderMetadata providerMetadata, Function<State,
           NodeMetadata.Status> toPortableStatus, GroupNamingConvention.Factory namingConvention,
                                  Supplier<Map<String, ? extends Image>> images,
                                  @Memoized Supplier<Set<? extends Location>> locations) {
      this.providerMetadata = checkNotNull(providerMetadata, "providerMetadata");
      this.toPortableStatus = checkNotNull(toPortableStatus, "toPortableStatus cannot be null");
      this.nodeNamingConvention = checkNotNull(namingConvention, "namingConvention").createWithoutPrefix();
      this.images = checkNotNull(images, "images cannot be null");
      this.locations = checkNotNull(locations, "locations");
   }

   @Override
   public NodeMetadata apply(VirtualMachine vm) {
      String name = cleanUpName(vm.getName());
      String group = nodeNamingConvention.extractGroup(name);
      NodeMetadataBuilder builder = new NodeMetadataBuilder();
      builder.ids(vm.getVirtualMachineID())
              .name(name)
              .group(group)
              .hostname(vm.getDnsName())
               // TODO Set up hardware
              .hardware(new HardwareBuilder()
                      .id("")
                      .ram(vm.getRamAllocatedMB())
                      .processor(new Processor(vm.getCpuShares(), vm.getCpuLimitMHz()))
                      .build());
      //builder.status(toPortableStatus.apply(vm.getPowerState()));
      builder.imageId(vm.getSourceTemplateId());
      //builder.loginPort(getLoginPort(vm));
      builder.publicAddresses(getPublicIpAddresses());
      builder.privateAddresses(getPrivateIpAddresses(vm));
      builder.location(Iterables.getOnlyElement(locations.get()));
      Image image = images.get().get(vm.getSourceTemplateId());
      builder.imageId(image.getId());
      builder.operatingSystem(image.getOperatingSystem());

      return builder.build();
   }

   private String cleanUpName(String name) {
      return name.startsWith("/") ? name.substring(1) : name;
   }

   private Iterable<String> getPrivateIpAddresses(VirtualMachine virtualMachine) {
      return ImmutableList.of(virtualMachine.getIpAddress());
   }

   private List<String> getPublicIpAddresses() {
      String dockerIpAddress = URI.create(providerMetadata.getEndpoint()).getHost();
      return ImmutableList.of(dockerIpAddress);
   }

}
