package de.hszigr.atocc.internal;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Server;

import de.hszigr.atocc.ExampleService;

/**
 * Extension of the default OSGi bundle activator
 */
public final class BackendActivator implements BundleActivator {
    
    private Server server;
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc) throws Exception {
        System.out.println("STARTING AtoCC Backend");

        Dictionary props = new Properties();
        // add specific service properties here...
        
        System.out.println("START Restlet Server");
        

        System.out.println("REGISTER de.hszigr.atocc.ExampleService");

        // Register our example service implementation in the OSGi service
        // registry
        bc.registerService(ExampleService.class.getName(), new ExampleServiceImpl(), props);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc) throws Exception {
        System.out.println("STOPPING AtoCC Backend");

        // no need to unregister our service - the OSGi framework handles it for
        // us
    }
}
