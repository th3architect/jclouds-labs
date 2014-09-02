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
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType.VAPP;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType.VAPP_TEMPLATE;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType.VDC;
import java.net.URI;
import java.util.List;
import java.util.Map;
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
import org.jclouds.vcloud.director.v1_5.compute.util.VCloudDirectorComputeUtils;
import org.jclouds.vcloud.director.v1_5.domain.Link;
import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.jclouds.vcloud.director.v1_5.domain.ResourceEntity;
import org.jclouds.vcloud.director.v1_5.domain.Session;
import org.jclouds.vcloud.director.v1_5.domain.Task;
import org.jclouds.vcloud.director.v1_5.domain.VApp;
import org.jclouds.vcloud.director.v1_5.domain.VAppTemplate;
import org.jclouds.vcloud.director.v1_5.domain.Vdc;
import org.jclouds.vcloud.director.v1_5.domain.Vm;
import org.jclouds.vcloud.director.v1_5.domain.network.FirewallRule;
import org.jclouds.vcloud.director.v1_5.domain.network.FirewallRuleProtocols;
import org.jclouds.vcloud.director.v1_5.domain.network.FirewallService;
import org.jclouds.vcloud.director.v1_5.domain.network.IpRange;
import org.jclouds.vcloud.director.v1_5.domain.network.IpRanges;
import org.jclouds.vcloud.director.v1_5.domain.network.IpScope;
import org.jclouds.vcloud.director.v1_5.domain.network.IpScopes;
import org.jclouds.vcloud.director.v1_5.domain.network.NatService;
import org.jclouds.vcloud.director.v1_5.domain.network.Network;
import org.jclouds.vcloud.director.v1_5.domain.network.NetworkAssignment;
import org.jclouds.vcloud.director.v1_5.domain.network.NetworkConfiguration;
import org.jclouds.vcloud.director.v1_5.domain.network.NetworkConnection;
import org.jclouds.vcloud.director.v1_5.domain.network.NetworkFeatures;
import org.jclouds.vcloud.director.v1_5.domain.network.NetworkServiceType;
import org.jclouds.vcloud.director.v1_5.domain.network.RouterInfo;
import org.jclouds.vcloud.director.v1_5.domain.network.VAppNetworkConfiguration;
import org.jclouds.vcloud.director.v1_5.domain.org.Org;
import org.jclouds.vcloud.director.v1_5.domain.params.ComposeVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.params.DeployVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.params.InstantiateVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.params.InstantiateVAppTemplateParams;
import org.jclouds.vcloud.director.v1_5.domain.params.InstantiationParams;
import org.jclouds.vcloud.director.v1_5.domain.params.RecomposeVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.params.SourcedCompositionItemParam;
import org.jclouds.vcloud.director.v1_5.domain.params.UndeployVAppParams;
import org.jclouds.vcloud.director.v1_5.domain.section.GuestCustomizationSection;
import org.jclouds.vcloud.director.v1_5.domain.section.NetworkConfigSection;
import org.jclouds.vcloud.director.v1_5.domain.section.NetworkConnectionSection;
import org.jclouds.vcloud.director.v1_5.predicates.ReferencePredicates;
import org.jclouds.vcloud.director.v1_5.predicates.TaskSuccess;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * defines the connection between the {@link VCloudDirectorApi} implementation and
 * the jclouds {@link org.jclouds.compute.ComputeService}
 */
