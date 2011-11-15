package de.hszigr.atocc.starter.impl;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public final class TestResource extends ServerResource {

    @Get
    public String test() {
        return "REST is working";
    }
    
}
