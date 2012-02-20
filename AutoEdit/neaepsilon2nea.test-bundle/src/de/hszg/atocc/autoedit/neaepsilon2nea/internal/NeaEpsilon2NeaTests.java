package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class NeaEpsilon2NeaTests {

    private static WebUtilService webService;
    private static XmlUtilService xmlUtils;
    private static AutomatonService automatonService;

    private static Document neaEpsilonDocument1;
    private static Automaton neaEpsilon1;

    private static Document neaDocument1;
    private static Automaton nea1;

    private static Document invalidTypeDocument;

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
    public static void initialize() throws XPathExpressionException, Exception {
        neaEpsilonDocument1 = xmlUtils.documentFromFile("Nea_Epsilon_1.xml");
        neaEpsilon1 = automatonService.automatonFrom(neaEpsilonDocument1);
        
        // FIXME: when testing the document seems to be not beeing sent correctly
        neaDocument1 = webService.post("http://localhost:8081/autoedit/neaepsilon2nea",
                neaEpsilonDocument1);
        
        if(XmlUtilService.ERROR.equals(xmlUtils.getResultStatus(neaDocument1))) {
            throw new Exception(xmlUtils.getErrorMessage(neaDocument1) + "\n" + xmlUtils.getErrorCause(neaDocument1));
        }
        
        nea1 = automatonService.automatonFrom(neaDocument1);

        invalidTypeDocument = xmlUtils.documentFromFile("Nea_Epsilon_1.xml");
        final Element typeElement = (Element) invalidTypeDocument.getElementsByTagName("TYPE")
                .item(0);
        typeElement.setAttribute("value", "DEA");
    }

    @Test
    public void verifyInvalidNeaEpsilonProducesError() {
        Document invalid = xmlUtils.createEmptyDocument();
        final Element automatonElement = invalid.createElement("AUTOMATON");
        invalid.appendChild(automatonElement);

        final Document resultDocument = webService.post(
                "http://localhost:8081/autoedit/neaepsilon2nea", invalid);

        final String result = xmlUtils.getResultStatus(resultDocument);

        Assert.assertEquals("error", result);
    }

    @Test
    public void verifyInvalidAutomatonTypeProducesError() {
        final Document resultDocument = webService.post(
                "http://localhost:8081/autoedit/neaepsilon2nea", invalidTypeDocument);

        final String result = xmlUtils.getResultStatus(resultDocument);

        Assert.assertEquals("error", result);
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
    public void testFinalStatesAreEqualForNea1() {
        Assert.assertEquals(neaEpsilon1.getFinalStates(), nea1.getFinalStates());
    }

    @Test
    public void testTransitionsFromQ0ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFor("q_0");

        final Set<Transition> expectedTransitions = new HashSet<>();
        expectedTransitions.add(new Transition("q_0", "q_1", "a"));
        expectedTransitions.add(new Transition("q_0", "q_5", "a"));
        expectedTransitions.add(new Transition("q_0", "q_4", "a"));
        expectedTransitions.add(new Transition("q_0", "q_3", "a"));
        expectedTransitions.add(new Transition("q_0", "q_2", "b"));

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ1ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFor("q_1");

        final Set<Transition> expectedTransitions = new HashSet<>();
        expectedTransitions.add(new Transition("q_1", "q_4", "a"));
        expectedTransitions.add(new Transition("q_1", "q_5", "a"));
        expectedTransitions.add(new Transition("q_1", "q_2", "b"));

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ2ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFor("q_2");

        final Set<Transition> expectedTransitions = new HashSet<>();

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ3ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFor("q_3");

        final Set<Transition> expectedTransitions = new HashSet<>();
        expectedTransitions.add(new Transition("q_3", "q_2", "b"));
        expectedTransitions.add(new Transition("q_3", "q_4", "a"));
        expectedTransitions.add(new Transition("q_3", "q_4", "b"));
        expectedTransitions.add(new Transition("q_3", "q_5", "a"));
        expectedTransitions.add(new Transition("q_3", "q_5", "b"));

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ4ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFor("q_4");

        final Set<Transition> expectedTransitions = new HashSet<>();

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ5ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFor("q_5");

        final Set<Transition> expectedTransitions = new HashSet<>();

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

}
