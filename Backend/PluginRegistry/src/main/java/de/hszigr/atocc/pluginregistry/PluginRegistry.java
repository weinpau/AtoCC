package de.hszigr.atocc.pluginregistry;

import org.restlet.resource.ServerResource;

public interface PluginRegistry {

    void register(String urlPattern, Class<? extends ServerResource> c);
    
}
