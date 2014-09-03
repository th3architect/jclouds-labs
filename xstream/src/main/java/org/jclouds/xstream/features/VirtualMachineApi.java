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
package org.jclouds.xstream.features;

import java.io.Closeable;
import java.util.Set;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.jclouds.Fallbacks;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.xstream.domain.VirtualMachine;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.binders.BindToJsonPayload;

@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v{jclouds.api-version}")
public interface VirtualMachineApi extends Closeable {

   String VIRTUAL_MACHINES_FILTER = "IsTemplate eq false and IsRemoved eq false";
   String VIRTUAL_MACHINE_FILTER = "IsRemoved eq false";
   String TEMPLATES_FILTER = "IsTemplate eq true and IsRemoved eq false and TenantID eq ";
   String TEMPLATE_FILTER = "IsRemoved eq false";

   /**
    * List all virtual machines
    *
    * @return a set of virtual machines
    */
   @Named("virtualmachine:list")
   @GET
   @Path("/VirtualMachine")
   @Fallback(Fallbacks.EmptySetOnNotFoundOr404.class)
   @QueryParams(keys = "$filter", values = VIRTUAL_MACHINES_FILTER)
   Set<VirtualMachine> listVirtualMachines();

   /**
    * List all running containers
    *
    * @param options the options to list the containers (@see ListContainerOptions)
    * @return a set of containers
   @Named("virtualmachine:list")
   @GET
   @Path("/VirtualMachine")
   @Fallback(Fallbacks.EmptySetOnNotFoundOr404.class)
   Set<VirtualMachine> listVirtualMachines(ListVirtualMachineOptions options);
   */

   /**
    * Create a virtual machine
    *
    * @param virtualMachine the virtual machine’s configuration (@see BindToJsonPayload)
    * @return a new virtual machine
    */
   @Named("virtualmachine:create")
   @POST
   @Path("/VirtualMachine/SetVM")
   VirtualMachine createVirtualMachine(@BinderParam(BindToJsonPayload.class) VirtualMachine virtualMachine);

   /**
    * Return low-level information on the virtual machine
    * @param virtualMachineId  The id of the container to get.
    * @return The details of the virtual machine or <code>null</code> if the VM with the given id doesn't exist.
    */
   @Named("virtualmachine:get")
   @GET
   @Path("/VirtualMachine/{id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @QueryParams(keys = "$filter", values = VIRTUAL_MACHINE_FILTER)
   VirtualMachine getVirtualMachine(@PathParam("id") String virtualMachineId);

   /**
    * Remove the virtual machine by id from the filesystem
    *
    * @param virtualMachineId The id of the VM to be removed.
    */
   @Named("virtualmachine:delete")
   @POST
   @Path("/VirtualMachine/{id}/Remove")
   void removeVirtualMachine(@PathParam("id") String virtualMachineId);

   /**
    * Remove the container by id from the filesystem
    *
    * @param containerId The id of the container to be removed.
    * @param options the operation’s configuration (@see RemoveContainerOptions)
   @Named("container:delete")
   @DELETE
   @Path("/containers/{id}")
   void removeContainer(@PathParam("id") String containerId, RemoveContainerOptions options);
   */

   /**
    * Power on a VM by id.
    *
    * @param virtualMachineId The id of the VM to be powered on.
    */
   @Named("virtualmachine:poweron")
   @POST
   @Path("/VirtualMachine/{id}/PowerOn")
   void powerOn(@PathParam("id") String virtualMachineId);

   /**
    * Power off a VM by id.
    *
    * @param virtualMachineId The id of the VM to be stopped.
    */
   @Named("virtualmachine:poweroff")
   @POST
   @Path("/VirtualMachine/{id}/PowerOff")
   void powerOff(@PathParam("id") String virtualMachineId);


   /**
    * Reboot OS of a VM by id.
    *
    * @param virtualMachineId The id of the VM to be stopped.
    */
   @Named("virtualmachine:rebootOS")
   @POST
   @Path("/VirtualMachine/{id}/RebootOS")
   void rebootOS(@PathParam("id") String virtualMachineId);

   /**
    * Mark a VM as template
    *
    * @param virtualMachineId The id of the VM to be stopped.
    */
   @Named("virtualmachine:markAsTemplate")
   @POST
   @Path("/VirtualMachine/MarkAsTemplate")
   void markAsTemplate(@BinderParam(BindToJsonPayload.class) String virtualMachineId);

   /**
    * List templates
    *
    * @return the templates available.
    */
   @Named("virtualmachine:list")
   @GET
   @Path("/VirtualMachine")
   @Fallback(Fallbacks.EmptySetOnNotFoundOr404.class)
   @QueryParams(keys = "$filter", values = TEMPLATES_FILTER)
   Set<VirtualMachine> listTemplates();

   /**
    * Get template
    *
    * @param virtualMachineId The id of the template to inspect.
    * @return low-level information on the template
    */
   @Named("image:inspect")
   @GET
   @Path("/VirtualMachine/{id}/json")
   @QueryParams(keys = "$filter", values = TEMPLATE_FILTER)
   VirtualMachine getTemplate(@PathParam("name") String virtualMachineId);

}
