package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;

public abstract class AbstractServiceTest {

    private static WebUtilService webUtilService;
    private static XmlUtilService xmlUtilService;
    private static AutomatonService automatonService;
    
    static void setWebService(WebUtilService service) {
        webUtilService = service;
    }

    static void setXmlUtils(XmlUtilService service) {
        xmlUtilService = service;
    }

    static void setAutomatonService(AutomatonService service) {
        automatonService = service;
    }
    
    protected static WebUtilService getWebUtilService() {
        return webUtilService;
    }
    
    protected static XmlUtilService getXmlUtilService() {
        return xmlUtilService;
    }

    protected static AutomatonService getAutomatonService() {
        return automatonService;
    }
    
}
