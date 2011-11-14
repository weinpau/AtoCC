package de.hszigr.atocc.pluginregistry.internal;

import org.restlet.Component;
import org.restlet.resource.ServerResource;

import de.hszigr.atocc.pluginregistry.PluginRegistry;

public final class PluginRegistryImpl implements PluginRegistry {

    private Component component;
    
    public PluginRegistryImpl() {
        System.out.println("Registry created");
    }
    
    public void setComponent(final Component component) {
        this.component = component;
    }
    
    @Override
    public void register(final String uri, final Class<? extends ServerResource> c) {
        component.getDefaultHost().attach(uri, c);
    }

}
