package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidatorService;
import de.hszg.atocc.core.util.test.services.GetTestService;
import de.hszg.atocc.core.util.test.services.PostTestService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public final class Activator implements BundleActivator {

    private ServiceReference<AutomatonService> automatonServiceReference;
    private ServiceReference<XmlUtilService> xmlServiceReference;
    private ServiceReference<SetService> setServiceReference;
    private ServiceReference<XmlValidatorService> validatorServiceReference;
    private ServiceReference<WebUtilService> webUtilServiceReference;
    private ServiceReference<PluginRegistryService> pluginRegistryServiceReference;
    
    private AutomatonService automatonService;
    private XmlUtilService xmlService;
    private SetService setService;
    private XmlValidatorService validatorService;
    private WebUtilService webUtilService;
    private PluginRegistryService pluginRegistryService;

    @Override
    public void start(BundleContext context) throws Exception {
        getServiceReferences(context);
        getServices(context);
        passServicesToTests();
        
        startTestServices();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        pluginRegistryService.unregister(PostTestService.class);
        pluginRegistryService.unregister(GetTestService.class);
    }

    private void getServiceReferences(BundleContext context) {
        automatonServiceReference = context.getServiceReference(AutomatonService.class);
        xmlServiceReference = context.getServiceReference(XmlUtilService.class);
        setServiceReference = context.getServiceReference(SetService.class);
        validatorServiceReference = context.getServiceReference(XmlValidatorService.class);
        webUtilServiceReference = context.getServiceReference(WebUtilService.class);
        pluginRegistryServiceReference = context.getServiceReference(PluginRegistryService.class);
    }

    private void getServices(BundleContext context) {
        automatonService = context.getService(automatonServiceReference);
        xmlService = context.getService(xmlServiceReference);
        setService = context.getService(setServiceReference);
        validatorService = context.getService(validatorServiceReference);
        webUtilService = context.getService(webUtilServiceReference);
        pluginRegistryService = context.getService(pluginRegistryServiceReference);
    }

    private void passServicesToTests() {
        AbstractTestHelper.setAutomatonService(automatonService);
        AbstractTestHelper.setXmlService(xmlService);
        AbstractTestHelper.setSetService(setService);
        AbstractTestHelper.setXmlValidatorService(validatorService);
        AbstractTestHelper.setWebUtils(webUtilService);
    }
    
    private void startTestServices() {
       pluginRegistryService.register("/utiltest/post", PostTestService.class);
       pluginRegistryService.register("/utiltest/get", GetTestService.class);
    }

}
