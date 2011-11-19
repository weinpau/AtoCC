package de.hszigr.atocc.starter.impl;

import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.util.ServiceUtils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Component;
import org.restlet.data.Protocol;

public final class Activator implements BundleActivator {

    private static final int PORT = 8081;

    public void start(final BundleContext context) throws Exception {
        System.out.println("START AtoCC");

        final Component component = initializeComponent();
        initializePluginRegistry(context, component);
    }

    private void initializePluginRegistry(final BundleContext context, final Component component) {
        final PluginRegistry pluginRegistry = ServiceUtils
                .getService(context, PluginRegistry.class);
        pluginRegistry.setComponent(component);
        pluginRegistry.register("/test", TestResource.class);

    }

    public void stop(final BundleContext context) throws Exception {
        ServiceUtils.ungetService(context, PluginRegistry.class);
    }

    private Component initializeComponent() throws Exception {
        final Component component = new Component();
        component.getServers().add(Protocol.HTTP, PORT);
        component.start();
        return component;
    }
}
