package de.hszg.atocc.core.util;

public final class ServiceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ServiceNotFoundException(String serviceName) {
        super(serviceName);
    }

}
