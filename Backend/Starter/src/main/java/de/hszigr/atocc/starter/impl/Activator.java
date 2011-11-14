package de.hszigr.atocc.starter.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.util.ServiceUtils;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("START AtoCC");
        
        PluginRegistry service = ServiceUtils.getService(context, PluginRegistry.class);
        service.register("/test", null);
    }

    public void stop(BundleContext context) throws Exception {
 
    }

}
