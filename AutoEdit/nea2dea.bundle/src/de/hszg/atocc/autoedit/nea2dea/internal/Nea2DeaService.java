package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.util.XmlValidatorService;

public final class Nea2DeaService {

    private PluginRegistryService pluginRegistry;
    private XmlValidatorService xmlValidator;
    
    public synchronized void setPluginRegistryService(PluginRegistryService service) {
        pluginRegistry = service;
        pluginRegistry.register("/autoedit/nea2dea", Nea2Dea.class);
    }

    public synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            pluginRegistry.unregister(Nea2Dea.class);
            
            pluginRegistry = null;
        }
    }
    
    public synchronized void setXmlValidatorService(XmlValidatorService service) {
        xmlValidator = service;
    }

    public synchronized void unsetXmlValidatorService(XmlValidatorService service) {
        if (xmlValidator == service) {
            xmlValidator = null;
        }
    }
}
