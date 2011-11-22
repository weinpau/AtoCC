package de.hszg.atocc.autoedit.nea2dea.internal;

import org.osgi.framework.BundleContext;

import de.hszg.atocc.core.pluginregistry.AbstractBundleActivator;

public class Activator extends AbstractBundleActivator {

    @Override
    protected void onStart(final BundleContext context) {
        System.out.println("START Nea2Dea");
       
        register("/nea2dea", Nea2Dea.class);
    }
}
