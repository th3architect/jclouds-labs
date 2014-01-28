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
package org.jclouds.docker.compute.extensions;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.inject.Module;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.extensions.internal.BaseImageExtensionLiveTest;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Live test for Docker {@link org.jclouds.compute.extensions.ImageExtension} implementation.
 *
 * @author Andrea Turli
 */
@Test(groups = "live", singleThreaded = true, testName = "DockerImageExtensionLiveTest")
public class DockerImageExtensionLiveTest extends BaseImageExtensionLiveTest {

   public DockerImageExtensionLiveTest() {
      provider = "docker";
   }

   @Override
   protected Module getSshModule() {
      return new SshjSshClientModule();
   }

   @Override
   protected Iterable<? extends Image> listImages() {
      Uninterruptibles.sleepUninterruptibly(1, TimeUnit.MINUTES); // expiration time for the images cache
      return super.listImages();
   }

   @Override
   public Template getNodeTemplate() {
      return view.getComputeService().templateBuilder().imageId("3fcc6dfa328aa0fd0fab0d6dfbcd74e6e0738df0bf750f3f0905206b26c29730").build();
   }
}
