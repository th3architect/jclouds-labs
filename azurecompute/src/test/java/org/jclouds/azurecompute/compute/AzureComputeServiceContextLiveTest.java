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
package org.jclouds.azurecompute.compute;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jclouds.azurecompute.config.AzureComputeProperties.SUBSCRIPTION_ID;
import static org.testng.Assert.assertEquals;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Named;

import org.jclouds.azurecompute.options.AzureComputeTemplateOptions;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.internal.BaseComputeServiceContextLiveTest;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.logging.Logger;
import org.jclouds.ssh.SshClient;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.testng.annotations.Test;

import com.google.inject.Module;

@Test(groups = "live", testName = "SoftLayerComputeServiceContextLiveTest")
public class AzureComputeServiceContextLiveTest extends BaseComputeServiceContextLiveTest {

   @Override
   protected Properties setupProperties() {
      Properties props = super.setupProperties();
      setIfTestSystemPropertyPresent(props, SUBSCRIPTION_ID);
      return props;
   }

   @Override
   protected Module getSshModule() {
      return new SshjSshClientModule();
   }

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   public AzureComputeServiceContextLiveTest() {
      provider = "azurecompute";
   }

   @Test
   public void testLaunchClusterWithMinDisk() throws RunNodesException {
      int numNodes = 1;
      final String group = "node" + new Random().nextLong();

      TemplateBuilder templateBuilder = view.getComputeService().templateBuilder();
      templateBuilder.imageId("b39f27a8b8c64d52b05eac6a62ebad85__Ubuntu_DAILY_BUILD-trusty-14_04_1-LTS-amd64-server-20141212-en-us-30GB");
      templateBuilder.hardwareId("BASIC_A2");
      Template template = templateBuilder.build();

      // test passing custom options
      AzureComputeTemplateOptions options = template.getOptions().as(AzureComputeTemplateOptions.class);
      options.inboundPorts(22, 2375, 2376);
      //options.storageAccountName("jclouds123456789");

      Set<? extends NodeMetadata> nodes = view.getComputeService().createNodesInGroup(group, numNodes, template);
      assertEquals(numNodes, nodes.size(), "wrong number of nodes");
      for (NodeMetadata node : nodes) {
         logger.debug("Created Node: %s", node);
         SshClient client = view.utils().sshForNode().apply(node);
         client.connect();
         ExecResponse hello = client.exec("echo hello");
         assertThat(hello.getOutput().trim()).isEqualTo("hello");
         view.getComputeService().destroyNode(node.getId());
      }
   }

}
