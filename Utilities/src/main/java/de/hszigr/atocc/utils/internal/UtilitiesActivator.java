package de.hszigr.atocc.utils.internal;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.utils.UtilitiesService;

/**
 * Extension of the default OSGi bundle activator
 */
public final class UtilitiesActivator implements BundleActivator {
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc) throws Exception {
        System.out.println("STARTING de.hszigr.atocc.utils");

        Dictionary props = new Properties();
        // add specific service properties here...

        System.out.println("REGISTER de.hszigr.atocc.utils.ExampleService");

        // Register our example service implementation in the OSGi service
        // registry
        bc.registerService(UtilitiesService.class.getName(), new UtilitiesServiceImpl(), props);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc) throws Exception {
        System.out.println("STOPPING de.hszigr.atocc.utils");

        // no need to unregister our service - the OSGi framework handles it for
        // us
    }
}
