package de.hszg.atocc.core.util.test.services;

import de.hszg.atocc.core.util.AbstractXmlTransformationService;
import de.hszg.atocc.core.util.XmlTransormationException;
import de.hszg.atocc.core.util.XmlUtilService;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PostTestService extends AbstractXmlTransformationService {

    private static final String TEST_ROOT = "testRoot";
    private static final String TEST_ELEMENT = "testElement";
    private XmlUtilService xmlUtils;

    @Override
    protected final void transform() throws XmlTransormationException {
        tryToGetRequiredServices();

        checkInput();

        final Document document = xmlUtils.createEmptyDocument();

        final Element root = document.createElement(TEST_ROOT);
        document.appendChild(root);

        Element element = document.createElement(TEST_ELEMENT);
        root.appendChild(element);

        element = document.createElement(TEST_ELEMENT);
        root.appendChild(element);

        element = document.createElement(TEST_ELEMENT);
        root.appendChild(element);

        setOutput(document);
    }

    private void checkInput() throws XmlTransormationException {
        try {
            final String rootElementName = getInput().getDocumentElement().getNodeName();

            if (!TEST_ROOT.equals(rootElementName)) {
                throw new XmlTransormationException("INVALID_INPUT", null);
            }
        } catch (NullPointerException ex) {
            throw new XmlTransormationException("EMPTY_INPUT_NOT_ALLOWED", ex);
        }
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
    }
}
