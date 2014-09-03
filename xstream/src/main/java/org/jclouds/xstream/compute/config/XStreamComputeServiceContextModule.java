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
package org.jclouds.xstream.compute.config;

import com.google.common.base.Function;
import com.google.inject.TypeLiteral;
import org.jclouds.compute.ComputeServiceAdapter;
import org.jclouds.compute.config.ComputeServiceAdapterContextModule;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.xstream.compute.functions.VirtualMachineToNodeMetadata;
import org.jclouds.xstream.compute.functions.VirtualMachineToImage;
import org.jclouds.xstream.compute.functions.StateToStatus;
import org.jclouds.xstream.compute.options.XStreamTemplateOptions;
import org.jclouds.xstream.compute.strategy.XStreamComputeServiceAdapter;
import org.jclouds.xstream.domain.State;
import org.jclouds.domain.Location;
import org.jclouds.functions.IdentityFunction;
import org.jclouds.xstream.domain.VirtualMachine;

public class XStreamComputeServiceContextModule extends
        ComputeServiceAdapterContextModule<VirtualMachine, Hardware, VirtualMachine, Location> {

   @SuppressWarnings("unchecked")
   @Override
   protected void configure() {
      super.configure();
      bind(new TypeLiteral<ComputeServiceAdapter<VirtualMachine, Hardware, VirtualMachine, Location>>() {
      }).to(XStreamComputeServiceAdapter.class);
      bind(new TypeLiteral<Function<VirtualMachine, NodeMetadata>>() {
      }).to(VirtualMachineToNodeMetadata.class);
      bind(new TypeLiteral<Function<VirtualMachine, Image>>() {
      }).to(VirtualMachineToImage.class);
      bind(new TypeLiteral<Function<Hardware, Hardware>>() {
      }).to(Class.class.cast(IdentityFunction.class));
      bind(new TypeLiteral<Function<Location, Location>>() {
      }).to(Class.class.cast(IdentityFunction.class));
      bind(new TypeLiteral<Function<State, NodeMetadata.Status>>() {
      }).to(StateToStatus.class);
      bind(TemplateOptions.class).to(XStreamTemplateOptions.class);
   }

}
