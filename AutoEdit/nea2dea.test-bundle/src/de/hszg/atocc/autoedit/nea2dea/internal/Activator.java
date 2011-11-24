package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public final class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        final ServiceReference<WebUtilService> webUtilServiceReference = context
                .getServiceReference(WebUtilService.class);

        final ServiceReference<XmlUtilService> xmlServiceReference = context
                .getServiceReference(XmlUtilService.class);

        final WebUtilService service = context.getService(webUtilServiceReference);

        final XmlUtilService xmlService = context.getService(xmlServiceReference);

        Nea2DeaTests.setWebService(service);
        Nea2DeaTests.setXmlUtils(xmlService);
    }

    public void stop(BundleContext context) throws Exception {
    }

}
