package de.hszg.atocc.core.util;

import org.w3c.dom.Document;

public interface WebUtilService {

    Document get(String uri);

    /**
     * Send a HTTP POST and return the result document. Throws an exception if the result document
     * contains an error.
     * @param uri
     * @param doc
     * @return
     */
    Document post(String uri, Document doc);

}
