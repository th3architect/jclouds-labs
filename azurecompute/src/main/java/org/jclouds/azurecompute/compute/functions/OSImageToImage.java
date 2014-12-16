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
package org.jclouds.azurecompute.compute.functions;

import org.jclouds.azurecompute.compute.functions.internal.OperatingSystems;
import org.jclouds.azurecompute.domain.OSImage;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OsFamily;

import com.google.common.base.Function;

public class OSImageToImage implements Function<OSImage, Image> {

   private static final String UNRECOGNIZED = "UNRECOGNIZED";

   @Override
   public Image apply(OSImage osImage) {

      OsFamily osFamily = OperatingSystems.osFamily().apply(osImage.label());
      String osVersion = OperatingSystems.version().apply(osImage);

      org.jclouds.compute.domain.OperatingSystem os = org.jclouds.compute.domain.OperatingSystem.builder()
              .description(osImage.description() == null ? UNRECOGNIZED : osImage.description())
              .family(osFamily)
              .version(osVersion)
              .is64Bit(is64bit(osImage.label()))
              .build();

      return new ImageBuilder()
              .ids(osImage.name())
              .description(osImage.description())
              .operatingSystem(os)
              .status(Image.Status.AVAILABLE)
              .build();
   }

   private boolean is64bit(String label) {
      return label.matches("-x64-");
   }
}
