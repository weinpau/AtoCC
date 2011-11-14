package de.hszigr.atocc.utils.internal;

import java.io.StringReader;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.restlet.resource.ClientResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import de.hszigr.atocc.utils.UtilitiesException;
import de.hszigr.atocc.utils.UtilitiesService;

/**
 * Internal implementation of our example OSGi service
 */
public final class UtilitiesServiceImpl implements UtilitiesService {

    private BundleContext context;

    @Override
    public void setContext(BundleContext bc) {
        context = bc;
    }

    @Override
    public <T> T getService(Class<T> c) {
        if(context == null) {
            throw new RuntimeException("Context not set");
        }
        
        ServiceReference serviceReference = context.getServiceReference(c.getName());

        if(serviceReference == null) {
            throw new RuntimeException("ServiceRef not found: " + c.getName());
        }
        
        T service = (T) context.getService(serviceReference);
        
        if(service == null) {
            throw new RuntimeException("Service not found: " + c.getName());
        }
        
        return service;
    }

    @Override
    public Document callWebService(String uri) {

        try {
            final String data = new ClientResource(uri).get().getText();

            InputSource source = new InputSource(new StringReader(data));
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(source);
        } catch (Exception e) {
            throw new UtilitiesException();
        }

    }

}
