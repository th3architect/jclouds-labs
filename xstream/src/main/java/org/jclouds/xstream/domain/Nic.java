package org.jclouds.xstream.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Nic {

   @SerializedName("VirtualMachineNicID")
   private final String virtualMachineNicId;
   @SerializedName("NetworkID")
   private final String networkId;
   @SerializedName("NetworkIdentifier")
   private final String networkIdentifier;
   @SerializedName("NicNumber")
   private final int nicNumber;
   @SerializedName("AdapterType")
   private final int adapterType;
   @SerializedName("DeviceKey")
   private final int deviceKey;
   @SerializedName("MacAddress")
   private final String macAddress;


   protected Nic(String virtualMachineNicId, String networkId, String networkIdentifier, int nicNumber,
                  int adapterType, int deviceKey, String macAddress) {
      this.virtualMachineNicId = virtualMachineNicId;
      this.networkId = networkId;
      this.networkIdentifier = networkIdentifier;
      this.nicNumber = nicNumber;
      this.adapterType = adapterType;
      this.deviceKey = deviceKey;
      this.macAddress = macAddress;
   }

   public String getVirtualMachineNicId() {
      return virtualMachineNicId;
   }

   public String getNetworkId() {
      return networkId;
   }

   public String getNetworkIdentifier() {
      return networkIdentifier;
   }

   public int getNicNumber() {
      return nicNumber;
   }

   public int getAdapterType() {
      return adapterType;
   }

   public int getDeviceKey() {
      return deviceKey;
   }

   public String getMacAddress() {
      return macAddress;
   }

   public static Builder builder() {
      return new Builder();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Nic that = (Nic) o;

      return Objects.equal(this.virtualMachineNicId, that.virtualMachineNicId) &&
              Objects.equal(this.networkId, that.networkId) &&
              Objects.equal(this.networkIdentifier, that.networkIdentifier) &&
              Objects.equal(this.nicNumber, that.nicNumber) &&
              Objects.equal(this.adapterType, that.adapterType) &&
              Objects.equal(this.deviceKey, that.deviceKey) &&
              Objects.equal(this.macAddress, that.macAddress);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(virtualMachineNicId, networkId, networkIdentifier, nicNumber, adapterType, deviceKey,
              macAddress);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("virtualMachineNicId", virtualMachineNicId)
              .add("networkId", networkId)
              .add("networkIdentifier", networkIdentifier)
              .add("nicNumber", nicNumber)
              .add("adapterType", adapterType)
              .add("deviceKey", deviceKey)
              .add("macAddress", macAddress)
              .toString();
   }


   public static final class Builder {
      private String virtualMachineNicId;
      private String networkId;
      private String networkIdentifier;
      private int nicNumber;
      private int adapterType;
      private int deviceKey;
      private String macAddress;

      public Builder virtualMachineNicId(String virtualMachineNicId) {
         this.virtualMachineNicId = virtualMachineNicId;
         return this;
      }

      public Builder networkId(String networkId) {
         this.networkId = networkId;
         return this;
      }

      public Builder networkIdentifier(String networkIdentifier) {
         this.networkIdentifier = networkIdentifier;
         return this;
      }

      public Builder nicNumber(int nicNumber) {
         this.nicNumber = nicNumber;
         return this;
      }

      public Builder adapterType(int adapterType) {
         this.adapterType = adapterType;
         return this;
      }

      public Builder deviceKey(int deviceKey) {
         this.deviceKey = deviceKey;
         return this;
      }

      public Builder macAddress(String macAddress) {
         this.macAddress = macAddress;
         return this;
      }

      public Nic build() {
         return new Nic(virtualMachineNicId, networkId, networkIdentifier, nicNumber, adapterType, deviceKey,
                 macAddress);
      }
   }
}
