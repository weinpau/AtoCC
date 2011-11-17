package de.hszigr.atocc.nea2dea.impl;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hszigr.atocc.util.XmlUtils;

public class Nea2Dea extends ServerResource {

    @Put
    public Document transform(Document nea) {
        try {
            // TODO: validateAgainstSchema(nea);
            checkAutomatonType(nea);
            
            final Document dea = XmlUtils.createEmptyDocument();
            final Element automatonElement = dea.createElement("AUTOMATON");
            dea.appendChild(automatonElement);
            
            return XmlUtils.createResult(dea);
        } catch (final Exception e) {
            return XmlUtils.createResultWithError("TRANSFORM_FAILED", e.getMessage());
        }

    }

    private void checkAutomatonType(Document nea) throws Exception {
        final Element automatonElement = nea.getDocumentElement();
        final Element typeElement = (Element) automatonElement.getElementsByTagName("TYPE").item(0);
        
        if(!"NEA".equals(typeElement.getAttribute("value"))) {
            throw new Exception("INVALID_AUTOMATON_TYPE");
        }
    }
}
