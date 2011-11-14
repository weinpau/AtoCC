package de.hszigr.atocc.pluginregistry.internal;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.pluginregistry.PluginRegistryService;

public final class PluginRegistryServiceImpl implements PluginRegistryService {

    private PluginRegistry registry;
    
    public PluginRegistryServiceImpl(final PluginRegistry registry) {
        this.registry = registry;
    }
    
    @Override
    public PluginRegistry getRegistry() {
        return registry;
    }

}
