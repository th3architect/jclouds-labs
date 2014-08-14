package org.jclouds.dmtf.ovf;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "References")
@XmlType(name="ReferencesType")//, propOrder={"reference"})
public class ReferencesType extends ContainerType {

  /*
  @XmlElementRef(name="Reference", namespace="http://www.vmware.com/vcloud/v1.5", type=JAXBElement.class)
  protected List<JAXBElement<ReferenceType>> reference;

  public List<JAXBElement<ReferenceType>> getReference()
  {
    if (this.reference == null) {
      this.reference = new ArrayList();
    }
    return this.reference;
  }
   */
}