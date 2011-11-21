package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistry;

import org.restlet.Component;
import org.restlet.resource.ServerResource;

public final class PluginRegistryImpl implements PluginRegistry {

    private static final String COMPONENT_NOT_SET = "Component not set";

    private Component component;

    @Override
    public void setComponent(final Component aComponent) {
        this.component = aComponent;
    }

    @Override
    public void register(final String urlPattern, final Class<? extends ServerResource> c) {
        System.out.println("REGISTER " + urlPattern);

        if (component == null) {
            throw new NullPointerException(COMPONENT_NOT_SET);
        }

        component.getDefaultHost().attach(urlPattern, c);
    }

    @Override
    public void unregister(final Class<? extends ServerResource> c) {
        if (component == null) {
            throw new NullPointerException(COMPONENT_NOT_SET);
        }

        component.getDefaultHost().detach(c);
    }

}
