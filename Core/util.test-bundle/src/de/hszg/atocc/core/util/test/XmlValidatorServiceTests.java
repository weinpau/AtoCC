package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.SchemaNotRegisteredException;
import de.hszg.atocc.core.util.SchemaRegistrationException;
import de.hszg.atocc.core.util.XmlValidationException;

import org.junit.Assert;

import org.junit.Test;

public final class XmlValidatorServiceTests extends AbstractTestHelper {

    private static final String AUTOMATON = "AUTOMATON";

    @Test(expected = SchemaRegistrationException.class)
    public void registerSchemaShouldFailIfNameAlreadyExists() throws SchemaRegistrationException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON);
    }

    @Test(expected = SchemaRegistrationException.class)
    public void registerSchemaShouldFailIfSchemaAlreadyExists() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testRegisterSchema() {
        Assert.fail("Not yet implemented");
    }

    @Test(expected = SchemaNotRegisteredException.class)
    public void unregisterSchemaShouldFailIfNameDoesNotExists() {
        Assert.fail("Not yet implemented");
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
