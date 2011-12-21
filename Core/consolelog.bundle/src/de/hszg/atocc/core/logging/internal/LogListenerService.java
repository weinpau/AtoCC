package de.hszg.atocc.core.logging.internal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;

public final class LogListenerService implements LogListener {

    private static final String LOG_MESSAGE = "%s - %d\nBundle: %s\nMessage: %s\n";

    @Override
    public void logged(LogEntry entry) {

        System.out.println(format(entry));

        if (entry.getLevel() == LogService.LOG_WARNING || entry.getLevel() == LogService.LOG_ERROR) {
            logToFile(entry);
            sendMail(entry);
        }
    }

    private void logToFile(LogEntry entry) {
        File logFile = new File("atocc.log");

        try (FileOutputStream stream = new FileOutputStream(logFile, true);
                BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.append(format(entry));
            writer.append('\n');
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMail(LogEntry entry) {

    }

    private String format(LogEntry entry) {
        return String.format(LOG_MESSAGE, nameOfLogLevel(entry.getLevel()), entry.getTime(), entry
                .getBundle().getSymbolicName(), entry.getMessage());
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
