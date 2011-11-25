package de.hszg.atocc.core.pluginregistry.internal;

import de.hszg.atocc.core.pluginregistry.PluginRegistryService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.ServiceNotFoundException;
import de.hszg.atocc.core.util.XmlUtilService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
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
        final Map<Class<? extends ServerResource>, String> registeredServices =
                getService(PluginRegistryService.class).getRegisteredServices();

        for (Entry<Class<? extends ServerResource>, String> entry : registeredServices.entrySet()) {
            createServiceElement(entry.getKey(), entry.getValue());
        }
    }

    private void createServiceElement(Class<? extends ServerResource> resourceClass, String url) {
        final Element serviceElement = serviceListDocument.createElement("service");
        servicesElement.appendChild(serviceElement);

        final Element nameElement = serviceListDocument.createElement("name");
        nameElement.setTextContent(resourceClass.getSimpleName());
        serviceElement.appendChild(nameElement);

        final Element urlElement = serviceListDocument.createElement("url");
        urlElement.setTextContent(url);
        serviceElement.appendChild(urlElement);

        createInterfaceElements(resourceClass, serviceElement);
    }

    private void createInterfaceElements(Class<? extends ServerResource> resourceClass,
            final Element serviceElement) {
        final Method[] methods = resourceClass.getMethods();

        for (Method method : methods) {
            final Class<? extends Annotation> webServiceType = getWebServiceAnnotation(method);

            if (isWebServiceAnnotation(webServiceType)) {
                createMethodElement(serviceElement, webServiceType);
            }
        }
    }

    private void createMethodElement(final Element serviceElement,
            final Class<? extends Annotation> webServiceType) {
        final Element methodElement = serviceListDocument.createElement("method");
        methodElement.setTextContent(webServiceType.getSimpleName().toUpperCase());
        serviceElement.appendChild(methodElement);
    }

    private Class<? extends Annotation> getWebServiceAnnotation(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            final Class<? extends Annotation> type = annotation.annotationType();

            if (isRestletResourceAnnotation(type)) {
                return type;
            }
        }

        return null;
    }

    private boolean isWebServiceAnnotation(Class<? extends Annotation> webServiceAnnotationType) {
        return webServiceAnnotationType != null;
    }
    
    private boolean isRestletResourceAnnotation(final Class<? extends Annotation> type) {
        return type.equals(Get.class) || type.equals(Post.class) || type.equals(Put.class)
                || type.equals(Delete.class);
    }
}
