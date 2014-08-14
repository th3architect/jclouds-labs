package org.jclouds.dmtf.ovf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="LinkType")
public class LinkType extends ReferenceType
{

  @XmlAttribute(required=true)
  protected String rel;

  public String getRel()
  {
    return this.rel;
  }

  public void setRel(String value)
  {
    this.rel = value;
  }
}