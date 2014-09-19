package org.jclouds.xstream.domain;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Consumption {

   @SerializedName("VirtualMachineConsumptionID")
   private final String virtualMachineConsumptionId;
   @SerializedName("TenantID")
   private final String tenantId;
   @SerializedName("Last30DayVmuHour")
   private final float last30DayVmuHour;
   @SerializedName("Last30DayCoreTierVmuHour")
   private final float last30DayCoreTierVmuHour;
   @SerializedName("Last30DayBasicTierVmuHour")
   private final float last30DayBasicTierVmuHour;
   @SerializedName("Last7DayVmuHour")
   private final float last7DayVmuHour;
   @SerializedName("Last7DayCoreTierVmuHour")
   private final float last7DayCoreTierVmuHour;

   public Consumption(String virtualMachineConsumptionId, String tenantId, float last30DayVmuHour, float last30DayCoreTierVmuHour, float last30DayBasicTierVmuHour, float last7DayVmuHour, long last7DayCoreTierVmuHour) {
      this.virtualMachineConsumptionId = virtualMachineConsumptionId;
      this.tenantId = tenantId;
      this.last30DayVmuHour = last30DayVmuHour;
      this.last30DayCoreTierVmuHour = last30DayCoreTierVmuHour;
      this.last30DayBasicTierVmuHour = last30DayBasicTierVmuHour;
      this.last7DayVmuHour = last7DayVmuHour;
      this.last7DayCoreTierVmuHour = last7DayCoreTierVmuHour;
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("virtualMachineConsumptionId", virtualMachineConsumptionId)
              .add("tenantId", tenantId)
              .add("last30DayVmuHour", last30DayVmuHour)
              .add("last30DayCoreTierVmuHour", last30DayCoreTierVmuHour)
              .add("last30DayBasicTierVmuHour", last30DayBasicTierVmuHour)
              .add("last7DayVmuHour", last7DayVmuHour)
              .add("last7DayCoreTierVmuHour", last7DayCoreTierVmuHour)
              .toString();
   }



   /*
           "": 0.0,
           "": 0.0,
           "Last7DayBasicTierVmuHour": 0.0,
           "Last30DayAveVmu": 0.0,
           "Last30DayCoreTierAveVmu": 0.0,
           "Last30DayBasicTierAveVmu": 0.0,
           "Last7DayAveVmu": 0.0,
           "Last7DayCoreTierAveVmu": 0.0,
           "Last7DayBasicTierAveVmu": 0.0
           */
}
