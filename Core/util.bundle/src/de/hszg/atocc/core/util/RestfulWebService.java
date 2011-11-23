package de.hszg.atocc.core.util;

import java.util.concurrent.ConcurrentMap;

import org.restlet.resource.ServerResource;

public class RestfulWebService extends ServerResource {

    private ConcurrentMap<String, Object> attributes;
    
    @Override
    protected final void doInit() {
        super.doInit();
        
        attributes = getContext().getAttributes();
    }
    
    protected final <T> T getService(Class<T> c) {
        return (T) attributes.get(c.getName()); 
    }
    
}
