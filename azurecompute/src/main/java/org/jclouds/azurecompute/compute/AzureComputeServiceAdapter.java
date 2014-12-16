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
package org.jclouds.azurecompute.compute;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.jclouds.azurecompute.config.AzureComputeProperties.SUBSCRIPTION_ID;
import static org.jclouds.azurecompute.config.AzureComputeProperties.OPERATION_TIMEOUT;
import static org.jclouds.azurecompute.domain.Deployment.InstanceStatus.READY_ROLE;
import static org.jclouds.util.Predicates2.retry;

import java.net.URI;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.azurecompute.AzureComputeApi;
import org.jclouds.azurecompute.domain.CloudService;
import org.jclouds.azurecompute.domain.Deployment;
import org.jclouds.azurecompute.domain.DeploymentParams;
import org.jclouds.azurecompute.domain.DeploymentParams.ExternalEndpoint;
import org.jclouds.azurecompute.domain.Location;
import org.jclouds.azurecompute.domain.OSImage;
import org.jclouds.azurecompute.domain.Operation;
import org.jclouds.azurecompute.domain.Role;
import org.jclouds.azurecompute.domain.RoleSize;
import org.jclouds.azurecompute.domain.NetworkConfiguration;
import org.jclouds.azurecompute.domain.StorageService;
import org.jclouds.azurecompute.domain.StorageServiceParams;
import org.jclouds.azurecompute.options.AzureComputeTemplateOptions;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.logging.Logger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * defines the connection between the {@link AzureComputeApi} implementation and the
 * jclouds {@link org.jclouds.compute.ComputeService}
 */
@Singleton
public class AzureComputeServiceAdapter implements ComputeServiceAdapter<Deployment, RoleSize, OSImage, Location> {

   private static final String DEFAULT_VIRTUAL_NETWORK_NAME = "jclouds-virtual-network";
   private static final String DEFAULT_ADDRESS_SPACE_ADDRESS_PREFIX = "10.0.0.0/20";
   private static final String DEFAULT_SUBNET_NAME = "jclouds-1";
   private static final String DEFAULT_SUBNET_ADDRESS_PREFIX = "10.0.0.0/23";
   private static final String DEFAULT_STORAGE_SERVICE_TYPE = "Standard_GRS";

   private static final String DEFAULT_LOGIN_USER = "jclouds";
   private static final String DEFAULT_LOGIN_PASSWORD = "Azur3Compute!";

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private final AzureComputeApi api;
   private final String subscriptionId;
   private final long operationTimeout;
   private Predicate<String> operationSucceeded;

   @Inject
   public AzureComputeServiceAdapter(final AzureComputeApi api, @Named(SUBSCRIPTION_ID) String subscriptionId, @Named(OPERATION_TIMEOUT) long operationTimeout) {
      this.api = checkNotNull(api, "api");
      this.subscriptionId = checkNotNull(subscriptionId, "subscriptionId");
      this.operationTimeout = checkNotNull(operationTimeout, "operationTimeout");
      this.operationSucceeded = retry(new Predicate<String>() {
         public boolean apply(String input) {
            return api.getOperationApi().get(input).status() == Operation.Status.SUCCEEDED;
         }
      }, operationTimeout, 5, 5, SECONDS);
   }

