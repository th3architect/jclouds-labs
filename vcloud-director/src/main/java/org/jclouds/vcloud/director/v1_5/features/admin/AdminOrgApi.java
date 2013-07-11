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
package org.jclouds.vcloud.director.v1_5.features.admin;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.rest.annotations.BinderParam;
import org.jclouds.rest.annotations.EndpointParam;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.JAXBResponseParser;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.binders.BindToXMLPayload;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType;
import org.jclouds.vcloud.director.v1_5.domain.org.AdminOrg;
import org.jclouds.vcloud.director.v1_5.domain.org.Org;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgEmailSettings;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgGeneralSettings;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgLdapSettings;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgLeaseSettings;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgPasswordPolicySettings;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgSettings;
import org.jclouds.vcloud.director.v1_5.domain.org.OrgVAppTemplateLeaseSettings;
import org.jclouds.vcloud.director.v1_5.features.OrgApi;
import org.jclouds.vcloud.director.v1_5.filters.AddVCloudAuthorizationAndCookieToRequest;
import org.jclouds.vcloud.director.v1_5.functions.URNToAdminHref;

/**
 * Provides access to {@link Org} objects.
 * @author danikov, Adrian Cole
 */
@RequestFilters(AddVCloudAuthorizationAndCookieToRequest.class)
public interface AdminOrgApi extends OrgApi {

