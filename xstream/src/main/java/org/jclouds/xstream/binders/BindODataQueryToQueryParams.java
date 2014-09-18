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
package org.jclouds.xstream.binders;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Singleton;

import org.jclouds.http.HttpRequest;
import org.jclouds.rest.Binder;

import com.google.common.base.Charsets;

/**
 * Binds IDs to corresponding query parameters
 */
@Singleton
public class BindODataQueryToQueryParams implements Binder {

   /**
    * Binds the ids to query parameters. The pattern, as specified by GoGrid's specification, is:
    * 
    * https://api.gogrid.com/api/grid/server/get ?id=5153 &id=3232
    * 
    * @param request
    *           request where the query params will be set
    * @param input
    *           array of String params
    */
   @SuppressWarnings("unchecked")
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {

      if (checkNotNull(input, "input is null") instanceof String) {
         String odataQuery;
         try {
            odataQuery = URLEncoder.encode((String) input, Charsets.UTF_8.name());
         } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Bad encoding on input: " + input, e);
         }
         return (R) request.toBuilder().replaceQueryParam("$filter", odataQuery).build();
      } else {
         throw new IllegalArgumentException("this binder is only valid for String arguments: " + input.getClass());
      }
   }
}
