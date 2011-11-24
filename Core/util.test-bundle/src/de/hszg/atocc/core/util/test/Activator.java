package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.XmlUtilService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("TESTING");

        final ServiceReference<?> automatonServiceReference =
                context.getServiceReference(AutomatonService.class);

        final ServiceReference<?> xmlServiceReference =
                context.getServiceReference(XmlUtilService.class);

        AutomatonServiceTests.automatonService = (AutomatonService) context.getService(automatonServiceReference);
        AutomatonServiceTests.xmlService = (XmlUtilService) context.getService(xmlServiceReference);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub

    }

}
