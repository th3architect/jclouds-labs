package org.jclouds.dmtf.ovf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VCloudExtensibleType", propOrder={"vCloudExtension"})
public abstract class VCloudExtensibleType
{

  @XmlElement(name="VCloudExtension")
  protected List<VCloudExtensionType> vCloudExtension;

  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap();

  public List<VCloudExtensionType> getVCloudExtension()
  {
    if (this.vCloudExtension == null) {
      this.vCloudExtension = new ArrayList();
    }
    return this.vCloudExtension;
  }

  public Map<QName, String> getOtherAttributes()
  {
    return this.otherAttributes;
  }
}