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
package org.jclouds.xstream;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.Constants;
import org.jclouds.apis.ApiMetadata;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.rest.internal.BaseHttpApiMetadata;
import org.jclouds.xstream.compute.config.XStreamComputeServiceContextModule;
import org.jclouds.xstream.config.XStreamHttpApiModule;

import java.net.URI;
import java.util.Properties;

import static org.jclouds.reflect.Reflection2.typeToken;

/**
 * Implementation of {@link BaseHttpApiMetadata} for the Virtustream xStream API
 */
public class XStreamApiMetadata extends BaseHttpApiMetadata<XStreamApi> {

   @Override
   public Builder toBuilder() {
      return new Builder().fromApiMetadata(this);
   }

   public XStreamApiMetadata() {
      this(new Builder());
   }

   protected XStreamApiMetadata(Builder builder) {
      super(builder);
   }

   public static Properties defaultProperties() {
      Properties properties = BaseHttpApiMetadata.defaultProperties();
      properties.setProperty(Constants.PROPERTY_MAX_RETRIES, "15");
      properties.setProperty("jclouds.ssh.retry-auth", "true");
      //properties.setProperty(Constants.PROPERTY_CONNECTION_TIMEOUT, "1200000"); // 15 minutes
      //properties.setProperty(TEMPLATE, "osFamily=UBUNTU,os64Bit=true,osVersionMatches=1[012].[01][04]");
      return properties;
   }

   public static class Builder extends BaseHttpApiMetadata.Builder<XStreamApi, Builder> {

      protected Builder() {
         super(XStreamApi.class);
         id("xstream")
                 .name("XStream API")
                 .identityName("user")
                 .credentialName("password")
                 .documentation(URI.create("https://10.12.227.9"))
                 .version("1.3")
                 .defaultEndpoint("https://10.12.227.9/api/v1.3/")
                 .defaultProperties(XStreamApiMetadata.defaultProperties())
                 .view(typeToken(ComputeServiceContext.class))
                 .defaultModules(ImmutableSet.<Class<? extends Module>>of(
                         XStreamHttpApiModule.class,
                         XStreamComputeServiceContextModule.class));
      }

      @Override
      public XStreamApiMetadata build() {
         return new XStreamApiMetadata(this);
      }

      @Override
      protected Builder self() {
         return this;
      }

      @Override
      public Builder fromApiMetadata(ApiMetadata in) {
         return this;
      }
   }
}
