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
package org.jclouds.docker.internal;

import static com.google.common.util.concurrent.MoreExecutors.sameThreadExecutor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jclouds.util.Strings2.toStringAndClose;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.jclouds.ContextBuilder;
import org.jclouds.concurrent.config.ExecutorServiceModule;
import org.jclouds.docker.DockerApi;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

/**
 * Base class for all Docker mock tests.
 */
public class BaseDockerMockTest {
   private final Set<Module> modules = ImmutableSet.<Module> of(new ExecutorServiceModule(sameThreadExecutor(),
         sameThreadExecutor()));

   protected String provider;
   private final String identity;
   private final String credential;

   public BaseDockerMockTest() {
      provider = "docker";
      // self-signed dummy cert:
      // keytool -genkey -alias test -keyalg RSA -keysize 1024 -validity 5475 -dname "CN=localhost" -keystore
      // docker-test.p12 -storepass dockerpass -storetype pkcs12
      identity = this.getClass().getResource("/docker-test.p12").getFile();
      credential = "dockerpass";
   }

   public DockerApi api(URL url) {
      return ContextBuilder.newBuilder(provider)
            .credentials(identity, credential)
            .endpoint(url.toString())
            .modules(modules)
            .overrides(setupProperties())
            .buildApi(DockerApi.class);
   }

   protected Properties setupProperties() {
      return new Properties();
   }

   public static MockWebServer mockDockerWebServer() throws IOException {
      MockWebServer server = new MockWebServer();
      server.play();
      return server;
   }

   public byte[] payloadFromResource(String resource) {
      try {
         return toStringAndClose(getClass().getResourceAsStream(resource)).getBytes(Charsets.UTF_8);
      } catch (IOException e) {
         throw Throwables.propagate(e);
      }
   }

   protected RecordedRequest assertSent(MockWebServer server, String method, String path) throws InterruptedException {
      RecordedRequest request = server.takeRequest();
      assertThat(request.getMethod()).isEqualTo(method);
      assertThat(request.getPath()).isEqualTo(path);
      assertThat(request.getHeader(HttpHeaders.ACCEPT)).isEqualTo(MediaType.APPLICATION_JSON);
      return request;
   }

}
