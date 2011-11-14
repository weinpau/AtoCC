package de.hszigr.atocc.backend.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.restlet.Component;
import org.restlet.data.Protocol;

import de.hszigr.atocc.utils.UtilitiesService;
import de.hszigr.atocc.pluginregistry.PluginRegistry;
import de.hszigr.atocc.pluginregistry.PluginRegistryService;

/**
 * Extension of the default OSGi bundle activator
 */
public final class BackendActivator implements BundleActivator {

    private UtilitiesService utilitiesService;
    private PluginRegistryService pluginRegistryService;
    private PluginRegistry pluginRegistry;
    
    private Component component;

    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc) throws Exception {
        System.out.println("STARTING AtoCC Backend");

        initializeUtilitiesService(bc);
        initializePluginRegistryService(bc);

        initializeRestletComponent();
        
        pluginRegistry.setComponent(component);
        
        component.start();

        // System.out.println("WS: " +
        // service.callWebService("http://localhost:8080/kfgedit").getNodeName());
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc) throws Exception {
        System.out.println("STOPPING AtoCC Backend");

        component.stop();
    }

    private void initializeUtilitiesService(BundleContext bc) {
        ServiceReference serviceReference = bc.getServiceReference(UtilitiesService.class.getName());
        
        if(serviceReference == null) {
            throw new RuntimeException("ServiceRef not found: Utilities");
        }
        
        utilitiesService = (UtilitiesService) bc.getService(serviceReference);
        
        if(utilitiesService == null) {
            throw new RuntimeException("Service not found: Utilities");
        }
        
        utilitiesService.setContext(bc);
    }
    
    private void initializePluginRegistryService(BundleContext bc) {
        pluginRegistryService = utilitiesService.getService(PluginRegistryService.class);
        
        pluginRegistry = pluginRegistryService.getRegistry();
    }
    
    private void initializeRestletComponent() {
        component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);
    }
}
