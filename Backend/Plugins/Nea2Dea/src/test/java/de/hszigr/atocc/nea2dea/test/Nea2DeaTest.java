package de.hszigr.atocc.nea2dea.test;

import javax.xml.transform.TransformerException;

import junit.framework.Assert;

import org.junit.Test;
import org.restlet.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hszigr.atocc.nea2dea.impl.Nea2Dea;
import de.hszigr.atocc.util.Converter;
import de.hszigr.atocc.util.XmlUtils;
import de.hszigr.atocc.util.test.AbstractServiceTest;

public class Nea2DeaTest extends AbstractServiceTest {

    private Document testDocument;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        
        testDocument = createTestDocument();
    }
    
    @Test
    public void verifyThatXmlPutSucceeds() throws TransformerException {
        final Document result = put("/nea2dea", testDocument);
        
        Assert.assertNotNull(result);
    }
    
    @Test
    public void verifyServiceFailsForAutomatonTypeOtherThanNEA() {
        changeTypeToDea(testDocument);
        final Document result = put("/nea2dea", testDocument);
        
        Element resultElement = result.getDocumentElement();
        
        Assert.assertEquals("error", resultElement.getAttribute("status"));
    }
    
    @Test
    public void test() throws TransformerException {
        final Document result = put("/nea2dea", testDocument);
        
        System.out.println(Converter.xmlToString(result));
    }

    @Override
    protected void registerServices(final Component component) {
        component.getDefaultHost().attach("/nea2dea", Nea2Dea.class);
    }
    
    private Document createTestDocument() {
        return XmlUtils.documentFromFile("target/test-classes/TestNea.xml");
    }
    
    private void changeTypeToDea(Document nea) {
        Element automaton = nea.getDocumentElement();
        Element type = (Element) automaton.getElementsByTagName("TYPE").item(0);
        type.setAttribute("value", "DEA");
    }

}
