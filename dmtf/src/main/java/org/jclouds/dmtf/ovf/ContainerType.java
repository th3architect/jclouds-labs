package org.jclouds.dmtf.ovf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ContainerType")
public class ContainerType extends ResourceType {

  @XmlAttribute
  protected String name;

  @XmlAttribute
  protected Integer page;

  @XmlAttribute
  protected Integer pageSize;

  @XmlAttribute
  protected Long total;

  public String getName()
  {
    return this.name;
  }

  public void setName(String value)
  {
    this.name = value;
  }

  public Integer getPage()
  {
    return this.page;
  }

  public void setPage(Integer value)
  {
    this.page = value;
  }

  public Integer getPageSize()
  {
    return this.pageSize;
  }

  public void setPageSize(Integer value)
  {
    this.pageSize = value;
  }

  public Long getTotal()
  {
    return this.total;
  }

  public void setTotal(Long value)
  {
    this.total = value;
  }
}