package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;

public final class Nea2DeaTests {

    private static WebUtilService webService;
    private static XmlUtilService xmlUtils;

    public static void setWebService(WebUtilService service) {
        webService = service;
    }

    public static void setXmlUtils(XmlUtilService service) {
        xmlUtils = service;
    }

    @Test
    public void test() throws XmlUtilsException {
        final Document nea4 = xmlUtils.documentFromFile("nea4.xml");

        final Document dea = webService.post("http://localhost:8081/autoedit/nea2dea", nea4);

        Assert.assertNotNull(dea);

        Assert.fail("Not yet implemented");
    }

}
