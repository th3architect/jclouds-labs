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
package org.jclouds.docker.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.jclouds.docker.suppliers.OverrideProtocolsSSLSocketFactory;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpUtils;
import org.jclouds.http.IOExceptionRetryHandler;
import org.jclouds.http.handlers.DelegatingErrorHandler;
import org.jclouds.http.handlers.DelegatingRetryHandler;
import org.jclouds.http.internal.HttpWire;
import org.jclouds.http.internal.JavaUrlHttpCommandExecutorService;
import org.jclouds.io.ContentMetadataCodec;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

/**
 * Custom implementation of the HTTP driver to read the response body in order to get the real response status.
 * <p>
 * If the Docker daemon is set to use an encrypted TCP socket (--tls, or --tlsverify), then the @see
 * OverrideProtocolsSSLSocketFactory will override the available protocols list to use only TLSv1
 * <p>
 */
@Singleton
public class DockerHttpCommandExecutorService extends JavaUrlHttpCommandExecutorService {

   @Inject
   public DockerHttpCommandExecutorService(HttpUtils utils, ContentMetadataCodec contentMetadataCodec, DelegatingRetryHandler retryHandler, IOExceptionRetryHandler ioRetryHandler, DelegatingErrorHandler errorHandler, HttpWire wire, @Named("untrusted") HostnameVerifier verifier, @Named("untrusted") Supplier<SSLContext> untrustedSSLContextProvider, Function<URI, Proxy> proxyForURI) {
      super(utils, contentMetadataCodec, retryHandler, ioRetryHandler, errorHandler, wire, verifier, untrustedSSLContextProvider, proxyForURI);
   }

   /**
    * Creates and initializes the connection.
    */
   protected HttpURLConnection initConnection(HttpRequest request) throws IOException {
      URL url = request.getEndpoint().toURL();
      HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxyForURI.apply(request.getEndpoint()));
      if (connection instanceof HttpsURLConnection) {
         HttpsURLConnection sslCon = (HttpsURLConnection) connection;
         if (utils.relaxHostname())
            sslCon.setHostnameVerifier(verifier);
         if (sslContextSupplier != null) {
            // used for providers which e.g. use certs for authentication (like Docker)
            // Provider provides SSLContext impl (which inits context with key manager)
            SSLSocketFactory sslSocketFactory = sslContextSupplier.get().getSocketFactory();
            final SSLSocketFactory wrappedFactory = new OverrideProtocolsSSLSocketFactory(sslSocketFactory, new String[] {"TLSv1"});
            sslCon.setSSLSocketFactory(wrappedFactory);
         } else if (utils.trustAllCerts()) {
            sslCon.setSSLSocketFactory(untrustedSSLContextProvider.get().getSocketFactory());
         }
         return sslCon;
      }
      return connection;
   }

}
