package de.hszigr.atocc.pluginregistry.internal;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.PluginRegistryService;

/**
 * Extension of the default OSGi bundle activator
 */
public final class PluginRegistryActivator implements BundleActivator {
    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc) throws Exception {

        Dictionary props = new Properties();

        bc.registerService(PluginRegistryService.class.getName(), new PluginRegistryServiceImpl(new PluginRegistryImpl()), props);
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc) throws Exception {
    }
}
