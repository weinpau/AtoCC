package de.hszg.atocc.core.util;

import de.hszg.atocc.core.util.internal.ConverterException;

import java.util.Locale;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

public interface XmlUtilService {
    
    String SUCCESS = "success";
    String ERROR = "error";

    Document documentFromFile(String filename) throws XmlUtilsException;

    Document createResult(Document doc);

    Document createResultWithError(String errorCode, String errorMessage, Exception reason);

    Document createResultWithError(String errorCode, Exception reason, Locale locale);

    String getResultStatus(Document resultDocument);
    String getErrorMessage(Document resultDocument);
    String getErrorCause(Document resultDocument);

    Document getContent(Document resultDocument);

    Document createEmptyDocument();

    String xmlToString(Document doc) throws TransformerException;

    Document stringToXml(String data) throws ConverterException;

}
