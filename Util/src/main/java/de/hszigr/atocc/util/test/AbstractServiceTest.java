package de.hszigr.atocc.util.test;

import org.junit.After;
import org.junit.Before;
import org.restlet.Component;
import org.restlet.data.Protocol;
import org.w3c.dom.Document;

import de.hszigr.atocc.util.WebService;

public abstract class AbstractServiceTest {

    private static final Protocol PROTOCOL = Protocol.HTTP;
    private static final String HOST = "localhost";
    private static final int PORT = 8182;
    
    private Component component;
    
    @Before
    public void setUp() throws Exception {
        component = new Component();
        component.getServers().add(PROTOCOL, PORT);
        
        registerServices(component);
        
        component.start();
    }

    @After
    public void tearDown() throws Exception {
        component.stop();
    }
    
    public final Document get(final String path) {
        return WebService.get(getBaseUrl() + path);
    }
    
    public final Document put(final String path, final Document payload) {
        return WebService.put(getBaseUrl() + path, payload);
    }
    
    protected abstract void registerServices(final Component component);
    
    private final String getBaseUrl() {
        return String.format("%s://%s:%d", PROTOCOL.getName().toLowerCase(), HOST, PORT);
    }
}
