package de.hszigr.atocc.starter.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Component;
import org.restlet.data.Protocol;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.util.ServiceUtils;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        System.out.println("START AtoCC");
        
        Component component = initializeComponent();
        initializePluginRegistry(context, component);
    }

    private void initializePluginRegistry(BundleContext context, Component component) {
        PluginRegistry pluginRegistry = ServiceUtils.getService(context, PluginRegistry.class);
        pluginRegistry.setComponent(component);
        pluginRegistry.register("/test", TestResource.class);
        ServiceUtils.ungetService(context, PluginRegistry.class);
    }

    public void stop(BundleContext context) throws Exception {
        
    }

    private Component initializeComponent() throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);
        component.start();
        return component;
    }
}
