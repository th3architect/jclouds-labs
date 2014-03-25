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

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Andrea Turli
 */
public class Container {

   @SerializedName("Id")
   final private String id;
   @SerializedName("Name")
   final private String name;
   @SerializedName("Created")
   final private String created;
   @SerializedName("Path")
   final private String path;
   @SerializedName("Args")
   final private String[] args;
   @SerializedName("Config")
   final private Config config;
   @SerializedName("State")
   final private State state;
   @SerializedName("Image")
   final private String image;
   @SerializedName("NetworkSettings")
   final private NetworkSettings networkSettings;
   @SerializedName("ResolvConfPath")
   final private String resolvConfPath;
   @SerializedName("Driver")
   final private String driver;
   @SerializedName("ExecDriver")
   final private String execDriver;
   @SerializedName("Volumes")
   final private Map<String, String> volumes;
   @SerializedName("VolumesRW")
   final private Map<String, Boolean> volumesRw;
   @SerializedName("Command")
   final private String command;
   @SerializedName("Status")
   final private String status;
   @SerializedName("HostConfig")
   final private HostConfig hostConfig;
   @SerializedName("Ports")
   final private List<Port> ports;

   @ConstructorProperties({ "Id", "Name", "Created", "Path", "Args", "Config", "State", "Image", "NetworkSettings",
           "ResolvConfPath", "Driver", "ExecDriver", "Volumes", "VolumesRW", "Command", "Status", "HostConfig", "Ports" })
   public Container(String id, String name, String created, String path, String[] args, Config config, State state,
                    String image,  NetworkSettings networkSettings, String resolvConfPath,
                    String driver, String execDriver, Map<String, String> volumes, Map<String, Boolean> volumesRW,
                    String command, String status, HostConfig hostConfig, List<Port> ports) {
      this.id = checkNotNull(id, "id");
      this.name = checkNotNull(name, "name");
      this.created = checkNotNull(created, "created");
      this.path = checkNotNull(path, "path");
      this.args = checkNotNull(args, "args");
      this.config = checkNotNull(config, "config");
      this.state = checkNotNull(state, "state");
      this.image = checkNotNull(image, "image");
      this.networkSettings = checkNotNull(networkSettings, "networkSettings");
      this.resolvConfPath = checkNotNull(resolvConfPath, "resolvConfPath");
      this.driver = checkNotNull(driver, "driver");
      this.execDriver = checkNotNull(execDriver, "execDriver");
      this.volumes = checkNotNull(volumes, "volumes");
      this.volumesRw = checkNotNull(volumesRW, "volumesRW");
      this.command = checkNotNull(command, "command");
      this.status = checkNotNull(status, "status");
      this.hostConfig = checkNotNull(hostConfig, "hostConfig");
      this.ports = checkNotNull(ports, "ports");
   }

   public String getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public String getCreated() {
      return created;
   }

   public String getPath() {
      return path;
   }

   public String[] getArgs() {
      return args;
   }

   public Config getConfig() {
      return config;
   }

   public State getState() {
      return state;
   }

   public String getImage() {
      return image;
   }

   public NetworkSettings getNetworkSettings() {
      return networkSettings;
   }

   public String getResolvConfPath() {
      return resolvConfPath;
   }

   public String getDriver() {
      return driver;
   }

   public String getExecDriver() {
      return execDriver;
   }

   public Map<String, String> getVolumes() {
      return volumes;
   }

   public Map<String, Boolean> getVolumesRw() {
      return volumesRw;
   }

   public String getCommand() {
      return command;
   }

   public String getStatus() {
      return status;
   }

   public HostConfig getHostConfig() {
      return hostConfig;
   }

