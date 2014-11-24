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
package org.jclouds.docker.options;

import org.jclouds.http.options.BaseHttpRequestOptions;

/**
 * Options to kill container builder.
 */
public class KillOptions extends BaseHttpRequestOptions {

   public static final KillOptions NONE = new KillOptions();

   /**
    *
    * @param signal Signal to send to the container: integer or string like "SIGINT".
    *               When not set, SIGKILL is assumed and the call will waits for the container to exit.
    * @return KillOptions
    */
   public KillOptions signal(String signal) {
      this.queryParameters.put("signal", signal);
      return this;
   }

   public static class Builder {

      /**
       * @see KillOptions#signal
       */
      public static KillOptions signal(String signal) {
         KillOptions options = new KillOptions();
         return options.signal(signal);
      }
   }

}
