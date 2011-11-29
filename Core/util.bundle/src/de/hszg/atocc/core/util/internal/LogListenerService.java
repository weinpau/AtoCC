package de.hszg.atocc.core.util.internal;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;

public final class LogListenerService implements LogListener {
    
    private LogReaderService logReader;
    
    public LogListenerService() {
        System.out.println("Listener");
    }
    
    @Override
    public void logged(LogEntry entry) {
        System.out.println("LOGGED: " + entry);
    }
    
    public synchronized void setLogReaderService(LogReaderService service) {
        logReader = service;
        System.out.println("setLogReader");
        logReader.addLogListener(this);
    }

    public synchronized void unsetLogReaderService(LogReaderService service) {
        if (logReader == service) {
            logReader.removeLogListener(this);
            logReader = null;
        }
    }
}
