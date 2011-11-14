package de.hszigr.atocc.pluginregistry;

import org.restlet.Component;
import org.restlet.resource.ServerResource;

public interface PluginRegistry {

    void setComponent(final Component component);
    void register(final String uri, final Class<? extends ServerResource> c);
    
}
