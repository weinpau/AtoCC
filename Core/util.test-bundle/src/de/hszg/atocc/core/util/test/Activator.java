package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.XmlUtilService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public final class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        final ServiceReference<?> automatonServiceReference =
                context.getServiceReference(AutomatonService.class);

        final ServiceReference<?> xmlServiceReference =
                context.getServiceReference(XmlUtilService.class);

        AutomatonServiceTests.setAutomatonService((AutomatonService) context
                .getService(automatonServiceReference));
        
        TestAutomatons.setAutomatonService((AutomatonService) context
                .getService(automatonServiceReference));

        TestAutomatons.setXmlService((XmlUtilService) context
                .getService(xmlServiceReference));
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
