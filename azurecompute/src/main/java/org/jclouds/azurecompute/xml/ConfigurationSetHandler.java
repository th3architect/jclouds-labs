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
package org.jclouds.azurecompute.xml;

import static org.jclouds.util.SaxUtils.currentOrNull;
import java.util.List;

import org.jclouds.azurecompute.domain.Role.ConfigurationSet;
import org.jclouds.azurecompute.domain.Role.ConfigurationSet.InputEndpoint;
import org.jclouds.azurecompute.domain.Role.ConfigurationSet.PublicIP;
import org.jclouds.azurecompute.domain.Role.ConfigurationSet.SubnetName;
import org.jclouds.http.functions.ParseSax;
import org.xml.sax.Attributes;

import com.google.common.collect.Lists;

public class ConfigurationSetHandler extends ParseSax.HandlerForGeneratedRequestWithResult<ConfigurationSet> {
   private String configurationSetType;
   private List<InputEndpoint> inputEndpoints = Lists.newArrayList();
   private List<SubnetName> subnetNames = Lists.newArrayList();
   private String staticVirtualNetworkIPAddress;
   private List<PublicIP> publicIPs = Lists.newArrayList();

   private boolean inInputEndpoints;

   private final InputEndpointHandler inputEndpointHandler = new InputEndpointHandler();
   private final StringBuilder currentText = new StringBuilder();

   @Override
   public ConfigurationSet getResult() {
      ConfigurationSet result = ConfigurationSet.create(configurationSetType, inputEndpoints, subnetNames, staticVirtualNetworkIPAddress, publicIPs);
      resetState(); // handler is called in a loop.
      return result;
   }

   private void resetState() {
      configurationSetType = staticVirtualNetworkIPAddress = null;
      inputEndpoints = Lists.newArrayList();
      subnetNames = Lists.newArrayList();
      publicIPs = Lists.newArrayList();
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) {
      if (qName.equals("InputEndpoints")) {
         inInputEndpoints = true;
      }
      if (inInputEndpoints) {
         inputEndpointHandler.startElement(uri, localName, qName, attributes);
      }
   }

   @Override
   public void endElement(String ignoredUri, String ignoredName, String qName) {
      if (qName.equals("InputEndpoints")) {
         inInputEndpoints = false;
         inputEndpoints.add(inputEndpointHandler.getResult());
      } else if (inInputEndpoints) {
         inputEndpointHandler.endElement(ignoredUri, ignoredName, qName);
      } else if (qName.equals("ConfigurationSetType")) {
         configurationSetType = currentOrNull(currentText);
      }
      currentText.setLength(0);
   }

   @Override
   public void characters(char ch[], int start, int length) {
      if (inInputEndpoints) {
         inputEndpointHandler.characters(ch, start, length);
      } else {
         currentText.append(ch, start, length);
      }
   }

}
