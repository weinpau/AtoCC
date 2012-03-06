package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.SchemaNotRegisteredException;
import de.hszg.atocc.core.util.SchemaRegistrationException;
import de.hszg.atocc.core.util.XmlValidationException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;

public final class XmlValidatorServiceTests extends AbstractTestHelper {

    private static final String AUTOMATON = "AUTOMATON";
    private static final String AUTOMATON2 = "AUTOMATON2";
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        try {
            getValidatorService().unregisterSchema(AUTOMATON);
        } catch (SchemaNotRegisteredException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = SchemaRegistrationException.class)
    public void registerSchemaShouldFailIfNameAlreadyExists() throws SchemaRegistrationException {
        Assert.assertTrue(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON);
    }

    @Test(expected = SchemaRegistrationException.class)
    public void registerSchemaShouldFailIfSchemaAlreadyExists() throws SchemaRegistrationException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON);
        Assert.assertTrue(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON2);
    }

    @Test
    public void testRegisterSchema() throws SchemaRegistrationException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON);
        Assert.assertTrue(getValidatorService().isSchemaRegistered(AUTOMATON));
    }

    @Test(expected = SchemaNotRegisteredException.class)
    public void unregisterSchemaShouldFailIfNameDoesNotExist() throws SchemaNotRegisteredException {
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().unregisterSchema(AUTOMATON);
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
