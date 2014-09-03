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
package org.jclouds.docker.features;

import com.google.common.collect.ImmutableMultimap;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.jclouds.docker.internal.BaseXStreamMockTest;
import org.jclouds.xstream.XStreamApi;
import org.jclouds.xstream.domain.VirtualMachine;
import org.jclouds.xstream.features.VirtualMachineApi;
import org.jclouds.rest.ResourceNotFoundException;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Mock tests for the {@link org.jclouds.xstream.XStreamApi} class.
 */
@Test(groups = "unit", testName = "VirtualMachineApiMockTest")
public class VirtualMachineApiMockTest extends BaseXStreamMockTest {

   public void testListVirtualMachines() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setBody(payloadFromResource("/virtualMachines.json")));

      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();

      try {
         Set<VirtualMachine> vms = remoteApi.listVirtualMachines();
         assertRequestHasCommonFields(server.takeRequest(), "/VirtualMachine?$filter=");
         assertEquals(vms.size(), 1);
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testListNonexistentVirtualMachines() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(404));

      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();

      try {
         Set<VirtualMachine> vms = remoteApi.listVirtualMachines();
         assertRequestHasCommonFields(server.takeRequest(), "/VirtualMachine?$filter=");
         assertTrue(vms.isEmpty());
      } finally {
         api.close();
         server.shutdown();
      }
   }


   @Test(timeOut = 10000l)
   public void testListAllVirtualMachines() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setBody(payloadFromResource("/virtualMachines.json")));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      try {
         Set<VirtualMachine> virtualMachines = remoteApi.listVirtualMachines();
         assertRequestHasParameters(server.takeRequest(), "/virtualMachines/json", ImmutableMultimap.of("all", "true"));
         assertEquals(virtualMachines.size(), 1);
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testGetVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setBody(payloadFromResource("/virtualMachine.json")));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      String virtualMachineId = "b03d4cd15b76f8876110615cdeed15eadf77c9beb408d62f1687dcc69192cd6d";
      try {
         VirtualMachine virtualMachine = remoteApi.getVirtualMachine(virtualMachineId);
         assertRequestHasCommonFields(server.takeRequest(), "/virtualMachines/" + virtualMachineId + "/json");
         assertNotNull(virtualMachine);
         assertNotNull(virtualMachine.getId(), virtualMachineId);
         assertNotNull(virtualMachine.getHostConfig());
         assertEquals(virtualMachine.getName(), "/tender_lumiere");
         assertEquals(virtualMachine.getState().isRunning(), true);
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testGetNonExistingVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(404));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      String virtualMachineId = "notExisting";
      try {
         VirtualMachine virtualMachine = remoteApi.getVirtualMachine(virtualMachineId);
         assertRequestHasCommonFields(server.takeRequest(), "/virtualMachines/" + virtualMachineId + "/json");
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testCreateVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setBody(payloadFromResource("/virtualMachine-creation.json")));

      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      VirtualMachine virtualMachineConfig = VirtualMachine.builder()
              .build();
      try {
         VirtualMachine virtualMachine = remoteApi.createVirtualMachine(virtualMachineConfig);
         assertRequestHasCommonFields(server.takeRequest(), "POST", "/virtualMachines/create?name=test");
         assertNotNull(virtualMachine);
         assertEquals(virtualMachine.getId(), "c6c74153ae4b1d1633d68890a68d89c40aa5e284a1ea016cbc6ef0e634ee37b2");
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testRemoveVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(204));

      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      String virtualMachineId = "6d35806c1bd2b25cd92bba2d2c2c5169dc2156f53ab45c2b62d76e2d2fee14a9";

      try {
         remoteApi.removeVirtualMachine(virtualMachineId);
         assertRequestHasCommonFields(server.takeRequest(), "DELETE", "/virtualMachines/" + virtualMachineId);
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testRemoveNonExistingVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(404));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      String virtualMachineId = "nonExisting";
      try {
         remoteApi.removeVirtualMachine(virtualMachineId);
         fail("Remove virtualMachine must fail on 404");
      } catch (ResourceNotFoundException ex) {
         // Expected exception
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testStartVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(200));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      try {
         remoteApi.powerOn("1");
         assertRequestHasCommonFields(server.takeRequest(), "POST", "/virtualMachines/1/start");
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testStartNonExistingVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(404));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      try {
         try {
            remoteApi.powerOn("1");
            fail("Start virtualMachine must fail on 404");
         } catch (ResourceNotFoundException ex) {
            // Expected exception
         }
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testStopVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(200));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      try {
         remoteApi.powerOff("1");
         assertRequestHasCommonFields(server.takeRequest(), "POST", "/virtualMachines/1/stop");
      } finally {
         api.close();
         server.shutdown();
      }
   }

   public void testStopNonExistingVirtualMachine() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(404));
      XStreamApi api = api(server.getUrl("/"));
      VirtualMachineApi remoteApi = api.getVirtualMachineApi();
      try {
         remoteApi.powerOff("1");
         fail("Stop virtualMachine must fail on 404");
      } catch (ResourceNotFoundException ex) {
         // Expected exception
      } finally {
         api.close();
         server.shutdown();
      }
   }

}
