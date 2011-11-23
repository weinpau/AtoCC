package de.hszg.atocc.core.pluginregistry;

import org.restlet.Component;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public interface PluginRegistryService {

    void setComponent(Component component);
    void setRouter(Router router);
    
    void register(String urlPattern, Class<? extends ServerResource> c);
    void unregister(Class<? extends ServerResource> c);
    
}
