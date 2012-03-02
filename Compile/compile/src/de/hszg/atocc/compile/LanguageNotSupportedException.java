package de.hszg.atocc.compile;

public class LanguageNotSupportedException extends Exception {

    private static final long serialVersionUID = 1L;

    public LanguageNotSupportedException(String language) {
        super("'" + language + "'");
    }
    
}
