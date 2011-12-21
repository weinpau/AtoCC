package de.hszg.atocc.core.translation.impl;

import de.hszg.atocc.core.translation.TranslationService;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.osgi.service.log.LogService;

public final class TranslationServiceImpl implements TranslationService {

    private static final String COULD_NOT_LOAD_TRANSLATIONS = "Could not load translations";

    private static final String LANGUAGE = "LANGUAGE";

    private LogService logger;

    private Set<URL> loadedResources = new HashSet<URL>();

    private Map<Locale, Properties> translations = new HashMap<>();

    @Override
    public String translate(String key, Locale locale) {
        if (!isTranslationsAvailableFor(key, locale)) {
            loadNewRessourcesIfNeccessary();
        }

        if (!isTranslationsAvailableFor(key, locale)) {
            return key;
        }

        return translations.get(locale).getProperty(key);
    }

    public synchronized void setLogService(LogService service) {
        logger = service;
    }

    public synchronized void unsetLogService(LogService service) {
        if (logger == service) {
            logger = null;
        }
    }

    private boolean isTranslationsAvailableFor(String key, Locale locale) {
        return translations.containsKey(locale) && translations.get(locale).containsKey(key);
    }

    private void loadNewRessourcesIfNeccessary() {
        final ClassLoader classLoader = getClass().getClassLoader();

        try {
            final Enumeration<URL> resources = classLoader.getResources("messages.properties");

            while (resources.hasMoreElements()) {
                final URL resource = resources.nextElement();

                if (!loadedResources.contains(resource)) {
                    try {
                        loadTranslationsFrom(resource);
                        loadedResources.add(resource);
                    } catch (IOException e) {
                        logger.log(LogService.LOG_ERROR, COULD_NOT_LOAD_TRANSLATIONS, e);
                    }
                }
            }
        } catch (IOException e) {
            logger.log(LogService.LOG_ERROR, COULD_NOT_LOAD_TRANSLATIONS, e);
        }
    }

    private void loadTranslationsFrom(URL resource) throws IOException {
        logger.log(LogService.LOG_INFO, "Loading translations from " + resource.toString());

        final Properties properties = new Properties();
        properties.load(resource.openStream());

        if (!properties.containsKey(LANGUAGE)) {
            throw new IOException(resource.toString() + " is missing 'LANGUAGE'-Definition");
        }

        mergeTranslationsWith(properties);
    }

    private void mergeTranslationsWith(Properties properties) {
        final Locale locale = new Locale(properties.getProperty(LANGUAGE));

        if (!translations.containsKey(locale)) {
            translations.put(locale, new Properties());
        }

        translations.get(locale).putAll(properties);
    }
}
