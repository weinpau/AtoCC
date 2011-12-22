package de.hszg.atocc.core.util.internal;

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

public final class Converter {

    public String xmlToString(final Document doc) throws TransformerException {
        final StringWriter sw = new StringWriter();

        final Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.transform(new DOMSource(doc), new StreamResult(sw));

        return sw.toString();
    }

    public Document stringToXml(final String data) throws ConverterException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(data)));
        } catch (final SAXException | IOException | ParserConfigurationException e) {
            throw new ConverterException(e);
        }
    }

}
