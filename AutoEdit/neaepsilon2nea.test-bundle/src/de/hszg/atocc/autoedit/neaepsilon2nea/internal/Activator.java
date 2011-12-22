package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.AutomatonService;
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

        final ServiceReference<AutomatonService> automatonServiceReference = context
                .getServiceReference(AutomatonService.class);

        NeaEpsilon2NeaTests.setWebService(context.getService(webUtilServiceReference));
        NeaEpsilon2NeaTests.setXmlUtils(context.getService(xmlServiceReference));
        NeaEpsilon2NeaTests.setAutomatonService(context.getService(automatonServiceReference));
    }

    public void stop(BundleContext context) throws Exception {
    }

}
