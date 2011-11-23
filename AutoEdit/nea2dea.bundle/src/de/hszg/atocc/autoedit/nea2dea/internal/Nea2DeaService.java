package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;

public final class Nea2DeaService {

    private PluginRegistryService pluginRegistry;
    
    public synchronized void setPluginRegistryService(PluginRegistryService service) {
        System.out.println("NEA2DEA started");
        pluginRegistry = service;
        pluginRegistry.register("/nea2dea", Nea2Dea.class);
    }

    public synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            pluginRegistry.unregister(Nea2Dea.class);
            
            pluginRegistry = null;
        }
    }
}
