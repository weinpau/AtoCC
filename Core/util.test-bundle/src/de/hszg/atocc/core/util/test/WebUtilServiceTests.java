package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.WebServiceResultStatus;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public final class WebUtilServiceTests extends AbstractTestHelper {

    private static final String LISTSERVICE_URL = "http://localhost:8081/services/list";

    @Test
    public void testGetShouldReturnSuccess() {
        final Document result = getWebUtils().get(LISTSERVICE_URL);
        Assert.assertEquals(WebServiceResultStatus.SUCCESS, 
                getXmlService().getResultStatus(result));
    }

    @Test
    public void testGetShouldReturnXmlContent() {
        final Document result = getWebUtils().get(LISTSERVICE_URL);

        final NodeList serviceElements = result.getElementsByTagName("service");

        Assert.assertTrue(serviceElements.getLength() > 0);
    }

    @Test
    public void testPost() {
        Assert.fail("Not yet implemented");
    }

}
