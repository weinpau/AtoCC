package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.SchemaNotRegisteredException;
import de.hszg.atocc.core.util.SchemaRegistrationException;
import de.hszg.atocc.core.util.XmlValidationException;

import org.junit.Assert;

import org.junit.Test;

public final class XmlValidatorServiceTests extends AbstractTestHelper {

    private static final String AUTOMATON = "AUTOMATON";
    private static final String AUTOMATON2 = "AUTOMATON2";
    private static final String AUTOMATON3 = "AUTOMATON3";
    private static final String AUTOMATON4 = "AUTOMATON4";
    private static final String AUTOMATON5 = "AUTOMATON5";

    @Test(expected = SchemaRegistrationException.class)
    public void registerSchemaShouldFailIfNameAlreadyExists() throws SchemaRegistrationException {
        Assert.assertTrue(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON);
    }

    @Test(expected = SchemaRegistrationException.class)
    public void registerSchemaShouldFailIfSchemaAlreadyExists() throws SchemaRegistrationException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON2));
        getValidatorService().registerSchema(null, AUTOMATON2);
        getValidatorService().registerSchema(null, AUTOMATON3);
    }

    @Test
    public void testRegisterSchema() throws SchemaRegistrationException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON5));
        getValidatorService().registerSchema(null, AUTOMATON5);
        Assert.assertTrue(getValidatorService().isSchemaRegistered(AUTOMATON5));
    }

    @Test(expected = SchemaNotRegisteredException.class)
    public void unregisterSchemaShouldFailIfNameDoesNotExist() throws SchemaNotRegisteredException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON4));
        getValidatorService().unregisterSchema(AUTOMATON4);
    }

    @Test(expected = SchemaNotRegisteredException.class)
    public void validateShoulsFailIfSchemaIsNotRegistered() {
        Assert.fail("Not yet implemented");
    }

    @Test(expected = XmlValidationException.class)
    public void validateShouldThrowIfDocumentIsInvalid() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void validateShouldSucceedForValidInput() {
        Assert.fail("Not yet implemented");
    }
}
