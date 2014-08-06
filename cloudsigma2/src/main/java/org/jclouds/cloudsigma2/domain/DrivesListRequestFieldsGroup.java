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
package org.jclouds.cloudsigma2.domain;

import java.util.Iterator;

public class DrivesListRequestFieldsGroup {
   private final Iterable<String> fields;

<<<<<<< HEAD:cloudsigma2/src/main/java/org/jclouds/cloudsigma2/domain/DrivesListRequestFieldsGroup.java
   public DrivesListRequestFieldsGroup(Iterable<String> fields) {
      this.fields = fields;
   }
=======
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class VServerWithVNICs extends VServer {

   @XmlElementWrapper(name = "vnics")
   @XmlElement(name = "vnic")
   protected Set<VNIC> vnics = Sets.newLinkedHashSet();
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations:fgcp/src/main/java/org/jclouds/fujitsu/fgcp/domain/VServerWithVNICs.java

   public Iterable<String> getFields() {
      return fields;
   }

   @Override
   public String toString() {
<<<<<<< HEAD:cloudsigma2/src/main/java/org/jclouds/cloudsigma2/domain/DrivesListRequestFieldsGroup.java
      String returnString = "";

      Iterator<String> iterator = fields.iterator();

      while (iterator.hasNext()) {
         returnString += iterator.next();

         if (iterator.hasNext()) {
            returnString += ",";
         }
      }

      return returnString;
=======
      return MoreObjects.toStringHelper(this).omitNullValues().add("id", id)
            .add("name", name).add("type", type).add("creator", creator)
            .add("diskimageId", diskimageId).add("vnics", vnics).toString();
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations:fgcp/src/main/java/org/jclouds/fujitsu/fgcp/domain/VServerWithVNICs.java
   }
}
