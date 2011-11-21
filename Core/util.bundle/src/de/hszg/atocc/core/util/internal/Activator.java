package de.hszg.atocc.core.util.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public final void start(final BundleContext context) throws Exception {
        System.out.println("START Util");
    }

    @Override
    public void stop(final BundleContext context) throws Exception {
    }

}
