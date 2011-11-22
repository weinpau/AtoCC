package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.pluginregistry.AbstractBundleActivator;

import org.osgi.framework.BundleContext;

public final class Activator extends AbstractBundleActivator {

    @Override
    protected void onStart(final BundleContext context) {
        System.out.println("START Nea2Dea");

        register("/nea2dea", Nea2Dea.class);
    }
}
