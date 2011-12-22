package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidatorService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public final class Activator implements BundleActivator {

    private ServiceReference<AutomatonService> automatonServiceReference;
    private ServiceReference<XmlUtilService> xmlServiceReference;
    private ServiceReference<SetService> setServiceReference;
    private ServiceReference<XmlValidatorService> validatorServiceReference;

    private AutomatonService automatonService;
    private XmlUtilService xmlService;
    private SetService setService;
    private XmlValidatorService validatorService;

    @Override
    public void start(BundleContext context) throws Exception {
        getServiceReferences(context);
        getServices(context);
        passServicesToTests();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

    private void getServiceReferences(BundleContext context) {
        automatonServiceReference = context.getServiceReference(AutomatonService.class);
        xmlServiceReference = context.getServiceReference(XmlUtilService.class);
        setServiceReference = context.getServiceReference(SetService.class);
        validatorServiceReference = context.getServiceReference(XmlValidatorService.class);
    }

    private void getServices(BundleContext context) {
        automatonService = context.getService(automatonServiceReference);
        xmlService = context.getService(xmlServiceReference);
        setService = context.getService(setServiceReference);
        validatorService = context.getService(validatorServiceReference);
    }

    private void passServicesToTests() {
        AbstractTestHelper.setAutomatonService(automatonService);
        AbstractTestHelper.setXmlService(xmlService);
        AbstractTestHelper.setSetService(setService);
        AbstractTestHelper.setXmlValidatorService(validatorService);
    }

}
