package de.hszg.atocc.core.pluginregistry;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractServiceComponent {

    private Collection<WebService> annotations = new ArrayList<>();

    private PluginRegistryService pluginRegistry;

    public final synchronized void setPluginRegistryService(PluginRegistryService service) {
        pluginRegistry = service;
        extractServiceAnnotations();
        registerWebServices();
    }

    public final synchronized void unsetPluginRegistryService(PluginRegistryService service) {
        if (pluginRegistry == service) {
            unregisterWebServices();
            pluginRegistry = null;
        }
    }

    private void extractServiceAnnotations() {
        final WebServices servicesAnnotation = getClass().getAnnotation(WebServices.class);

        if (servicesAnnotation != null) {
            for (WebService serviceAnnotation : servicesAnnotation.value()) {
                annotations.add(serviceAnnotation);
            }
        } else {
            final WebService serviceAnnotation = getClass().getAnnotation(WebService.class);

            if (serviceAnnotation != null) {
                annotations.add(serviceAnnotation);
            }
        }
    }

    private void registerWebServices() {
        for (WebService serviceAnnotation : annotations) {
            pluginRegistry.register(serviceAnnotation.url(), serviceAnnotation.resource());
        }
    }

    private void unregisterWebServices() {
        for (WebService serviceAnnotation : annotations) {
            pluginRegistry.unregister(serviceAnnotation.resource());
        }

        annotations.clear();
    }

}
