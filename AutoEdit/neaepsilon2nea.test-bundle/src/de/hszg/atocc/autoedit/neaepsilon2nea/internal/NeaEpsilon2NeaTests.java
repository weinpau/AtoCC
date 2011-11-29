package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;
import de.hszg.atocc.core.util.automaton.Automaton;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

public final class NeaEpsilon2NeaTests {

    private static WebUtilService webService;
    private static XmlUtilService xmlUtils;
    private static AutomatonService automatonService;
    
    private static Document neaEpsilonDocument1;
    private static Automaton neaEpsilon1;
    
    private static Document neaDocument1;
    private static Automaton nea1;

    public static void setWebService(WebUtilService service) {
        webService = service;
    }

    public static void setXmlUtils(XmlUtilService service) {
        xmlUtils = service;
    }
    
    public static void setAutomatonService(AutomatonService service) {
        automatonService = service;
    }
    
    @BeforeClass
    public static void initialize() throws XmlUtilsException {
        neaEpsilonDocument1 = xmlUtils.documentFromFile("Nea_Epsilon_1.xml");
        neaEpsilon1 = automatonService.automatonFrom(neaEpsilonDocument1);
        
        neaDocument1 = webService.post("http://localhost:8081/autoedit/neaepsilon2nea", neaEpsilonDocument1);
        nea1 = automatonService.automatonFrom(neaDocument1);
    }
    
    @Test
    public void testAlphabetsAreEqualForNea1() {
        Assert.assertEquals(neaEpsilon1.getAlphabet(), nea1.getAlphabet());
    }

    @Test
    public void testStatesAreEqualForNea1() {
        Assert.assertEquals(neaEpsilon1.getStates(), nea1.getStates());
    }
    
    @Test
    public void testInitialStatesAreEqualForNea1() {
        Assert.assertEquals(neaEpsilon1.getInitialState(), nea1.getInitialState());
    }
    
    @Test
    public void testFinalStatesAreEqualNea1() {
        Assert.assertEquals(neaEpsilon1.getFinalStates(), nea1.getFinalStates());
    }
    
    @Test
    public void testTransitionsFromQ0ForNea1() {
        Assert.fail("Not yet implemented");
    }
    
    @Test
    public void testTransitionsFromQ1ForNea1() {
        Assert.fail("Not yet implemented");
    }
    
    @Test
    public void testTransitionsFromQ2ForNea1() {
        Assert.fail("Not yet implemented");
    }
    
    @Test
    public void testTransitionsFromQ3ForNea1() {
        Assert.fail("Not yet implemented");
    }
    
    @Test
    public void testTransitionsFromQ4ForNea1() {
        Assert.fail("Not yet implemented");
    }
    
    @Test
    public void testTransitionsFromQ5ForNea1() {
        Assert.fail("Not yet implemented");
    }

}
