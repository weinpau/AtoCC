package de.hszg.atocc.core.pluginregistry;

import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public interface PluginRegistryService {

    void setRouter(Router router);
    
    void register(String urlPattern, Class<? extends ServerResource> c);
    void unregister(Class<? extends ServerResource> c);
    
}
