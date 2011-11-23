package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public final class XmlUtilServiceImpl implements XmlUtilService {

    private static final String ERROR = "error";

    public Document documentFromFile(final String filename) throws XmlUtilsException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(filename));
        } catch (final SAXException e) {
            throw new XmlUtilsException(e);
        } catch (final IOException e) {
            throw new XmlUtilsException(e);
        } catch (final ParserConfigurationException e) {
            throw new XmlUtilsException(e);
        }
    }

    public Document createResult(final Document doc) {

        final Document resultDocument = createEmptyDocument();
        final Element resultElement = createResultElement(resultDocument, "success");

        final Node domRootNode = doc.getDocumentElement();

        final Node adoptedNode = resultDocument.adoptNode(domRootNode);
        resultElement.appendChild(adoptedNode);

        return resultDocument;
    }

    public Document createResultWithError(final String errorMessage, final Exception reason) {

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

    public Document createEmptyDocument() {
        Document result = null;

        try {
            result = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Should not reach this point");
        }

        return result;
    }

    private Element createResultElement(final Document doc, final String status) {
        final Element resultElement = doc.createElement("result");
        resultElement.setAttribute("status", status);
        doc.appendChild(resultElement);

        return resultElement;
    }

    private Element createErrorMessageElement(final Document doc, final String errorMessage) {
        final Element errorMessageElement = doc.createElement("message");
        errorMessageElement.setTextContent(errorMessage);

        return errorMessageElement;
    }

    private Element createErrorReasonElement(final Document doc, final Exception reason) {

        final StringWriter sw = new StringWriter();
        reason.printStackTrace(new PrintWriter(sw));
        final String stacktrace = sw.toString();

        final Element errorReasonElement = doc.createElement("reason");
        errorReasonElement.setTextContent(String.format("%s: %s\n%s", reason.getClass()
                .getSimpleName(), reason.getLocalizedMessage(), stacktrace));

        return errorReasonElement;
    }

}
