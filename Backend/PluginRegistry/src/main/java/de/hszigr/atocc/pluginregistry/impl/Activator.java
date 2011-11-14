package de.hszigr.atocc.pluginregistry.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("START PluginRegistry");
    }

    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        
    }

}
