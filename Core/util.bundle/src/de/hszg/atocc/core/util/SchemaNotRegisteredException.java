package de.hszg.atocc.core.util;

public final class SchemaNotRegisteredException extends Exception {

    private static final long serialVersionUID = 1L;

    public SchemaNotRegisteredException(String schemaName) {
        super(schemaName);
    }

}
