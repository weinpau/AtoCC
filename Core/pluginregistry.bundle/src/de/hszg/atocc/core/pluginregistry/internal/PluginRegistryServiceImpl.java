package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;

import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public final class PluginRegistryServiceImpl implements PluginRegistryService {

    private static final String ROUTER_NOT_SET = "Router not set";

    private Router router;
    
    @Override
    public void setRouter(Router aRouter) {
        router = aRouter;
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

}
