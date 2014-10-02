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
package org.jclouds.docker.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Info {

   @SerializedName("Containers")
   private int containers;
   @SerializedName("Images")
   private int images;
   @SerializedName("Driver")
   private String driver;
   @SerializedName("ExecutionDriver")
   private String executionDriver;
   @SerializedName("KernelVersion")
   private String kernelVersion;
   @SerializedName("Debug")
   private int debug;
   @SerializedName("NFd")
   private int NFd;
   @SerializedName("NGoroutines")
   private int NGoroutines;
   @SerializedName("NEventsListener")
   private int nEventsListener;
   @SerializedName("InitPath")
   private String initPath;
   @SerializedName("IndexServerAddress")
   private String indexServerAddress;
   @SerializedName("MemoryLimit")
   private int memoryLimit;
   @SerializedName("SwapLimit")
   private int swapLimit;
   @SerializedName("IPv4Forwarding")
   private int iPv4Forwarding;

   protected Info(int containers, int images, String driver, String executionDriver, String kernelVersion, int debug,
                  int NFd, int NGoroutines, int nEventsListener, String initPath, String indexServerAddress,
                  int memoryLimit, int swapLimit, int iPv4Forwarding) {
      this.containers = containers;
      this.images = images;
      this.driver = checkNotNull(driver, "driver");
      this.executionDriver = checkNotNull(executionDriver, "executionDriver");
      this.kernelVersion = checkNotNull(kernelVersion, "kernelVersion");
      this.debug = debug;
      this.NFd = NFd;
      this.NGoroutines = NGoroutines;
      this.nEventsListener = nEventsListener;
      this.initPath = checkNotNull(initPath, "initPath");
      this.indexServerAddress = checkNotNull(indexServerAddress, "indexServerAddress");
      this.memoryLimit = memoryLimit;
      this.swapLimit = swapLimit;
      this.iPv4Forwarding = iPv4Forwarding;
   }

   public int getContainers() {
      return containers;
   }

   public int getImages() {
      return images;
   }

   public String getDriver() {
      return driver;
   }

   public String getExecutionDriver() {
      return executionDriver;
   }

   public String getKernelVersion() {
      return kernelVersion;
   }

   public int getDebug() {
      return debug;
   }

   public int getNFd() {
      return NFd;
   }

   public int getNGoroutines() {
      return NGoroutines;
   }

   public int getnEventsListener() {
      return nEventsListener;
   }

   public String getInitPath() {
      return initPath;
   }

   public String getIndexServerAddress() {
      return indexServerAddress;
   }

   public int getMemoryLimit() {
      return memoryLimit;
   }

   public int getSwapLimit() {
      return swapLimit;
   }

   public int getiPv4Forwarding() {
      return iPv4Forwarding;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Info that = (Info) o;

      return Objects.equal(this.containers, that.containers) &&
              Objects.equal(this.images, that.images) &&
              Objects.equal(this.driver, that.driver) &&
              Objects.equal(this.executionDriver, that.executionDriver) &&
              Objects.equal(this.kernelVersion, that.kernelVersion) &&
              Objects.equal(this.debug, that.debug) &&
              Objects.equal(this.NFd, that.NFd) &&
              Objects.equal(this.NGoroutines, that.NGoroutines) &&
              Objects.equal(this.nEventsListener, that.nEventsListener) &&
              Objects.equal(this.initPath, that.initPath) &&
              Objects.equal(this.indexServerAddress, that.indexServerAddress) &&
              Objects.equal(this.memoryLimit, that.memoryLimit) &&
              Objects.equal(this.swapLimit, that.swapLimit) &&
              Objects.equal(this.iPv4Forwarding, that.iPv4Forwarding);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(containers, images, driver, executionDriver, kernelVersion, debug,
              NFd, NGoroutines, nEventsListener, initPath, indexServerAddress,
              memoryLimit, swapLimit, iPv4Forwarding);
   }


   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("containers", containers)
              .add("images", images)
              .add("driver", driver)
              .add("executionDriver", executionDriver)
              .add("kernelVersion", kernelVersion)
              .add("debug", debug)
              .add("NFd", NFd)
              .add("NGoroutines", NGoroutines)
              .add("nEventsListener", nEventsListener)
              .add("initPath", initPath)
              .add("indexServerAddress", indexServerAddress)
              .add("memoryLimit", memoryLimit)
              .add("swapLimit", swapLimit)
              .add("iPv4Forwarding", iPv4Forwarding)
              .toString();
   }
}
