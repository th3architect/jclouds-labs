package org.jclouds.vcloud.director.v1_5.domain.network;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "IpScopes")
@XmlAccessorType(XmlAccessType.FIELD)
public class IpScopes {

   @XmlElement(name = "IpScope")
   private List<IpScope> ipsScopes = null;

   public List<IpScope> getIpsScopes() {
      return ipsScopes;
   }
}