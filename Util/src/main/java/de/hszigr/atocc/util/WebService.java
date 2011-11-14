package de.hszigr.atocc.util;

import javax.xml.ws.WebServiceException;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;

public final class WebService {

    public static Document get(final String uri) {
        try {
            return XmlUtils.stringToXml(new ClientResource(uri).get().getText());
        } catch (Exception e) {
            throw new WebServiceInvocationException(e);
        }
    }

    public static Document put(final String uri, final Document doc) {

        try {
            ClientResource resource = new ClientResource(uri);
            String result = resource.put(new StringRepresentation(XmlUtils.xmlToString(doc)),
                MediaType.APPLICATION_ALL_XML).getText();

            return XmlUtils.stringToXml(result);
        } catch (Exception e) {
            throw new WebServiceException(e);
        }
    }

}
