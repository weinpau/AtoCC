package de.hszg.atocc.core.starter.internal;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public final class TestResource extends ServerResource {

    public TestResource() {
        System.out.println("RESOURCE CREATED");
    }
    
    @Get
    public String test() {
        return "REST is working";
    }
    
}
