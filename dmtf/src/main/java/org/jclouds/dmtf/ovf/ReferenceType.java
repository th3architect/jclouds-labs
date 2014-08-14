package org.jclouds.dmtf.ovf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReferenceType")
public class ReferenceType extends VCloudExtensibleType {

  @XmlAttribute(required=true)
  protected String href;

  @XmlAttribute
  protected String id;

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected String type;

  public String getHref()
  {
    return this.href;
  }

  public void setHref(String value)
  {
    this.href = value;
  }

  public String getId()
  {
    return this.id;
  }

  public void setId(String value)
  {
    this.id = value;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String value)
  {
    this.name = value;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String value)
  {
    this.type = value;
  }
}