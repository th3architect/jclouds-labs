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
package org.jclouds.azurecompute.compute.config;

import org.jclouds.azurecompute.compute.AzureComputeServiceAdapter;
import org.jclouds.azurecompute.compute.functions.DeploymentToNodeMetadata;
import org.jclouds.azurecompute.compute.functions.LocationToLocation;
import org.jclouds.azurecompute.compute.functions.OSImageToImage;
import org.jclouds.azurecompute.compute.functions.RoleSizeToHardware;
import org.jclouds.azurecompute.compute.strategy.UseNodeCredentialsButOverrideFromTemplate;
import org.jclouds.azurecompute.domain.Deployment;
import org.jclouds.azurecompute.domain.Location;
import org.jclouds.azurecompute.domain.OSImage;
import org.jclouds.azurecompute.domain.RoleSize;
import org.jclouds.azurecompute.options.AzureComputeTemplateOptions;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.config.ComputeServiceAdapterContextModule;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.compute.strategy.PrioritizeCredentialsFromTemplate;

import com.google.common.base.Function;
import com.google.inject.TypeLiteral;

public class AzureComputeServiceContextModule
      extends ComputeServiceAdapterContextModule<Deployment, RoleSize, OSImage, Location> {

   @Override
   protected void configure() {
      super.configure();
      bind(new TypeLiteral<ComputeServiceAdapter<Deployment, RoleSize, OSImage, Location>>() {
      }).to(AzureComputeServiceAdapter.class);
      bind(new TypeLiteral<Function<OSImage, org.jclouds.compute.domain.Image>>() {
      }).to(OSImageToImage.class);
      bind(new TypeLiteral<Function<RoleSize, Hardware>>() {
      }).to(RoleSizeToHardware.class);
      bind(new TypeLiteral<Function<Deployment, NodeMetadata>>() {
      }).to(DeploymentToNodeMetadata.class);

      bind(PrioritizeCredentialsFromTemplate.class).to(UseNodeCredentialsButOverrideFromTemplate.class);
      bind(new TypeLiteral<Function<Location, org.jclouds.domain.Location>>() {
      }).to(LocationToLocation.class);
      // to have the compute service adapter override default locations
      install(new LocationsFromComputeServiceAdapterModule<Deployment, RoleSize, OSImage, Location>(){});

      bind(TemplateOptions.class).to(AzureComputeTemplateOptions.class);
   }
}
