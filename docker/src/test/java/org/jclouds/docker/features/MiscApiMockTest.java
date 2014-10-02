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

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.jclouds.docker.DockerApi;
import org.jclouds.docker.domain.Info;
import org.jclouds.docker.domain.Version;
import org.jclouds.docker.internal.BaseDockerMockTest;
import org.jclouds.docker.options.BuildOptions;
import org.jclouds.io.Payload;
import org.jclouds.io.Payloads;
import org.jclouds.rest.ResourceNotFoundException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;
import java.io.File;
import java.io.FileInputStream;

/**
 * Mock tests for the {@link org.jclouds.docker.features.MiscApi} class.
 */
@Test(groups = "unit", testName = "MiscApiMockTest")
public class MiscApiMockTest extends BaseDockerMockTest {

   public void testGetVersion() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setBody(payloadFromResource("/version.json")));
      DockerApi dockerApi = api(server.getUrl("/"));
      MiscApi api = dockerApi.getMiscApi();
      try {
         Version version = api.getVersion();
         assertRequestHasCommonFields(server.takeRequest(), "/version");
         assertNotNull(version);
         assertNotNull(version.getApiVersion());
         assertNotNull(version.getVersion());
         assertNotNull(version.getGitCommit());
         assertNotNull(version.getGoVersion());
      } finally {
         dockerApi.close();
         server.shutdown();
      }
   }

   public void testGetInfo() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setBody(payloadFromResource("/info.json")));
      DockerApi dockerApi = api(server.getUrl("/"));
      MiscApi api = dockerApi.getMiscApi();

      try {
         Info info = api.getInfo();
         assertRequestHasCommonFields(server.takeRequest(), "/info");
         assertNotNull(info);
         assertNotNull(info.getContainers());
         assertNotNull(info.getDebug());
         assertNotNull(info.getDriver());
         assertNotNull(info.getExecutionDriver());
         assertNotNull(info.getImages());
         assertNotNull(info.getIndexServerAddress());
         assertNotNull(info.getInitPath());
         assertNotNull(info.getiPv4Forwarding());
         assertNotNull(info.getKernelVersion());
         assertNotNull(info.getMemoryLimit());
         assertNotNull(info.getnEventsListener());
         assertNotNull(info.getNFd());
         assertNotNull(info.getNGoroutines());
         assertNotNull(info.getSwapLimit());
      } finally {
         dockerApi.close();
         server.shutdown();
      }
   }

   public void testBuildContainer() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(200));
      DockerApi dockerApi = api(server.getUrl("/"));
      MiscApi api = dockerApi.getMiscApi();
      File dockerFile = File.createTempFile("docker", "tmp");
      try {
         api.build(dockerFile, BuildOptions.NONE);
         assertRequestHasCommonFields(server.takeRequest(), "POST", "/build");
      } finally {
         dockerFile.delete();
         dockerApi.close();
         server.shutdown();
      }
   }

   public void testBuildContainerUsingPayload() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(200));
      DockerApi dockerApi = api(server.getUrl("/"));
      MiscApi api = dockerApi.getMiscApi();
      File file = File.createTempFile("docker", "tmp");
      FileInputStream data = new FileInputStream(file);
      Payload payload = Payloads.newInputStreamPayload(data);
      payload.getContentMetadata().setContentLength(file.length());

      try {
         api.build(payload, BuildOptions.NONE);
         assertRequestHasCommonFields(server.takeRequest(), "POST", "/build");
      } finally {
         dockerApi.close();
         server.shutdown();
      }
   }

   public void testBuildNonexistentContainer() throws Exception {
      MockWebServer server = mockWebServer();
      server.enqueue(new MockResponse().setResponseCode(404));
      DockerApi dockerApi = api(server.getUrl("/"));
      MiscApi api = dockerApi.getMiscApi();
      File dockerFile = File.createTempFile("docker", "tmp");
      try {
         try {
            api.build(dockerFile, BuildOptions.NONE);
            fail("Build container must fail on 404");
         } catch (ResourceNotFoundException ex) {
            // Expected exception
         }
      } finally {
         dockerFile.delete();
         dockerApi.close();
         server.shutdown();
      }
   }

}
