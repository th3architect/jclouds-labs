package org.jclouds.dmtf.ovf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="VCloudExtensionType", propOrder={"any"})
public class VCloudExtensionType
{

  @XmlAnyElement(lax=true)
  protected List<Object> any;

  @XmlAttribute
  protected Boolean required;

  @XmlAnyAttribute
  private Map<QName, String> otherAttributes = new HashMap();

  public List<Object> getAny()
  {
    if (this.any == null) {
      this.any = new ArrayList();
    }
    return this.any;
  }

  public boolean isRequired()
  {
    if (this.required == null) {
      return true;
    }
    return this.required.booleanValue();
  }

  public void setRequired(Boolean value)
  {
    this.required = value;
  }

  public Map<QName, String> getOtherAttributes()
  {
    return this.otherAttributes;
  }
}