   @Override
   public NodeAndInitialCredentials<Deployment> createNodeWithGroupEncodedIntoName(
           final String group, final String name, Template template) {

      // azure-specific options
      AzureComputeTemplateOptions templateOptions = template.getOptions().as(AzureComputeTemplateOptions.class);
      final String virtualNetworkName = templateOptions.getVirtualNetworkName().or(DEFAULT_VIRTUAL_NETWORK_NAME);
      final String addressSpaceAddressPrefix = templateOptions.getAddressSpaceAddressPrefix().or(DEFAULT_ADDRESS_SPACE_ADDRESS_PREFIX);
      final String subnetName = templateOptions.getSubnetName().or(DEFAULT_SUBNET_NAME);
      final String subnetAddressPrefix = templateOptions.getSubnetAddressPrefix().or(DEFAULT_SUBNET_ADDRESS_PREFIX);
      final String storageAccountType = templateOptions.getStorageAccountType().or(DEFAULT_STORAGE_SERVICE_TYPE);

      final String loginUser = templateOptions.getLoginUser() != null ? templateOptions.getLoginUser() : DEFAULT_LOGIN_USER;
      final String loginPassword = templateOptions.getLoginPassword() != null ? templateOptions.getLoginPassword() : DEFAULT_LOGIN_PASSWORD;
      final String location = template.getLocation().getId();
      final int[] inboundPorts = template.getOptions().getInboundPorts();

      final String storageAccountName = templateOptions.getStorageAccountName().orNull();
      String storageServiceName;
      Optional<StorageService> storageServiceOptional = tryFindDefaultStorageServiceOrSpecified(location, storageAccountName);
      if (!storageServiceOptional.isPresent()) {
         logger.debug("There are not available storage services");
         String newStorageAccountName = tryCheckAvailableGenerateServiceNameOrSpecified(storageAccountType, storageAccountName);
         String createStorateServiceRequestId = api.getStorageAccountApi().create(StorageServiceParams.builder()
                 .name(newStorageAccountName)
                 .label(newStorageAccountName)
                 .location(location)
                 .accountType(StorageServiceParams.Type.valueOf(storageAccountType))
                 .build());
         if (!operationSucceeded.apply(createStorateServiceRequestId)) {
            logger.warn("StorageServiceAccount(%s) has not completed within %sms", newStorageAccountName, Long.toString(operationTimeout));
            throw new IllegalStateException(format("Network configuration has not completed within %sms. " +
                    "Please, try by increasing `jclouds.azure.operation-timeout` and try again", newStorageAccountName, Long.toString(operationTimeout)));
         }
         logger.info("Create StorageService operation (%s) succeeded", createStorateServiceRequestId);
         storageServiceName = newStorageAccountName;
      } else {
         storageServiceName = storageServiceOptional.get().serviceName();
      }

      NetworkConfiguration networkConfiguration = api.getVirtualNetworkApi().get();
      if (networkConfiguration == null || networkConfiguration.virtualNetworkSites().isEmpty() ||
              FluentIterable.from(networkConfiguration.virtualNetworkSites())
                      .filter(new Predicate<NetworkConfiguration.VirtualNetworkSite>() {
                         @Override
                         public boolean apply(NetworkConfiguration.VirtualNetworkSite input) {
                            return input.name().equals(virtualNetworkName);
                         }
                      }).isEmpty()) {
         String setNetworkConfigurationRequestId = api.getVirtualNetworkApi().set(NetworkConfiguration.create(null,
                 ImmutableList.of(NetworkConfiguration.VirtualNetworkSite.create(
                         virtualNetworkName,
                         location,
                         NetworkConfiguration.AddressSpace.create(addressSpaceAddressPrefix),
                         ImmutableList.of(NetworkConfiguration.Subnet.create(subnetName, subnetAddressPrefix))))));
         if (!operationSucceeded.apply(setNetworkConfigurationRequestId)) {
            logger.warn("Network configuration has not completed within %sms", Long.toString(operationTimeout));
            throw new IllegalStateException(format("Network configuration has not completed within %sms. " +
                    "Please, try by increasing `jclouds.azure.operation-timeout` and try again", name, Long.toString(operationTimeout)));
         }
         logger.info("Set NetworkConfiguration operation (%s) succeeded", setNetworkConfigurationRequestId);
      }

      String createCloudServiceRequestId = api.getCloudServiceApi().createWithLabelInLocation(name, name, location);
      if (!operationSucceeded.apply(createCloudServiceRequestId)) {
         logger.warn("Cloud Service (%s) has not been created within %sms so it will be destroyed.", name, Long
                 .toString(operationTimeout));
         api.getCloudServiceApi().delete(name);
         throw new IllegalStateException(format("Cloud Service(%s) is being destroyed as it doesn't have login details" +
                 " after %sms. Please, try by increasing `jclouds.azure.operation-timeout` and " +
                 " try again", name, Long.toString(operationTimeout)));
      }
      logger.info("createCloudService operation (%s) succeeded", createCloudServiceRequestId);

      final OSImage.Type os = template.getImage().getOperatingSystem().getFamily().equals(OsFamily.WINDOWS) ?
              OSImage.Type.WINDOWS : OSImage.Type.LINUX;
      Set<ExternalEndpoint> externalEndpoints = Sets.newHashSet();
      for (int inboundPort : inboundPorts) {
         externalEndpoints.add(ExternalEndpoint.inboundTcpToLocalPort(inboundPort, inboundPort));
      }
      String createDeploymentRequestId = api.getDeploymentApiForService(name).create(
              DeploymentParams.builder()
                      .name(name)
                      .os(os)
                      .username(loginUser)
                      .password(loginPassword)
                      .sourceImageName(template.getImage().getId())
                      .mediaLink(createMediaLink(storageServiceName, name))
                      .size(RoleSize.Type.fromString(template.getHardware().getName()))
                      .externalEndpoints(externalEndpoints)
                      .virtualNetworkName(virtualNetworkName)
                      .build());
      if (!operationSucceeded.apply(createDeploymentRequestId)) {
         logger.warn("Deployment (%s) has not been created within %sms so it will be destroyed.", name, Long
                 .toString(operationTimeout));
         api.getDeploymentApiForService(group).delete(name);
         throw new IllegalStateException(format("Deployment (%s) is being destroyed as it doesn't have login details" +
                 " after %sms. Please, try by increasing `jclouds.azure.operation-timeout` and " +
                 " try again", name, Long.toString(operationTimeout)));
      }

      if (!retry(new Predicate<String>() {
         public boolean apply(String name) {
            return FluentIterable.from(api.getDeploymentApiForService(name).get(name).roleInstanceList())
                    .allMatch(new Predicate<Deployment.RoleInstance>() {
                       @Override
                       public boolean apply(Deployment.RoleInstance input) {
                          return input.instanceStatus() == READY_ROLE;
                       }
                    });
         }
      }, 30 * 60, 1, SECONDS).apply(name)) {
         logger.warn("Instances %s of %s has not reached the status %s within %sms so it will be destroyed.",
                 Iterables.toString(api.getDeploymentApiForService(name).get(name).roleInstanceList()), name,
                 READY_ROLE, Long.toString(operationTimeout));
         api.getDeploymentApiForService(group).delete(name);
         api.getCloudServiceApi().delete(name);
         throw new IllegalStateException(format("Deployment %s is being destroyed as its instanceStatus didn't reach " +
                 "status %s after %ss. Please, try by increasing `jclouds.azure.operation-timeout` and " +
                 " try again", name, READY_ROLE, 30 * 60));
      }

      Deployment deployment = api.getDeploymentApiForService(name).get(name);
      return new NodeAndInitialCredentials(deployment, deployment.name(),
              LoginCredentials.builder().user(loginUser).password(loginPassword).build());
   }

