package de.hszg.atocc.core.starter.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistry;
import de.hszg.atocc.core.util.ServiceUtils;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

public final class Activator implements BundleActivator {

    private static final int PORT = 8081;
    private LogService logger;

    public void start(final BundleContext context) throws Exception {
        logger = ServiceUtils.getService(context, LogService.class);
        logger.log(LogService.LOG_INFO, "STARTING ATOCC");
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
        final Server server = component.getServers().add(Protocol.HTTP, PORT);
        server.getContext().getParameters().add("maxThreads", "255");

        component.start();
        return component;
    }
}
