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

public class VirtualMachine {

   @SerializedName("VirtualMachineID")
   private final String virtualMachineID;
   @SerializedName("BootTime")
   private final String bootTime;
   @SerializedName("Cdroms")
   private final List<Cdrom> cdroms;
   @SerializedName("Consumption")
   private final Consumption consumption;
   @SerializedName("CpuLimitMHz")
   private final int cpuLimitMHz;
   @SerializedName("CpuShares")
   private final int cpuShares;
   @SerializedName("Disks")
   private final List<Disk> disks;
   @SerializedName("DnsName")
   private final String dnsName;
   @SerializedName("Group")
   private final String group;
   @SerializedName("Guest")
   private final Guest guest;
   @SerializedName("IPAddress")
   private final String ipAddress;
   @SerializedName("IsTemplate")
   private final boolean isTemplate;
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
   @SerializedName("RamAllocatedMB")
   private final int ramAllocatedMB;
   @SerializedName("RamLimitMB")
   private final int ramLimitMB;
   @SerializedName("RamShares")
   private final int ramShares;
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
   @SerializedName("HypervisorToolsStatus")
   private final String hypervisorToolsStatus;
   @SerializedName("HypervisorToolsVersion")
   private final String hypervisorToolsVersion;
   @SerializedName("VmHostName")
   private final String vmHostName;
   @SerializedName("CorrelationId")
   private final String correlationId;
   @SerializedName("Hypervisor")
   private final Hypervisor hypervisor;
   @SerializedName("ExternalIdentifier")
   private final String externalIdentifier;
   @SerializedName("TenantID")
   private final String tenantId;
   @SerializedName("Name")
   private final String name;
   @SerializedName("CustomerDefinedName")
   private final String customerDefinedName;
   @SerializedName("ResourceGroups")
   private final List<String> resourceGroups;
   @SerializedName("UniqueId")
   private final String uniqueId;
   @SerializedName("Acl")
   private final List<Acl> acl;

   @SerializedName("SourceTemplateID")
   private final String sourceTemplateId;


