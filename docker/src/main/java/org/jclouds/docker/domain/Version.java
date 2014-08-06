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

import org.jclouds.json.SerializedNames;

<<<<<<< HEAD
import com.google.auto.value.AutoValue;
=======
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

@AutoValue
public abstract class Version {

<<<<<<< HEAD
   public abstract String apiVersion();
=======
   @ConstructorProperties({ "Arch", "GitCommit", "GoVersion", "KernelVersion", "Os", "Version" })
   protected Version(String arch, String gitCommit, String goVersion, String kernelVersion, String os, String version) {
      this.arch = checkNotNull(arch, "arch");
      this.gitCommit = checkNotNull(gitCommit, "gitCommit");
      this.goVersion = checkNotNull(goVersion, "goVersion");
      this.kernelVersion = checkNotNull(kernelVersion, "kernelVersion");
      this.os = checkNotNull(os, "os");
      this.version = checkNotNull(version, "version");
   }

   public String getArch() {
      return arch;
   }

   public String getGitCommit() {
      return gitCommit;
   }

   public String getGoVersion() {
      return goVersion;
   }

   public String getKernelVersion() {
      return kernelVersion;
   }

   public String getOs() {
      return os;
   }

   public String getVersion() {
      return version;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Version that = (Version) o;

      return Objects.equal(this.arch, that.arch) &&
              Objects.equal(this.gitCommit, that.gitCommit) &&
              Objects.equal(this.goVersion, that.goVersion) &&
              Objects.equal(this.kernelVersion, that.kernelVersion) &&
              Objects.equal(this.os, that.os) &&
              Objects.equal(this.version, that.version);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(arch, gitCommit, goVersion, kernelVersion, os, version);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("arch", arch)
              .add("gitCommit", gitCommit)
              .add("goVersion", goVersion)
              .add("kernelVersion", kernelVersion)
              .add("os", os)
              .add("version", version)
              .toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromVersion(this);
   }

   public static final class Builder {

      private String arch;
      private String gitCommit;
      private String goVersion;
      private String kernelVersion;
      private String os;
      private String version;

      public Builder arch(String arch) {
         this.arch = arch;
         return this;
      }
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

   public abstract String arch();

   public abstract String gitCommit();

   public abstract String goVersion();

   public abstract String kernelVersion();

   public abstract String os();

   public abstract String version();

   @SerializedNames({ "ApiVersion", "Arch", "GitCommit", "GoVersion", "KernelVersion", "Os", "Version" })
   public static Version create(String apiVersion, String arch, String gitCommit, String goVersion,
                                String kernelVersion, String os, String version) {
      return new AutoValue_Version(apiVersion, arch, gitCommit, goVersion, kernelVersion, os, version);
   }
}
