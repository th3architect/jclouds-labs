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

<<<<<<< HEAD
import static org.jclouds.docker.internal.NullSafeCopies.copyOf;
=======
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.SerializedName;
import org.jclouds.javax.annotation.Nullable;
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

import java.util.List;

import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.SerializedNames;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Image {
   public abstract String id();

   @Nullable public abstract String parent();

   @Nullable public abstract String created();

   @Nullable public abstract String container();

   @Nullable public abstract String dockerVersion();

   @Nullable public abstract String architecture();

   @Nullable public abstract String os();

   public abstract long size();

   @Nullable public abstract long virtualSize();

<<<<<<< HEAD
   public abstract List<String> repoTags();
=======
   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("id", id)
              .add("parent", parent)
              .add("created", created)
              .add("container", container)
              .add("dockerVersion", dockerVersion)
              .add("architecture", architecture)
              .add("os", os)
              .add("size", size)
              .add("virtualSize", virtualSize)
              .add("repoTags", repoTags)
              .toString();
   }
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

   @SerializedNames({ "Id", "Parent", "Created", "Container", "DockerVersion", "Architecture", "Os", "Size",
         "VirtualSize", "RepoTags" })
   public static Image create(String id, String parent, String created, String container, String dockerVersion,
         String architecture, String os, long size, long virtualSize, List<String> repoTags) {
      return new AutoValue_Image(id, parent, created, container, dockerVersion, architecture, os, size, virtualSize,
            copyOf(repoTags));
   }

}
