package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.pluginregistry.AbstractServiceComponent;
import de.hszg.atocc.core.pluginregistry.WebService;
import de.hszg.atocc.core.util.XmlValidatorService;

@WebService(url = "/autoedit/nea2dea", resource = Nea2Dea.class)
public final class Nea2DeaService extends AbstractServiceComponent {

    private XmlValidatorService xmlValidator;

    public synchronized void setXmlValidatorService(XmlValidatorService service) {
        xmlValidator = service;
    }

    public synchronized void unsetXmlValidatorService(XmlValidatorService service) {
        if (xmlValidator == service) {
            xmlValidator = null;
        }
    }
}
