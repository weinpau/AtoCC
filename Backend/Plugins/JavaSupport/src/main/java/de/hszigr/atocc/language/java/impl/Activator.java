package de.hszigr.atocc.language.java.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.util.ServiceUtils;

/**
 * Activator class for the JavaSupport plugin.
 *
 * @author Stefan Bradl
 *
 */
public final class Activator implements BundleActivator {

    private PluginRegistry registry;

    /**
     * Register the provided web services.
     */
    public void start(final BundleContext context) throws Exception {
        System.out.println("START JavaSupport");
        
        registry = ServiceUtils.getService(context, PluginRegistry.class);
        registry.register("/java", TestResource.class);
    }

    /**
     * Unregister the provided web services.
     */
    public void stop(final BundleContext context) throws Exception {
        registry.unregister(TestResource.class);
        ServiceUtils.ungetService(context, PluginRegistry.class);
    }

}
