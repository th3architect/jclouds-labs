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

import java.util.List;

import org.jclouds.azurecompute.domain.NetworkConfiguration;
import org.jclouds.http.functions.ParseSax;
import org.xml.sax.Attributes;

import com.google.common.collect.Lists;

public class VirtualNetworkSiteHandler extends ParseSax.HandlerForGeneratedRequestWithResult<NetworkConfiguration.VirtualNetworkSite> {
   private String name;
   private String location;

   private NetworkConfiguration.AddressSpace addressSpace;

   private List<NetworkConfiguration.Subnet> subnets = Lists.newArrayList();

   private boolean inSubnets;
   private boolean inAddressSpace;
   private final SubnetHandler subnetHandler = new SubnetHandler();
   private final AddressSpaceHandler addressSpaceHandler = new AddressSpaceHandler();

   private StringBuilder currentText = new StringBuilder();

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) {
      if (qName.equals("AddressSpace")) {
         inAddressSpace = true;
      } else if (qName.equals("Subnets")) {
         inSubnets = true;
      } else if (qName.equals("Subnet")) {
         subnetHandler.startElement(uri, localName, qName, attributes);
      } else if (qName.equals("VirtualNetworkSite")) {
         for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.getLocalName(i).equals("name")) {
               name = attributes.getValue(i);
            } else if (attributes.getLocalName(i).equals("Location")) {
               location = attributes.getValue(i);
            }
         }
      }
   }

   @Override
   public NetworkConfiguration.VirtualNetworkSite getResult() {
      return NetworkConfiguration.VirtualNetworkSite.create(name, location, addressSpace, subnets);
   }

   @Override public void endElement(String ignoredUri, String ignoredName, String qName) {
      if (qName.equals("AddressSpace")) {
         addressSpace = addressSpaceHandler.getResult();
         inAddressSpace = false;
      } else if (inAddressSpace) {
         addressSpaceHandler.endElement(ignoredUri, ignoredName, qName);
      } else if (qName.equals("Subnets")) {
         inSubnets = false;
      } else if (qName.equals("Subnet")) {
         subnets.add(subnetHandler.getResult());
      } else if (inSubnets) {
         subnetHandler.endElement(ignoredUri, ignoredName, qName);
      }
      currentText.setLength(0);
   }

   @Override public void characters(char ch[], int start, int length){
         if (inAddressSpace) {
         addressSpaceHandler.characters(ch, start, length);
      } else if (inSubnets) {
         subnetHandler.characters(ch, start, length);
      } else if (!inSubnets && !inAddressSpace) {
         currentText.append(ch, start, length);
      }
   }

}
