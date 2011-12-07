package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.InvalidSchemaNameException;
import de.hszg.atocc.core.util.SchemaAlreadyRegisteredException;
import de.hszg.atocc.core.util.SchemaRegistrationException;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.XmlValidatorService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public final class XmlValidatorServiceImpl implements XmlValidatorService {

    private Map<String, Schema> schemas = new HashMap<>();

    // TODO: move schema registration to client service code
    public XmlValidatorServiceImpl() {
        try {
            final SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            final Schema schema = schemaFactory.newSchema(getClass().getResource("/AutoEdit.xsd"));

            registerSchema(schema, "AUTOMATON");
        } catch (final SAXException | SchemaRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerSchema(Schema schema, String name) throws SchemaRegistrationException {
        try {
            verifySchemaNameIsUnique(name);
            verifySchemaIsNotRegistered(schema, name);

            schemas.put(name, schema);
        } catch (final Exception e) {
            throw new SchemaRegistrationException(e);
        }
    }

    @Override
    public void unregisterSchema(String name) {
        schemas.remove(name);
    }
    
    @Override
    public boolean isSchemaRegistered(String name) {
        return schemas.containsKey(name); 
    }

    @Override
    public void validate(Document document, String schemaName) throws XmlValidationException {
        final Schema schema = schemas.get(schemaName);

        final Validator validator = schema.newValidator();

        try {
            validator.validate(new DOMSource(document));
        } catch (final SAXException | IOException e) {
            throw new XmlValidationException(e);
        }
    }

    private void verifySchemaIsNotRegistered(Schema schema, String name)
        throws SchemaAlreadyRegisteredException {
        if (schemas.containsValue(schema)) {
            throw new SchemaAlreadyRegisteredException(name);
        }
    }

    private void verifySchemaNameIsUnique(String name) throws InvalidSchemaNameException {
        if (schemas.containsKey(name)) {
            throw new InvalidSchemaNameException(name);
        }
    }
}
