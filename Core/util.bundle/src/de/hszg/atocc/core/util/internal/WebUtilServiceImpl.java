package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.WebUtilService;

import javax.xml.ws.WebServiceException;

import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.w3c.dom.Document;

public final class WebUtilServiceImpl implements WebUtilService {

    public Document get(final String uri) {

        try {
            return new ClientResource(uri).get(Document.class);
        } catch (final ResourceException e) {
            throw new WebServiceException(e);
        }

    }

    public Document put(final String uri, final Document doc) {

        try {
            final ClientResource resource = new ClientResource(uri);

            return resource.put(doc, Document.class);
        } catch (final ResourceException e) {
            throw new WebServiceException(e);
        }

    }
}
