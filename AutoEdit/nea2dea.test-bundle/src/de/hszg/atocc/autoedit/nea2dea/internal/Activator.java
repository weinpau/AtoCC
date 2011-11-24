package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.util.WebUtilService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        final ServiceReference<?> webUtilServiceReference =
                context.getServiceReference(WebUtilService.class);

        WebUtilService service = (WebUtilService) context.getService(webUtilServiceReference);
        Nea2DeaTests.setWebService(service);
    }

    public void stop(BundleContext context) throws Exception {
    }

}
