package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.ServiceNotFoundException;
import de.hszg.atocc.core.util.XmlUtilService;

import org.restlet.resource.Finder;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
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
            createServiceElements();

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

    private void createServiceElements() {
        final Router router = getService(PluginRegistryService.class).getRouter();

        for (Route route : router.getRoutes()) {
            createServiceElement(route);
        }
    }
    
    private void createServiceElement(final Route route) {
        final Element serviceElement = serviceListDocument.createElement("service");
        servicesElement.appendChild(serviceElement);
        
        final String[] routeParts = route.toString().split("->");
        final String url = routeParts[0].trim().replace("\"", "");
        
        final Element urlElement = serviceListDocument.createElement("url");
        urlElement.setTextContent(url);
        serviceElement.appendChild(urlElement);
    }
}
