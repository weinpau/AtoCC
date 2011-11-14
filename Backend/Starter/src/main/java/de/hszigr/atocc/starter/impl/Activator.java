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
        
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);
        component.start();
        
        PluginRegistry service = ServiceUtils.getService(context, PluginRegistry.class);
        service.setComponent(component);
        service.register("/test", TestResource.class);
    }

    public void stop(BundleContext context) throws Exception {

    }

}
