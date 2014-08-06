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
package org.jclouds.docker.domain;

import org.jclouds.json.SerializedNames;

<<<<<<< HEAD
import com.google.auto.value.AutoValue;
=======
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

@AutoValue
public abstract class State {
   public abstract int pid();

<<<<<<< HEAD
   public abstract boolean running();
=======
   @ConstructorProperties({ "Pid", "Running", "ExitCode", "StartedAt", "FinishedAt", "Ghost" })
   protected State(int pid, boolean running, int exitCode, String startedAt, String finishedAt, boolean ghost) {
      this.pid = checkNotNull(pid, "pid");
      this.running = checkNotNull(running, "running");
      this.exitCode = checkNotNull(exitCode, "exitCode");
      this.startedAt = checkNotNull(startedAt, "startedAt");
      this.finishedAt = checkNotNull(finishedAt, "finishedAt");
      this.ghost = checkNotNull(ghost, "ghost");
   }

   public int getPid() {
      return pid;
   }

   public boolean isRunning() {
      return running;
   }

   public int getExitCode() {
      return exitCode;
   }

   public String getStartedAt() {
      return startedAt;
   }

   public String getFinishedAt() {
      return finishedAt;
   }

   public boolean isGhost() {
      return ghost;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      State that = (State) o;

      return Objects.equal(this.pid, that.pid) &&
              Objects.equal(this.running, that.running) &&
              Objects.equal(this.exitCode, that.exitCode) &&
              Objects.equal(this.startedAt, that.startedAt) &&
              Objects.equal(this.finishedAt, that.finishedAt) &&
              Objects.equal(this.ghost, that.ghost);
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(pid, running, exitCode, startedAt, finishedAt, ghost);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
              .add("pid", pid)
              .add("running", running)
              .add("exitCode", exitCode)
              .add("startedAt", startedAt)
              .add("finishedAt", finishedAt)
              .add("ghost", ghost)
              .toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public Builder toBuilder() {
      return builder().fromState(this);
   }

   public static final class Builder {

      private int pid;
      private boolean running;
      private int exitCode;
      private String startedAt;
      private String finishedAt;
      private boolean ghost;

      public Builder pid(int pid) {
         this.pid = pid;
         return this;
      }

      public Builder running(boolean running) {
         this.running = running;
         return this;
      }

      public Builder exitCode(int exitCode) {
         this.exitCode = exitCode;
         return this;
      }
>>>>>>> JCLOUDS-653: Address Guava 18 deprecations

   public abstract int exitCode();

   public abstract String startedAt();

   public abstract String finishedAt();

   public abstract boolean ghost();

   @SerializedNames({ "Pid", "Running", "ExitCode", "StartedAt", "FinishedAt", "Ghost" })
   public static State create(int pid, boolean running, int exitCode, String startedAt, String finishedAt,
         boolean ghost) {
      return new AutoValue_State(pid, running, exitCode, startedAt, finishedAt, ghost);
   }
}
