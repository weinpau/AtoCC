package de.hszg.atocc.core.starter.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.starter.StarterService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidatorService;

import java.util.concurrent.ConcurrentMap;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public final class StarterServiceImpl implements StarterService {

    private static final int PORT = 8081;

    private Component component;
    private Router router;

    private PluginRegistryService pluginRegistry;
    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;
    private XmlValidatorService xmlValidator;
    private WebUtilService webUtils;
    
    public synchronized void setXmlUtilService(XmlUtilService service) {
        xmlUtils = service;
    }

    public synchronized void unsetXmlUtilService(XmlUtilService service) {
        if (xmlUtils == service) {
            xmlUtils = null;
        }
    }
    
    public synchronized void setWebUtilService(WebUtilService service) {
        webUtils = service;
    }

    public synchronized void unsetWebUtilService(WebUtilService service) {
        if (webUtils == service) {
            webUtils = null;
        }
    }
    
    public synchronized void setXmlValidatorService(XmlValidatorService service) {
        xmlValidator = service;
    }

    public synchronized void unsetXmlValidatorService(XmlValidatorService service) {
        if (xmlValidator == service) {
            xmlValidator = null;
        }
    }
    
    public synchronized void setAutomatonUtilService(AutomatonService service) {
        automatonUtils = service;
    }

    public synchronized void unsetAutomatonUtilService(AutomatonService service) {
        if (automatonUtils == service) {
            automatonUtils = null;
        }
    }

    public synchronized void setPluginRegistryService(PluginRegistryService service) {
        initializeComponent();
        
        pluginRegistry = service;
        pluginRegistry.setRouter(router);
    }

    public synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            pluginRegistry = null;
        }
    }

    private void initializeComponent() {
        component = new Component();

        final Server server = component.getServers().add(Protocol.HTTP, PORT);
        server.getContext().getParameters().add("maxThreads", "255");

        try {
            initializeRouter();
            
            component.getDefaultHost().attach(router);
            component.start();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeRouter() {
        router = new Router(component.getContext().createChildContext());
        
        final ConcurrentMap<String, Object> attributes = router.getContext().getAttributes(); 
        attributes.put(XmlUtilService.class.getName(), xmlUtils);
        attributes.put(AutomatonService.class.getName(), automatonUtils);
        attributes.put(XmlValidatorService.class.getName(), xmlValidator);
        attributes.put(WebUtilService.class.getName(), webUtils);
    }
    
}