   @ConstructorProperties({ "VirtualMachineID", "BootTime", "Cdroms", "Consumption", "CpuLimitMHz",
           "CpuShares", "Disks", "DnsName", "Group", "Guest",
           "IPAddress", "IsTemplate", "Nics", "NumCpu", "NumNic",
           "NumVirtualDisk", "OS",
           "OSFullName", "ParentHost", "PowerState", "RamAllocatedMB", "RamLimitMB", "RamShares",
           "StorageCapacityAllocatedMB", "StorageCapacityUsedMB", "StorageCapacityUsedPc",
           "StorageIoAllocated", "TierLevel",
           "Timestamp", "HypervisorToolsStatus", "HypervisorToolsVersion", "VmHostName", "CorrelationId", "Hypervisor",
           "ExternalIdentifier", "TenantID", "Name",
           "CustomerDefinedName", "ResourceGroups", "UniqueId", "Acl", "SourceTemplateID" })
   protected VirtualMachine(String virtualMachineID, String bootTime, List<Cdrom> cdroms, Consumption consumption,
                            int cpuLimitMHz, int cpuShares,
                       List<Disk> disks, String dnsName, String group,
                       Guest guest, String ipAddress, boolean isTemplate,
                       List<Nic> nics, int numCpu, int numNic, int numVirtualDisk,
                       String os, String osFullName, String parentHost, String powerState, int ramAllocatedMB,
                       int ramLimitMB, int ramShares, int storageCapacityAllocatedMB, int storageCapacityUsedMB,
                       int storageCapacityUsedPc, int storageIoAllocated, int tierLevel, String timestamp,
                       String hypervisorToolsStatus, String hypervisorToolsVersion, String vmHostName,
                       String correlationId, Hypervisor hypervisor,
                       String externalIdentifier, String tenantId, String name, String customerDefinedName,
                       List<String> resourceGroups, String uniqueId, List<Acl> acl, String sourceTemplateId) {
      this.virtualMachineID = virtualMachineID;
      this.bootTime = bootTime;
      this.cdroms = cdroms;
      this.consumption = consumption;
      this.cpuLimitMHz = cpuLimitMHz;
      this.cpuShares = cpuShares;
      this.disks = disks != null ? ImmutableList.copyOf(disks) : ImmutableList.<Disk>of();
      this.dnsName = dnsName;
      this.group = group;
      this.guest = guest;
      this.ipAddress = ipAddress;
      this.isTemplate = isTemplate;
      this.nics = nics != null ? ImmutableList.copyOf(nics) : ImmutableList.<Nic>of();
      this.numCpu = numCpu;
      this.numNic = numNic;
      this.numVirtualDisk = numVirtualDisk;
      this.os = os;
      this.osFullName = osFullName;
      this.parentHost = parentHost;
      this.powerState = powerState;
      this.ramAllocatedMB = ramAllocatedMB;
      this.ramLimitMB = ramLimitMB;
      this.ramShares = ramShares;
      this.storageCapacityAllocatedMB = storageCapacityAllocatedMB;
      this.storageCapacityUsedMB = storageCapacityUsedMB;
      this.storageCapacityUsedPc = storageCapacityUsedPc;
      this.storageIoAllocated = storageIoAllocated;
      this.tierLevel = tierLevel;
      this.timestamp = timestamp;
      this.hypervisorToolsStatus = hypervisorToolsStatus;
      this.hypervisorToolsVersion = hypervisorToolsVersion;
      this.vmHostName = vmHostName;
      this.correlationId = correlationId;
      this.hypervisor = hypervisor;
      this.externalIdentifier = externalIdentifier;
      this.tenantId= tenantId;
      this.name = name;
      this.customerDefinedName = customerDefinedName;
      this.resourceGroups = resourceGroups != null ? ImmutableList.copyOf(resourceGroups) : ImmutableList.<String>of();
      this.uniqueId = uniqueId;
      this.acl = acl != null ? ImmutableList.copyOf(acl) : ImmutableList.<Acl>of();
      this.sourceTemplateId = sourceTemplateId;
   }

   public String getVirtualMachineID() {
      return virtualMachineID;
   }

   public String getBootTime() {
      return bootTime;
   }

   public List<Cdrom> getCdroms() {
      return cdroms;
   }

   public Consumption getConsumption() {
      return consumption;
   }

   public int getCpuLimitMHz() {
      return cpuLimitMHz;
   }

   public int getCpuShares() {
      return cpuShares;
   }

   public List<Disk> getDisks() {
      return disks;
   }

   public String getDnsName() {
      return dnsName;
   }

   public String getGroup() {
      return group;
   }

   public Guest getGuest() {
      return guest;
   }

   public String getIpAddress() {
      return ipAddress;
   }

