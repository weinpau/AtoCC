package de.hszigr.atocc.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public final class XmlUtils {

    private static final String ERROR = "error";

    private XmlUtils() {

    }

    public static Document documentFromFile(final String filename) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(filename));
        } catch (final SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Document createResult(final Document doc) {

        final Document resultDocument = createEmptyDocument();
        final Element resultElement = createResultElement(resultDocument, "success");

        final Node domRootNode = doc.getDocumentElement();

        final Node adoptedNode = resultDocument.adoptNode(domRootNode);
        resultElement.appendChild(adoptedNode);

        return resultDocument;
    }

    public static Document createResultWithError(final String errorMessage, final String reason) {

        final Document resultDocument = createEmptyDocument();

        final Element resultElement = createResultElement(resultDocument, ERROR);

        final Element errorElement = resultDocument.createElement(ERROR);
        resultElement.appendChild(errorElement);

        final Element errorMessageElement = createErrorMessageElement(resultDocument, errorMessage);
        errorElement.appendChild(errorMessageElement);

        final Element errorReasonElement = createErrorReasonElement(resultDocument, reason);
        errorElement.appendChild(errorReasonElement);

        return resultDocument;
    }

    public static Document createEmptyDocument() {
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
        doc.appendChild(resultElement);

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
