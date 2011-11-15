package de.hszigr.atocc.pluginregistry.impl;

import org.restlet.Component;
import org.restlet.resource.ServerResource;

import de.hszigr.atocc.pluginregistry.PluginRegistry;

public final class PluginRegistryImpl implements PluginRegistry {

    private Component component;
    
    @Override
    public void setComponent(Component component) {
        this.component = component;
    }
    
    @Override
    public void register(String urlPattern, Class<? extends ServerResource> c) {
        System.out.println("REGISTER " + urlPattern);
        
        if(component == null) {
            throw new NullPointerException("Component not set");
        }
        
        component.getDefaultHost().attach(urlPattern, c);
    }
    
    @Override
    public void unregister(Class<? extends ServerResource> c) {
        if(component == null) {
            throw new NullPointerException("Component not set");
        }
        
        component.getDefaultHost().detach(c);
    }

}
