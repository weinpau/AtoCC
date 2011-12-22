package de.hszg.atocc.core.translation;

import java.util.Locale;

public interface TranslationService {

    String translate(String key, Locale locale);

}
