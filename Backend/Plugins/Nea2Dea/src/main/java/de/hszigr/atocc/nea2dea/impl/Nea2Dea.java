package de.hszigr.atocc.nea2dea.impl;

import java.io.IOException;

import org.restlet.ext.xml.DomRepresentation;
import org.restlet.ext.xml.XmlRepresentation;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;

import de.hszigr.atocc.util.XmlUtils;

public class Nea2Dea extends ServerResource {

    @Put("xml:xml")
    public XmlRepresentation transform(final DomRepresentation input) {        
        
        try {
            Document nea = input.getDocument();
            final Document dea = transform(nea);
            
            return XmlUtils.createResult(dea);
        } catch (IOException e) {
            return XmlUtils.createResultWithError("TRANSFORM_FAILED", e.getMessage());
        }
        
    }
    
    private Document transform(final Document nea) {
        return nea;
    }
}