   private String tryCheckAvailableGenerateServiceNameOrSpecified(String storageAccountType, String storageAccountName) {
      String newStorageAccountName;
      if (storageAccountName == null) {
         newStorageAccountName = generateStorageServiceName();
         logger.debug("Trying to create one with generated name %s and type %s ... ", newStorageAccountName,
                 storageAccountType);
      } else {
         newStorageAccountName = storageAccountName;
         logger.debug("Trying to create one with desired name %s and type %s ...", newStorageAccountName,
                 storageAccountType);
      }
      if (!api.getStorageAccountApi().checkAvailable(subscriptionId, newStorageAccountName).result()) {
         logger.warn("The new storage account name %s is not available", newStorageAccountName);
         throw new IllegalStateException(format("Can't create a valid storage account with name %s. " +
                 "Please, try by choosing a different `storageAccountName` in templateOptions and try again", newStorageAccountName));
      }
      return newStorageAccountName;
   }

   private Optional<StorageService> tryFindDefaultStorageServiceOrSpecified(final String location, final String storageAccountName) {
      return Iterables.tryFind(api.getStorageAccountApi().list(subscriptionId), new Predicate<StorageService>() {
         @Override
         public boolean apply(StorageService input) {
            boolean isSameLocationAndCreated = input.storageServiceProperties().location().equals(location) &&
                    input.storageServiceProperties().status().equals("Created");
            if (storageAccountName != null) {
               return isSameLocationAndCreated && input.serviceName().equals(storageAccountName);
            } else {
               return isSameLocationAndCreated;
            }
         }
      });
   }

   private String generateStorageServiceName() {
      String characters = "abcdefghijklmnopqrstuvwxyz";
      StringBuilder builder = new StringBuilder();
      builder.append("jclouds");
      int charactersLength = characters.length();
      for (int i = 0; i < 10; i++) {
         double index = Math.random() * charactersLength;
         builder.append(characters.charAt((int) index));
      }
      return builder.toString();
   }

