package de.hszigr.atocc.util;

import java.io.IOException;

import javax.xml.ws.WebServiceException;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

public final class WebService {

    private WebService() {

    }

    public static Document get(final String uri) {

        try {
            return XmlUtils.stringToXml(new ClientResource(uri).get().getText());
        } catch (final ResourceException | IOException e) {
            throw new WebServiceException(e);
        }

    }

    public static Document put(final String uri, final Document doc) {

        try {
            final ClientResource resource = new ClientResource(uri);
            final String result = resource.put(new StringRepresentation(XmlUtils.xmlToString(doc)),
                MediaType.APPLICATION_ALL_XML).getText();

            return XmlUtils.stringToXml(result);
        } catch (final ResourceException | IOException e) {
            throw new WebServiceException(e);
        }

    }

}
