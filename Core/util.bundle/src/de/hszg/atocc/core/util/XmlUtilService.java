package de.hszg.atocc.core.util;

import de.hszg.atocc.core.util.internal.ConverterException;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

public interface XmlUtilService {

    Document documentFromFile(String filename) throws XmlUtilsException;
    
    Document createResult(Document doc);
    Document createResultWithError(String errorMessage, Exception reason);
    
    String getResultStatus(Document resultDocument);
    
    Document getContent(Document resultDocument);
    
    Document createEmptyDocument();
    
    String xmlToString(Document doc) throws TransformerException;
    Document stringToXml(String data) throws ConverterException;
    
}
