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
package org.jclouds.vcloud.director.v1_5.compute.strategy;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.tryFind;
import static org.jclouds.util.Predicates2.retry;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType.ORG_NETWORK;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType.VAPP_TEMPLATE;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType.VDC;
import java.net.URI;
import java.util.Random;
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
import org.jclouds.dmtf.ovf.MsgType;
import org.jclouds.domain.Location;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.logging.Logger;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorApi;
import org.jclouds.vcloud.director.v1_5.domain.Link;
import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.jclouds.vcloud.director.v1_5.domain.ResourceEntity;
import org.jclouds.vcloud.director.v1_5.domain.Session;
import org.jclouds.vcloud.director.v1_5.domain.Task;
import org.jclouds.vcloud.director.v1_5.domain.VApp;
import org.jclouds.vcloud.director.v1_5.domain.VAppTemplate;
import org.jclouds.vcloud.director.v1_5.domain.Vdc;
import org.jclouds.vcloud.director.v1_5.domain.Vm;
import org.jclouds.vcloud.director.v1_5.domain.network.Network;
import org.jclouds.vcloud.director.v1_5.domain.network.NetworkConfiguration;
import org.jclouds.vcloud.director.v1_5.domain.network.VAppNetworkConfiguration;
import org.jclouds.vcloud.director.v1_5.domain.org.Org;
import org.jclouds.vcloud.director.v1_5.domain.params.DeployVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.params.InstantiateVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.params.InstantiateVAppTemplateParams;
import org.jclouds.vcloud.director.v1_5.domain.params.InstantiationParams;
import org.jclouds.vcloud.director.v1_5.domain.params.UndeployVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.section.NetworkConfigSection;
import org.jclouds.vcloud.director.v1_5.predicates.ReferencePredicates;
import org.jclouds.vcloud.director.v1_5.predicates.TaskSuccess;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * defines the connection between the {@link VCloudDirectorApi} implementation and
 * the jclouds {@link org.jclouds.compute.ComputeService}
 */
