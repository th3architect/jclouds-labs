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
package org.jclouds.xstream.compute.strategy;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.find;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.HardwareBuilder;
import org.jclouds.compute.domain.Processor;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.xstream.domain.Config;
import org.jclouds.xstream.domain.Disk;
import org.jclouds.xstream.XStreamApi;
import org.jclouds.xstream.compute.options.XStreamTemplateOptions;
import org.jclouds.xstream.domain.Nic;
import org.jclouds.xstream.domain.Task;
import org.jclouds.xstream.domain.VirtualMachine;
import org.jclouds.xstream.domain.HostConfig;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.logging.Logger;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * defines the connection between the {@link org.jclouds.xstream.XStreamApi} implementation and
 * the jclouds {@link org.jclouds.compute.ComputeService}
 */
@Singleton
public class XStreamComputeServiceAdapter implements
        ComputeServiceAdapter<VirtualMachine, Hardware, VirtualMachine, Location> {

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final XStreamApi api;

   @Inject
   public XStreamComputeServiceAdapter(XStreamApi api) {
      this.api = checkNotNull(api, "api");
   }

   @Override
   public NodeAndInitialCredentials<VirtualMachine> createNodeWithGroupEncodedIntoName(String group, String name,
                                                                                       Template template) {
      checkNotNull(template, "template was null");
      checkNotNull(template.getOptions(), "template options was null");

      String imageId = checkNotNull(template.getImage().getId(), "template image id must not be null");
      String loginUser = template.getImage().getDefaultCredentials().getUser();
      String loginUserPassword = template.getImage().getDefaultCredentials().getPassword();

      XStreamTemplateOptions templateOptions = XStreamTemplateOptions.class.cast(template.getOptions());
      int[] inboundPorts = templateOptions.getInboundPorts();

      /*
      Map<String, Object> exposedPorts = Maps.newHashMap();
      for (int inboundPort : inboundPorts) {
         exposedPorts.put(inboundPort + "/tcp", Maps.newHashMap());
      }

      Config.Builder containerConfigBuilder = Config.builder()
              .imageId(imageId)
              .exposedPorts(exposedPorts);

      if (templateOptions.getCommands().isPresent()) {
         containerConfigBuilder.cmd(templateOptions.getCommands().get());
      }

      if (templateOptions.getMemory().isPresent()) {
         containerConfigBuilder.memory(templateOptions.getMemory().get());
      }

      if (templateOptions.getCpuShares().isPresent()) {
         containerConfigBuilder.cpuShares(templateOptions.getCpuShares().get());
      }

      if (templateOptions.getVolumes().isPresent()) {
         Map<String, Object> volumes = Maps.newLinkedHashMap();
         for (String containerDir : templateOptions.getVolumes().get().values()) {
            volumes.put(containerDir, Maps.newHashMap());
         }
         containerConfigBuilder.volumes(volumes);
      }
      Config containerConfig = containerConfigBuilder.build();
      */

      Config vmConfig = Config.builder()
              .virtualMachineID("2aed1f6b-7e0c-4397-7ce1-6b43273d4e4a")
              .customerDefinedName("jclouds-test")
              .numCpu(1)
              .ramAllocatedMB(2048)
              .sourceTemplateId(imageId)
              .computeProfileId("2aaaafc4-9a28-f217-2a77-ae1f908bfe9b")
              .hypervisorResourceId("b6897114-79c1-49b5-9277-7bc89371a7cf")
              .tenantId("1357ddcd-ca97-4994-a1fc-90b67f30b4fc")
              .disks(ImmutableList.of(Disk.builder()
                      .storageId("8a2ce623-fb4f-bfe7-098f-cb1372a724f4")
                      .capacityKB(102400)
                      .deviceKey(2000)
                      .storageProfileId("560022dc-74f0-abe8-72e4-69797569cc58")
                      .build()))
              .nics(ImmutableList.of(Nic.builder()
                      .networkId("a625b03b-2759-9da9-8d91-fb697976fb90")
                      .adapterType(1)
                      .deviceKey(4000)
                      .virtualMachineNicId("45971571-9323-4c1c-4472-f1ec3504ce9e")
                      .build()))
              .build();
      logger.debug(">> creating new virtual machine with vm config(%s)", vmConfig);
      Task creationTask = api.getVirtualMachineApi().createVirtualMachine(vmConfig);
      logger.trace("<< virtualMachine(%s)", creationTask.getId());

      /*

      HostConfig.Builder hostConfigBuilder = HostConfig.builder()
              .publishAllPorts(true)
              .privileged(true);

      if (templateOptions.getDns().isPresent()) {
         hostConfigBuilder.dns(templateOptions.getDns().get());
      }
      // set up for volume bindings
      if (templateOptions.getVolumes().isPresent()) {
         for (Map.Entry<String, String> entry : templateOptions.getVolumes().get().entrySet()) {
            hostConfigBuilder.binds(ImmutableList.of(entry.getKey() + ":" + entry.getValue()));
         }
      }

      api.getVirtualMachineApi().powerOn(virtualMachine.getVirtualMachineID());
      virtualMachine = api.getVirtualMachineApi().getVirtualMachine(virtualMachine.getVirtualMachineID());
      if (!virtualMachine.getPowerState().equals("powerOn")) {
         destroyNode(virtualMachine.getVirtualMachineID());
         throw new IllegalStateException(String.format("VM %s has not started correctly", virtualMachine.getVirtualMachineID()));
      }
      */
      VirtualMachine virtualMachine = api.getVirtualMachineApi().getVirtualMachine("");
      return new NodeAndInitialCredentials(virtualMachine, virtualMachine.getVirtualMachineID(),
              LoginCredentials.builder().user(loginUser).password(loginUserPassword).build());
   }

   @Override
   public Iterable<Hardware> listHardwareProfiles() {
      Set<Hardware> hardware = Sets.newLinkedHashSet();
      // todo they are only placeholders at the moment
      hardware.add(new HardwareBuilder().ids("micro").hypervisor("lxc").name("micro").processor(new Processor(1, 1)).ram(512).build());
      hardware.add(new HardwareBuilder().ids("small").hypervisor("lxc").name("small").processor(new Processor(1, 1)).ram(1024).build());
      hardware.add(new HardwareBuilder().ids("medium").hypervisor("lxc").name("medium").processor(new Processor(1, 1)).ram(2048).build());
      hardware.add(new HardwareBuilder().ids("large").hypervisor("lxc").name("large").processor(new Processor(1, 1)).ram(3072).build());
      return hardware;
   }

   @Override
   public Iterable<VirtualMachine> listImages() {
      return api.getVirtualMachineApi().listTemplates();
   }

   @Override
   public VirtualMachine getImage(final String imageId) {
      // less efficient than just inspectImage but listImages return repoTags
      return find(listImages(), new Predicate<VirtualMachine>() {

         @Override
         public boolean apply(VirtualMachine input) {
            return input.getVirtualMachineID().equals(imageId);
         }
      }, null);
   }

   @Override
   public Iterable<VirtualMachine> listNodes() {
      return api.getVirtualMachineApi().listVirtualMachines();
   }

   @Override
   public Iterable<VirtualMachine> listNodesByIds(final Iterable<String> ids) {
      Set<VirtualMachine> containers = Sets.newHashSet();
      for (String id : ids) {
         containers.add(api.getVirtualMachineApi().getVirtualMachine(id));
      }
      return containers;
   }

   @Override
   public Iterable<Location> listLocations() {
      return ImmutableSet.of();
   }

   @Override
   public VirtualMachine getNode(String id) {
      return api.getVirtualMachineApi().getVirtualMachine(id);
   }

   @Override
   public void destroyNode(String id) {
      api.getVirtualMachineApi().removeVirtualMachine(id);
   }

   @Override
   public void rebootNode(String id) {
      api.getVirtualMachineApi().rebootOS(id);
   }

   @Override
   public void resumeNode(String id) {
      throw new UnsupportedOperationException("resume not supported");
   }

   @Override
   public void suspendNode(String id) {
      throw new UnsupportedOperationException("suspend not supported");
   }

}
