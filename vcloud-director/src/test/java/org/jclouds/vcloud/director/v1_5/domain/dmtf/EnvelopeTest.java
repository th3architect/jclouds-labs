package org.jclouds.vcloud.director.v1_5.domain.dmtf;

import static org.testng.Assert.assertNotNull;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test(groups = "unit", testName = "EnvelopeTest")
public class EnvelopeTest {

   private JAXBContext jc;
   @BeforeClass
   protected void setup() throws JAXBException {
      jc = JAXBContext.newInstance(Envelope.class);
      assertNotNull(jc);
   }

   public void testUnmarshallEnvelope() throws JAXBException {
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
      File xml = new File("vcloud-director/src/test/resources/dmtf/envelope.xml");
      Envelope envelope = (Envelope) unmarshaller.unmarshal(xml);
      System.out.println(envelope);
   }

   /*
   public void testMarshallEnvelope() throws JAXBException {
      Marshaller marshaller = jc.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
      EnvelopeType envelope = EnvelopeType.builder()
              .references(new ReferencesType())
              .virtualSystem(
                      VirtualSystemType.builder()
                              .operatingSystemSection(OperatingSystemSection.builder().osType("windows7Server64Guest").build())
                              .build())
              .build();
      marshaller.marshal(envelope, System.out);
   }
   */

}