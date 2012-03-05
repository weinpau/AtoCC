package de.hszg.atocc.compile.internal.util;

import de.hszg.atocc.compile.internal.task.TaskDefinition;
import de.hszg.atocc.core.util.SerializationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class TaskDefinitionDeserializer {

    private Document document;
    private TaskDefinition task = new TaskDefinition();

    private TaskDefinitionDeserializer() {
    }

    private TaskDefinitionDeserializer(Document aDocument) {
        document = aDocument;
    }

    public static TaskDefinition deserialize(Document aDocument) throws SerializationException {
        TaskDefinitionDeserializer deserializer = new TaskDefinitionDeserializer(aDocument);

        deserializer.deserialize();

        return deserializer.task;
    }

    private void deserialize() throws SerializationException {
        setLanguage();
        addSources();
    }

    private void setLanguage() throws SerializationException {
        try {
            
            final XPath xpath = XPathFactory.newInstance().newXPath();
            final String language = (String) xpath.evaluate("//option[@name='language']/text()",
                    document, XPathConstants.STRING);

            task.setLanguage(language);
            
        } catch (XPathExpressionException ex) {
            throw new SerializationException(ex);
        }
    }

    private void addSources() throws SerializationException {
        try {
            
            final XPath xpath = XPathFactory.newInstance().newXPath();
            final NodeList sources = (NodeList) xpath.evaluate("//sources/source", document,
                    XPathConstants.NODESET);
            
            for(int i = 0; i < sources.getLength(); ++i) {
               final Element sourceNode = (Element) sources.item(i);
               final String filename = sourceNode.getAttribute("filename");
               final String source = sourceNode.getTextContent();
               
               task.addSource(filename, source);
            }
            
        } catch (XPathExpressionException ex) {
            throw new SerializationException(ex);
        }
    }

}
