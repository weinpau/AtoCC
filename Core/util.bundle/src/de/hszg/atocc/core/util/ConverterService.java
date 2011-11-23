package de.hszg.atocc.core.util;

import de.hszg.atocc.core.util.internal.ConverterException;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

public interface ConverterService {

    String xmlToString(Document doc) throws TransformerException;
    Document stringToXml(String data) throws ConverterException;
    
}
