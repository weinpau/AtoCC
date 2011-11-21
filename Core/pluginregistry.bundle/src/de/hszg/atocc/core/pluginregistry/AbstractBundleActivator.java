package de.hszg.atocc.core.pluginregistry;

import de.hszg.atocc.core.util.ServiceUtils;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.resource.ServerResource;

public abstract class AbstractBundleActivator implements BundleActivator {

    private PluginRegistry registry;

    private Collection<Class<? extends ServerResource>> resources = new ArrayList<Class<? extends ServerResource>>();

    @Override
    public final void start(final BundleContext context) throws Exception {
        registry = ServiceUtils.getService(context, PluginRegistry.class);
        onStart(context);
    }

    @Override
    public final void stop(final BundleContext context) throws Exception {
        unregisterResources();
        ServiceUtils.ungetService(context, PluginRegistry.class);
    }

    protected void onStart(final BundleContext context) {

    }

    protected final void register(final String urlPattern, 
        final Class<? extends ServerResource> c) {
        resources.add(c);
        registry.register(urlPattern, c);
    }

    private void unregisterResources() {
        for (Class<? extends ServerResource> resource : resources) {
            registry.unregister(resource);
        }

        resources.clear();
    }

}