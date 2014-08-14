package org.jclouds.dmtf.ovf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ResourceType", propOrder={"link"})
public class ResourceType extends VCloudExtensibleType {

  @XmlElement(name="Link")
  protected List<LinkType> link;

  @XmlAttribute
  protected String href;

  @XmlAttribute
  protected String type;

  public List<LinkType> getLink()
  {
    if (this.link == null) {
      this.link = new ArrayList();
    }
    return this.link;
  }

  public String getHref()
  {
    return this.href;
  }

  public void setHref(String value)
  {
    this.href = value;
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