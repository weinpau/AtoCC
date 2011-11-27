package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;

public final class NeaEpsilon2NeaService {

    private PluginRegistryService pluginRegistry;
    
    public synchronized void setPluginRegistryService(PluginRegistryService service) {
        pluginRegistry = service;
        pluginRegistry.register("/autoedit/neaepsilon2nea", NeaEpsilon2Nea.class);
    }

    public synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            pluginRegistry.unregister(NeaEpsilon2Nea.class);
            
            pluginRegistry = null;
        }
    }
}
