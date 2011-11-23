package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;

import org.restlet.Component;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public final class PluginRegistryServiceImpl implements PluginRegistryService {

    private static final String COMPONENT_NOT_SET = "Component not set";

    private Component component;
    private Router router;

    @Override
    public void setComponent(final Component aComponent) {
        component = aComponent;
    }
    
    @Override
    public void setRouter(Router aRouter) {
        router = aRouter;
    }

    @Override
    public void register(final String urlPattern, final Class<? extends ServerResource> c) {
        System.out.println("REGISTER " + urlPattern);

        if (component == null) {
            throw new NullPointerException(COMPONENT_NOT_SET);
        }

//        component.getDefaultHost().attach(urlPattern, c);
        router.attach(urlPattern, c);
    }

    @Override
    public void unregister(final Class<? extends ServerResource> c) {
        if (component == null) {
            throw new NullPointerException(COMPONENT_NOT_SET);
        }

//        component.getDefaultHost().detach(c);
        router.detach(c);
    }

}
