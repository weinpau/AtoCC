package de.hszg.atocc.core.pluginregistry;

import org.restlet.Component;
import org.restlet.resource.ServerResource;

public interface PluginRegistry {

    void setComponent(Component component);
    
    void register(String urlPattern, Class<? extends ServerResource> c);
    void unregister(Class<? extends ServerResource> c);
    
}
