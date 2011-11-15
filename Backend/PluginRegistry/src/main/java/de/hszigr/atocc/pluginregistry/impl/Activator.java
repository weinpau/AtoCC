package de.hszigr.atocc.pluginregistry.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.PluginRegistry;

public final class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("START PluginRegistry");
        
        context.registerService(PluginRegistry.class.getName(), new PluginRegistryImpl(), null);
    }

    public void stop(BundleContext context) throws Exception {
        
    }

}
