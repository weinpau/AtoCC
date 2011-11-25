package de.hszg.atocc.core.pluginregistry;

import java.util.Map;

import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public interface PluginRegistryService {

    void setRouter(Router router);
    
    void register(String urlPattern, Class<? extends ServerResource> c);
    void unregister(Class<? extends ServerResource> c);
    
    Map<Class<? extends ServerResource>, String> getRegisteredServices();
    
}
