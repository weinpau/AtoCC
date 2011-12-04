package de.hszg.atocc.core.pluginregistry;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractServiceComponent {

    private Collection<WebService> annotations = new ArrayList<>();

    private PluginRegistryService pluginRegistry;

    public final synchronized void setPluginRegistryService(PluginRegistryService service) {
        pluginRegistry = service;
        registerWebServices();
    }

    public final synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            unregisterWebServices();
            pluginRegistry = null;
        }
    }

    private void registerWebServices() {
        final WebServices servicesAnnotation = getClass().getAnnotation(WebServices.class);

        if (servicesAnnotation != null) {
            for (WebService serviceAnnotation : servicesAnnotation.value()) {
                registerWebService(serviceAnnotation);
            }
        } else {
            final WebService annotation = getClass().getAnnotation(WebService.class);

            if (annotation != null) {
                registerWebService(annotation);
            }
        }
    }

    private void registerWebService(WebService serviceAnnotation) {
        annotations.add(serviceAnnotation);
        pluginRegistry.register(serviceAnnotation.url(), serviceAnnotation.resource());
    }
    
    private void unregisterWebServices() {
        for (WebService annotation : annotations) {
            pluginRegistry.unregister(annotation.resource());
        }
    }

}
