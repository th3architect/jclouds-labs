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
package org.jclouds.vcloud.director.v1_5.features;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import org.jclouds.vcloud.director.v1_5.domain.Catalog;
import org.jclouds.vcloud.director.v1_5.domain.CatalogItem;
import org.jclouds.vcloud.director.v1_5.filters.AddVCloudAuthorizationAndCookieToRequest;
import org.jclouds.vcloud.director.v1_5.functions.URNToHref;

/**
 * Provides access to {@link Catalog} objects.
 * 
 * @author grkvlt@apache.org, Adrian Cole, Andrea Turli
 */
@RequestFilters(AddVCloudAuthorizationAndCookieToRequest.class)
public interface CatalogApi {
   
   /**
    * Retrieves a catalog.
    * 
    * <pre>
    * GET /catalog/{id}
    * </pre>
    * 
    * @param catalogUri
    *           the reference for the catalog
    * @return a catalog
    */
   @GET
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   Catalog get(@EndpointParam(parser = URNToHref.class) String catalogUrn);

   /**
    * Creates a catalog item in a catalog.
    * 
    * <pre>
    * POST /catalog/{id}/catalogItems
    * </pre>
    * 
    * @param catalogUri
    *           the URI of the catalog
    * @param item
    *           the catalog item to add
    * @return the addd catalog item
    */
   @POST
   @Path("/catalogItems")
   @Consumes(VCloudDirectorMediaType.CATALOG_ITEM)
   @Produces(VCloudDirectorMediaType.CATALOG_ITEM)
   @JAXBResponseParser
   CatalogItem addItem(@EndpointParam(parser = URNToHref.class) String catalogUrn,
            @BinderParam(BindToXMLPayload.class) CatalogItem catalogItem);

   /**
    * Retrieves a catalog item.
    * 
    * <pre>
    * GET /catalogItem/{id}
    * </pre>
    * 
    * @param catalogItemRef
    *           the reference for the catalog item
    * @return the catalog item
    */
   @GET
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   CatalogItem getItem(@EndpointParam(parser = URNToHref.class) String catalogItemUrn);

   /**
    * Modifies a catalog item.
    * 
    * <pre>
    * PUT /catalogItem/{id}
    * </pre>
    * 
    * @param catalogItemRef
    *           the reference for the catalog item
    * @param catalogItem
    *           the catalog item
    * @return the edited catalog item
    */
   @PUT
   @Consumes(VCloudDirectorMediaType.CATALOG_ITEM)
   @Produces(VCloudDirectorMediaType.CATALOG_ITEM)
   @JAXBResponseParser
   CatalogItem editItem(@EndpointParam(parser = URNToHref.class) String catalogItemUrn,
            @BinderParam(BindToXMLPayload.class) CatalogItem catalogItem);

   /**
    * Deletes a catalog item.
    * 
    * <pre>
    * DELETE /catalogItem/{id}
    * </pre>
    * 
    * @param catalogItemRef
    *           the reference for the catalog item
    */
   @DELETE
   @Consumes
   @JAXBResponseParser
   Void removeItem(@EndpointParam(parser = URNToHref.class) String catalogItemUrn);

   /**
    * Retrieves a catalog item.
    * 
    * <pre>
    * GET /catalogItem/{id}
    * </pre>
    * 
    * @param catalogItemRef
    *           the reference for the catalog item
    * @return the catalog item
    */
   @GET
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   Catalog get(@EndpointParam URI catalogHref);

   /**
    * Creates a catalog item in a catalog.
    * 
    * <pre>
    * POST /catalog/{id}/catalogItems
    * </pre>
    * 
    * @param catalogUri
    *           the URI of the catalog
    * @param item
    *           the catalog item to add
    * @return the addd catalog item
    */
   @POST
   @Path("/catalogItems")
   @Consumes(VCloudDirectorMediaType.CATALOG_ITEM)
   @Produces(VCloudDirectorMediaType.CATALOG_ITEM)
   @JAXBResponseParser
   CatalogItem addItem(@EndpointParam URI catalogHref,
            @BinderParam(BindToXMLPayload.class) CatalogItem catalogItem);

   /**
    * Retrieves a catalog item.
    * 
    * <pre>
    * GET /catalogItem/{id}
    * </pre>
    * 
    * @param catalogItemRef
    *           the reference for the catalog item
    * @return the catalog item
    */
   @GET
   @Consumes
   @JAXBResponseParser
   @Fallback(NullOnNotFoundOr404.class)
   CatalogItem getItem(@EndpointParam URI catalogItemHref);

   /**
    * @see CatalogApi#editItem(URI, CatalogItem)
    */
   @PUT
   @Consumes(VCloudDirectorMediaType.CATALOG_ITEM)
   @Produces(VCloudDirectorMediaType.CATALOG_ITEM)
   @JAXBResponseParser
   CatalogItem editItem(@EndpointParam URI catalogItemHref,
            @BinderParam(BindToXMLPayload.class) CatalogItem catalogItem);

   /**
    * Deletes a catalog item.
    * 
    * <pre>
    * DELETE /catalogItem/{id}
    * </pre>
    * 
    * @param catalogItemRef
    *           the reference for the catalog item
    */
   @DELETE
   @Consumes
   @JAXBResponseParser
   Void removeItem(@EndpointParam URI catalogItemHref);
}