   public boolean isTemplate() {
      return isTemplate;
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

   public int getRamAllocatedMB() {
      return ramAllocatedMB;
   }

   public int getRamLimitMB() {
      return ramLimitMB;
   }

   public int getRamShares() {
      return ramShares;
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

   public String getHypervisorToolsStatus() {
      return hypervisorToolsStatus;
   }

   public String getHypervisorToolsVersion() {
      return hypervisorToolsVersion;
   }

   public String getVmHostName() {
      return vmHostName;
   }

   public String getCorrelationId() {
      return correlationId;
   }

   public Hypervisor getHypervisor() {
      return hypervisor;
   }

   public String getExternalIdentifier() {
      return externalIdentifier;
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

   public List<String> getResourceGroups() {
      return resourceGroups;
   }

   public String getUniqueId() {
      return uniqueId;
   }

   public List<Acl> getAcl() {
      return acl;
   }

   public String getSourceTemplateId() {
      return sourceTemplateId;
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromVirtualMachine(this);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      VirtualMachine that = (VirtualMachine) o;

      return Objects.equal(this.virtualMachineID, that.virtualMachineID) &&
              Objects.equal(this.bootTime, that.bootTime) &&
              Objects.equal(this.cdroms, that.cdroms) &&
              Objects.equal(this.consumption, that.consumption) &&
              Objects.equal(this.cpuLimitMHz, that.cpuLimitMHz) &&
              Objects.equal(this.cpuShares, that.cpuShares) &&
              Objects.equal(this.disks, that.disks) &&
              Objects.equal(this.dnsName, that.dnsName) &&
              Objects.equal(this.group, that.group) &&
              Objects.equal(this.guest, that.guest) &&
              Objects.equal(this.ipAddress, that.ipAddress) &&
              Objects.equal(this.isTemplate, that.isTemplate) &&
              Objects.equal(this.nics, that.nics) &&
              Objects.equal(this.numCpu, that.numCpu) &&
              Objects.equal(this.numNic, that.numNic) &&
              Objects.equal(this.numVirtualDisk, that.numVirtualDisk) &&
              Objects.equal(this.os, that.os) &&
              Objects.equal(this.osFullName, that.osFullName) &&
              Objects.equal(this.parentHost, that.parentHost) &&
              Objects.equal(this.powerState, that.powerState) &&
              Objects.equal(this.ramAllocatedMB, that.ramAllocatedMB) &&
              Objects.equal(this.ramLimitMB, that.ramLimitMB) &&
              Objects.equal(this.ramShares, that.ramShares) &&
              Objects.equal(this.storageCapacityAllocatedMB, that.storageCapacityAllocatedMB) &&
              Objects.equal(this.storageCapacityUsedMB, that.storageCapacityUsedMB) &&
              Objects.equal(this.storageCapacityUsedPc, that.storageCapacityUsedPc) &&
              Objects.equal(this.storageIoAllocated, that.storageIoAllocated) &&
              Objects.equal(this.tierLevel, that.tierLevel) &&
              Objects.equal(this.timestamp, that.timestamp) &&
              Objects.equal(this.hypervisorToolsStatus, that.hypervisorToolsStatus) &&
              Objects.equal(this.hypervisorToolsVersion, that.hypervisorToolsVersion) &&
              Objects.equal(this.vmHostName, that.vmHostName) &&
              Objects.equal(this.correlationId, that.correlationId) &&
              Objects.equal(this.hypervisor, that.hypervisor) &&
              Objects.equal(this.externalIdentifier, that.externalIdentifier) &&
              Objects.equal(this.tenantId, that.tenantId) &&
              Objects.equal(this.name, that.name) &&
              Objects.equal(this.customerDefinedName, that.customerDefinedName) &&
              Objects.equal(this.resourceGroups, that.resourceGroups) &&
              Objects.equal(this.uniqueId, that.uniqueId) &&
              Objects.equal(this.acl, that.acl);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(virtualMachineID, bootTime, cdroms, consumption, cpuLimitMHz, cpuShares,
              disks, dnsName, group, guest, ipAddress,
              isTemplate, nics, numCpu, numNic, numVirtualDisk,
              os, osFullName, parentHost, powerState, ramAllocatedMB,
              ramLimitMB, ramShares, storageCapacityAllocatedMB, storageCapacityUsedMB, storageCapacityUsedPc,
              storageIoAllocated, tierLevel, timestamp, hypervisorToolsStatus, hypervisorToolsVersion,
              vmHostName, correlationId, hypervisor, externalIdentifier, tenantId,
              name, customerDefinedName, resourceGroups, uniqueId, acl);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("virtualMachineID", virtualMachineID)
              .add("bootTime", bootTime)
              .add("cdroms", cdroms)
              .add("consumption", consumption)
              .add("cpuLimitMHz", cpuLimitMHz)
              .add("cpuShares", cpuShares)
              .add("disks", disks)
              .add("dnsName", dnsName)
              .add("group", group)
              .add("guest", guest)
              .add("ipAddress", ipAddress)
              .add("isTemplate", isTemplate)
              .add("nics", nics)
              .add("numCpu", numCpu)
              .add("numNic", numNic)
              .add("numVirtualDisk", numVirtualDisk)
              .add("os", os)
              .add("osFullName", osFullName)
              .add("parentHost", parentHost)
              .add("powerState", powerState)
              .add("ramAllocatedMB", ramAllocatedMB)
              .add("ramLimitMB", ramLimitMB)
              .add("ramShares", ramShares)
              .add("storageCapacityAllocatedMB", storageCapacityAllocatedMB)
              .add("storageCapacityUsedMB", storageCapacityUsedMB)
              .add("storageCapacityUsedPc", storageCapacityUsedPc)
              .add("storageIoAllocated", storageIoAllocated)
              .add("tierLevel", tierLevel)
              .add("timestamp", timestamp)
              .add("hypervisorToolsStatus", hypervisorToolsStatus)
              .add("hypervisorToolsVersion", hypervisorToolsVersion)
              .add("vmHostName", vmHostName)
              .add("correlationId", correlationId)
              .add("hypervisor", hypervisor)
              .add("externalIdentifier", externalIdentifier)
              .add("tenantId", tenantId)
              .add("name", name)
              .add("customerDefinedName", customerDefinedName)
              .add("resourceGroups", resourceGroups)
              .add("uniqueId", uniqueId)
              .add("acl", acl)
              .toString();
   }


   public static final class Builder {

      private String virtualMachineID;
      private String bootTime;
      private List<Cdrom> cdroms;
      private Consumption consumption;
      private int cpuLimitMHz;
      private int cpuShares;
      private List<Disk> disks = ImmutableList.of();
      private String dnsName;
      private String group;
      private Guest guest;
      private String ipAddress;
      private boolean isTemplate;
      private List<Nic> nics = ImmutableList.of();
      private int numCpu;
      private int numNic;
      private int numVirtualDisk;
      private String os;
      private String osFullName;
      private String parentHost;
      private String powerState;
      private int ramAllocatedMB;
      private int ramLimitMB;
      private int ramShares;
      private int storageCapacityAllocatedMB;
      private int storageCapacityUsedMB;
      private int storageCapacityUsedPc;
      private int storageIoAllocated;
      private int tierLevel;
      private String timestamp;
      private String hypervisorToolsStatus;
      private String hypervisorToolsVersion;
      private String vmHostName;
      private String correlationId;
      private Hypervisor hypervisor;
      private String externalIdentifier;
      private String tenantId;
      private String name;
      private String customerDefinedName;
      private List<String> resourceGroups = ImmutableList.of();
      private String uniqueId;
      private List<Acl> acl = ImmutableList.of();
      ///
      private String sourceTemplateId;

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

      // TODO
      public Builder sourceTemplateId(String sourceTemplateId) {
         this.sourceTemplateId = sourceTemplateId;
         return this;
      }


      public VirtualMachine build() {
         return new VirtualMachine(virtualMachineID, bootTime, cdroms, consumption, cpuLimitMHz, cpuShares,
                 disks, dnsName, group, guest, ipAddress,
                 isTemplate, nics, numCpu, numNic, numVirtualDisk,
                 os, osFullName, parentHost, powerState, ramAllocatedMB,
                 ramLimitMB, ramShares, storageCapacityAllocatedMB, storageCapacityUsedMB, storageCapacityUsedPc,
                 storageIoAllocated, tierLevel, timestamp, hypervisorToolsStatus, hypervisorToolsVersion,
                 vmHostName, correlationId, hypervisor, externalIdentifier, tenantId,
                 name, customerDefinedName, resourceGroups, uniqueId, acl, sourceTemplateId);
      }

      public Builder fromVirtualMachine(VirtualMachine in) {
         return this.name(in.getName());
      }

   }

}
