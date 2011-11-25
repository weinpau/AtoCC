package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public final class PluginRegistryServiceImpl implements PluginRegistryService {

    private static final String ROUTER_NOT_SET = "Router not set";

    private Router router;
    private Map<Class<? extends ServerResource>, String> registeredWebServices =
            new HashMap<Class<? extends ServerResource>, String>();

    @Override
    public void setRouter(Router aRouter) {
        router = aRouter;

        registerRegistryServices();
    }

    @Override
    public void register(final String urlPattern, final Class<? extends ServerResource> c) {
        System.out.println("REGISTER " + urlPattern);

        if (router == null) {
            throw new NullPointerException(ROUTER_NOT_SET);
        }

        router.attach(urlPattern, c);
        
        registeredWebServices.put(c, urlPattern);
    }

    @Override
    public void unregister(final Class<? extends ServerResource> c) {
        if (router == null) {
            throw new NullPointerException(ROUTER_NOT_SET);
        }

        router.detach(c);
        
        registeredWebServices.remove(c);
    }

    private void registerRegistryServices() {
        final ConcurrentMap<String, Object> attributes = router.getContext().getAttributes();
        attributes.put(PluginRegistryService.class.getName(), this);

        register("/services/list", ListService.class);
    }

    @Override
    public Map<Class<? extends ServerResource>, String> getRegisteredServices() {
        return registeredWebServices;
    }

}
