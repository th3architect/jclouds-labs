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
package org.jclouds.xstream.features;

import org.jclouds.xstream.compute.BaseXStreamApiLiveTest;
import org.jclouds.xstream.domain.Config;
import org.jclouds.xstream.domain.Task;
import org.jclouds.xstream.domain.VirtualMachine;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Test(groups = "live", testName = "RemoteApiLiveTest", singleThreaded = true)
public class VirtualMachineApiLiveTest extends BaseXStreamApiLiveTest {

   private VirtualMachine virtualMachine = null;

   public void listTemplates() {
      assertNotNull(api().listTemplates());
   }

   @Test(dependsOnMethods = "testListImages")
   public void testVirtualMachine() throws IOException, InterruptedException {
      Config vmConfig = Config.builder()
              .build();
      Task creationTask = api().createVirtualMachine(vmConfig);
      assertNotNull(virtualMachine);
      assertNotNull(virtualMachine.getVirtualMachineID());
   }

   @Test(dependsOnMethods = "testVirtualMachine")
   public void testPowerOn() throws IOException, InterruptedException {
      api().powerOn(virtualMachine.getVirtualMachineID());
      assertTrue(api().getVirtualMachine(virtualMachine.getVirtualMachineID()).getPowerState().equals("powerOn"));
   }

   @Test(dependsOnMethods = "testPowerOn")
   public void testPowerOff() {
      api().powerOff(virtualMachine.getVirtualMachineID());
      assertFalse(api().getVirtualMachine(virtualMachine.getVirtualMachineID()).getPowerState().equals("powerOn"));
   }

   @Test(dependsOnMethods = "testPowerOff", expectedExceptions = NullPointerException.class)
   public void testRemoveVirtualMachine() {
      api().removeVirtualMachine(virtualMachine.getVirtualMachineID());
      assertFalse(api().getVirtualMachine(virtualMachine.getVirtualMachineID()).getPowerState().equals("powerOn"));
   }

   private VirtualMachineApi api() {
      return api.getVirtualMachineApi();
   }

}
