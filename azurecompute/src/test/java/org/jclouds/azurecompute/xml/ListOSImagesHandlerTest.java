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

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jclouds.azurecompute.domain.OSImage;
import org.jclouds.date.internal.SimpleDateFormatDateService;
import org.jclouds.http.functions.BaseHandlerTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

@Test(groups = "unit", testName = "ListImagesHandlerTest")
public class ListOSImagesHandlerTest extends BaseHandlerTest {

   public void test() {
      InputStream is = getClass().getResourceAsStream("/images.xml");
      List<OSImage> result = factory.create(new ListOSImagesHandler()).parse(is);

      assertEquals(result, expected());
   }

   public static List<OSImage> expected() {
      return ImmutableList.of( //
              OSImage.create( //
                      "CANONICAL__Canonical-Ubuntu-12-04-amd64-server-20120528.1.3-en-us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "Ubuntu Server 12.04 LTS", // label
                      "Ubuntu Server 12.04 LTS amd64 20120528 Cloud Image", //description
                      "Canonical", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      30, // logicalSizeInGB
                      Arrays.asList("http://www.ubuntu.com/project/about-ubuntu/licensing"), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "MSFT__Win2K8R2SP1-120612-1520-121206-01-en-us-30GB.vhd", // name
                      Arrays.asList("East Asia", "Southeast Asia", "North Europe"), // locations
                      null, // affinityGroup
                      "Windows Server 2008 R2 SP1, June 2012", // label
                      "Windows Server 2008 R2 is a multi-purpose server.", //description
                      "Microsoft", // category
                      OSImage.Type.WINDOWS, // os
                      URI.create("http://blobs/disks/mydeployment/MSFT__Win2K8R2SP1-120612-1520-121206-01-en-us-30GB.vhd"),
                      // mediaLink
                      30, // logicalSizeInGB
                      Collections.<String>emptyList(), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "MSFT__Sql-Server-11EVAL-11.0.2215.0-05152012-en-us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "Microsoft SQL Server 2012 Evaluation Edition", // label
                      "SQL Server 2012 Evaluation Edition (64-bit).", //description
                      "Microsoft", // category
                      OSImage.Type.WINDOWS, // os
                      null, // mediaLink
                      30, // logicalSizeInGB
                      Arrays.asList("http://go.microsoft.com/fwlink/?LinkID=251820",
                              "http://go.microsoft.com/fwlink/?LinkID=131004"), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "MSFT__Win2K12RC-Datacenter-201207.02-en.us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "Windows Server 2012 Release Candidate, July 2012", // label
                      "Windows Server 2012 incorporates Microsoft's experience building.", //description
                      "Microsoft", // category
                      OSImage.Type.WINDOWS, // os
                      null, // mediaLink
                      30, // logicalSizeInGB
                      Collections.<String>emptyList(), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "MSFT__Win2K8R2SP1-Datacenter-201207.01-en.us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "Windows Server 2008 R2 SP1, July 2012", // label
                      "Windows Server 2008 R2 is a multi-purpose server.", //description
                      "Microsoft", // category
                      OSImage.Type.WINDOWS, // os
                      null, // mediaLink
                      30, // logicalSizeInGB
                      Collections.<String>emptyList(), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "OpenLogic__OpenLogic-CentOS-62-20120531-en-us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "OpenLogic CentOS 6.2", // label
                      "This distribution of Linux is based on CentOS.", //description
                      "OpenLogic", // category
                      OSImage.Type.LINUX, // os
                      URI.create("http://blobs/disks/mydeployment/OpenLogic__OpenLogic-CentOS-62-20120531-en-us-30GB.vhd"),
                      // mediaLink
                      30, // logicalSizeInGB
                      Arrays.asList("http://www.openlogic.com/azure/service-agreement/"), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "SUSE__openSUSE-12-1-20120603-en-us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "openSUSE 12.1", // label
                      "openSUSE is a free and Linux-based operating system!", //description
                      "SUSE", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      30, // logicalSizeInGB
                      Arrays.asList("http://opensuse.org/"), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "SUSE__SUSE-Linux-Enterprise-Server-11SP2-20120601-en-us-30GB.vhd", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "SUSE Linux Enterprise Server", // label
                      "SUSE Linux Enterprise Server is a highly reliable value.", //description
                      "SUSE", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      30, // logicalSizeInGB
                      Arrays.asList("http://www.novell.com/licensing/eula/"), // eula
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "0b11de9248dd4d87b18621318e037d37__RightImage-CentOS-6.4-x64-v13.4", // name
                      Collections.<String>emptyList(), // locations
                      null, // affinityGroup
                      "RightImage-CentOS-6.4-x64-v13.4", // label
                      null, //description
                      "RightScale with Linux", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      10, // logicalSizeInGB
                      Collections.<String>emptyList(), // No EULA, as RightScale stuffed ';' into the field.
                      null, // imageFamily
                      null, // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      null, // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      null, // isPremium
                      null, // showInGui
                      null  // publisherName
              ),
              OSImage.create( //
                      "03f55de797f546a1b29d1b8d66be687a__Team-Foundation-Server-2013-Update4-WS2012R2", // name
                      ImmutableList.of("East Asia", "Southeast Asia", "Australia East", "Australia Southeast",
                              "North Europe", "West Europe", "Japan West", "Central US", "East US", "East US 2", "South Central US", "West US"), // locations
                      null, // affinityGroup
                      "Team Foundation Server 2013 Update 4 on Windows Server 2012 R2", // label
                      "Microsoft Team Foundation Server 2013 Trial on Windows Server 2012 R2 Update.", //description
                      "Public", // category
                      OSImage.Type.WINDOWS, // os
                      null, // mediaLink
                      128, // logicalSizeInGB
                      ImmutableList.of("http://www.microsoft.com/en-us/download/details.aspx?id=13350"), // eula
                      "Team Foundation Server 2013 Update 4 on Windows Server 2012 R2", // imageFamily
                      new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-11-12T08:00:00Z"), // publishedDate
                      "VisualStudio2013_100.png", // iconUri
                      "VisualStudio2013_45.png", // smallIconUri
                      URI.create("http://go.microsoft.com/fwlink/?LinkID=286720"), // privacyUri
                      null, // pricingDetailLink
                      "Medium", // recommendedVMSize
                      false, // isPremium
                      null, // showInGui
                      "Microsoft Visual Studio Group"  // publisherName
              ),
              OSImage.create( //
                      "0b11de9248dd4d87b18621318e037d37__RightImage-CentOS-6.6-x64-v13.5.5", // name
                      ImmutableList.of("East Asia", "Southeast Asia", "North Europe", "West Europe", "Japan West",
                              "Central US", "East US", "East US 2", "South Central US", "West US"), // locations
                      null, // affinityGroup
                      "RightImage-CentOS-6.6-x64-v13.5.5", // label
                      "CentOS 6.6 with RightLink 5.8", //description
                      "Public", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      10, // logicalSizeInGB
                      ImmutableList.of("http://support.rightscale.com/12-Guides/RightLink/RightLink_End_User_License_Agreeement"), // eula
                      "RightScale Linux v13", // imageFamily
                      new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-11-12T00:00:00Z"), // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      URI.create("http://www.rightscale.com/privacy_policy.php"), // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      false, // isPremium
                      false, // showInGui
                      "RightScale with Linux"  // publisherName
              ),
              OSImage.create( //
                      "0b11de9248dd4d87b18621318e037d37__RightImage-CentOS-7.0-x64-v14.1.5.1", // name
                      ImmutableList.of("East Asia", "Southeast Asia", "North Europe", "West Europe", "Japan West",
                              "Central US", "East US", "East US 2", "South Central US", "West US"), // locations
                      null, // affinityGroup
                      "RightImage-CentOS-7.0-x64-v14.1.5.1", // label
                      "CentOS 7.0 with RightLink 6.2", //description
                      "Public", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      10, // logicalSizeInGB
                      ImmutableList.of("http://support.rightscale.com/12-Guides/RightLink/RightLink_End_User_License_Agreeement"), // eula
                      "RightScale Linux v14", // imageFamily
                      new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-12-10T00:00:00Z"), // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      URI.create("http://www.rightscale.com/privacy_policy.php"), // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      false, // isPremium
                      false, // showInGui
                      "RightScale with Linux"  // publisherName
              ),
              OSImage.create( //
                      "0b11de9248dd4d87b18621318e037d37__RightImage-Ubuntu-12.04-x64-v14.1.5.1", // name
                      ImmutableList.of("East Asia", "Southeast Asia", "North Europe", "West Europe", "Japan West",
                              "Central US", "East US", "East US 2", "South Central US", "West US"), // locations
                      null, // affinityGroup
                      "RightImage-Ubuntu-12.04-x64-v14.1.5.1", // label
                      "Ubuntu 12.04 with RightLink 6.2", //description
                      "Public", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      10, // logicalSizeInGB
                      ImmutableList.of("http://support.rightscale.com/12-Guides/RightLink/RightLink_End_User_License_Agreeement"), // eula
                      "RightScale Linux v14", // imageFamily
                      new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-12-10T00:00:00Z"), // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      URI.create("http://www.rightscale.com/privacy_policy.php"), // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      false, // isPremium
                      false, // showInGui
                      "RightScale with Linux"  // publisherName
              ),
              OSImage.create( //
                      "0b11de9248dd4d87b18621318e037d37__RightImage-Ubuntu-14.04-x64-v14.1.5.1", // name
                      ImmutableList.of("East Asia", "Southeast Asia", "North Europe", "West Europe", "Japan West",
                              "Central US", "East US", "East US 2", "South Central US", "West US"), // locations
                      null, // affinityGroup
                      "RightImage-Ubuntu-14.04-x64-v14.1.5.1", // label
                      "Ubuntu 14.04 with RightLink 6.2", //description
                      "Public", // category
                      OSImage.Type.LINUX, // os
                      null, // mediaLink
                      10, // logicalSizeInGB
                      ImmutableList.of("http://support.rightscale.com/12-Guides/RightLink/RightLink_End_User_License_Agreeement"), // eula
                      "RightScale Linux v14", // imageFamily
                      new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-12-10T00:00:00Z"), // publishedDate
                      null, // iconUri
                      null, // smallIconUri
                      URI.create("http://www.rightscale.com/privacy_policy.php"), // privacyUri
                      null, // pricingDetailLink
                      null, // recommendedVMSize
                      false, // isPremium
                      false, // showInGui
                      "RightScale with Linux"  // publisherName
              ),
              OSImage.create( //
                      "0c0083a6d9a24f2d91800e52cad83950__JDK-1.6.0_75-0614-Win-GA", // name
                      ImmutableList.of("East Asia", "Southeast Asia", "Australia East", "Australia Southeast",
                              "North Europe", "West Europe", "Japan West",
                              "Central US", "East US", "East US 2", "South Central US", "West US"), // locations
                      null, // affinityGroup
                      "JDK 6 on Windows Server 2012", // label
                      "[Java Platform|http://www.oracle.com/java|_blank], Standard Edition 6 (update 75) enables development\n" +
                              "      of secure, portable, high-performance applications and includes a Java Development Kit (JDK), Java Runtime\n" +
                              "      Environment (JRE), and tools for developing, debugging, and monitoring Java applications. WARNING: These older\n" +
                              "      versions of the JRE and JDK are provided to help developers debug issues in older systems. They are not\n" +
                              "      recommended for use in production. Minimum recommended virtual machine size for this image is\n" +
                              "      [Small|http://go.microsoft.com/fwlink/?LinkID=309169|_blank]. [Learn\n" +
                              "      More|http://www.windowsazure.com/en-us/documentation/articles/virtual-machines-java-run-tomcat-application-server/|_blank]", //description
                      "Public", // category
                      OSImage.Type.WINDOWS, // os
                      null, // mediaLink
                      128, // logicalSizeInGB
                      ImmutableList.of("http://go.microsoft.com/fwlink/?LinkId=321312"), // eula
                      "JDK 6 on Windows Server 2012", // imageFamily
                      new SimpleDateFormatDateService().iso8601SecondsDateParse("2014-06-09T07:00:00Z"), // publishedDate
                      "Java6_100.png", // iconUri
                      "Java6_45.png", // smallIconUri
                      URI.create("http://go.microsoft.com/fwlink/?LinkId=321694"), // privacyUri
                      URI.create("http://go.microsoft.com/fwlink/?LinkId=386544"), // pricingDetailLink
                      "Small", // recommendedVMSize
                      true, // isPremium
                      true, // showInGui
                      "Microsoft Open Technologies, Inc."  // publisherName
              )
      );
   }
}
