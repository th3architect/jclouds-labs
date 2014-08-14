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
package org.jclouds.vcloud.director.v1_5.config;

/**
 * Configures the cloudstack connection.
 */
/*
@ConfiguresRestClient
public class VCloudDirectorRestClientModule extends RestClientModule<VCloudDirectorApi, VCloudDirectorAsyncApi> {
   
   public static final Map<Class<?>, Class<?>> USER_DELEGATE_MAP = ImmutableMap.<Class<?>, Class<?>>builder()
         .put(CatalogApi.class, CatalogAsyncApi.class)
         .put(MediaApi.class, MediaAsyncApi.class)
         .put(MetadataApi.class, MetadataAsyncApi.class)
         .put(NetworkApi.class, NetworkAsyncApi.class)
         .put(OrgApi.class, OrgAsyncApi.class)
         .put(QueryApi.class, QueryAsyncApi.class)
         .put(TaskApi.class, TaskAsyncApi.class)
         .put(UploadApi.class, UploadAsyncApi.class)
         .put(VAppApi.class, VAppAsyncApi.class)
         .put(VAppTemplateApi.class, VAppTemplateAsyncApi.class)
         .put(VdcApi.class, VdcAsyncApi.class)
         .put(VmApi.class, VmAsyncApi.class)
         .build();
   
   public static final Map<Class<?>, Class<?>> ADMIN_DELEGATE_MAP = ImmutableMap.<Class<?>, Class<?>>builder()
         .putAll(USER_DELEGATE_MAP)
         .put(AdminCatalogApi.class, AdminCatalogAsyncApi.class)
         .put(AdminNetworkApi.class, AdminNetworkAsyncApi.class)
         .put(AdminOrgApi.class, AdminOrgAsyncApi.class)
         .put(AdminQueryApi.class, AdminQueryAsyncApi.class)
         .put(AdminVdcApi.class, AdminVdcAsyncApi.class)
         .put(GroupApi.class, GroupAsyncApi.class)
         .put(UserApi.class, UserAsyncApi.class)
         .build();
   
   public VCloudDirectorRestClientModule() {
      super(ADMIN_DELEGATE_MAP);
   }
   
   @Override
   protected void configure() {
      bind(new TypeLiteral<RestContext<VCloudDirectorAdminApi, VCloudDirectorAdminAsyncApi>>() {
      }).to(new TypeLiteral<RestContextImpl<VCloudDirectorAdminApi, VCloudDirectorAdminAsyncApi>>() {
      });
      
      // Bind apis that are used directly in Functions, Predicates and other circumstances
      bindSyncToAsyncHttpApi(binder(), OrgApi.class, OrgAsyncApi.class);
      bindSyncToAsyncHttpApi(binder(), SessionApi.class, SessionAsyncApi.class);
      bindSyncToAsyncHttpApi(binder(), TaskApi.class, TaskAsyncApi.class);
      bindSyncToAsyncHttpApi(binder(), VAppApi.class, VAppAsyncApi.class);
      bindSyncToAsyncHttpApi(binder(), VmApi.class, VmAsyncApi.class);
      
      bind(HttpRetryHandler.class).annotatedWith(ClientError.class).to(InvalidateSessionAndRetryOn401AndLogoutOnClose.class);
      
      super.configure();
      bindSyncToAsyncHttpApi(binder(),  VCloudDirectorAdminApi.class, VCloudDirectorAdminAsyncApi.class);

   }
   
   @Override
   protected void bindErrorHandlers() {
      bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(VCloudDirectorErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(VCloudDirectorErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(VCloudDirectorErrorHandler.class);
   }
   
   @Provides
   @Login
   protected Supplier<URI> loginUrl(@Provider Supplier<URI> provider) {
      // TODO: technically, we should implement version api, but this will work
      return Suppliers.compose(new Function<URI, URI>() {
         
         @Override
         public URI apply(URI arg0) {
            return URI.create(arg0.toASCIIString() + "/sessions");
         }
         
      }, provider);
   }
   
   @Provides
   protected Supplier<Session> currentSession(Supplier<SessionWithToken> in) {
      return Suppliers.compose(new Function<SessionWithToken, Session>() {
         
         @Override
         public Session apply(SessionWithToken arg0) {
            return arg0.getSession();
         }
         
      }, in);
      
   }
   
   @Provides
   @Singleton
   @org.jclouds.vcloud.director.v1_5.annotations.Session
   protected Supplier<String> sessionToken(Supplier<SessionWithToken> in) {
      return Suppliers.compose(new Function<SessionWithToken, String>() {
         
         @Override
         public String apply(SessionWithToken arg0) {
            return arg0.getToken();
         }
         
      }, in);
      
   }

   @Provides
   @Singleton
   LoadingCache<String, Entity> resolveEntityCache(ResolveEntity loader, @Named(PROPERTY_SESSION_INTERVAL) int seconds) {
      return CacheBuilder.newBuilder().expireAfterWrite(seconds, TimeUnit.SECONDS).build(loader);
   }

   @Provides
   @Singleton
   LoadingCache<Credentials, SessionWithToken> provideSessionWithTokenCache(LoginUserInOrgWithPassword loader,
         @Named(PROPERTY_SESSION_INTERVAL) int seconds) {
      return CacheBuilder.newBuilder().expireAfterWrite(seconds, TimeUnit.SECONDS).build(loader);
   }
   
   // Temporary conversion of a cache to a supplier until there is a single-element cache
   // http://code.google.com/p/guava-libraries/issues/detail?id=872
   @Provides
   @Singleton
   protected Supplier<SessionWithToken> provideSessionWithTokenSupplier(
         final LoadingCache<Credentials, SessionWithToken> cache, @Provider final Supplier<Credentials> creds) {
      return new Supplier<SessionWithToken>() {
         @Override
         public SessionWithToken get() {
            return cache.getUnchecked(creds.get());
         }
      };
   }
}
*/