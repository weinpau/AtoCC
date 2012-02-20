package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.translation.TranslationService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public final class XmlUtilServiceImpl implements XmlUtilService {

    private static final String UNKNOWN = "UNKNOWN";

    private static final String STATUS = "status";

    private TranslationService translator;

    @Override
    public Document documentFromFile(String filename) throws XmlUtilsException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(filename));
        } catch (final SAXException | IOException | ParserConfigurationException e) {
            throw new XmlUtilsException(e);
        }
    }

    @Override
    public Document createResult(Document doc) {

        final Document resultDocument = createEmptyDocument();
        final Element resultElement = createResultElement(resultDocument, SUCCESS);

        final Node domRootNode = doc.getDocumentElement();

        final Node adoptedNode = resultDocument.adoptNode(domRootNode);
        resultElement.appendChild(adoptedNode);

        return resultDocument;
    }

    @Override
    public Document createResultWithError(String errorCode, Exception reason, Locale locale) {
        return createResultWithError(errorCode, translator.translate(errorCode, locale), reason);
    }

    @Override
    public Document createResultWithError(String errorCode, String errorMessage, Exception reason) {

        final Document resultDocument = createEmptyDocument();

        final Element resultElement = createResultElement(resultDocument, ERROR);

        final Element errorElement = resultDocument.createElement(ERROR);
        resultElement.appendChild(errorElement);

        final Element errorCodeElement = createErrorCodeElement(resultDocument, errorCode);
        errorElement.appendChild(errorCodeElement);

        final Element errorMessageElement = createErrorMessageElement(resultDocument, errorMessage);
        errorElement.appendChild(errorMessageElement);

        final Element errorReasonElement = createErrorReasonElement(resultDocument, reason);
        errorElement.appendChild(errorReasonElement);

        return resultDocument;
    }

    @Override
    public String getResultStatus(Document resultDocument) {
        return resultDocument.getDocumentElement().getAttribute(STATUS);
    }

    @Override
    public String getErrorMessage(Document resultDocument) {
        try {
            final XPath xpath = XPathFactory.newInstance().newXPath();
            return (String) xpath.evaluate("//error/message/text()", resultDocument,
                    XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            return UNKNOWN;
        }
    }

    @Override
    public String getErrorCause(Document resultDocument) {
        try {
            final XPath xpath = XPathFactory.newInstance().newXPath();
            return (String) xpath.evaluate("//error/reason/text()", resultDocument,
                    XPathConstants.STRING);
        } catch (XPathExpressionException ex) {
            return UNKNOWN;
        }
    }

    @Override
    public Document getContent(Document resultDocument) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final Node contentNode = (Node) xpath.evaluate("/result/node()[1]", resultDocument,
                    XPathConstants.NODE);

            final Document document = createEmptyDocument();
            document.appendChild(document.adoptNode(contentNode));

            return document;
        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Document createEmptyDocument() {
        Document result = null;

        try {
            result = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Should not reach this point");
        }

        return result;
    }

    @Override
    public String xmlToString(Document doc) throws TransformerException {
        final Converter converter = new Converter();

        return converter.xmlToString(doc);
    }

    @Override
    public Document stringToXml(String data) throws ConverterException {
        final Converter converter = new Converter();

        return converter.stringToXml(data);
    }

    public synchronized void setTranslationService(TranslationService service) {
        translator = service;
    }

    public synchronized void unsetTranslationService(TranslationService service) {
        if (translator == service) {
            translator = null;
        }
    }

    private Element createResultElement(Document doc, String status) {
        final Element resultElement = doc.createElement("result");
        resultElement.setAttribute(STATUS, status);
        doc.appendChild(resultElement);

        return resultElement;
    }

    private Element createErrorCodeElement(Document doc, String errorCode) {
        final Element errorCodeElement = doc.createElement("code");

        if (errorCode.contains("|")) {
            errorCodeElement.setTextContent((errorCode.split("\\|"))[1]);
        } else {
            errorCodeElement.setTextContent(errorCode);
        }

        return errorCodeElement;
    }

    private Element createErrorMessageElement(Document doc, String errorMessage) {
        final Element errorMessageElement = doc.createElement("message");
        errorMessageElement.setTextContent(errorMessage);

        return errorMessageElement;
    }

    private Element createErrorReasonElement(Document doc, Exception reason) {

        final StringWriter sw = new StringWriter();
        reason.printStackTrace(new PrintWriter(sw));
        final String stacktrace = sw.toString();

        final Element errorReasonElement = doc.createElement("reason");
        errorReasonElement.setTextContent(String.format("%s: %s\n%s", reason.getClass()
                .getSimpleName(), reason.getLocalizedMessage(), stacktrace));

        return errorReasonElement;
    }

}
