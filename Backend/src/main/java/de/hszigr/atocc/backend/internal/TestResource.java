package de.hszigr.atocc.backend.internal;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class TestResource extends ServerResource {

    @Get
    public String myService() {
        return "hello from rest";
    }
    
}
