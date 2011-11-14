package de.hszigr.atocc.pluginregistry.impl;

import org.restlet.resource.ServerResource;

import de.hszigr.atocc.pluginregistry.PluginRegistry;

public class PluginRegistryImpl implements PluginRegistry {

    public void register(String urlPattern, Class<? extends ServerResource> c) {
        System.out.println("REGISTER " + urlPattern);
    }

}
