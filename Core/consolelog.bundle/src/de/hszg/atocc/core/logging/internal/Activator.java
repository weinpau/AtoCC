package de.hszg.atocc.core.logging.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogReaderService;

public final class Activator implements BundleActivator {

    private LogListenerService logListener;
    private LogReaderService logReader;
    
    public void start(BundleContext context) throws Exception {
        final ServiceReference<LogReaderService> serviceReference = context
                .getServiceReference(LogReaderService.class);

        logReader = context.getService(serviceReference);
        
        logListener = new LogListenerService();
        logReader.addLogListener(logListener);
    }

    public void stop(BundleContext context) throws Exception {
        if(logReader != null) {
            logReader.removeLogListener(logListener);
        }
    }

}
