package de.hszg.atocc.core.util;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public final class ServiceUtils {

    private ServiceUtils() {

    }

    @SuppressWarnings("rawtypes")
    public static <T> T getService(final BundleContext bc, final Class<T> c) {
        final ServiceReference serviceReference = tryToGetServiceReference(bc, c.getName());

        return tryToGetService(bc, serviceReference);
    }

    @SuppressWarnings("rawtypes")
    public static <T> void ungetService(final BundleContext bc, final Class<T> c) {
        final ServiceReference serviceReference = tryToGetServiceReference(bc, c.getName());

        bc.ungetService(serviceReference);
    }

    @SuppressWarnings("rawtypes")
    private static ServiceReference tryToGetServiceReference(final BundleContext bc,
            final String className) {
        final ServiceReference serviceReference = bc.getServiceReference(className);

        if (serviceReference == null) {
            throw new NullPointerException("Service reference not found: " + className);
        }

        return serviceReference;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static <T> T tryToGetService(final BundleContext bc,
            final ServiceReference serviceReference) {
        final T service = (T) bc.getService(serviceReference);

        if (service == null) {
            throw new NullPointerException("Service not found");
        }

        return service;
    }

}
