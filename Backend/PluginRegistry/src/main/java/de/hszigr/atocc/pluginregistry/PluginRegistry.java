package de.hszigr.atocc.pluginregistry;

import org.restlet.Component;
import org.restlet.resource.ServerResource;

public interface PluginRegistry {

    void setComponent(Component component);
    void register(String urlPattern, Class<? extends ServerResource> c);
    
}
