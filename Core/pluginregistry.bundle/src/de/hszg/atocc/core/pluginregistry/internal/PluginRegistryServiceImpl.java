package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;

import java.util.concurrent.ConcurrentMap;

import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public final class PluginRegistryServiceImpl implements PluginRegistryService {

    private static final String ROUTER_NOT_SET = "Router not set";

    private Router router;
    
    @Override
    public void setRouter(Router aRouter) {
        router = aRouter;
        
        registerRegistryServices();
    }
    
    @Override
    public Router getRouter() {
        return router;
    }

    @Override
    public void register(final String urlPattern, final Class<? extends ServerResource> c) {
        System.out.println("REGISTER " + urlPattern);

        if (router == null) {
            throw new NullPointerException(ROUTER_NOT_SET);
        }

        router.attach(urlPattern, c);
    }

    @Override
    public void unregister(final Class<? extends ServerResource> c) {
        if (router == null) {
            throw new NullPointerException(ROUTER_NOT_SET);
        }

        router.detach(c);
    }
    
    private void registerRegistryServices() {
        final ConcurrentMap<String, Object> attributes = router.getContext().getAttributes(); 
        attributes.put(PluginRegistryService.class.getName(), this);
        
        register("/services/list", ListService.class);
    }

}
