package de.hszg.atocc.core.translation.impl;

import de.hszg.atocc.core.translation.TranslationService;

import java.io.File;
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

    private LogService logger;

    private Set<URL> loadedResources = new HashSet<URL>();

    private Map<Locale, Properties> translations = new HashMap<>();

    @Override
    public String translate(String key, Locale locale) {
        if (!isTranslationsAvailableFor(key, locale)) {
            loadNewRessourcesIfNeccessary();
        }

        if (!isTranslationsAvailableFor(key, locale)) {
            logger.log(LogService.LOG_DEBUG,
                    String.format("Key '%s' not translated for Locale %s", key, locale.toString()));
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
            for (Locale locale : Locale.getAvailableLocales()) {
                final Enumeration<URL> resources = classLoader.getResources(String.format("%s/%s",
                        locale.getLanguage(), "messages.properties"));

                while (resources.hasMoreElements()) {
                    final URL resource = resources.nextElement();
                    loadResourceIfNeccessary(resource);
                }
            }
        } catch (IOException e) {
            logger.log(LogService.LOG_ERROR, COULD_NOT_LOAD_TRANSLATIONS, e);
        }
    }

    private void loadResourceIfNeccessary(final URL resource) {
        if (!loadedResources.contains(resource)) {
            try {
                loadTranslationsFrom(resource);
                loadedResources.add(resource);
            } catch (IOException e) {
                logger.log(LogService.LOG_ERROR, COULD_NOT_LOAD_TRANSLATIONS, e);
            }
        }
    }

    private void loadTranslationsFrom(URL resource) throws IOException {
        logger.log(LogService.LOG_INFO, "Loading translations from " + resource.toString());

        final Properties properties = new Properties();
        properties.load(resource.openStream());

        final File resourceFile = new File(resource.getPath());
        final String language = resourceFile.getParent().substring(1);

        mergeTranslationsWith(properties, new Locale(language));
    }

    private void mergeTranslationsWith(Properties properties, Locale locale) {
        if (!translations.containsKey(locale)) {
            translations.put(locale, new Properties());
        }

        translations.get(locale).putAll(properties);
    }
}
