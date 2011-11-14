package de.hszigr.atocc.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtils {

    public static String xmlToString(final Document doc) {

        try {
            final StringWriter sw = new StringWriter();

            final TransformerFactory factory = TransformerFactory.newInstance();
            final Transformer transformer = factory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            
            return sw.toString();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static Document stringToXml(String text) {
        InputSource source = new InputSource(new StringReader(text));
        
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(source);
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
}
