package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.WebServiceResultStatus;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class WebUtilServiceTests extends AbstractTestHelper {

    private static final String LISTSERVICE_URL = "http://localhost:8081/services/list";
    private static final String TEST_POST_SERVICE_URL = "http://localhost:8081/utiltest/post";

    @Test
    public void testGetShouldReturnSuccess() {
        final Document resultDocument = getWebUtils().get(LISTSERVICE_URL);
        Assert.assertEquals(WebServiceResultStatus.SUCCESS,
                getXmlService().getResultStatus(resultDocument));
    }

    @Test
    public void testGetShouldReturnXmlContent() {
        final Document resultDocument = getWebUtils().get(LISTSERVICE_URL);

        final NodeList serviceElements = resultDocument.getElementsByTagName("service");

        Assert.assertTrue(serviceElements.getLength() > 0);
    }

    @Test
    public void testPostShouldReturnErrorForEmptyInput() {
        final Document result = getWebUtils().post(TEST_POST_SERVICE_URL,
                getXmlService().createEmptyDocument());
        Assert.assertEquals(WebServiceResultStatus.ERROR, getXmlService().getResultStatus(result));
    }

    @Test
    public void testPostShouldReturnSuccessForNonEmptyInput() {
        final Document input = getXmlService().createEmptyDocument();
        final Element element = input.createElement("testRoot");
        input.appendChild(element);

        final Document resultDocument = getWebUtils().post(TEST_POST_SERVICE_URL, input);

        Assert.assertEquals(WebServiceResultStatus.SUCCESS,
                getXmlService().getResultStatus(resultDocument));
    }

}
