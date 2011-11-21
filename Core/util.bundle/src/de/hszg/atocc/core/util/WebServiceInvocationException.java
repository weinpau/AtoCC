package de.hszg.atocc.core.util;

public class WebServiceInvocationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public WebServiceInvocationException(final Exception cause) {
        super(cause);
    }

}