@Singleton
public class VCloudDirectorComputeServiceAdapter implements
        ComputeServiceAdapter<Vm, Hardware, VAppTemplate, Location> {

   protected static final long TASK_TIMEOUT_SECONDS = 300L;

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final VCloudDirectorApi api;
   private Predicate<Task> retryTaskSuccess;

   @Inject
   public VCloudDirectorComputeServiceAdapter(VCloudDirectorApi api) {
      this.api = checkNotNull(api, "api");
   }

   @Override
   public NodeAndInitialCredentials<Vm> createNodeWithGroupEncodedIntoName(String group, String name,
                                                                                  Template template) {
      checkNotNull(template, "template was null");
      checkNotNull(template.getOptions(), "template options was null");

      String imageId = checkNotNull(template.getImage().getId(), "template image id must not be null");
      String imageName = checkNotNull(template.getImage().getName(), "template image name must not be null");

      String loginUser = template.getImage().getDefaultCredentials().getUser();
      String loginUserPassword = template.getImage().getDefaultCredentials().getPassword();

      // workflow
      /*
         1. tryFindVdcByName(InOrg)
         2. tryFindVAppTemplateByName(InOrg)
         2. build the InstantiateVAppParams using:
            2.1 NetworkConfiguration
            2.2 vAppTemplateURI
       */

      Session session = api.getCurrentSession();
      Org org = api.getOrgApi().get(find(api.getOrgApi().list(), ReferencePredicates.nameEquals(session.get())).getHref());
      final Network network;
      Network.FenceMode fenceMode = Network.FenceMode.NAT_ROUTED;
      Optional<Network> optionalNetwork = tryFindNetworkInOrgWithFenceMode(org, fenceMode);
      if (!optionalNetwork.isPresent()) {
         throw new IllegalStateException();
      }
      network = optionalNetwork.get();


      Vdc vdc = api.getVdcApi()
              .get(find(org.getLinks(), ReferencePredicates.<Link> typeEquals(VDC)).getHref());
      String vdcUrn = vdc.getId();

      Set<Reference> networks = vdc.getAvailableNetworks();
      Optional<Reference> parentNetwork = Iterables.tryFind(networks, new Predicate<Reference>() {
         @Override
         public boolean apply(Reference reference) {
            return reference.getHref().equals(network.getHref());
         }
      });

      NetworkConfiguration networkConfiguration = NetworkConfiguration.builder().parentNetwork(parentNetwork.get())
              .fenceMode(fenceMode).build();

      NetworkConfigSection networkConfigSection = NetworkConfigSection
              .builder()
              .info(MsgType.builder().value("Configuration parameters for logical networks").build())
              .networkConfigs(
                      ImmutableSet.of(VAppNetworkConfiguration.builder().networkName("vAppNetwork")
                              .configuration(networkConfiguration).build())).build();

      InstantiationParams instantiationParams = InstantiationParams.builder()
              .sections(ImmutableSet.of(networkConfigSection)).build();
/*
      VAppTemplate vAppTemplate;
      Optional<VAppTemplate> optionalvAppTemplate = tryFindVAppTemplateByNameInOrg(vdc, imageName);
      if (!optionalvAppTemplate.isPresent()) {
         throw new IllegalStateException();
      }
      vAppTemplate = optionalvAppTemplate.get();
*/
      InstantiateVAppTemplateParams instantiate = InstantiateVAppTemplateParams.builder().name(name("test-vapp-"))
              .notDeploy().description("Test VApp").instantiationParams(instantiationParams)
              .source(api.getVAppTemplateApi().get(imageId).getHref()).build();

      VApp vApp = api.getVdcApi().instantiateVApp(vdcUrn, instantiate);
      //Task vAppDeployment = api.getVAppApi().deploy(vApp.getHref(), DeployVAppParams.builder().build());

      if (!vApp.getTasks().isEmpty()) {
         for (Task task : vApp.getTasks()) {
            retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
            logger.debug(">> awaiting vApp(%s) deployment", vApp.getId());
            boolean vAppDeployed = retryTaskSuccess.apply(task);
            logger.trace("<< vApp(%s) deployment completed(%s)", vApp.getId(), vAppDeployed);
         }
      }

      Vm vm = Iterables.getOnlyElement(api.getVAppApi().get(vApp.getHref()).getChildren().getVms());

      Task powerOnTask = api.getVmApi().powerOn(vm.getHref());
      retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
      logger.debug(">> awaiting login details for vm(%s)", vm.getId());
      boolean vmStarted = retryTaskSuccess.apply(powerOnTask);
      logger.trace("<< vm(%s) complete(%s)", vm.getId(), vmStarted);

      return new NodeAndInitialCredentials(vm, vm.getId(),
              LoginCredentials.builder().user(loginUser).password(loginUserPassword).build());
   }

   public Optional<VAppTemplate> tryFindVAppTemplateByNameInOrg(Vdc vdc, final String name) {
      FluentIterable<VAppTemplate> vAppTemplates =  FluentIterable.from(vdc.getResourceEntities())
              .filter(ReferencePredicates.typeEquals(VAPP_TEMPLATE))
              .transform(new Function<Reference, VAppTemplate>() {

                 @Override
                 public VAppTemplate apply(Reference in) {
                    return api.getVAppTemplateApi().get(in.getHref());
                 }})
              .filter(Predicates.notNull());

      return tryFind(vAppTemplates, new Predicate<VAppTemplate>() {

         @Override
         public boolean apply(VAppTemplate input) {
            return input.getName().contains(name);
         }

      });
   }

   public static String name(String prefix) {
      return prefix + Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
   }

   public Optional<Network> tryFindNetworkInOrgWithFenceMode(Org org, final Network.FenceMode fenceMode) {
      FluentIterable<Network> networks = FluentIterable.from(org.getLinks())
              .filter(ReferencePredicates.typeEquals(ORG_NETWORK))
              .transform(new Function<Link, Network>() {
                 @Override
                 public Network apply(Link in) {
                    return api.getNetworkApi().get(in.getHref());
                 }
              });

      return tryFind(networks, new Predicate<Network>() {
         @Override
         public boolean apply(Network input) {
            if (input.getTasks().size() != 0) return false;
            return input.getConfiguration().getFenceMode().equals(fenceMode);
         }
      });
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
   public Set<VAppTemplate> listImages() {
      Org org = getOrgForSession();
      Vdc vdc = api.getVdcApi().get(find(org.getLinks(), ReferencePredicates.<Link>typeEquals(VDC)).getHref());
      return FluentIterable.from(vdc.getResourceEntities())
              .filter(ReferencePredicates.typeEquals(VAPP_TEMPLATE))
              .transform(new Function<Reference, VAppTemplate>() {

                 @Override
                 public VAppTemplate apply(Reference in) {
                    return api.getVAppTemplateApi().get(in.getHref());
                 }
              })
              .filter(Predicates.notNull()).toSet();
   }

   private Org getOrgForSession() {
      Session session = api.getCurrentSession();
      return api.getOrgApi().get(find(api.getOrgApi().list(), ReferencePredicates.nameEquals(session.get())).getHref());

   }

   @Override
   public VAppTemplate getImage(final String imageId) {
      return null;
   }

   @Override
   public Iterable<Vm> listNodes() {
      return Sets.newHashSet();
   }

   @Override
   public Iterable<Vm> listNodesByIds(final Iterable<String> ids) {
      return null;
   }

   @Override
   public Iterable<Location> listLocations() {
      return ImmutableSet.of();
   }

   @Override
   public Vm getNode(String id) {
      return api.getVmApi().get(id);
   }

   @Override
   public void destroyNode(String id) {
      if (api.getVmApi().get(id).getStatus() == ResourceEntity.Status.POWERED_ON) {
         URI vAppRef = null;
         for (Link link : api.getVmApi().get(id).getLinks()) {
            if (link.getRel() != null && link.getRel().value().equalsIgnoreCase("up")) {
               vAppRef = link.getHref();
            }
         }
         VApp vApp = api.getVAppApi().get(vAppRef);
         if (!vApp.getTasks().isEmpty()) {
            for (Task task : vApp.getTasks()) {
               retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
               logger.debug(">> awaiting vApp(%s) tasks completion", vApp.getId());
               boolean vAppDeployed = retryTaskSuccess.apply(task);
               logger.trace("<< vApp(%s) tasks completions(%s)", vApp.getId(), vAppDeployed);
            }
         }
         UndeployVAppParams params = UndeployVAppParams.builder()
                 .undeployPowerAction(UndeployVAppParams.PowerAction.POWER_OFF)
                 .build();
         Task undeployTask = api.getVAppApi().undeploy(vAppRef, params);
         retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
         logger.debug(">> awaiting vApp(%s) undeploy completion", vApp.getId());
         boolean vAppUndeployed = retryTaskSuccess.apply(undeployTask);
         logger.trace("<< vApp(%s) undeploy completions(%s)", vApp.getId(), vAppUndeployed);

         Task removeTask = api.getVAppApi().remove(vAppRef);
         retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
         logger.debug(">> awaiting vApp(%s) remove completion", vApp.getId());
         boolean vAppRemoved = retryTaskSuccess.apply(removeTask);
         logger.trace("<< vApp(%s) remove completions(%s)", vApp.getId(), vAppRemoved);
      }
   }

   @Override
   public void rebootNode(String id) {
      api.getVmApi().reboot(id);
   }

   @Override
   public void resumeNode(String id) {
      throw new UnsupportedOperationException("resume not supported");
   }

   @Override
   public void suspendNode(String id) {
      api.getVmApi().suspend(id);
   }

}
