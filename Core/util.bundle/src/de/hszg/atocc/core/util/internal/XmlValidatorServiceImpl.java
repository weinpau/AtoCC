package de.hszg.atocc.core.util.internal;

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
            final SchemaFactory schemaFactory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            final Schema schema =
                    schemaFactory.newSchema(getClass().getResource("/AutoEdit.xsd"));

            registerSchema(schema, "AUTOMATON");
        } catch (final SAXException e) {
            e.printStackTrace();
        }
    }

    // TODO: use dedicated exceptions (e.g. SchemaRegistrationException -> SchemaAlreadyRegistered, SchemaNameAlreadyInUse)
    @Override
    public void registerSchema(Schema schema, String name) {
        if (schemas.containsKey(name)) {
            throw new IllegalArgumentException("SCHEMA_NAME_ALREADY_REGISTERED");
        }

        if (schemas.containsValue(schema)) {
            throw new IllegalArgumentException("SCHEMA_ALREADY_REGISTERED");
        }

        schemas.put(name, schema);
    }

    @Override
    public void unregisterSchema(String name) {
        schemas.remove(name);
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

}
