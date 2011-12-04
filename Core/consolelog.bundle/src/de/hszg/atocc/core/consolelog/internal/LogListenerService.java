package de.hszg.atocc.core.consolelog.internal;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

public final class LogListenerService implements LogListener {

    private static final String LOG_MESSAGE = "%s - %d\nBundle: %s\nMessage: %s\n";

    @Override
    public void logged(LogEntry entry) {
        
        System.out.println(String.format(LOG_MESSAGE, nameOfLogLevel(entry.getLevel()),
                entry.getTime(), entry.getBundle().getSymbolicName(), entry.getMessage()));

    }

    private String nameOfLogLevel(int level) {
        switch (level) {
        case LogService.LOG_DEBUG:
            return "DEBUG";

        case LogService.LOG_INFO:
            return "INFO";

        case LogService.LOG_WARNING:
            return "WARNING";

        case LogService.LOG_ERROR:
            return "ERROR";

        default:
            throw new RuntimeException("Unreachable");
        }
    }
}
