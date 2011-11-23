package de.hszg.atocc.core.util;


import org.w3c.dom.Document;

public interface XmlUtilService {

    Document documentFromFile(String filename) throws XmlUtilsException;
    
    Document createResult(Document doc);
    Document createResultWithError(String errorMessage, Exception reason);
    
    Document createEmptyDocument();
    
}
