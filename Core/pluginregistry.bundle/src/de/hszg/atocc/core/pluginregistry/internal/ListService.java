package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.ServiceNotFoundException;
import de.hszg.atocc.core.util.XmlUtilService;

import org.restlet.resource.Get;
import org.restlet.routing.Route;
import org.restlet.routing.Router;
import org.restlet.util.RouteList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class ListService extends RestfulWebService {

    private XmlUtilService xmlUtils;

    private Document serviceListDocument;
    private Element servicesElement;

    @Get
    public Document listAvailableServices() {
        xmlUtils = getService(XmlUtilService.class);

        try {
            createServiceListDocument();
            createServicesElement();
            createserviceElements();

            return xmlUtils.createResult(serviceListDocument);
        } catch (final ServiceNotFoundException e) {
            return xmlUtils.createResultWithError("COULD_NOT_RETREIVE_SERVICE_LIST", e);
        }
    }

    private void createServiceListDocument() {
        serviceListDocument = xmlUtils.createEmptyDocument();
    }

    private void createServicesElement() {
        servicesElement = serviceListDocument.createElement("services");
        serviceListDocument.appendChild(servicesElement);
    }

    private void createserviceElements() {
        final Router router = getService(PluginRegistryService.class).getRouter();
        final RouteList routes = router.getRoutes();

        for (int i = 0; i < routes.size(); ++i) {
            final Route route = routes.get(i);
            createServiceElement(route);
        }
    }
    
    private void createServiceElement(final Route route) {
        final Element serviceElement = serviceListDocument.createElement("service");
        servicesElement.appendChild(serviceElement);
        
        final Element urlElement = serviceListDocument.createElement("url");
        urlElement.setTextContent(route.getTemplate().getPattern());
        serviceElement.appendChild(urlElement);
    }
}
