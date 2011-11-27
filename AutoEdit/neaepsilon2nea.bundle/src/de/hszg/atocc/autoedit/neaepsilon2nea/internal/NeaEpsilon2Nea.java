package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.XmlUtilService;

import org.restlet.resource.Post;
import org.w3c.dom.Document;

public class NeaEpsilon2Nea extends RestfulWebService {
    
    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;

    @Post
    public Document transform(Document neaEpsilon) {
        tryToGetRequiredServices();
        
        return xmlUtils.createResult(neaEpsilon);
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
        automatonUtils = getService(AutomatonService.class);
    }
}
