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

import static org.jclouds.docker.internal.NullSafeCopies.copyOf;

import java.util.List;

<<<<<<< HEAD
import org.jclouds.json.SerializedNames;
=======
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.annotations.SerializedName;
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ExposedPorts {
   public abstract String portAndProtocol();

<<<<<<< HEAD
   public abstract List<String> hostPorts();
=======
   @ConstructorProperties({ "PortAndProtocol", "HostPorts" })
   protected ExposedPorts(String portAndProtocol, @Nullable Set<String> hostPorts) {
      this.portAndProtocol = checkNotNull(portAndProtocol, "portAndProtocol");
      this.hostPorts = hostPorts != null ? ImmutableSet.copyOf(hostPorts) : ImmutableSet.<String> of();
   }

   public String getPortAndProtocol() {
      return portAndProtocol;
   }

   public Set<String> getHostPorts() {
      return hostPorts;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ExposedPorts that = (ExposedPorts) o;

      return Objects.equal(this.portAndProtocol, that.portAndProtocol) &&
             Objects.equal(this.hostPorts, that.hostPorts);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(portAndProtocol, hostPorts);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("portAndProtocol", portAndProtocol)
              .add("hostPorts", hostPorts)
              .toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromExposedPorts(this);
   }

   public static final class Builder {

      private String portAndProtocol;
      private Set<String> hostPorts = ImmutableSet.of();

      public Builder portAndProtocol(String portAndProtocol) {
         this.portAndProtocol = portAndProtocol;
         return this;
      }

      public Builder hostPorts(Set<String> hostPorts) {
         this.hostPorts = ImmutableSet.copyOf(checkNotNull(hostPorts, "hostPorts"));
         return this;
      }

      public ExposedPorts build() {
         return new ExposedPorts(portAndProtocol, hostPorts);
      }
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

   @SerializedNames({ "PortAndProtocol", "HostPorts" })
   public static ExposedPorts create(String portAndProtocol, List<String> hostPorts) {
      return new AutoValue_ExposedPorts(portAndProtocol, copyOf(hostPorts));
   }
}
