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
package org.jclouds.xstream.compute.functions;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.testng.Assert.assertEquals;

import org.easymock.EasyMock;
import org.jclouds.compute.domain.Image;
import org.jclouds.xstream.compute.functions.VirtualMachineToImage;
import org.jclouds.xstream.domain.VirtualMachine;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link org.jclouds.xstream.compute.functions.VirtualMachineToImage} class.
 */
@Test(groups = "unit", testName = "ImageToImageTest")
public class VirtualMachineToImageTest {

   private VirtualMachineToImage function;

   private VirtualMachine image;

   @BeforeMethod
   public void setup() {
      image = VirtualMachine.builder()
                                             .build();
      function = new VirtualMachineToImage();
   }

   public void testImageToImage() {
      VirtualMachine mockImage = mockImage();

      Image image = function.apply(mockImage);

      verify(mockImage);

      assertEquals(mockImage.getVirtualMachineID(), image.getId().toString());
   }

   private VirtualMachine mockImage() {
      VirtualMachine mockImage = EasyMock.createMock(VirtualMachine.class);

      expect(mockImage.getVirtualMachineID()).andReturn(image.getVirtualMachineID()).anyTimes();
      replay(mockImage);

      return mockImage;
   }
}
