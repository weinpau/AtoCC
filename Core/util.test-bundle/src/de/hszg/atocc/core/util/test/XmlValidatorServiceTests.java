package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.SchemaNotRegisteredException;
import de.hszg.atocc.core.util.SchemaRegistrationException;
import de.hszg.atocc.core.util.XmlValidationException;

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;

public final class XmlValidatorServiceTests extends AbstractTestHelper {

    private static final String AUTOMATON = "AUTOMATON";
    private static final String AUTOMATON2 = "AUTOMATON2";
    
    @Before
    public void setUp() {
        try {
            getValidatorService().unregisterSchema(AUTOMATON);
        } catch (SchemaNotRegisteredException e) {
            e.printStackTrace();
        }
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
        Assert.assertFalse(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(null, AUTOMATON);
        Assert.assertTrue(getValidatorService().isSchemaRegistered(AUTOMATON));
        getValidatorService().registerSchema(new Schema() {
            
            @Override
            public ValidatorHandler newValidatorHandler() {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Validator newValidator() {
                // TODO Auto-generated method stub
                return null;
            }
        }, AUTOMATON);
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