@Singleton
public class VCloudDirectorComputeServiceAdapter implements
        ComputeServiceAdapter<Vm, Hardware, VAppTemplate, Location> {

   protected static final long TASK_TIMEOUT_SECONDS = 300L;
   private static final String HTTP_SECURITY_GROUP = "http";
   private static final String DEFAULT_SECURITY_GROUP = "default";

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

      // workflow
      /*
         1. tryFindVdcByName(InOrg)
         2. tryFindVAppTemplateByName(InOrg)
         2. build the InstantiateVAppParams using:
            2.1 NetworkConfiguration
            2.2 vAppTemplateURI
       */

      Session session = api.getCurrentSession();
      final Org org = api.getOrgApi().get(find(api.getOrgApi().list(), ReferencePredicates.nameEquals(session.get()))
              .getHref());
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

      /*
      ImmutableList<Reference> vAppReferences =  FluentIterable.from(vdc.getResourceEntities())
              .filter(ReferencePredicates.typeEquals(VAPP))
              .toList();

      for (Reference vAppReference : vAppReferences) {
         api.getVAppApi().get(vAppReference.getHref());
      }
      */

      Set<Reference> networks = vdc.getAvailableNetworks();
      Optional<Reference> parentNetwork = Iterables.tryFind(networks, new Predicate<Reference>() {
         @Override
         public boolean apply(Reference reference) {
            return reference.getHref().equals(network.getHref());
         }
      });

      NetworkConfiguration networkConfiguration = NetworkConfiguration.builder()
              .parentNetwork(parentNetwork.get())
              .fenceMode(Network.FenceMode.BRIDGED) // simplest solution
              .build();

      NetworkConfigSection networkConfigSection = NetworkConfigSection
              .builder()
              .info(MsgType.builder().value("Configuration parameters for logical networks").build())
              .networkConfigs(
                      ImmutableSet.of(VAppNetworkConfiguration.builder()
                              .networkName("vAppNetwork")
                              .configuration(networkConfiguration)
                              .build()))
              .build();

      VAppTemplate vAppTemplate = api.getVAppTemplateApi().get(imageId);
      Set<Vm> vms = getAvailableVMsFromVAppTemplate(vAppTemplate);
      // get the first vm to be added to vApp
      Vm toAddVm = Iterables.get(vms, 0);

      String networkName = "M523007043-2739-default-routed"; //name("vAppNetwork-");
      SourcedCompositionItemParam vmItem = createVmItem(toAddVm, networkName);
      Set<String> securityGroups = ImmutableSet.of(DEFAULT_SECURITY_GROUP); // template.getOptions().getGroups()
      ComposeVAppParams compositionParams = ComposeVAppParams.builder()
              .name(name("composed-"))
              .instantiationParams(instantiationParams(org, vdc, networkName, securityGroups))
              .sourcedItems(ImmutableList.of(vmItem))
              .deploy()
              .powerOn()
              .build();
      VApp vApp = api.getVdcApi().composeVApp(vdcUrn, compositionParams);
      Task compositionTask = Iterables.getFirst(vApp.getTasks(), null);
      retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
      logger.debug(">> awaiting vApp(%s) deployment", vApp.getId());
      boolean vAppDeployed = retryTaskSuccess.apply(compositionTask);
      logger.trace("<< vApp(%s) deployment completed(%s)", vApp.getId(), vAppDeployed);

      if (!vApp.getTasks().isEmpty()) {
         for (Task task : vApp.getTasks()) {
            retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
            logger.debug(">> awaiting vApp(%s) deployment", vApp.getId());
            boolean vmReady = retryTaskSuccess.apply(task);
            logger.trace("<< vApp(%s) deployment completed(%s)", vApp.getId(), vmReady);
         }
      }
/*
      InstantiationParams instantiationParams = InstantiationParams.builder()
              .sections(ImmutableSet.of(networkConfigSection))
              .build();

      InstantiateVAppTemplateParams instantiate = InstantiateVAppTemplateParams.builder()
              .name(name)
              .notDeploy()
              .notPowerOn()
              .description("jclouds vapp")
              .instantiationParams(instantiationParams)
              .source(vAppTemplate.getHref())
              .build();

      VApp vApp = api.getVdcApi().instantiateVApp(vdcUrn, instantiate);

      if (!vApp.getTasks().isEmpty()) {
         for (Task task : vApp.getTasks()) {
            retryTaskSuccess = retry(new TaskSuccess(api.getTaskApi()), TASK_TIMEOUT_SECONDS * 1000L);
            logger.debug(">> awaiting vApp(%s) deployment", vApp.getId());
            boolean vAppDeployed = retryTaskSuccess.apply(task);
            logger.trace("<< vApp(%s) deployment completed(%s)", vApp.getId(), vAppDeployed);
         }
      }
      */

      Vm vm = Iterables.getOnlyElement(api.getVAppApi().get(vApp.getHref()).getChildren().getVms());
/*
      GuestCustomizationSection customizationSection = api.getVmApi().getGuestCustomizationSection(vm.getHref());
      GuestCustomizationSection newSection = customizationSection.toBuilder()
              .resetPasswordRequired(false)
              .build();
      Task editGuestCustomizationSection = api.getVmApi().editGuestCustomizationSection(vm.getHref(), newSection);
      logger.debug(">> awaiting vm(%s) guest customization reset", vm.getId());
      boolean finished = retryTaskSuccess.apply(editGuestCustomizationSection);
      logger.trace("<< vm(%s) reset completed(%s)", vm.getId(), finished);

      Task powerOnTask = api.getVAppApi().powerOn(vApp.getHref());
      logger.debug(">> awaiting vApp(%s) guest customization reset", vApp.getId());
      boolean poweredOn = retryTaskSuccess.apply(powerOnTask);
      logger.trace("<< vApp(%s) reset completed(%s)", vApp.getId(), poweredOn);
*/
      LoginCredentials loginCredentials = VCloudDirectorComputeUtils.getCredentialsFrom(vm);
      return new NodeAndInitialCredentials(vm, vm.getId(), loginCredentials.toBuilder().user("root").build());
   }

   private SourcedCompositionItemParam createVmItem(Vm vm, String networkName) {
      // creating an item element. this item will contain the vm which should be added to the vapp.
      Reference reference = Reference.builder().name(name("vm-")).href(vm.getHref()).type(vm.getType()).build();
      SourcedCompositionItemParam vmItem = SourcedCompositionItemParam.builder().source(reference).build();

      InstantiationParams vmInstantiationParams = null;
      Set<NetworkAssignment> networkAssignments = Sets.newLinkedHashSet();

      // add a new network connection section for the vm.
      /*
      NetworkConnectionSection networkConnectionSection = NetworkConnectionSection.builder()
              .info(MsgType.builder()
                      .value("Empty network configuration parameters")
                      .build())
              .build();
      */

      NetworkConnection networkConnection = NetworkConnection.builder()
              .network(networkName)
              .ipAddressAllocationMode(NetworkConnection.IpAddressAllocationMode.POOL)
              .isConnected(true)
              .build();

      NetworkConnectionSection networkConnectionSection = NetworkConnectionSection.builder()
              .info(MsgType.builder().value("networkInfo").build())
              .primaryNetworkConnectionIndex(0).networkConnection(networkConnection).build();

      // adding the network connection section to the instantiation params of the vapp.
      GuestCustomizationSection customizationSection = api.getVmApi().getGuestCustomizationSection(vm.getHref());
      GuestCustomizationSection guestCustomizationSection = customizationSection.toBuilder()
              .adminPasswordAuto(true)
              .resetPasswordRequired(false)
              .build();

      vmInstantiationParams = InstantiationParams.builder()
              .sections(ImmutableSet.of(networkConnectionSection, guestCustomizationSection))
              .build();
/*
      // if the vm contains a network connection and the vApp does not contain any configured
      // network
      if (vmHasNetworkConnectionConfigured(vm)) {
         //if (!vAppHasNetworkConfigured(vApp)) {
            // add a new network connection section for the vm.
            NetworkConnectionSection networkConnectionSection = NetworkConnectionSection.builder()
                    .info(MsgType.builder().value("Empty network configuration parameters").build())
                    .build();
            // adding the network connection section to the instantiation params of the vapp.
            vmInstantiationParams = InstantiationParams.builder().sections(ImmutableSet.of(networkConnectionSection))
                    .build();
         //}

         // if the vm already contains a network connection section and if the vapp contains a
         // configured network -> vm could be mapped to that network.
         else {
            Set<VAppNetworkConfiguration> vAppNetworkConfigurations = listVappNetworkConfigurations(vApp);
            for (VAppNetworkConfiguration vAppNetworkConfiguration : vAppNetworkConfigurations) {
               NetworkAssignment networkAssignment = NetworkAssignment.builder()
                       .innerNetwork(vAppNetworkConfiguration.getNetworkName())
                       .containerNetwork(vAppNetworkConfiguration.getNetworkName()).build();
               networkAssignments.add(networkAssignment);
            }
         }
      }

      // if the vm does not contain any network connection sections and if the
      // vapp contains a network configuration. we should add the vm to this
      // vapp network
      else {
         if (vAppHasNetworkConfigured(vApp)) {
            VAppNetworkConfiguration vAppNetworkConfiguration = getVAppNetworkConfig(vApp);
            NetworkConnection networkConnection = NetworkConnection.builder()
                    .network(vAppNetworkConfiguration.getNetworkName())
                    .ipAddressAllocationMode(NetworkConnection.IpAddressAllocationMode.DHCP).build();

            NetworkConnectionSection networkConnectionSection = NetworkConnectionSection.builder()
                    .info(MsgType.builder().value("networkInfo").build())
                    .primaryNetworkConnectionIndex(0).networkConnection(networkConnection).build();

            // adding the network connection section to the instantiation params of the vapp.
            vmInstantiationParams = InstantiationParams.builder().sections(ImmutableSet.of(networkConnectionSection))
                    .build();
         }
      }
      */

      if (vmInstantiationParams != null)
         vmItem = SourcedCompositionItemParam.builder().fromSourcedCompositionItemParam(vmItem)
                 .instantiationParams(vmInstantiationParams).build();

      if (networkAssignments != null)
         vmItem = SourcedCompositionItemParam.builder().fromSourcedCompositionItemParam(vmItem)
                 .networkAssignment(networkAssignments).build();
      return vmItem;
   }

   protected Set<VAppNetworkConfiguration> listVappNetworkConfigurations(VApp vApp) {
      Set<VAppNetworkConfiguration> vAppNetworkConfigs = api.getVAppApi().getNetworkConfigSection(vApp.getId())
              .getNetworkConfigs();
      return vAppNetworkConfigs;
   }

   protected boolean vAppHasNetworkConfigured(VApp vApp) {
      return getVAppNetworkConfig(vApp) != null;
   }

   protected VAppNetworkConfiguration getVAppNetworkConfig(VApp vApp) {
      Set<VAppNetworkConfiguration> vAppNetworkConfigs = api.getVAppApi().getNetworkConfigSection(vApp.getId())
              .getNetworkConfigs();
      return Iterables.tryFind(vAppNetworkConfigs, Predicates.notNull()).orNull();
   }

   boolean vmHasNetworkConnectionConfigured(Vm vm) {
      return listNetworkConnections(vm).size() > 0;
   }

   protected Set<NetworkConnection> listNetworkConnections(Vm vm) {
      return api.getVmApi().getNetworkConnectionSection(vm.getId()).getNetworkConnections();
   }

   protected InstantiationParams instantiationParams(Org org, Vdc vdc, String networkName, Set<String> securityGroups) {
      NetworkConfiguration networkConfiguration = networkConfiguration(org, vdc, securityGroups);

      InstantiationParams instantiationParams = InstantiationParams.builder()
              .sections(ImmutableSet.of(networkConfigSection(networkName, networkConfiguration)))
              .build();

      return instantiationParams;
   }

   /** Build a {@link NetworkConfigSection} object. */
   private NetworkConfigSection networkConfigSection(String networkName, NetworkConfiguration networkConfiguration) {
      NetworkConfigSection networkConfigSection = NetworkConfigSection
              .builder()
              .info(MsgType.builder().value("Configuration parameters for logical networks").build())
              .networkConfigs(
                      ImmutableSet.of(VAppNetworkConfiguration.builder()
                              .networkName(networkName)
                              .configuration(networkConfiguration)
                              .build()))
              .build();

      return networkConfigSection;
   }

   private NetworkConfiguration networkConfiguration(Org org, Vdc vdc, Set<String> securityGroups) {
      // Create a vAppNetwork with firewall rules
      Network.FenceMode fenceMode = Network.FenceMode.NAT_ROUTED;
      final Optional<Network> optionalNetwork = tryFindNetworkInOrgWithFenceMode(org, fenceMode);
      if (!optionalNetwork.isPresent()) {
         throw new IllegalStateException();
      }
      Set<Reference> networks = vdc.getAvailableNetworks();
      Optional<Reference> parentNetwork = Iterables.tryFind(networks, new Predicate<Reference>() {
         @Override
         public boolean apply(Reference reference) {
            return reference.getHref().equals(optionalNetwork.get().getHref());
         }
      });

      Map<String, NetworkConfiguration> securityGroupToNetworkConfig = addSecurityGroupToNetworkConfiguration(parentNetwork.get());

      Set<FirewallRule> firewallRules = Sets.newLinkedHashSet();
      for (String securityGroup : securityGroups) {
         Set<FirewallRule> securityGroupFirewallRules = retrieveAllFirewallRules(securityGroupToNetworkConfig.get(securityGroup).getNetworkFeatures());
         firewallRules.addAll(securityGroupFirewallRules);
      }
      FirewallService firewallService = addFirewallService(firewallRules);

      return NetworkConfiguration.builder()
              .parentNetwork(parentNetwork.get())
              //.ipScopes(IpScopes.builder().ipScope(addNewIpScope()).build())
              .fenceMode(Network.FenceMode.BRIDGED)
              .retainNetInfoAcrossDeployments(false)
              //.features(toNetworkFeatures(ImmutableSet.of(firewallService)))//, natService)))
              .build();
   }

   private IpScope addNewIpScope() {
      IpRange newIpRange = addIpRange();
      IpRanges newIpRanges = IpRanges.builder()
              .ipRange(newIpRange)
              .build();
      return IpScope.builder()
              .isInherited(false)
              .gateway("192.168.2.1")
              .netmask("255.255.0.0")
              .ipRanges(newIpRanges).build();
   }

   private IpRange addIpRange() {
      IpRange newIpRange = IpRange.builder()
              .startAddress("192.168.2.100")
              .endAddress("192.168.2.199")
              .build();
      return newIpRange;
   }

   private NetworkFeatures toNetworkFeatures(Set<? extends NetworkServiceType<?>> networkServices) {
      NetworkFeatures networkFeatures = NetworkFeatures.builder()
              .services(networkServices)
              .build();
      return networkFeatures;
   }

   private FirewallService addFirewallService(Set<FirewallRule> firewallRules) {
      FirewallService firewallService = FirewallService.builder()
              .enabled(true)
              .defaultAction("drop")
              .logDefaultAction(false)
              .firewallRules(firewallRules)
              .build();
      return firewallService;
   }

   private Map<String, NetworkConfiguration> addSecurityGroupToNetworkConfiguration(Reference parentNetworkRef) {
      Set<FirewallRule> defaultFirewallRules = defaultFirewallRules();
      Set<FirewallRule> httpFirewallRules = httpIngressFirewallRules();

      Map<String, NetworkConfiguration> securityGroupToNetworkConfigurations = Maps.newHashMap();
      securityGroupToNetworkConfigurations.put(DEFAULT_SECURITY_GROUP, addNetworkConfiguration(parentNetworkRef, defaultFirewallRules));
      securityGroupToNetworkConfigurations.put(HTTP_SECURITY_GROUP, addNetworkConfiguration(parentNetworkRef, httpFirewallRules));

      return securityGroupToNetworkConfigurations;
   }

   private NetworkConfiguration addNetworkConfiguration(Reference parentNetworkRef, Set<FirewallRule> newFirewallRules) {
      FirewallService firewallService = addFirewallService(newFirewallRules);

      NetworkConfiguration newConfiguration = NetworkConfiguration.builder()
              //TODO .ipScope(ipScope)
              .parentNetwork(parentNetworkRef)
              .fenceMode(Network.FenceMode.NAT_ROUTED)
              .retainNetInfoAcrossDeployments(false)
              .features(toNetworkFeatures(ImmutableSet.of(firewallService)))
              .build();
      return newConfiguration;
   }

   private Set<FirewallRule> defaultFirewallRules() {
      FirewallRuleProtocols protocols = FirewallRuleProtocols.builder()
              .any(true)
              .build();
      FirewallRule sshIngoing = addFirewallRule(FirewallRuleProtocols.builder().tcp(true).build(), "allow ssh ingoing traffic", -1, 22, "in");
      FirewallRule egressAll = addFirewallRule(protocols, "allow all outgoing traffic", -1, -1, "out");
      FirewallRule ingressInternal = addFirewallRule(protocols, "allow all incoming traffic", -1,  -1, "Internal",
              "Any", "in");

      return ImmutableSet.of(egressAll, sshIngoing, ingressInternal);
   }

   private Set<FirewallRule> httpIngressFirewallRules() {
      FirewallRuleProtocols protocols = FirewallRuleProtocols.builder().tcp(true).build();
      FirewallRule httpIngoing = addFirewallRule(protocols, "allow http ingoing traffic", 80, 80, "in");
      FirewallRule httpsIngoing = addFirewallRule(protocols , "allow https ingoing traffic", 443, 443, "in");
      return ImmutableSet.of(httpIngoing, httpsIngoing);
   }

   private FirewallRule addFirewallRule(FirewallRuleProtocols protocols, String description, int sourcePort,
                                        int outPort, String direction) {
      return  addFirewallRule(protocols, description, sourcePort, outPort, "Any", "Any", direction);
   }

   private FirewallRule addFirewallRule(FirewallRuleProtocols protocols, String description, int sourcePort,
                                        int outPort, String sourceIp, String destinationIp, String direction) {
      return FirewallRule.builder()
              .isEnabled(true)
              .description(description)
              .policy("allow")
              .protocols(protocols)
              .port(outPort)
              .destinationIp(destinationIp)
              .sourcePort(sourcePort)
              .sourceIp(sourceIp)
              .direction(direction)
              .enableLogging(false)
              .build();
   }

   private Set<FirewallRule> retrieveAllFirewallRules(NetworkFeatures networkFeatures) {
      Set<FirewallRule> firewallRules = Sets.newLinkedHashSet();
      for (NetworkServiceType<?> networkServiceType : networkFeatures.getNetworkServices()) {
         if (networkServiceType instanceof FirewallService) {
            firewallRules.addAll(((FirewallService) networkServiceType).getFirewallRules());
         }
      }
      return firewallRules;
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

   private Set<Vm> getAvailableVMsFromVAppTemplate(VAppTemplate vAppTemplate) {
      return ImmutableSet.copyOf(Iterables.filter(vAppTemplate.getChildren(), new Predicate<Vm>() {
         // filter out vms in the vApp template with computer name that contains underscores, dots,
         // or both.
         @Override
         public boolean apply(Vm input) {
            GuestCustomizationSection guestCustomizationSection = api.getVmApi().getGuestCustomizationSection(input.getId());
            String computerName = guestCustomizationSection.getComputerName();
            /*
            String retainComputerName = CharMatcher.inRange('0', '9').or(CharMatcher.inRange('a', 'z'))
                    .or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.is('-')).retainFrom(computerName);
            */
            return computerName.equals(computerName);
         }
      }));
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
