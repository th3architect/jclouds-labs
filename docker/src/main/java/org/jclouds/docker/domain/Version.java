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

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class Version {

   @SerializedName("ApiVersion")
   private final String apiVersion;
   @SerializedName("Version")
   private final String version;
   @SerializedName("GitCommit")
   private final String gitCommit;
   @SerializedName("GoVersion")
   private final String goVersion;

   @ConstructorProperties({"ApiVersion", "Version", "GitCommit", "GoVersion"})
   protected Version(String apiVersion, String version, String gitCommit, String goVersion) {
      this.apiVersion = checkNotNull(apiVersion, "apiVersion");
      this.version = checkNotNull(version, "version");
      this.gitCommit = checkNotNull(gitCommit, "gitCommit");
      this.goVersion = checkNotNull(goVersion, "goVersion");
   }

   public String getApiVersion() {
      return apiVersion;
   }

   public String getVersion() {
      return version;
   }

   public String getGitCommit() {
      return gitCommit;
   }

   public String getGoVersion() {
      return goVersion;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Version that = (Version) o;

      return Objects.equal(this.apiVersion, that.apiVersion) &&
              Objects.equal(this.version, that.version) &&
              Objects.equal(this.gitCommit, that.gitCommit) &&
              Objects.equal(this.goVersion, that.goVersion);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(apiVersion, version, gitCommit, goVersion);
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromVersion(this);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("apiVersion", apiVersion)
              .add("version", version)
              .add("gitCommit", gitCommit)
              .add("goVersion", goVersion)
              .toString();
   }

   public static final class Builder {

      private String apiVersion;
      private String version;
      private String gitCommit;
      private String goVersion;

      public Builder apiVersion(String apiVersion) {
         this.apiVersion = apiVersion;
         return this;
      }

      public Builder version(String version) {
         this.version = version;
         return this;
      }

      public Builder gitCommit(String gitCommit) {
         this.gitCommit = gitCommit;
         return this;
      }

      public Builder goVersion(String goVersion) {
         this.goVersion = goVersion;
         return this;
      }

      public Version build() {
         return new Version(apiVersion, version, gitCommit, goVersion);
      }

      public Builder fromVersion(Version in) {
         return this
                 .apiVersion(in.getApiVersion())
                 .version(in.getVersion())
                 .gitCommit(in.getGitCommit())
                 .goVersion(in.getGoVersion());
      }
   }
}
