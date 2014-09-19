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
package org.jclouds.xstream.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;
import org.jclouds.javax.annotation.Nullable;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class Config {

   @SerializedName("VirtualMachineID")
   private final String virtualMachineID;
   @SerializedName("AllowedGeographicalLocations")
   private final List<Object> allowedGeographicalLocations;
   @SerializedName("BootTime")
   private final String bootTime;
   @SerializedName("BootToBios")
   private final boolean bootToBios;
   @SerializedName("Cdroms")
   private final List<Cdrom> cdroms;
   @SerializedName("Consumption")
   private final Consumption consumption;
   @SerializedName("Comment")
   private final String comment;
   @SerializedName("CpuAllocatedMHz")
   private final int cpuAllocatedMHz;
   @SerializedName("CpuLimitMHz")
   private final int cpuLimitMHz;
   @SerializedName("CpuReservationMHz")
   private final int cpuReservationMHz;
   @SerializedName("CpuShares")
   private final int cpuShares;
   @SerializedName("CpuUsedAvgMHz")
   private final int cpuUsedAvgMHz;
   @SerializedName("CpuUsedPc")
   private final int cpuUsedPc;
   @SerializedName("ConsoleConnectionCount")
   private final int consoleConnectionCount;
   @SerializedName("CurrentSnapshotID")
   private final String currentSnapshotId;
   @SerializedName("CustomizationSpecification")
   private final CustomizationSpecification customizationSpecification;
   @SerializedName("Description")
   private final String description;
   @SerializedName("Designation")
   private final String designation;
   @SerializedName("Disks")
   private final List<Disk> disks;
   @SerializedName("DnsName")
   private final String dnsName;
   @SerializedName("IsDrsEnabled")
   private final String isDrsEnabled;
   @SerializedName("Group")
   private final String group;
   @SerializedName("Guest")
   private final Guest guest;
   @SerializedName("HostName")
   private final String hostName;
   @SerializedName("IsBackupScheduled")
   private final boolean isBackupScheduled;
   @SerializedName("IsMonitored")
   private final boolean isMonitored;
   @SerializedName("IopsAllocatedK")
   private final int iopsAllocatedK;
   @SerializedName("IopsUsedPc")
   private final int iopsUsedPc;
   @SerializedName("IPAddress")
   private final String ipAddress;
   @SerializedName("IsFaultTolerant")
   private final boolean isFaultTolerant;
   @SerializedName("IsGlobalTemplate")
   private final boolean isGlobalTemplate;
   @SerializedName("IsTemplate")
   private final boolean isTemplate;
   @SerializedName("IsTrustRequired")
   private final boolean isTrustRequired;
   @SerializedName("NetBandwidthAllocatedKBps")
   private final int netBandwidthAllocatedKBps;
   @SerializedName("Nics")
   private final List<Nic> nics;
   @SerializedName("NumCpu")
   private final int numCpu;
   @SerializedName("NumNic")
   private final int numNic;
   @SerializedName("NumVirtualDisk")
   private final int numVirtualDisk;
   @SerializedName("OS")
   private final String os;
   @SerializedName("OSFullName")
   private final String osFullName;
   @SerializedName("ParentHost")
   private final String parentHost;
   @SerializedName("PowerState")
   private final String powerState;
   @SerializedName("RamActiveAvgKB")
   private final int ramActiveAvgKB;
   @SerializedName("RamAllocatedMB")
   private final int ramAllocatedMB;
   @SerializedName("RamLimitMB")
   private final int ramLimitMB;
   @SerializedName("RamReservationMB")
   private final int ramReservationMB;
   @SerializedName("RamShares")
   private final int ramShares;
   @SerializedName("RamUsedAvgKB")
   private final int ramUsedAvgKB;
   @SerializedName("RamUsedPc")
   private final int ramUsedPc;
   @SerializedName("ResourcePoolID")
   private final String resourcePoolID;
   @SerializedName("ResourcePoolIdentifier")
   private final String resourcePoolIdentifier;
   @SerializedName("ResourcePoolName")
   private final String resourcePoolName;
   @SerializedName("Snapshots")
   private final List<Object> snapshots;
   @SerializedName("SourceTemplateID")
   private final String sourceTemplateId;
   @SerializedName("SourceTemplateIdentifier")
   private final String sourceTemplateIdentifier;
   @SerializedName("StorageBandwidthAllocatedKBps")
   private final int storageBandwidthAllocatedKBps;
   @SerializedName("StorageCapacityAllocatedMB")
   private final int storageCapacityAllocatedMB;
   @SerializedName("StorageCapacityUsedMB")
   private final int storageCapacityUsedMB;
   @SerializedName("StorageCapacityUsedPc")
   private final int storageCapacityUsedPc;
   @SerializedName("StorageIoAllocated")
   private final int storageIoAllocated;
   @SerializedName("TierLevel")
   private final int tierLevel;
   @SerializedName("Timestamp")
   private final String timestamp;
   @SerializedName("TriggeredAlarmCount")
   private final int triggeredAlarmCount;
   @SerializedName("TrustedVm")
   private final boolean trustedVm;
   @SerializedName("HypervisorToolsStatus")
   private final String hypervisorToolsStatus;
   @SerializedName("HypervisorToolsVersion")
   private final String hypervisorToolsVersion;
   @SerializedName("HypervisorToolsInstallerMounted")
   private final boolean hypervisorToolsInstallerMounted;
   @SerializedName("VmHostName")
   private final String vmHostName;
   @SerializedName("CorrelationId")
   private final String correlationId;
   @SerializedName("CatalogTemplateId")
   private final String catalogTemplateId;
   @SerializedName("ComputeProfileID")
   private final String computeProfileId;
   @SerializedName("HardwareTemplateID")
   private final String hardwareTemplateId;
   @SerializedName("HypervisorResourceID")
   private final String hypervisorResourceId;
   @SerializedName("Hypervisor")
   private final Hypervisor hypervisor;
   @SerializedName("ManagedResourceID")
   private final String managedResourceID;
   @SerializedName("ExternalIdentifier")
   private final String externalIdentifier;
   @SerializedName("Customer")
   private final Customer customer;
   @SerializedName("TenantID")
   private final String tenantId;
   @SerializedName("Name")
   private final String name;
   @SerializedName("CustomerDefinedName")
   private final String customerDefinedName;
   @SerializedName("IsRemoved")
   private final boolean isRemoved;
   @SerializedName("ResourceGroups")
   private final List<String> resourceGroups;
   @SerializedName("ManagedResourceType")
   private final String managedResourceType;
   @SerializedName("Attributes")
   private final List<Object> attributes;
   @SerializedName("UniqueId")
   private final String uniqueId;
   @SerializedName("TargetPoolID")
   private final String targetPoolId;
   @SerializedName("Acl")
   private final List<Acl> acl;
   @SerializedName("Tags")
   private final List<Tag> tags;

   @ConstructorProperties({ "VirtualMachineID", "AllowedGeographicalLocations", "BootTime",
           "BootToBios", "Cdroms", "Consumption", "Comment",
           "CpuAllocatedMHz", "CpuLimitMHz", "CpuReservationMHz",
           "CpuShares", "CpuUsedAvgMHz", "CpuUsedPc",
           "ConsoleConnectionCount", "CurrentSnapshotID",
           "CustomizationSpecification", "Description", "Designation",
           "Disks", "DnsName", "IsDrsEnabled", "Group", "Guest", "HostName", "IsBackupScheduled", "IsMonitored",
           "IopsAllocatedK", "IopsUsedPc",
           "IPAddress", "IsFaultTolerant", "IsGlobalTemplate", "IsTemplate", "IsTrustRequired",
           "NetBandwidthAllocatedKBps", "Nics", "NumCpu", "NumNic", "NumVirtualDisk", "OS",
           "OSFullName", "ParentHost", "PowerState", "RamActiveAvgKB", "RamAllocatedMB",
           "RamLimitMB", "RamReservationMB", "RamShares", "RamUsedAvgKB", "RamUsedPc",
           "ResourcePoolID", "ResourcePoolIdentifier", "ResourcePoolName",
           "Snapshots", "SourceTemplateID", "SourceTemplateIdentifier",
           "StorageBandwidthAllocatedKBps", "StorageCapacityAllocatedMB", "StorageCapacityUsedMB",
           "StorageCapacityUsedPc", "StorageIoAllocated", "TierLevel", "Timestamp",
           "TriggeredAlarmCount", "TrustedVm", "HypervisorToolsStatus", "HypervisorToolsVersion",
           "HypervisorToolsInstallerMounted", "VmHostName", "CorrelationId", "CatalogTemplateId", "ComputeProfileID",
           "HardwareTemplateID",
           "HypervisorResourceID", "Hypervisor", "ManagedResourceID",
           "ExternalIdentifier", "Customer", "TenantID", "Name",
           "CustomerDefinedName", "IsRemoved", "ResourceGroups", "ManagedResourceType", "Attributes", "UniqueId",
           "TargetPoolID", "Acl", "Tags" })
   protected Config(String virtualMachineID, List<Object> allowedGeographicalLocations, String bootTime,
                boolean bootToBios, List<Cdrom> cdroms, Consumption consumption, String comment, int cpuAllocatedMHz,
                int cpuLimitMHz, int cpuReservationMHz, int cpuShares, int cpuUsedAvgMHz, int cpuUsedPc,
                int consoleConnectionCount, String currentSnapshotId,
                CustomizationSpecification customizationSpecification, String description, String designation,
                List<Disk> disks, String dnsName, String isDrsEnabled, String group, Guest guest, String hostName,
                boolean isBackupScheduled, boolean isMonitored, int iopsAllocatedK, int iopsUsedPc, String ipAddress,
                boolean isFaultTolerant, boolean isGlobalTemplate, boolean isTemplate, boolean isTrustRequired,
                int netBandwidthAllocatedKBps, List<Nic> nics, int numCpu, int numNic, int numVirtualDisk, String os,
                String osFullName, String parentHost, String powerState, int ramActiveAvgKB, int ramAllocatedMB,
                int ramLimitMB, int ramReservationMB, int ramShares, int ramUsedAvgKB, int ramUsedPc,
                String resourcePoolID, String resourcePoolIdentifier, String resourcePoolName,
                List<Object> snapshots, String sourceTemplateId, String sourceTemplateIdentifier,
                int storageBandwidthAllocatedKBps, int storageCapacityAllocatedMB, int storageCapacityUsedMB,
                int storageCapacityUsedPc, int storageIoAllocated, int tierLevel, String timestamp,
                int triggeredAlarmCount, boolean trustedVm, String hypervisorToolsStatus,
                String hypervisorToolsVersion, boolean hypervisorToolsInstallerMounted, String vmHostName,
                String correlationId, String catalogTemplateId, String computeProfileId, String hardwareTemplateId,
                String hypervisorResourceId, Hypervisor hypervisor, String managedResourceID,
                String externalIdentifier, Customer customer, String tenantId, String name,
                String customerDefinedName, boolean isRemoved, List<String> resourceGroups,
                String managedResourceType, List<Object> attributes, String uniqueId, String targetPoolId,
                List<Acl> acl, List<Tag> tags) {
      this.virtualMachineID = virtualMachineID;
      this.allowedGeographicalLocations = allowedGeographicalLocations != null ? ImmutableList.copyOf(allowedGeographicalLocations) :
              ImmutableList.of();
      this.bootTime = bootTime;
      this.bootToBios = bootToBios;
      this.cdroms = cdroms != null ? ImmutableList.copyOf(cdroms) : ImmutableList.<Cdrom>of();
      this.consumption = consumption;
      this.comment = comment;
      this.cpuAllocatedMHz = cpuAllocatedMHz;
      this.cpuLimitMHz = cpuLimitMHz;
      this.cpuReservationMHz = cpuReservationMHz;
      this.cpuShares = cpuShares;
      this.cpuUsedAvgMHz = cpuUsedAvgMHz;
      this.cpuUsedPc = cpuUsedPc;
      this.consoleConnectionCount = consoleConnectionCount;
      this.currentSnapshotId = currentSnapshotId;
      this.customizationSpecification = customizationSpecification;
      this.description = description;
      this.designation = designation;
      this.disks = disks != null ? ImmutableList.copyOf(disks) : ImmutableList.<Disk>of();
      this.dnsName = dnsName;
      this.isDrsEnabled = isDrsEnabled;
      this.group = group;
      this.guest = guest;
      this.hostName = hostName;
      this.isBackupScheduled = isBackupScheduled;
      this.isMonitored = isMonitored;
      this.iopsAllocatedK = iopsAllocatedK;
      this.iopsUsedPc = iopsUsedPc;
      this.ipAddress = ipAddress;
      this.isFaultTolerant = isFaultTolerant;
      this.isGlobalTemplate = isGlobalTemplate;
      this.isTemplate = isTemplate;
      this.isTrustRequired = isTrustRequired;
      this.netBandwidthAllocatedKBps = netBandwidthAllocatedKBps;
      this.nics = nics != null ? ImmutableList.copyOf(nics) : ImmutableList.<Nic>of();
      this.numCpu = numCpu;
      this.numNic = numNic;
      this.numVirtualDisk = numVirtualDisk;
      this.os = os;
      this.osFullName = osFullName;
      this.parentHost = parentHost;
      this.powerState = powerState;
      this.ramActiveAvgKB = ramActiveAvgKB;
      this.ramAllocatedMB = ramAllocatedMB;
      this.ramLimitMB = ramLimitMB;
      this.ramReservationMB = ramReservationMB;
      this.ramShares = ramShares;
      this.ramUsedAvgKB = ramUsedAvgKB;
      this.ramUsedPc = ramUsedPc;
      this.resourcePoolID = resourcePoolID;
      this.resourcePoolIdentifier = resourcePoolIdentifier;
      this.resourcePoolName = resourcePoolName;
      this.snapshots = snapshots;
      this.sourceTemplateId = sourceTemplateId;
      this.sourceTemplateIdentifier = sourceTemplateIdentifier;
      this.storageBandwidthAllocatedKBps = storageBandwidthAllocatedKBps;
      this.storageCapacityAllocatedMB = storageCapacityAllocatedMB;
      this.storageCapacityUsedMB = storageCapacityUsedMB;
      this.storageCapacityUsedPc = storageCapacityUsedPc;
      this.storageIoAllocated = storageIoAllocated;
      this.tierLevel = tierLevel;
      this.timestamp = timestamp;
      this.triggeredAlarmCount = triggeredAlarmCount;
      this.trustedVm = trustedVm;
      this.hypervisorToolsStatus = hypervisorToolsStatus;
      this.hypervisorToolsVersion = hypervisorToolsVersion;
      this.hypervisorToolsInstallerMounted = hypervisorToolsInstallerMounted;
      this.vmHostName = vmHostName;
      this.correlationId = correlationId;
      this.catalogTemplateId = catalogTemplateId;
      this.computeProfileId = computeProfileId;
      this.hardwareTemplateId = hardwareTemplateId;
      this.hypervisorResourceId = hypervisorResourceId;
      this.hypervisor = hypervisor;
      this.managedResourceID = managedResourceID;
      this.externalIdentifier = externalIdentifier;
      this.customer = customer;
      this.tenantId = tenantId;
      this.name = name;
      this.customerDefinedName = customerDefinedName;
      this.isRemoved = isRemoved;
      this.resourceGroups = resourceGroups != null ? ImmutableList.copyOf(resourceGroups) : ImmutableList.<String>of();
      this.managedResourceType = managedResourceType;
      this.attributes = attributes;
      this.uniqueId = uniqueId;
      this.targetPoolId = targetPoolId;
      this.acl = acl;
      this.tags = tags != null ? ImmutableList.copyOf(tags) : ImmutableList.<Tag>of();
   }

   public String getVirtualMachineID() {
      return virtualMachineID;
   }

   public List<Object> getAllowedGeographicalLocations() {
      return allowedGeographicalLocations;
   }

   public String getBootTime() {
      return bootTime;
   }

   public boolean isBootToBios() {
      return bootToBios;
   }

   public List<Cdrom> getCdroms() {
      return cdroms;
   }

   public Consumption getConsumption() {
      return consumption;
   }

   public String getComment() {
      return comment;
   }

   public int getCpuAllocatedMHz() {
      return cpuAllocatedMHz;
   }

   public int getCpuLimitMHz() {
      return cpuLimitMHz;
   }

   public int getCpuReservationMHz() {
      return cpuReservationMHz;
   }

   public int getCpuShares() {
      return cpuShares;
   }

   public int getCpuUsedAvgMHz() {
      return cpuUsedAvgMHz;
   }

   public int getCpuUsedPc() {
      return cpuUsedPc;
   }

   public int getConsoleConnectionCount() {
      return consoleConnectionCount;
   }

   public String getCurrentSnapshotId() {
      return currentSnapshotId;
   }

   public CustomizationSpecification getCustomizationSpecification() {
      return customizationSpecification;
   }

   public String getDescription() {
      return description;
   }

   public String getDesignation() {
      return designation;
   }

   public List<Disk> getDisks() {
      return disks;
   }

   public String getDnsName() {
      return dnsName;
   }

   public String getIsDrsEnabled() {
      return isDrsEnabled;
   }

   public String getGroup() {
      return group;
   }

   public Guest getGuest() {
      return guest;
   }

   public String getHostName() {
      return hostName;
   }

   public boolean isBackupScheduled() {
      return isBackupScheduled;
   }

   public boolean isMonitored() {
      return isMonitored;
   }

   public int getIopsAllocatedK() {
      return iopsAllocatedK;
   }

   public int getIopsUsedPc() {
      return iopsUsedPc;
   }

   public String getIpAddress() {
      return ipAddress;
   }

   public boolean isFaultTolerant() {
      return isFaultTolerant;
   }

   public boolean isGlobalTemplate() {
      return isGlobalTemplate;
   }

   public boolean isTemplate() {
      return isTemplate;
   }

   public boolean isTrustRequired() {
      return isTrustRequired;
   }

   public int getNetBandwidthAllocatedKBps() {
      return netBandwidthAllocatedKBps;
   }

   public List<Nic> getNics() {
      return nics;
   }

   public int getNumCpu() {
      return numCpu;
   }

   public int getNumNic() {
      return numNic;
   }

   public int getNumVirtualDisk() {
      return numVirtualDisk;
   }

   public String getOs() {
      return os;
   }

   public String getOsFullName() {
      return osFullName;
   }

   public String getParentHost() {
      return parentHost;
   }

   public String getPowerState() {
      return powerState;
   }

   public int getRamActiveAvgKB() {
      return ramActiveAvgKB;
   }

   public int getRamAllocatedMB() {
      return ramAllocatedMB;
   }

   public int getRamLimitMB() {
      return ramLimitMB;
   }

   public int getRamReservationMB() {
      return ramReservationMB;
   }

   public int getRamShares() {
      return ramShares;
   }

   public int getRamUsedAvgKB() {
      return ramUsedAvgKB;
   }

   public int getRamUsedPc() {
      return ramUsedPc;
   }

   public String getResourcePoolID() {
      return resourcePoolID;
   }

   public String getResourcePoolIdentifier() {
      return resourcePoolIdentifier;
   }

   public String getResourcePoolName() {
      return resourcePoolName;
   }

   public List<Object> getSnapshots() {
      return snapshots;
   }

   public String getSourceTemplateId() {
      return sourceTemplateId;
   }

   public String getSourceTemplateIdentifier() {
      return sourceTemplateIdentifier;
   }

   public int getStorageBandwidthAllocatedKBps() {
      return storageBandwidthAllocatedKBps;
   }

   public int getStorageCapacityAllocatedMB() {
      return storageCapacityAllocatedMB;
   }

   public int getStorageCapacityUsedMB() {
      return storageCapacityUsedMB;
   }

   public int getStorageCapacityUsedPc() {
      return storageCapacityUsedPc;
   }

   public int getStorageIoAllocated() {
      return storageIoAllocated;
   }

   public int getTierLevel() {
      return tierLevel;
   }

   public String getTimestamp() {
      return timestamp;
   }

   public int getTriggeredAlarmCount() {
      return triggeredAlarmCount;
   }

   public boolean isTrustedVm() {
      return trustedVm;
   }

   public String getHypervisorToolsStatus() {
      return hypervisorToolsStatus;
   }

   public String getHypervisorToolsVersion() {
      return hypervisorToolsVersion;
   }

   public boolean isHypervisorToolsInstallerMounted() {
      return hypervisorToolsInstallerMounted;
   }

   public String getVmHostName() {
      return vmHostName;
   }

   public String getCorrelationId() {
      return correlationId;
   }

   public String getCatalogTemplateId() {
      return catalogTemplateId;
   }

   public String getComputeProfileId() {
      return computeProfileId;
   }

   public String getHardwareTemplateId() {
      return hardwareTemplateId;
   }

   public String getHypervisorResourceId() {
      return hypervisorResourceId;
   }

   public Hypervisor getHypervisor() {
      return hypervisor;
   }

   public String getManagedResourceID() {
      return managedResourceID;
   }

   public String getExternalIdentifier() {
      return externalIdentifier;
   }

   public Customer getCustomer() {
      return customer;
   }

   public String getTenantId() {
      return tenantId;
   }

   public String getName() {
      return name;
   }

   public String getCustomerDefinedName() {
      return customerDefinedName;
   }

   public boolean isRemoved() {
      return isRemoved;
   }

   public List<String> getResourceGroups() {
      return resourceGroups;
   }

   public String getManagedResourceType() {
      return managedResourceType;
   }

   public List<Object> getAttributes() {
      return attributes;
   }

   public String getUniqueId() {
      return uniqueId;
   }

   public String getTargetPoolId() {
      return targetPoolId;
   }

   public List<Acl> getAcl() {
      return acl;
   }

   public List<Tag> getTags() {
      return tags;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Config that = (Config) o;

      return Objects.equal(this.virtualMachineID, that.virtualMachineID) &&
              Objects.equal(this.allowedGeographicalLocations, that.allowedGeographicalLocations) &&
              Objects.equal(this.bootTime, that.bootTime) &&
              Objects.equal(this.bootToBios, that.bootToBios) &&
              Objects.equal(this.cdroms, that.cdroms) &&
              Objects.equal(this.consumption, that.consumption) &&
              Objects.equal(this.comment, that.comment) &&
              Objects.equal(this.cpuAllocatedMHz, that.cpuAllocatedMHz) &&
              Objects.equal(this.cpuLimitMHz, that.cpuLimitMHz) &&
              Objects.equal(this.cpuReservationMHz, that.cpuReservationMHz) &&
              Objects.equal(this.cpuShares, that.cpuShares) &&
              Objects.equal(this.cpuUsedAvgMHz, that.cpuUsedAvgMHz) &&
              Objects.equal(this.cpuUsedPc, that.cpuUsedPc) &&
              Objects.equal(this.consoleConnectionCount, that.consoleConnectionCount) &&
              Objects.equal(this.currentSnapshotId, that.currentSnapshotId) &&
              Objects.equal(this.customizationSpecification, that.customizationSpecification) &&
              Objects.equal(this.description, that.description) &&
              Objects.equal(this.designation, that.designation) &&
              Objects.equal(this.disks, that.disks) &&
              Objects.equal(this.dnsName, that.dnsName) &&
              Objects.equal(this.isDrsEnabled, that.isDrsEnabled) &&
              Objects.equal(this.group, that.group) &&
              Objects.equal(this.guest, that.guest) &&
              Objects.equal(this.hostName, that.hostName) &&
              Objects.equal(this.isBackupScheduled, that.isBackupScheduled) &&
              Objects.equal(this.isMonitored, that.isMonitored) &&
              Objects.equal(this.iopsAllocatedK, that.iopsAllocatedK) &&
              Objects.equal(this.iopsUsedPc, that.iopsUsedPc) &&
              Objects.equal(this.ipAddress, that.ipAddress) &&
              Objects.equal(this.isFaultTolerant, that.isFaultTolerant) &&
              Objects.equal(this.isGlobalTemplate, that.isGlobalTemplate) &&
              Objects.equal(this.isTemplate, that.isTemplate) &&
              Objects.equal(this.isTrustRequired, that.isTrustRequired) &&
              Objects.equal(this.netBandwidthAllocatedKBps, that.netBandwidthAllocatedKBps) &&
              Objects.equal(this.nics, that.nics) &&
              Objects.equal(this.numCpu, that.numCpu) &&
              Objects.equal(this.numNic, that.numNic) &&
              Objects.equal(this.numVirtualDisk, that.numVirtualDisk) &&
              Objects.equal(this.os, that.os) &&
              Objects.equal(this.osFullName, that.osFullName) &&
              Objects.equal(this.parentHost, that.parentHost) &&
              Objects.equal(this.powerState, that.powerState) &&
              Objects.equal(this.ramActiveAvgKB, that.ramActiveAvgKB) &&
              Objects.equal(this.ramAllocatedMB, that.ramAllocatedMB) &&
              Objects.equal(this.ramLimitMB, that.ramLimitMB) &&
              Objects.equal(this.ramReservationMB, that.ramReservationMB) &&
              Objects.equal(this.ramShares, that.ramShares) &&
              Objects.equal(this.ramUsedAvgKB, that.ramUsedAvgKB) &&
              Objects.equal(this.ramUsedPc, that.ramUsedPc) &&
              Objects.equal(this.resourcePoolID, that.resourcePoolID) &&
              Objects.equal(this.resourcePoolIdentifier, that.resourcePoolIdentifier) &&
              Objects.equal(this.resourcePoolName, that.resourcePoolName) &&
              Objects.equal(this.snapshots, that.snapshots) &&
              Objects.equal(this.sourceTemplateId, that.sourceTemplateId) &&
              Objects.equal(this.sourceTemplateIdentifier, that.sourceTemplateIdentifier) &&
              Objects.equal(this.storageBandwidthAllocatedKBps, that.storageBandwidthAllocatedKBps) &&
              Objects.equal(this.storageCapacityAllocatedMB, that.storageCapacityAllocatedMB) &&
              Objects.equal(this.storageCapacityUsedMB, that.storageCapacityUsedMB) &&
              Objects.equal(this.storageCapacityUsedPc, that.storageCapacityUsedPc) &&
              Objects.equal(this.storageIoAllocated, that.storageIoAllocated) &&
              Objects.equal(this.tierLevel, that.tierLevel) &&
              Objects.equal(this.timestamp, that.timestamp) &&
              Objects.equal(this.triggeredAlarmCount, that.triggeredAlarmCount) &&
              Objects.equal(this.trustedVm, that.trustedVm) &&
              Objects.equal(this.hypervisorToolsStatus, that.hypervisorToolsStatus) &&
              Objects.equal(this.hypervisorToolsVersion, that.hypervisorToolsVersion) &&
              Objects.equal(this.hypervisorToolsInstallerMounted, that.hypervisorToolsInstallerMounted) &&
              Objects.equal(this.vmHostName, that.vmHostName) &&
              Objects.equal(this.correlationId, that.correlationId) &&
              Objects.equal(this.catalogTemplateId, that.catalogTemplateId) &&
              Objects.equal(this.computeProfileId, that.computeProfileId) &&
              Objects.equal(this.hardwareTemplateId, that.hardwareTemplateId) &&
              Objects.equal(this.hypervisorResourceId, that.hypervisorResourceId) &&
              Objects.equal(this.hypervisor, that.hypervisor) &&
              Objects.equal(this.managedResourceID, that.managedResourceID) &&
              Objects.equal(this.externalIdentifier, that.externalIdentifier) &&
              Objects.equal(this.customer, that.customer) &&
              Objects.equal(this.tenantId, that.tenantId) &&
              Objects.equal(this.name, that.name) &&
              Objects.equal(this.customerDefinedName, that.customerDefinedName) &&
              Objects.equal(this.isRemoved, that.isRemoved) &&
              Objects.equal(this.resourceGroups, that.resourceGroups) &&
              Objects.equal(this.managedResourceType, that.managedResourceType) &&
              Objects.equal(this.attributes, that.attributes) &&
              Objects.equal(this.uniqueId, that.uniqueId) &&
              Objects.equal(this.targetPoolId, that.targetPoolId) &&
              Objects.equal(this.acl, that.acl) &&
              Objects.equal(this.tags, that.tags);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(virtualMachineID, allowedGeographicalLocations, bootTime, bootToBios, cdroms, consumption,
              comment, cpuAllocatedMHz, cpuLimitMHz, cpuReservationMHz, cpuShares,
              cpuUsedAvgMHz, cpuUsedPc, consoleConnectionCount, currentSnapshotId, customizationSpecification,
              description, designation, disks, dnsName, isDrsEnabled,
              group, guest, hostName, isBackupScheduled, isMonitored,
              iopsAllocatedK, iopsUsedPc, ipAddress, isFaultTolerant, isGlobalTemplate,
              isTemplate, isTrustRequired, netBandwidthAllocatedKBps, nics, numCpu,
              numNic, numVirtualDisk, os, osFullName, parentHost,
              powerState, ramActiveAvgKB, ramAllocatedMB, ramLimitMB, ramReservationMB,
              ramShares, ramUsedAvgKB, ramUsedPc, resourcePoolID, resourcePoolIdentifier,
              resourcePoolName, snapshots, sourceTemplateId, sourceTemplateIdentifier, storageBandwidthAllocatedKBps,
              storageCapacityAllocatedMB, storageCapacityUsedMB, storageCapacityUsedPc, storageIoAllocated, tierLevel,
              timestamp, triggeredAlarmCount, trustedVm, hypervisorToolsStatus, hypervisorToolsVersion,
              hypervisorToolsInstallerMounted, vmHostName, correlationId, catalogTemplateId, computeProfileId,
              hardwareTemplateId, hypervisorResourceId, hypervisor, managedResourceID, externalIdentifier,
              customer, tenantId, name, customerDefinedName, isRemoved,
              resourceGroups, managedResourceType, attributes, uniqueId, targetPoolId,
              acl, tags);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("virtualMachineID", virtualMachineID)
              .add("allowedGeographicalLocations", allowedGeographicalLocations)
              .add("bootTime", bootTime)
              .add("bootToBios", bootToBios)
              .add("cdroms", cdroms)
              .add("consumption", consumption)
              .add("comment", comment)
              .add("cpuAllocatedMHz", cpuAllocatedMHz)
              .add("cpuLimitMHz", cpuLimitMHz)
              .add("cpuReservationMHz", cpuReservationMHz)
              .add("cpuShares", cpuShares)
              .add("cpuUsedAvgMHz", cpuUsedAvgMHz)
              .add("cpuUsedPc", cpuUsedPc)
              .add("consoleConnectionCount", consoleConnectionCount)
              .add("currentSnapshotId", currentSnapshotId)
              .add("customizationSpecification", customizationSpecification)
              .add("description", description)
              .add("designation", designation)
              .add("disks", disks)
              .add("dnsName", dnsName)
              .add("isDrsEnabled", isDrsEnabled)
              .add("group", group)
              .add("guest", guest)
              .add("hostName", hostName)
              .add("isBackupScheduled", isBackupScheduled)
              .add("isMonitored", isMonitored)
              .add("iopsAllocatedK", iopsAllocatedK)
              .add("iopsUsedPc", iopsUsedPc)
              .add("ipAddress", ipAddress)
              .add("isFaultTolerant", isFaultTolerant)
              .add("isGlobalTemplate", isGlobalTemplate)
              .add("isTemplate", isTemplate)
              .add("isTrustRequired", isTrustRequired)
              .add("netBandwidthAllocatedKBps", netBandwidthAllocatedKBps)
              .add("nics", nics)
              .add("numCpu", numCpu)
              .add("numNic", numNic)
              .add("numVirtualDisk", numVirtualDisk)
              .add("os", os)
              .add("osFullName", osFullName)
              .add("parentHost", parentHost)
              .add("powerState", powerState)
              .add("ramActiveAvgKB", ramActiveAvgKB)
              .add("ramAllocatedMB", ramAllocatedMB)
              .add("ramLimitMB", ramLimitMB)
              .add("ramReservationMB", ramReservationMB)
              .add("ramShares", ramShares)
              .add("ramUsedAvgKB", ramUsedAvgKB)
              .add("ramUsedPc", ramUsedPc)
              .add("resourcePoolID", resourcePoolID)
              .add("resourcePoolIdentifier", resourcePoolIdentifier)
              .add("resourcePoolName", resourcePoolName)
              .add("snapshots", snapshots)
              .add("sourceTemplateId", sourceTemplateId)
              .add("sourceTemplateIdentifier", sourceTemplateIdentifier)
              .add("storageBandwidthAllocatedKBps", storageBandwidthAllocatedKBps)
              .add("storageCapacityAllocatedMB", storageCapacityAllocatedMB)
              .add("storageCapacityUsedMB", storageCapacityUsedMB)
              .add("storageCapacityUsedPc", storageCapacityUsedPc)
              .add("storageIoAllocated", storageIoAllocated)
              .add("tierLevel", tierLevel)
              .add("timestamp", timestamp)
              .add("triggeredAlarmCount", triggeredAlarmCount)
              .add("trustedVm", trustedVm)
              .add("hypervisorToolsStatus", hypervisorToolsStatus)
              .add("hypervisorToolsVersion", hypervisorToolsVersion)
              .add("hypervisorToolsInstallerMounted", hypervisorToolsInstallerMounted)
              .add("vmHostName", vmHostName)
              .add("correlationId", correlationId)
              .add("catalogTemplateId", catalogTemplateId)
              .add("computeProfileId", computeProfileId)
              .add("hardwareTemplateId", hardwareTemplateId)
              .add("hypervisorResourceId", hypervisorResourceId)
              .add("hypervisor", hypervisor)
              .add("managedResourceID", managedResourceID)
              .add("externalIdentifier", externalIdentifier)
              .add("customer", customer)
              .add("tenantId", tenantId)
              .add("name", name)
              .add("customerDefinedName", customerDefinedName)
              .add("isRemoved", isRemoved)
              .add("resourceGroups", resourceGroups)
              .add("managedResourceType", managedResourceType)
              .add("attributes", attributes)
              .add("uniqueId", uniqueId)
              .add("targetPoolId", targetPoolId)
              .add("acl", acl)
              .add("tags", tags)
              .toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public static final class Builder {

      private String virtualMachineID;
      private List<Object> allowedGeographicalLocations;
      private String bootTime;
      private boolean bootToBios;
      private List<Cdrom> cdroms;
      private Consumption consumption;
      private String comment;
      private int cpuAllocatedMHz;
      private int cpuLimitMHz;
      private int cpuReservationMHz;
      private int cpuShares;
      private int cpuUsedAvgMHz;
      private int cpuUsedPc;
      private int consoleConnectionCount;
      private String currentSnapshotId;
      private CustomizationSpecification customizationSpecification;
      private String description;
      private String designation;
      private List<Disk> disks;
      private String dnsName;
      private String isDrsEnabled;
      private String group;
      private Guest guest;
      private String hostName;
      private boolean isBackupScheduled;
      private boolean isMonitored;
      private int iopsAllocatedK;
      private int iopsUsedPc;
      private String ipAddress;
      private boolean isFaultTolerant;
      private boolean isGlobalTemplate;
      private boolean isTemplate;
      private boolean isTrustRequired;
      private int netBandwidthAllocatedKBps;
      private List<Nic> nics;
      private int numCpu;
      private int numNic;
      private int numVirtualDisk;
      private String os;
      private String osFullName;
      private String parentHost;
      private String powerState;
      private int ramActiveAvgKB;
      private int ramAllocatedMB;
      private int ramLimitMB;
      private int ramReservationMB;
      private int ramShares;
      private int ramUsedAvgKB;
      private int ramUsedPc;
      private String resourcePoolID;
      private String resourcePoolIdentifier;
      private String resourcePoolName;
      private List<Object> snapshots;
      private String sourceTemplateId;
      private String sourceTemplateIdentifier;
      private int storageBandwidthAllocatedKBps;
      private int storageCapacityAllocatedMB;
      private int storageCapacityUsedMB;
      private int storageCapacityUsedPc;
      private int storageIoAllocated;
      private int tierLevel;
      private String timestamp;
      private int triggeredAlarmCount;
      private boolean trustedVm;
      private String hypervisorToolsStatus;
      private String hypervisorToolsVersion;
      private boolean hypervisorToolsInstallerMounted;
      private String vmHostName;
      private String correlationId;
      private String catalogTemplateId;
      private String computeProfileId;
      private String hardwareTemplateId;
      private String hypervisorResourceId;
      private Hypervisor hypervisor;
      private String managedResourceID;
      private String externalIdentifier;
      private Customer customer;
      private String tenantId;
      private String name;
      private String customerDefinedName;
      private boolean isRemoved;
      private List<String> resourceGroups;
      private String managedResourceType;
      private List<Object> attributes;
      private String uniqueId;
      private String targetPoolId;
      private List<Acl> acl;
      private List<Tag> tags;

      public Builder virtualMachineID(String virtualMachineID) {
         this.virtualMachineID = virtualMachineID;
         return this;
      }

      public Builder bootTime(String bootTime) {
         this.bootTime = bootTime;
         return this;
      }

      public Builder cdroms(String created) {
         this.cdroms = cdroms;
         return this;
      }

      public Builder consumption(Consumption consumption) {
         this.consumption = consumption;
         return this;
      }

      public Builder cpuLimitMHz(int cpuLimitMHz) {
         this.cpuLimitMHz = cpuLimitMHz;
         return this;
      }

      public Builder cpuShares(int cpuShares) {
         this.cpuShares = cpuShares;
         return this;
      }

      public Builder disks(Disk state) {
         this.disks = disks;
         return this;
      }

      public Builder dnsName(String dnsName) {
         this.dnsName = dnsName;
         return this;
      }

      public Builder group(String group) {
         this.group = group;
         return this;
      }

      public Builder guest(Guest guest) {
         this.guest = guest;
         return this;
      }

      public Builder ipAddress(String ipAddress) {
         this.ipAddress = ipAddress;
         return this;
      }

      public Builder nics(List<Nic> nics) {
         this.nics= nics;
         return this;
      }

      public Builder numCpu(int numCpu) {
         this.numCpu = numCpu;
         return this;
      }

      public Builder ramAllocatedMB(int ramAllocatedMB) {
         this.ramAllocatedMB = ramAllocatedMB;
         return this;
      }

      public Builder disks(List<Disk> disks) {
         this.disks = disks;
         return this;
      }

      public Builder tenantId(String tenantId) {
         this.tenantId = tenantId;
         return this;
      }

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder customerDefinedName(String customerDefinedName) {
         this.customerDefinedName = customerDefinedName;
         return this;
      }

      public Builder resourceGroups(List<String> resourceGroups) {
         this.resourceGroups= resourceGroups;
         return this;
      }

      public Builder sourceTemplateId(String sourceTemplateId) {
         this.sourceTemplateId = sourceTemplateId;
         return this;
      }

      public Builder computeProfileId(String computeProfileId) {
         this.computeProfileId = computeProfileId;
         return this;
      }

      public Builder hypervisorResourceId(String hypervisorResourceId) {
         this.hypervisorResourceId = hypervisorResourceId;
         return this;
      }

      public Config build() {
         return new Config(virtualMachineID, allowedGeographicalLocations, bootTime, bootToBios, cdroms, consumption,
                 comment, cpuAllocatedMHz, cpuLimitMHz, cpuReservationMHz, cpuShares,
                 cpuUsedAvgMHz, cpuUsedPc, consoleConnectionCount, currentSnapshotId, customizationSpecification,
                 description, designation, disks, dnsName, isDrsEnabled,
                 group, guest, hostName, isBackupScheduled, isMonitored,
                 iopsAllocatedK, iopsUsedPc, ipAddress, isFaultTolerant, isGlobalTemplate,
                 isTemplate, isTrustRequired, netBandwidthAllocatedKBps, nics, numCpu,
                 numNic, numVirtualDisk, os, osFullName, parentHost,
                 powerState, ramActiveAvgKB, ramAllocatedMB, ramLimitMB, ramReservationMB,
                 ramShares, ramUsedAvgKB, ramUsedPc, resourcePoolID, resourcePoolIdentifier,
                 resourcePoolName, snapshots, sourceTemplateId, sourceTemplateIdentifier, storageBandwidthAllocatedKBps,
                 storageCapacityAllocatedMB, storageCapacityUsedMB, storageCapacityUsedPc, storageIoAllocated, tierLevel,
                 timestamp, triggeredAlarmCount, trustedVm, hypervisorToolsStatus, hypervisorToolsVersion,
                 hypervisorToolsInstallerMounted, vmHostName, correlationId, catalogTemplateId, computeProfileId,
                 hardwareTemplateId, hypervisorResourceId, hypervisor, managedResourceID, externalIdentifier,
                 customer, tenantId, name, customerDefinedName, isRemoved,
                 resourceGroups, managedResourceType, attributes, uniqueId, targetPoolId,
                 acl, tags);
      }

      public Builder fromConfig(Config in) {
         return this.name(in.getName());
      }

   }
}
