package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidatorService;

public abstract class AbstractTestHelper {

    private static XmlUtilService xmlService;
    private static AutomatonService automatonService;
    private static SetService setService;
    private static XmlValidatorService validator;
    private static WebUtilService webUtils;

    public static void setXmlService(XmlUtilService service) {
        xmlService = service;
    }

    public static void setAutomatonService(AutomatonService service) {
        automatonService = service;
    }

    public static void setSetService(SetService service) {
        setService = service;
    }

    public static void setXmlValidatorService(XmlValidatorService service) {
        validator = service;
    }

    public static void setWebUtils(WebUtilService service) {
        webUtils = service;
    }

    public static XmlUtilService getXmlService() {
        return xmlService;
    }

    public static AutomatonService getAutomatonService() {
        return automatonService;
    }

    public static SetService getSetService() {
        return setService;
    }

    public static XmlValidatorService getValidatorService() {
        return validator;
    }

    public static WebUtilService getWebUtils() {
        return webUtils;
    }

}
