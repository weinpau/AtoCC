package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public abstract class AbstractTestActivator implements BundleActivator {

    public final void start(BundleContext context) throws Exception {
        final ServiceReference<WebUtilService> webUtilServiceReference = context
                .getServiceReference(WebUtilService.class);

        final ServiceReference<XmlUtilService> xmlServiceReference = context
                .getServiceReference(XmlUtilService.class);

        final ServiceReference<AutomatonService> automatonServiceReference = context
                .getServiceReference(AutomatonService.class);

        AbstractServiceTest.setWebService(context.getService(webUtilServiceReference));
        AbstractServiceTest.setXmlUtils(context.getService(xmlServiceReference));
        AbstractServiceTest.setAutomatonService(context.getService(automatonServiceReference));
    }
    
    public final void stop(BundleContext context) throws Exception {
    }

}