   public List<Port> getPorts() {
      return ports;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Container that = (Container) o;

      return Objects.equal(this.id, that.id) &&
              Objects.equal(this.name, that.name) &&
              Objects.equal(this.created, that.created) &&
              Objects.equal(this.path, that.path) &&
              Objects.equal(this.args, that.args) &&
              Objects.equal(this.config, that.config) &&
              Objects.equal(this.state, that.state) &&
              Objects.equal(this.image, that.image) &&
              Objects.equal(this.networkSettings, that.networkSettings) &&
              Objects.equal(this.resolvConfPath, that.resolvConfPath) &&
              Objects.equal(this.driver, that.driver) &&
              Objects.equal(this.execDriver, that.execDriver) &&
              Objects.equal(this.volumes, that.volumes) &&
              Objects.equal(this.volumesRw, that.volumesRw) &&
              Objects.equal(this.command, that.command) &&
              Objects.equal(this.status, that.status) &&
              Objects.equal(this.hostConfig, that.hostConfig) &&
              Objects.equal(this.ports, that.ports);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, name, created, path, args, config, state, image, networkSettings, resolvConfPath,
              driver, execDriver, volumes, volumesRw, command, status, hostConfig, ports);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("id", id)
              .add("name", name)
              .add("created", created)
              .add("path", path)
              .add("args", args)
              .add("config", config)
              .add("state", state)
              .add("image", image)
              .add("networkSettings", networkSettings)
              .add("resolvConfPath", resolvConfPath)
              .add("driver", driver)
              .add("execDriver", execDriver)
              .add("volumes", volumes)
              .add("volumesRw", volumesRw)
              .add("command", command)
              .add("status", status)
              .add("hostConfig", hostConfig)
              .add("ports", ports)
              .toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromContainer(this);
   }

   public static final class Builder {

      private String id;
      private String name;
      private String created;
      private String path;
      private String[] args;
      private Config config;
      private State state;
      private String image;
      private NetworkSettings networkSettings;
      private String resolvConfPath;
      private String driver;
      private String execDriver;
      private Map<String, String> volumes = ImmutableMap.of();
      private Map<String, Boolean> volumesRw = ImmutableMap.of();
      private String command;
      private String status;
      private HostConfig hostConfig;
      private List<Port> ports = ImmutableList.of();

      public Builder id(String id) {
         this.id = id;
         return this;
      }

      public Builder name(String name) {
         this.name = name;
         return this;
      }

      public Builder created(String created) {
         this.created = created;
         return this;
      }

      public Builder path(String path) {
         this.path = path;
         return this;
      }

      public Builder args(String[] args) {
         this.args = args;
         return this;
      }

      public Builder config(Config config) {
         this.config = config;
         return this;
      }

      public Builder state(State state) {
         this.state = state;
         return this;
      }

      public Builder image(String imageName) {
         this.image = imageName;
         return this;
      }

      public Builder networkSettings(NetworkSettings networkSettings) {
         this.networkSettings = networkSettings;
         return this;
      }

      public Builder resolvConfPath(String resolvConfPath) {
         this.resolvConfPath = resolvConfPath;
         return this;
      }

      public Builder driver(String driver) {
         this.driver = driver;
         return this;
      }

      public Builder execDriver(String execDriver) {
         this.execDriver = execDriver;
         return this;
      }

      public Builder volumes(Map<String, String> volumes) {
         this.volumes = volumes;
         return this;
      }

      public Builder volumesRw(Map<String, Boolean> volumesRw) {
         this.volumesRw = volumesRw;
         return this;
      }

      public Builder command(String command) {
         this.command = command;
         return this;
      }

      public Builder status(String status) {
         this.status = status;
         return this;
      }

      public Builder hostConfig(HostConfig hostConfig) {
         this.hostConfig = hostConfig;
         return this;
      }

      public Builder ports(List<Port> ports) {
         this.ports = ports;
         return this;
      }

      public Container build() {
         return new Container(id, name, created, path, args, config, state, image, networkSettings, resolvConfPath,
                 driver, execDriver, volumes, volumesRw, command, status, hostConfig, ports);
      }

      public Builder fromContainer(Container in) {
         return this
                 .id(in.getId())
                 .name(in.getName())
                 .created(in.getCreated())
                 .path(in.getPath())
                 .args(in.getArgs())
                 .config(in.getConfig())
                 .state(in.getState())
                 .image(in.getImage())
                 .networkSettings(in.getNetworkSettings())
                 .resolvConfPath(in.getResolvConfPath())
                 .driver(in.getDriver())
                 .execDriver(in.getExecDriver())
                 .volumes(in.getVolumes())
                 .volumesRw(in.getVolumesRw())
                 .command(in.getCommand())
                 .status(in.getStatus())
                 .hostConfig(in.getHostConfig())
                 .ports(in.getPorts());
      }
   }
}
