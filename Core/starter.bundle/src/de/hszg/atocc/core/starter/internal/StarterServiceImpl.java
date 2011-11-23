package de.hszg.atocc.core.starter.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.starter.StarterService;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;

public final class StarterServiceImpl implements StarterService {

    private static final int PORT = 8081;

    private Component component;

    private PluginRegistryService pluginRegistry;

    public synchronized void setPluginRegistryService(PluginRegistryService service) {
        System.out.println("SET REG");
        initializeComponent();
        
        pluginRegistry = service;
        pluginRegistry.setComponent(component);
    }

    public synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            pluginRegistry = null;
        }
    }

    private void initializeComponent() {
        component = new Component();

        final Server server = component.getServers().add(Protocol.HTTP, PORT);
        server.getContext().getParameters().add("maxThreads", "255");

        try {
            component.start();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
