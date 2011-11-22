package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistry;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public final class Activator implements BundleActivator {

    public void start(final BundleContext context) throws Exception {
        System.out.println("START PluginRegistry");
        
        context.registerService(PluginRegistry.class.getName(), new PluginRegistryImpl(), null);
    }

    public void stop(final BundleContext context) throws Exception {
    }

}
