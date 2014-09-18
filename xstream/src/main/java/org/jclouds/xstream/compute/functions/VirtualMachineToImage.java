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

import com.google.common.base.Function;
import com.google.common.base.Splitter;

import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.ImageBuilder;
import org.jclouds.compute.domain.OperatingSystem;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.logging.Logger;
import org.jclouds.xstream.domain.VirtualMachine;

import javax.annotation.Resource;
import javax.inject.Named;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.get;

public class VirtualMachineToImage implements Function<VirtualMachine, Image> {

   private static final String CENTOS = "CentOS";
   private static final String UBUNTU = "ubuntu";
   private static final String RHEL = "Red Hat Enterprise Linux";

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   @Override
   public Image apply(VirtualMachine from) {
      checkNotNull(from, "image");
      String name = checkNotNull(from.getName());

      OsFamily osFamily = osFamily().apply(from.getOs());
      String osVersion = parseVersion(from.getOsFullName());

      OperatingSystem os = OperatingSystem.builder()
              .description(name)
              .family(osFamily)
              .version(osVersion)
              .is64Bit(is64bit(from))
              .build();

      return new ImageBuilder()
              .ids(from.getVirtualMachineID())
              .name(get(Splitter.on(":").split(name), 0))
              .description(from.getOsFullName())
              .operatingSystem(os)
              .status(Image.Status.AVAILABLE)
              .build();
   }

   private boolean is64bit(VirtualMachine inspectedImage) {
      // TODO
      return true;
   }

   /**
    * Parses the item description to determine the OSFamily
    *
    * @return the @see OsFamily or OsFamily.UNRECOGNIZED
    */
   private Function<String, OsFamily> osFamily() {
      return new Function<String, OsFamily>() {

         @Override
         public OsFamily apply(final String osFullName) {
            if (osFullName != null) {
               if (osFullName.startsWith(CENTOS)) return OsFamily.CENTOS;
               else if (osFullName.startsWith(UBUNTU)) return OsFamily.UBUNTU;
               else if (osFullName.startsWith(RHEL)) return OsFamily.RHEL;
            }
            return OsFamily.UNRECOGNIZED;
         }
      };
   }

   private String parseVersion(String osFullName) {
      String version = "UNRECOGNIZED";
      if (osFullName.startsWith(CENTOS)) version = osFullName.subSequence(CENTOS.length() + 1,
              CENTOS.length() + 7).toString();
      else if (osFullName.startsWith(UBUNTU)) version = osFullName.subSequence(UBUNTU.length(),
              UBUNTU.length() + 1).toString();
      else if (osFullName.startsWith(RHEL)) version = osFullName.subSequence(RHEL.length() + 1,
              RHEL.length() + 2).toString();

      logger.debug("os version for item: %s is %s", osFullName, version);
      return version;
   }

}
