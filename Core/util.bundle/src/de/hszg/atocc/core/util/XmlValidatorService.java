package de.hszg.atocc.core.util;

import javax.xml.validation.Schema;

import org.w3c.dom.Document;

public interface XmlValidatorService {

    void registerSchema(Schema schema, String name);
    void unregisterSchema(String name);
    
    void validate(Document document, String schemaName) throws XmlValidationException;
    
}
