package org.jclouds.xstream.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Disk {

   @SerializedName("VirtualMachineDiskID")
   private final String virtualMachineDiskId;
   @SerializedName("StorageID")
   private final String storageID;
   @SerializedName("StorageIdentifier")
   private final String storageIdentifier;
   @SerializedName("CapacityKB")
   private final int capacityKB;
   @SerializedName("ControllerKey")
   private final String controllerKey;
   @SerializedName("DeviceKey")
   private final int deviceKey;
   @SerializedName("DiskFileName")
   private final String diskFileName;
   @SerializedName("DiskMode")
   private final int diskMode;
   @SerializedName("DiskNumber")
   private final int diskNumber;
   @SerializedName("UnitNumber")
   private final int unitNumber;
   @SerializedName("StorageProfileID")
   private final String storageProfileId;


   protected Disk(String virtualMachineDiskId, String storageID, String storageIdentifier, int capacityKB,
          String controllerKey, int deviceKey, String diskFileName, int diskMode, int diskNumber, int unitNumber,
          String storageProfileId) {
      this.virtualMachineDiskId = virtualMachineDiskId;
      this.storageID = storageID;
      this.storageIdentifier = storageIdentifier;
      this.capacityKB = capacityKB;
      this.controllerKey = controllerKey;
      this.deviceKey = deviceKey;
      this.diskFileName = diskFileName;
      this.diskMode = diskMode;
      this.diskNumber = diskNumber;
      this.unitNumber = unitNumber;
      this.storageProfileId = storageProfileId;
   }

   public String getVirtualMachineDiskId() {
      return virtualMachineDiskId;
   }

   public String getStorageID() {
      return storageID;
   }

   public String getStorageIdentifier() {
      return storageIdentifier;
   }

   public int getCapacityKB() {
      return capacityKB;
   }

   public String getControllerKey() {
      return controllerKey;
   }

   public int getDeviceKey() {
      return deviceKey;
   }

   public String getDiskFileName() {
      return diskFileName;
   }

   public int getDiskMode() {
      return diskMode;
   }

   public int getDiskNumber() {
      return diskNumber;
   }

   public int getUnitNumber() {
      return unitNumber;
   }

   public String getStorageProfileId() {
      return storageProfileId;
   }

   public static Builder builder() {
      return new Builder();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Disk that = (Disk) o;

      return Objects.equal(this.virtualMachineDiskId, that.virtualMachineDiskId) &&
              Objects.equal(this.storageID, that.storageID) &&
              Objects.equal(this.storageIdentifier, that.storageIdentifier) &&
              Objects.equal(this.capacityKB, that.capacityKB) &&
              Objects.equal(this.controllerKey, that.controllerKey) &&
              Objects.equal(this.deviceKey, that.deviceKey) &&
              Objects.equal(this.diskFileName, that.diskFileName) &&
              Objects.equal(this.diskMode, that.diskMode) &&
              Objects.equal(this.diskNumber, that.diskNumber) &&
              Objects.equal(this.unitNumber, that.unitNumber) &&
              Objects.equal(this.storageProfileId, that.storageProfileId);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(virtualMachineDiskId, storageID, storageIdentifier, capacityKB, controllerKey, deviceKey,
              diskFileName, diskMode, diskNumber, unitNumber, storageProfileId);
   }


   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("virtualMachineDiskId", virtualMachineDiskId)
              .add("storageID", storageID)
              .add("storageIdentifier", storageIdentifier)
              .add("capacityKB", capacityKB)
              .add("controllerKey", controllerKey)
              .add("deviceKey", deviceKey)
              .add("diskFileName", diskFileName)
              .add("diskMode", diskMode)
              .add("diskNumber", diskNumber)
              .add("unitNumber", unitNumber)
              .add("storageProfileId", storageProfileId)
              .toString();
   }

   public static final class Builder {
      private String virtualMachineDiskId;
      private String storageId;
      private String storageIdentifier;
      private int capacityKB;
      private String controllerKey;
      private int deviceKey;
      private String diskFileName;
      private int diskMode;
      private int diskNumber;
      private int unitNumber;
      private String storageProfileId;

      public Builder virtualMachineDiskId(String virtualMachineDiskId) {
         this.virtualMachineDiskId = virtualMachineDiskId;
         return this;
      }

      public Builder storageId(String storageId) {
         this.storageId = storageId;
         return this;
      }

      public Builder storageIdentifier(String storageIdentifier) {
         this.storageIdentifier = storageIdentifier;
         return this;
      }

      public Builder capacityKB(int capacityKB) {
         this.capacityKB = capacityKB;
         return this;
      }

      public Builder controllerKey(String controllerKey) {
         this.controllerKey = controllerKey;
         return this;
      }

      public Builder deviceKey(int deviceKey) {
         this.deviceKey = deviceKey;
         return this;
      }

      public Builder diskFileName(String diskFileName) {
         this.diskFileName = diskFileName;
         return this;
      }

      public Builder diskMode(int diskMode) {
         this.diskMode = diskMode;
         return this;
      }

      public Builder diskNumber(int diskNumber) {
         this.diskNumber = diskNumber;
         return this;
      }

      public Builder unitNumber(int unitNumber) {
         this.unitNumber = unitNumber;
         return this;
      }

      public Builder storageProfileId(String storageProfileId) {
         this.storageProfileId = storageProfileId;
         return this;
      }

      public Disk build() {
         return new Disk(virtualMachineDiskId, storageId, storageIdentifier, capacityKB, controllerKey, deviceKey,
                 diskFileName, diskMode, diskNumber, unitNumber, storageProfileId);
      }
   }
}
