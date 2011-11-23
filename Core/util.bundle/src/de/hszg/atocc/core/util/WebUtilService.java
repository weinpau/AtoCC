package de.hszg.atocc.core.util;

import org.w3c.dom.Document;

public interface WebUtilService {

    Document get(String uri);
    Document put(String uri, Document doc);
    
}
