package de.hszigr.atocc.language.java.impl;

import de.hszigr.atocc.pluginregistry.AbstractBundleActivator;

import org.osgi.framework.BundleContext;

/**
 * Activator class for the JavaSupport plugin.
 *
 * @author Stefan Bradl
 *
 */
public final class Activator extends AbstractBundleActivator {

    @Override
    protected void onStart(final BundleContext context) {
        System.out.println("START JavaSupport");
        
        register("/java", TestResource.class);
    }

}
