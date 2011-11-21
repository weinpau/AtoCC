package de.hszg.atocc.core.util;

import javax.xml.ws.WebServiceException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

public final class WebService {

    private WebService() {

    }

    public static Document get(final String uri) {

        try {
            return new ClientResource(uri).get(Document.class);
        } catch (final ResourceException e) {
            throw new WebServiceException(e);
        }

    }

    public static Document put(final String uri, final Document doc) {

        try {
            final ClientResource resource = new ClientResource(uri);

            return resource.put(doc, Document.class);
        } catch (final ResourceException e) {
            throw new WebServiceException(e);
        }

    }
}