   private URI createMediaLink(String storageServiceName, String diskName) {
      return URI.create(String.format("https://%s.blob.core.windows.net/vhds/disk-%s.vhd", storageServiceName, diskName));
   }

   @Override
   public Iterable<RoleSize> listHardwareProfiles() {
      return api.getSubscriptionApi().list(subscriptionId);
   }

   @Override
   public Iterable<OSImage> listImages() {
      return api.getOSImageApi().list();
   }

   @Override
   public OSImage getImage(String id) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public Iterable<Location> listLocations() {
      return api.getLocationApi().list();
   }

   @Override
   public Deployment getNode(final String id) {
      return FluentIterable.from(api.getCloudServiceApi().list())
              .transform(new Function<CloudService, Deployment>() {
                 @Override
                 public Deployment apply(CloudService input) {
                    return api.getDeploymentApiForService(input.name()).get(id);
                 }
              })
              .firstMatch(Predicates.notNull())
              .orNull();
   }

   @Override
   public void destroyNode(final String id) {
      for (CloudService cloudService : api.getCloudServiceApi().list()) {
         final Deployment deployment = api.getDeploymentApiForService(cloudService.name()).get(id);
         if (deployment != null) {
            String deleteDeploymentRequestId = api.getDeploymentApiForService(cloudService.name()).delete(id);
            if (!operationSucceeded.apply(deleteDeploymentRequestId)) {
               logger.warn("Deployment(%s) of cloudService(%s) has not been deleted within %sms.", id, cloudService
                               .name(),
                       Long.toString(operationTimeout));
               throw new IllegalStateException(format("Deployment (%s) of cloudService(%s) has not being destroyed after %sms." +
                       " Please, try by increasing `jclouds.azure.operation-timeout` and try again",
                       id, cloudService, Long.toString(operationTimeout)));
            }
            logger.info("Delete deployment operation (%s) succeeded", deleteDeploymentRequestId);

            for (Role role : deployment.roles()) {
               if (role.oSVirtualHardDisk() != null) {
                  String deleteDiskRequestID = api.getDiskApi().delete(role.oSVirtualHardDisk().diskName());
                  if (!operationSucceeded.apply(deleteDiskRequestID)) {
                     logger.warn("Cannot delete disk (%s) not been deleted within %sms.",
                             role.oSVirtualHardDisk().diskName(), Long.toString(operationTimeout));
                     throw new IllegalStateException(format("Disk (%s) has not being deleted after %sms." +
                                     " Please, try by increasing `jclouds.azure.operation-timeout` and try again",
                             id, cloudService, Long.toString(operationTimeout)));
                  }
                  logger.info("Delete disk operation (%s) succeeded", deleteDiskRequestID);
               }
            }
            String cloudServiceDeleteRequestId = api.getCloudServiceApi().delete(cloudService.name());
            if (!operationSucceeded.apply(cloudServiceDeleteRequestId)) {
               logger.warn("CloudService(%s) has not been deleted within %sms.", cloudService.name(),
                       Long.toString(operationTimeout));
               throw new IllegalStateException(format("CloudService(%s) has not being destroyed after %sms." +
                               " Please, try by increasing `jclouds.azure.operation-timeout` and try again",
                       cloudService, Long.toString(operationTimeout)));
            }
            logger.info("Delete cloud service operation (%s) succeeded", deleteDeploymentRequestId);
            break;
         }
      }
   }

   @Override
   public void rebootNode(String id) {
      // TODO Auto-generated method stub
   }

   @Override
   public void resumeNode(String id) {
      // TODO Auto-generated method stub
   }

   @Override
   public void suspendNode(String id) {
      // TODO Auto-generated method stub

   }

   @Override
   public Iterable<Deployment> listNodes() {
      Set<Deployment> deployments = FluentIterable.from(api.getCloudServiceApi().list())
              .transform(new Function<CloudService, Deployment>() {
                 @Override
                 public Deployment apply(CloudService cloudService) {
                    return api.getDeploymentApiForService(cloudService.name()).get(cloudService.name());
                 }
              })
              .filter(Predicates.notNull())
              .toSet();
      return deployments;
   }

   @Override public Iterable<Deployment> listNodesByIds(Iterable<String> ids) {
      // TODO Auto-generated method stub
      return null;
   }
}