   /**
    * Retrieves an admin view of an organization. The organization might be enabled or disabled. If
    * enabled, the organization allows login and all other operations.
    * 
    * <pre>
    * GET /admin/org/{id}
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the admin org
    */
   @Override
   @GET
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   AdminOrg get(@EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#get(URI)
    */
   @Override
   @GET
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   AdminOrg get(@EndpointParam URI adminOrgHref);

   /**
    * Gets organizational settings for this organization.
    * 
    * <pre>
    * GET /admin/org/{id}/settings
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the settings
    */
   @GET
   @Path("/settings")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgSettings getSettings(@EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getSettings(URI)
    */
   @GET
   @Path("/settings")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgSettings getSettings(@EndpointParam URI adminOrgHref);

   /**
    * Updates organizational settings for this organization.
    * 
    * <pre>
    * PUT /admin/org/{id}/settings
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @param newSettings
    *           the requested edited settings
    * @return the resultant settings
    */
   @PUT
   @Path("/settings")
   @Consumes(VCloudDirectorMediaType.ORG_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_SETTINGS)
   @JAXBResponseParser
   OrgSettings editSettings(@EndpointParam(parser = URNToAdminHref.class) String orgUrn,
            @BinderParam(BindToXMLPayload.class) OrgSettings settings);

   /**
    * @see AdminOrgApi#editSettings(URI, OrgSettings)
    */
   @PUT
   @Path("/settings")
   @Consumes(VCloudDirectorMediaType.ORG_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_SETTINGS)
   @JAXBResponseParser
   OrgSettings editSettings(@EndpointParam URI adminOrgHref,
            @BinderParam(BindToXMLPayload.class) OrgSettings settings);

   /**
    * Retrieves email settings for an organization.
    * 
    * <pre>
    * GET /admin/org/{id}/settings/email
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the email settings
    */
   @GET
   @Path("/settings/email")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgEmailSettings getEmailSettings(@EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getEmailSettings(URI)
    */
   @GET
   @Path("/settings/email")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgEmailSettings getEmailSettings(@EndpointParam URI adminOrgHref);

   /**
    * Updates email policy settings for organization.
    * 
    * <pre>
    * PUT /admin/org/{id}/settings/email
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @param newSettings
    *           the requested edited settings
    * @return the resultant settings
    */
   @PUT
   @Path("/settings/email")
   @Consumes(VCloudDirectorMediaType.ORG_EMAIL_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_EMAIL_SETTINGS)
   @JAXBResponseParser
   OrgEmailSettings editEmailSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn,
            @BinderParam(BindToXMLPayload.class) OrgEmailSettings settings);

   /**
    * @see AdminOrgApi#editEmailSettings(URI, OrgEmailSettings)
    */
   @PUT
   @Path("/settings/email")
   @Consumes(VCloudDirectorMediaType.ORG_EMAIL_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_EMAIL_SETTINGS)
   @JAXBResponseParser
   OrgEmailSettings editEmailSettings(@EndpointParam URI adminOrgHref,
            @BinderParam(BindToXMLPayload.class) OrgEmailSettings settings);

   /**
    * Gets general organization settings.
    * 
    * <pre>
    * GET /admin/org/{id}/settings/general
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the lease settings
    */
   @GET
   @Path("/settings/general")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgGeneralSettings getGeneralSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getGeneralSettings(URI)
    */
   @GET
   @Path("/settings/general")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgGeneralSettings getGeneralSettings(@EndpointParam URI adminOrgHref);

   /**
    * Updates general organization settings.
    * 
    * <pre>
    * PUT /admin/org/{id}/settings/general
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @param newSettings
    *           the requested edited settings
    * @return the resultant settings
    */
   @PUT
   @Path("/settings/general")
   @Consumes(VCloudDirectorMediaType.ORG_GENERAL_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_GENERAL_SETTINGS)
   @JAXBResponseParser
   OrgGeneralSettings editGeneralSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn,
            @BinderParam(BindToXMLPayload.class) OrgGeneralSettings settings);

   /**
    * @see AdminOrgApi#editGeneralSettings(URI, OrgGeneralSettings)
    */
   @PUT
   @Path("/settings/general")
   @Consumes(VCloudDirectorMediaType.ORG_GENERAL_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_GENERAL_SETTINGS)
   @JAXBResponseParser
   OrgGeneralSettings editGeneralSettings(@EndpointParam URI adminOrgHref,
            @BinderParam(BindToXMLPayload.class) OrgGeneralSettings settings);

   /**
    * Retrieves LDAP settings for an organization.
    * 
    * <pre>
    * GET /admin/org/{id}/settings/ldap
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the ldap settings
    */
   @GET
   @Path("/settings/ldap")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgLdapSettings getLdapSettings(@EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getPasswordPolicy(URI)
    */
   @GET
   @Path("/settings/ldap")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgLdapSettings getLdapSettings(@EndpointParam URI adminOrgHref);

   /**
    * Retrieves password policy settings for an organization.
    * 
    * <pre>
    * GET /admin/org/{id}/settings/passwordPolicy
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the lease settings
    */
   @GET
   @Path("/settings/passwordPolicy")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgPasswordPolicySettings getPasswordPolicy(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getPasswordPolicy(URI)
    */
   @GET
   @Path("/settings/passwordPolicy")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgPasswordPolicySettings getPasswordPolicy(@EndpointParam URI adminOrgHref);

   /**
    * Updates password policy settings for organization.
    * 
    * <pre>
    * PUT /admin/org/{id}/settings/passwordPolicy
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @param newSettings
    *           the requested edited settings
    * @return the resultant settings
    */
   @PUT
   @Path("/settings/passwordPolicy")
   @Consumes(VCloudDirectorMediaType.ORG_PASSWORD_POLICY_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_PASSWORD_POLICY_SETTINGS)
   @JAXBResponseParser
   OrgPasswordPolicySettings editPasswordPolicy(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn,
            @BinderParam(BindToXMLPayload.class) OrgPasswordPolicySettings settings);

   /**
    * @see AdminOrgApi#editPasswordPolicy(URI, OrgPasswordPolicySettings)
    */
   @PUT
   @Path("/settings/passwordPolicy")
   @Consumes(VCloudDirectorMediaType.ORG_PASSWORD_POLICY_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_PASSWORD_POLICY_SETTINGS)
   @JAXBResponseParser
   OrgPasswordPolicySettings editPasswordPolicy(@EndpointParam URI adminOrgHref,
            @BinderParam(BindToXMLPayload.class) OrgPasswordPolicySettings settings);

   /**
    * Gets organization resource cleanup settings on the level of vApp.
    * 
    * <pre>
    * GET /admin/org/{id}/settings/vAppLeaseSettings
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the lease settings
    */
   @GET
   @Path("/settings/vAppLeaseSettings")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgLeaseSettings getVAppLeaseSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getVAppLeaseSettings(URI)
    */
   @GET
   @Path("/settings/vAppLeaseSettings")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgLeaseSettings getVAppLeaseSettings(@EndpointParam URI adminOrgHref);

   /**
    * Updates organization resource cleanup settings on the level of vApp.
    * 
    * <pre>
    * PUT /admin/org/{id}/settings/vAppLeaseSettings
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @param newSettings
    *           the requested edited settings
    * @return the resultant settings
    */
   @PUT
   @Path("/settings/vAppLeaseSettings")
   @Consumes(VCloudDirectorMediaType.ORG_LEASE_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_LEASE_SETTINGS)
   @JAXBResponseParser
   OrgLeaseSettings editVAppLeaseSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn,
            @BinderParam(BindToXMLPayload.class) OrgLeaseSettings settings);

   /**
    * @see AdminOrgApi#editVAppLeaseSettings(URI, OrgVAppLeaseSettings)
    */
   @PUT
   @Path("/settings/vAppLeaseSettings")
   @Consumes(VCloudDirectorMediaType.ORG_LEASE_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_LEASE_SETTINGS)
   @JAXBResponseParser
   OrgLeaseSettings editVAppLeaseSettings(@EndpointParam URI adminOrgHref,
            @BinderParam(BindToXMLPayload.class) OrgLeaseSettings settings);

   /**
    * Retrieves expiration and storage policy for vApp templates in an organization.
    * 
    * <pre>
    * GET /admin/org/{id}/settings/vAppTemplateLeaseSettings
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @return the lease settings
    */
   @GET
   @Path("/settings/vAppTemplateLeaseSettings")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgVAppTemplateLeaseSettings getVAppTemplateLeaseSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn);

   /**
    * @see AdminOrgApi#getVAppTemplateLeaseSettings(URI)
    */
   @GET
   @Path("/settings/vAppTemplateLeaseSettings")
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   OrgVAppTemplateLeaseSettings getVAppTemplateLeaseSettings(@EndpointParam URI adminOrgHref);

   /**
    * Updates vApp template policy settings for organization.
    * 
    * <pre>
    * PUT /admin/org/{id}/settings/vAppTemplateLeaseSettings
    * </pre>
    * 
    * @param orgUrn
    *           the reference for the admin org
    * @param newSettings
    *           the requested edited settings
    * @return the resultant settings
    */
   @PUT
   @Path("/settings/vAppTemplateLeaseSettings")
   @Consumes(VCloudDirectorMediaType.ORG_VAPP_TEMPLATE_LEASE_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_VAPP_TEMPLATE_LEASE_SETTINGS)
   @JAXBResponseParser
   OrgVAppTemplateLeaseSettings editVAppTemplateLeaseSettings(
            @EndpointParam(parser = URNToAdminHref.class) String orgUrn,
            @BinderParam(BindToXMLPayload.class) OrgVAppTemplateLeaseSettings settings);

   /**
    * @see AdminOrgApi#editVAppTemplateLeaseSettings(URI, OrgVAppTemplateLeaseSettings)
    */
   @PUT
   @Path("/settings/vAppTemplateLeaseSettings")
   @Consumes(VCloudDirectorMediaType.ORG_VAPP_TEMPLATE_LEASE_SETTINGS)
   @Produces(VCloudDirectorMediaType.ORG_VAPP_TEMPLATE_LEASE_SETTINGS)
   @JAXBResponseParser
   OrgVAppTemplateLeaseSettings editVAppTemplateLeaseSettings(@EndpointParam URI adminOrgHref,
            @BinderParam(BindToXMLPayload.class) OrgVAppTemplateLeaseSettings settings);
}
