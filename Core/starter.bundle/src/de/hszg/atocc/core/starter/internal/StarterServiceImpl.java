package de.hszg.atocc.core.starter.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.starter.StarterService;
import de.hszg.atocc.core.translation.TranslationService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidatorService;

import java.util.concurrent.ConcurrentMap;

import org.osgi.service.log.LogService;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public final class StarterServiceImpl implements StarterService {

    private static final int PORT = 8081;

    private Component component;
    private Router router;

    private LogService logger;
    
    private PluginRegistryService pluginRegistry;
    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;
    private XmlValidatorService xmlValidator;
    private WebUtilService webUtils;
    private SetService setService;
    private TranslationService translationService;
    
    public synchronized void setLogService(LogService service) {
        logger = service;
    }

    public synchronized void unsetLogService(LogService service) {
        if (logger == service) {
            logger = null;
        }
    }
    
    public synchronized void setSetService(SetService service) {
        setService = service;
    }

    public synchronized void unsetSetService(SetService service) {
        if (setService == service) {
            setService = null;
        }
    }
    
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
    
    public synchronized void setTranslationService(TranslationService service) {
        translationService = service;
    }

    public synchronized void unsetTranslationService(TranslationService service) {
        if (translationService == service) {
            translationService = null;
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
        logger.log(LogService.LOG_INFO, "Starting webserver");
        
        component = new Component();

        final Server server = component.getServers().add(Protocol.HTTP, PORT);
        component.getClients().add(Protocol.HTTP);
        server.getContext().getParameters().add("maxThreads", "255");

        startComponent();
    }

    private void startComponent() {
        try {
            initializeRouter();
            
            component.getDefaultHost().attach(router);
            component.start();
            
            logger.log(LogService.LOG_INFO, "Webserver started");
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
        attributes.put(SetService.class.getName(), setService);
        attributes.put(TranslationService.class.getName(), translationService);
    }
    
}
