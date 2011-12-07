package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidatorService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public final class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        final ServiceReference<AutomatonService> automatonServiceReference = context
                .getServiceReference(AutomatonService.class);

        final ServiceReference<XmlUtilService> xmlServiceReference = context
                .getServiceReference(XmlUtilService.class);

        final ServiceReference<SetService> setServiceReference = context
                .getServiceReference(SetService.class);
        
        final ServiceReference<XmlValidatorService> validatorServiceReference =
                context.getServiceReference(XmlValidatorService.class);

        final AutomatonService automatonService = context.getService(automatonServiceReference);
        final SetService setService = context.getService(setServiceReference);
        final XmlValidatorService validatorService = context.getService(validatorServiceReference);
 
        AbstractTestHelper.setAutomatonService(automatonService);
        AbstractTestHelper.setXmlService(context.getService(xmlServiceReference));
        AbstractTestHelper.setSetService(setService);
        AbstractTestHelper.setXmlValidatorService(validatorService);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
