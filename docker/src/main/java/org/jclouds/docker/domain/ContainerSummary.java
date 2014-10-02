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
import java.beans.ConstructorProperties;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;

public class ContainerSummary {

   @SerializedName("Id")
   private final String id;
   @SerializedName("Names")
   private final List<String> names;
   @SerializedName("Created")
   private final String created;
   @SerializedName("Image")
   private final String image;
   @SerializedName("Command")
   private final String command;
   @SerializedName("Ports")
   private final List<Port> ports;
   @SerializedName("Status")
   private final String status;

   @ConstructorProperties({"Id", "Names", "Created", "Image", "Command", "Ports", "Status"})
   protected ContainerSummary(String id, List<String> names, String created, String image, String command, List<Port> ports, String status) {
      this.id = checkNotNull(id, "id");
      this.names = names != null ? ImmutableList.copyOf(names) : ImmutableList.<String>of();
      this.created = checkNotNull(created, "created");
      this.image = checkNotNull(image, "image");
      this.command = checkNotNull(command, "command");
      this.ports = ports != null ? ImmutableList.copyOf(ports) : ImmutableList.<Port>of();
      this.status = checkNotNull(status, "status");
   }

   public String getId() {
      return id;
   }

   public List<String> getNames() {
      return names;
   }

   public String getCreated() {
      return created;
   }

   public String getImage() {
      return image;
   }

   public String getCommand() {
      return command;
   }

   public List<Port> getPorts() {
      return ports;
   }

   public String getStatus() {
      return status;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ContainerSummary that = (ContainerSummary) o;

      return Objects.equal(this.id, that.id) &&
              Objects.equal(this.names, that.names) &&
              Objects.equal(this.created, that.created) &&
              Objects.equal(this.image, that.image) &&
              Objects.equal(this.command, that.command) &&
              Objects.equal(this.ports, that.ports) &&
              Objects.equal(this.status, that.status);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, names, created, image, command, ports,
              status);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("id", id)
              .add("names", names)
              .add("created", created)
              .add("image", image)
              .add("command", command)
              .add("ports", ports)
              .add("status", status)
              .toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromContainerSummary(this);
   }

   public static final class Builder {

      private String id;
      private List<String> names = ImmutableList.of();
      private String created;
      private String image;
      private String command;
      private List<Port> ports = ImmutableList.of();
      private String status;

      public Builder id(String id) {
         this.id = id;
         return this;
      }

      public Builder names(List<String> names) {
         this.names = names;
         return this;
      }

      public Builder created(String created) {
         this.created = created;
         return this;
      }

      public Builder image(String imageName) {
         this.image = imageName;
         return this;
      }

      public Builder command(String command) {
         this.command = command;
         return this;
      }

      public Builder ports(List<Port> ports) {
         this.ports = ports;
         return this;
      }

      public Builder status(String status) {
         this.status = status;
         return this;
      }

      public ContainerSummary build() {
         return new ContainerSummary(id, names, created, image, command, ports, status);
      }

      public Builder fromContainerSummary(ContainerSummary in) {
         return this
                 .id(in.getId())
                 .names(in.getNames())
                 .created(in.getCreated())
                 .image(in.getImage())
                 .command(in.getCommand())
                 .ports(in.getPorts())
                 .status(in.getStatus());
      }
   }
}
