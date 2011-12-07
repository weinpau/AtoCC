package de.hszg.atocc.core.util;

public final class InvalidSchemaNameException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidSchemaNameException(String schemaName) {
        super(schemaName);
    }
    
}
