package de.hszigr.atocc.language.java.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.util.ServiceUtils;

public class Activator implements BundleActivator {

    private PluginRegistry registry;
    
    public void start(BundleContext context) throws Exception {
        System.out.println("START JavaSupport");
        
        registry = ServiceUtils.getService(context, PluginRegistry.class);
        registry.register("/java", TestResource.class);
    }

    public void stop(BundleContext context) throws Exception {
        registry.unregister(TestResource.class);
        ServiceUtils.ungetService(context, PluginRegistry.class);
    }

}
