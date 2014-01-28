#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#!/bin/bash
#OS=$(awk '/DISTRIB_ID=/' /etc/*-release | sed 's/DISTRIB_ID=//' | tr '[:upper:]' '[:lower:]')
#ARCH=$(uname -m | sed 's/x86_//;s/i[3-6]86/32/')
#VERSION=$(awk '/DISTRIB_RELEASE=/' /etc/*-release | sed 's/DISTRIB_RELEASE=//' | sed 's/[.]0/./')

#!/bin/bash
# Survey of CentOS 6.5, Debian Jessie, Fedora 17, OSX and Ubuntu 12.04 suggests
# uname -m is the most reliable flag for architecture
ARCH=$(uname -m | sed 's/x86_//;s/i[3-6]86/32/')

# Try the standard
if [ -f /etc/os-release ]; then
    source /etc/os-release

# Try RedHat-based systems
elif [ -f /etc/redhat-release ]; then
    # Example: Red Hat Enterprise Linux Server release 6.3 (Santiago)
    # Match everything up to ' release'
    OS=$(cat /etc/redhat-release | sed 's/ release.*//')
    # Match everything between 'release ' and the next space
    VERSION=$(cat /etc/redhat-release | sed 's/.*release \([^ ]*\).*/\1/')

# Try Ubuntu
elif [ -f /etc/lsb-release ]; then
    # Example: DISTRIB_ID=Ubuntu
    #          DISTRIB_RELEASE=12.04
    #          DISTRIB_CODENAME=precise
    #          DISTRIB_DESCRIPTION="Ubuntu 12.04 LTS"
    OS=$(cat /etc/lsb-release| grep DISTRIB_ID= | sed 's/DISTRIB_ID=//')
    # Match everything between 'release ' and the next space
    VERSION=$(cat /etc/lsb-release| grep DISTRIB_RELEASE= | sed 's/DISTRIB_RELEASE=//')
fi
echo "os:$OS;arch:$ARCH;version:$VERSION"