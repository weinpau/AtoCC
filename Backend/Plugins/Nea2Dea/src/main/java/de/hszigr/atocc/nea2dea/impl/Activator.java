package de.hszigr.atocc.nea2dea.impl;

import org.osgi.framework.BundleContext;

import de.hszigr.atocc.pluginregistry.AbstractBundleActivator;

public class Activator extends AbstractBundleActivator {

    @Override
    protected void onStart(BundleContext context) {
        System.out.println("START Nea2Dea");
        
        register("/nea2dea", Nea2Dea.class);
    }
}
