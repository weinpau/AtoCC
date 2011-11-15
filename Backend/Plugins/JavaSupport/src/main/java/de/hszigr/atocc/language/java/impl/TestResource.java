package de.hszigr.atocc.language.java.impl;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public final class TestResource extends ServerResource {

    @Get
    public String compile() {
        return "compiling";
    }
    
}
