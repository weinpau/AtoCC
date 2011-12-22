package de.hszg.atocc.core.util;

public final class SchemaAlreadyRegisteredException extends Exception {

    private static final long serialVersionUID = 1L;

    public SchemaAlreadyRegisteredException(String schemaName) {
        super(schemaName);
    }

}
