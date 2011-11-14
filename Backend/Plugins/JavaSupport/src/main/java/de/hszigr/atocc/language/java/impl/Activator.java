package de.hszigr.atocc.language.java.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.util.ServiceUtils;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("START JavaSupport");
        
        PluginRegistry pluginRegistry = ServiceUtils.getService(context, PluginRegistry.class);
        pluginRegistry.register("/java", TestResource.class);
        ServiceUtils.ungetService(context, PluginRegistry.class);
    }

    public void stop(BundleContext context) throws Exception {
 
    }

}
