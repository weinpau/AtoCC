package de.hszigr.atocc.utils;

import org.osgi.framework.BundleContext;
import org.w3c.dom.Document;

/**
 * Public API representing an example OSGi service
 */
public interface UtilitiesService {
    
    void setContext(BundleContext bc);
    
    <T> T getService(Class<T> c);
    
    Document callWebService(final String uri);

}
