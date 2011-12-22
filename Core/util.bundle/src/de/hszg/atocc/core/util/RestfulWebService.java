package de.hszg.atocc.core.util;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentMap;

import org.restlet.data.Language;
import org.restlet.data.Preference;
import org.restlet.resource.ServerResource;

public class RestfulWebService extends ServerResource {

    private ConcurrentMap<String, Object> attributes;
    private Locale locale = new Locale("en");

    @Override
    protected final void doInit() {
        super.doInit();

        attributes = getContext().getAttributes();

        final List<Preference<Language>> acceptedLanguages = getRequest().getClientInfo()
                .getAcceptedLanguages();

        if (!acceptedLanguages.isEmpty()) {
            final Preference<Language> languagePreference = acceptedLanguages.get(0);
            final Language language = languagePreference.getMetadata();

            if (!"*".equals(language.getName())) {
                locale = new Locale(language.getName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    protected final <T> T getService(Class<T> c) {
        if (attributes.get(c.getName()) == null) {
            throw new ServiceNotFoundException(c.getName());
        }

        return (T) attributes.get(c.getName());
    }

    protected final Locale getLocale() {
        return locale;
    }

}
