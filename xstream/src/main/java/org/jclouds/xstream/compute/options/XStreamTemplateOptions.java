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
package org.jclouds.xstream.compute.options;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.domain.LoginCredentials;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.scriptbuilder.domain.Statement;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Contains options supported in the {@code ComputeService#runNode} operation on the
 * "xStream" provider. <h2>Usage</h2> The recommended way to instantiate a
 * XStreamTemplateOptions object is to statically import XStreamTemplateOptions.* and invoke a static
 * creation method followed by an instance mutator (if needed):
 * <p/>
 * <code>
 * import static XStreamTemplateOptions.Builder.*;
 * <p/>
 * ComputeService api = // get connection
 * templateBuilder.options(inboundPorts(22, 80, 8080, 443));
 * Set<? extends NodeMetadata> set = api.createNodesInGroup(tag, 2, templateBuilder.build());
 * <code>
 */
public class XStreamTemplateOptions extends TemplateOptions implements Cloneable {

   protected Optional<String> dns = Optional.absent();
   protected Optional<String> hostname = Optional.absent();
   protected Optional<Integer> memory = Optional.absent();
   protected Optional<Integer> cpuShares = Optional.absent();
   protected Optional<List<String>> commands = Optional.absent();
   protected Optional<Map<String, String>> volumes = Optional.absent();

   @Override
   public XStreamTemplateOptions clone() {
      XStreamTemplateOptions options = new XStreamTemplateOptions();
      copyTo(options);
      return options;
   }

   @Override
   public void copyTo(TemplateOptions to) {
      super.copyTo(to);
      if (to instanceof XStreamTemplateOptions) {
         XStreamTemplateOptions eTo = XStreamTemplateOptions.class.cast(to);
         if (volumes.isPresent()) {
            eTo.volumes(getVolumes().get());
         }
         if (hostname.isPresent()) {
            eTo.hostname(hostname.get());
         }
         if (dns.isPresent()) {
            eTo.dns(dns.get());
         }
         if (memory.isPresent()) {
            eTo.memory(memory.get());
         }
         if (commands.isPresent()) {
            eTo.commands(commands.get());
         }
         if (cpuShares.isPresent()) {
            eTo.cpuShares(cpuShares.get());
         }
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      XStreamTemplateOptions that = XStreamTemplateOptions.class.cast(o);
      return super.equals(that) && equal(this.volumes, that.volumes) &&
              equal(this.hostname, that.hostname) &&
              equal(this.dns, that.dns) &&
              equal(this.memory, that.memory) &&
              equal(this.commands, that.commands) &&
              equal(this.cpuShares, that.cpuShares);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), volumes, hostname, dns, memory, commands, cpuShares);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
              .add("dns", dns)
              .add("hostname", hostname)
              .add("memory", memory)
              .add("cpuShares", cpuShares)
              .add("commands", commands)
              .add("volumes", volumes)
              .toString();
   }

   public static final XStreamTemplateOptions NONE = new XStreamTemplateOptions();

   public XStreamTemplateOptions volumes(Map<String, String> volumes) {
      this.volumes = Optional.<Map<String, String>> of(ImmutableMap.copyOf(volumes));
      return this;
   }

   public TemplateOptions dns(@Nullable String dns) {
      this.dns = Optional.fromNullable(dns);
      return this;
   }

   public TemplateOptions hostname(@Nullable String hostname) {
      this.hostname = Optional.fromNullable(hostname);
      return this;
   }

   public TemplateOptions memory(@Nullable Integer memory) {
      this.memory = Optional.fromNullable(memory);
      return this;
   }

   public TemplateOptions commands(Iterable<String> commands) {
      for (String command : checkNotNull(commands, "commands"))
         checkNotNull(command, "all commands must be non-empty");
      this.commands = Optional.<List<String>> of(ImmutableList.copyOf(commands));
      return this;
   }

   public TemplateOptions commands(String... commands) {
      return commands(ImmutableList.copyOf(checkNotNull(commands, "commands")));
   }

   public TemplateOptions cpuShares(@Nullable Integer cpuShares) {
      this.cpuShares = Optional.fromNullable(cpuShares);
      return this;
   }

   public Optional<Map<String, String>> getVolumes() {
      return volumes;
   }

   public Optional<String> getDns() { return dns; }

   public Optional<String> getHostname() { return hostname; }

   public Optional<Integer> getMemory() { return memory; }

   public Optional<List<String>> getCommands() {
      return commands;
   }

   public Optional<Integer> getCpuShares() { return cpuShares; }

   public static class Builder {

      /**
       * @see XStreamTemplateOptions#volumes(java.util.Map)
       */
      public static XStreamTemplateOptions volumes(Map<String, String> volumes) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.volumes(volumes));
      }

      /**
       * @see XStreamTemplateOptions#dns(String)
       */
      public static XStreamTemplateOptions dns(String dns) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.dns(dns));
      }

      /**
       * @see XStreamTemplateOptions#hostname(String)
       */
      public static XStreamTemplateOptions hostname(String hostname) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.hostname(hostname));
      }

      /**
       * @see XStreamTemplateOptions#memory
       */
      public static XStreamTemplateOptions memory(int memory) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.memory(memory));
      }

      /**
       * @see XStreamTemplateOptions#commands(Iterable)
       */
      public static XStreamTemplateOptions commands(String... commands) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.commands(commands));
      }

      public static XStreamTemplateOptions commands(Iterable<String> commands) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.commands(commands));
      }

      /**
       * @see XStreamTemplateOptions#cpuShares(int)
       */
      public static XStreamTemplateOptions cpuShares(int cpuShares) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.cpuShares(cpuShares));
      }

      // methods that only facilitate returning the correct object type

      /**
       * @see TemplateOptions#inboundPorts
       */
      public static XStreamTemplateOptions inboundPorts(int... ports) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.inboundPorts(ports));
      }

      /**
       * @see TemplateOptions#port
       */
      public static XStreamTemplateOptions blockOnPort(int port, int seconds) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.blockOnPort(port, seconds));
      }

      /**
       * @see TemplateOptions#installPrivateKey
       */
      public static XStreamTemplateOptions installPrivateKey(String rsaKey) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.installPrivateKey(rsaKey));
      }

      /**
       * @see TemplateOptions#authorizePublicKey
       */
      public static XStreamTemplateOptions authorizePublicKey(String rsaKey) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.authorizePublicKey(rsaKey));
      }

      /**
       * @see TemplateOptions#userMetadata
       */
      public static XStreamTemplateOptions userMetadata(Map<String, String> userMetadata) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.userMetadata(userMetadata));
      }

      /**
       * @see TemplateOptions#nodeNames(Iterable)
       */
      public static XStreamTemplateOptions nodeNames(Iterable<String> nodeNames) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.nodeNames(nodeNames));
      }

      /**
       * @see TemplateOptions#networks(Iterable)
       */
      public static XStreamTemplateOptions networks(Iterable<String> networks) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return XStreamTemplateOptions.class.cast(options.networks(networks));
      }

      /**
       * @see TemplateOptions#overrideLoginUser
       */
      public static XStreamTemplateOptions overrideLoginUser(String user) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return options.overrideLoginUser(user);
      }

      /**
       * @see TemplateOptions#overrideLoginPassword
       */
      public static XStreamTemplateOptions overrideLoginPassword(String password) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return options.overrideLoginPassword(password);
      }

      /**
       * @see TemplateOptions#overrideLoginPrivateKey
       */
      public static XStreamTemplateOptions overrideLoginPrivateKey(String privateKey) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return options.overrideLoginPrivateKey(privateKey);
      }

      /**
       * @see TemplateOptions#overrideAuthenticateSudo
       */
      public static XStreamTemplateOptions overrideAuthenticateSudo(boolean authenticateSudo) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return options.overrideAuthenticateSudo(authenticateSudo);
      }

      /**
       * @see TemplateOptions#overrideLoginCredentials
       */
      public static XStreamTemplateOptions overrideLoginCredentials(LoginCredentials credentials) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return options.overrideLoginCredentials(credentials);
      }

      /**
       * @see TemplateOptions#blockUntilRunning
       */
      public static XStreamTemplateOptions blockUntilRunning(boolean blockUntilRunning) {
         XStreamTemplateOptions options = new XStreamTemplateOptions();
         return options.blockUntilRunning(blockUntilRunning);
      }

   }

   // methods that only facilitate returning the correct object type

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions blockOnPort(int port, int seconds) {
      return XStreamTemplateOptions.class.cast(super.blockOnPort(port, seconds));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions inboundPorts(int... ports) {
      return XStreamTemplateOptions.class.cast(super.inboundPorts(ports));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions authorizePublicKey(String publicKey) {
      return XStreamTemplateOptions.class.cast(super.authorizePublicKey(publicKey));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions installPrivateKey(String privateKey) {
      return XStreamTemplateOptions.class.cast(super.installPrivateKey(privateKey));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions blockUntilRunning(boolean blockUntilRunning) {
      return XStreamTemplateOptions.class.cast(super.blockUntilRunning(blockUntilRunning));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions dontAuthorizePublicKey() {
      return XStreamTemplateOptions.class.cast(super.dontAuthorizePublicKey());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions nameTask(String name) {
      return XStreamTemplateOptions.class.cast(super.nameTask(name));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions runAsRoot(boolean runAsRoot) {
      return XStreamTemplateOptions.class.cast(super.runAsRoot(runAsRoot));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions runScript(Statement script) {
      return XStreamTemplateOptions.class.cast(super.runScript(script));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions overrideLoginCredentials(LoginCredentials overridingCredentials) {
      return XStreamTemplateOptions.class.cast(super.overrideLoginCredentials(overridingCredentials));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions overrideLoginPassword(String password) {
      return XStreamTemplateOptions.class.cast(super.overrideLoginPassword(password));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions overrideLoginPrivateKey(String privateKey) {
      return XStreamTemplateOptions.class.cast(super.overrideLoginPrivateKey(privateKey));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions overrideLoginUser(String loginUser) {
      return XStreamTemplateOptions.class.cast(super.overrideLoginUser(loginUser));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions overrideAuthenticateSudo(boolean authenticateSudo) {
      return XStreamTemplateOptions.class.cast(super.overrideAuthenticateSudo(authenticateSudo));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions userMetadata(Map<String, String> userMetadata) {
      return XStreamTemplateOptions.class.cast(super.userMetadata(userMetadata));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions userMetadata(String key, String value) {
      return XStreamTemplateOptions.class.cast(super.userMetadata(key, value));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions nodeNames(Iterable<String> nodeNames) {
      return XStreamTemplateOptions.class.cast(super.nodeNames(nodeNames));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public XStreamTemplateOptions networks(Iterable<String> networks) {
      return XStreamTemplateOptions.class.cast(super.networks(networks));
   }

}
