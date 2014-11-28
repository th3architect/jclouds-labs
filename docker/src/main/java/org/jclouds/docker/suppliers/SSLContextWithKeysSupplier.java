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
package org.jclouds.docker.suppliers;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Throwables.propagate;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.jclouds.domain.Credentials;
import org.jclouds.http.HttpUtils;
import org.jclouds.http.config.SSLModule.TrustAllCerts;
import org.jclouds.location.Provider;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.google.common.io.Files;

@Singleton
public class SSLContextWithKeysSupplier implements Supplier<SSLContext> {
   private final TrustManager[] trustManager;
   private final Supplier<Credentials> creds;

   @Inject
   SSLContextWithKeysSupplier(@Provider Supplier<Credentials> creds, HttpUtils utils,
                              TrustAllCerts trustAllCerts) {
      this.trustManager = utils.trustAllCerts() ? new TrustManager[]{trustAllCerts} : null;
      this.creds = creds;
   }

   @Override
   public SSLContext get() {
      if (Security.getProvider("BC") == null) {
         Security.addProvider(new BouncyCastleProvider());
      }
      Credentials currentCreds = checkNotNull(creds.get(), "credential supplier returned null");
      try {
         // TODO this is a temporary solution to disable SSL v3.0 in JDK and JRE b/c of https://github.com/docker/docker/pull/8588/files.
         // This system property will be removed once we can use an http driver with strict control on TLS protocols
         System.setProperty("https.protocols", "TLSv1");
         SSLContext sslContext = SSLContext.getInstance("TLS");
         X509Certificate certificate = getCertificate(loadFile(currentCreds.identity));
         PrivateKey privateKey = getKey(loadFile(currentCreds.credential));
         sslContext.init(new KeyManager[]{new InMemoryKeyManager(certificate, privateKey)}, trustManager, new SecureRandom());
         return sslContext;
      } catch (NoSuchAlgorithmException e) {
         throw propagate(e);
      } catch (KeyManagementException e) {
         throw propagate(e);
      } catch (CertificateException e) {
         throw propagate(e);
      } catch (IOException e) {
         throw propagate(e);
      }
   }

   private X509Certificate getCertificate(String certificate) {
      try {
         return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(
                 new ByteArrayInputStream(certificate.getBytes(Charsets.UTF_8)));
      } catch (CertificateException ex) {
         throw new RuntimeException("Invalid certificate", ex);
      }
   }

   private PrivateKey getKey(String privateKey) {

      try {
         PEMReader pemReader = new PEMReader(new StringReader(privateKey));
         KeyPair keyPair = (KeyPair) pemReader.readObject();
         return keyPair.getPrivate();
      } catch (IOException ex) {
         throw new RuntimeException("Invalid private key", ex);
      }
   }

   private static String loadFile(final String filePath) throws IOException {
      return Files.toString(new File(filePath), Charsets.UTF_8);
   }

   private static class InMemoryKeyManager extends X509ExtendedKeyManager {
      private static final String DEFAULT_ALIAS = "docker";

      private final X509Certificate certificate;

      private final PrivateKey privateKey;

      public InMemoryKeyManager(final X509Certificate certificate, final PrivateKey privateKey)
              throws IOException, CertificateException {
         this.certificate = certificate;
         this.privateKey = privateKey;
      }

      @Override
      public String chooseClientAlias(final String[] keyType, final Principal[] issuers,
                                      final Socket socket) {
         return DEFAULT_ALIAS;
      }

      @Override
      public String chooseServerAlias(final String keyType, final Principal[] issuers,
                                      final Socket socket) {
         return DEFAULT_ALIAS;
      }

      @Override
      public X509Certificate[] getCertificateChain(final String alias) {
         return new X509Certificate[]{certificate};
      }

      @Override
      public String[] getClientAliases(final String keyType, final Principal[] issuers) {
         return new String[]{DEFAULT_ALIAS};
      }

      @Override
      public PrivateKey getPrivateKey(final String alias) {
         return privateKey;
      }

      @Override
      public String[] getServerAliases(final String keyType, final Principal[] issuers) {
         return new String[]{DEFAULT_ALIAS};
      }
   }

}
