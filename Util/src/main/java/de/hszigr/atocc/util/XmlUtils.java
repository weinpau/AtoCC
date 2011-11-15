package de.hszigr.atocc.util;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.restlet.data.MediaType;
import org.restlet.ext.xml.DomRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XmlUtils {

    private XmlUtils() {

    }

    public static DomRepresentation createResult(final Document doc) {

        final Document resultDocument = createEmptyDocument();
        final Element resultElement = createResultElement(resultDocument, "success");

        final Node domRootNode = doc.getDocumentElement();

        final Node adoptedNode = resultDocument.adoptNode(domRootNode);
        resultElement.appendChild(adoptedNode);

        return new DomRepresentation(MediaType.APPLICATION_XML, resultDocument);
    }

    public static DomRepresentation createResultWithError(final String errorMessage,
        final String reason) {

        final Document resultDocument = createEmptyDocument();

        final Element resultElement = createResultElement(resultDocument, "error");

        final Element errorElement = resultDocument.createElement("error");
        resultElement.appendChild(errorElement);

        final Element errorMessageElement = createErrorMessageElement(resultDocument, errorMessage);
        errorElement.appendChild(errorMessageElement);

        final Element errorReasonElement = createErrorReasonElement(resultDocument, reason);
        errorElement.appendChild(errorReasonElement);

        return new DomRepresentation(MediaType.APPLICATION_XML, resultDocument);
    }

    private static Document createEmptyDocument() {
        Document result = null;

        try {
            result = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Should not reach this point");
        }

        return result;
    }

    private static Element createResultElement(final Document doc, final String status) {
        final Element resultElement = doc.createElement("result");
        resultElement.setAttribute("status", status);

        return resultElement;
    }

    private static Element createErrorMessageElement(final Document doc, final String errorMessage) {
        final Element errorMessageElement = doc.createElement("message");
        errorMessageElement.setTextContent(errorMessage);

        return errorMessageElement;
    }

    private static Element createErrorReasonElement(final Document doc, final String reason) {
        final Element errorReasonElement = doc.createElement("reason");
        errorReasonElement.setTextContent(reason);
        
        return errorReasonElement;
    }
    
}
