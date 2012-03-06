package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.WebServiceResultStatus;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.Transition;
import de.hszg.atocc.core.util.test.AbstractServiceTest;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class NeaEpsilon2NeaTests extends AbstractServiceTest {

    private static Document neaEpsilonDocument1;
    private static Automaton neaEpsilon1;

    private static Document neaDocument1;
    private static Automaton nea1;

    private static Document invalidTypeDocument;

    @BeforeClass
    public static void initialize() throws XPathExpressionException, Exception {
        neaEpsilonDocument1 = getXmlUtilService().documentFromFile("Nea_Epsilon_1.xml");
        
        System.out.println("Doc: " + getXmlUtilService().xmlToString(neaEpsilonDocument1));
        neaEpsilon1 = getAutomatonService().automatonFrom(neaEpsilonDocument1);

        // FIXME: when testing the document seems to be not being sent correctly
        neaDocument1 = getWebUtilService().post("http://localhost:8081/autoedit/neaepsilon2nea",
                neaEpsilonDocument1);

        if (WebServiceResultStatus.ERROR == getXmlUtilService().getResultStatus(neaDocument1)) {
            throw new Exception(getXmlUtilService().getErrorMessage(neaDocument1) + "\n"
                    + getXmlUtilService().getErrorCause(neaDocument1));
        }

        nea1 = getAutomatonService().automatonFrom(neaDocument1);

        invalidTypeDocument = getXmlUtilService().documentFromFile("Nea_Epsilon_1.xml");
        final Element typeElement = (Element) invalidTypeDocument.getElementsByTagName("TYPE")
                .item(0);
        typeElement.setAttribute("value", "DEA");
    }

    @Test
    public void verifyInvalidNeaEpsilonProducesError() {
        Document invalid = getXmlUtilService().createEmptyDocument();
        final Element automatonElement = invalid.createElement("AUTOMATON");
        invalid.appendChild(automatonElement);

        final Document resultDocument = getWebUtilService().post(
                "http://localhost:8081/autoedit/neaepsilon2nea", invalid);

        final WebServiceResultStatus result = getXmlUtilService().getResultStatus(resultDocument);

        Assert.assertEquals(WebServiceResultStatus.ERROR, result);
    }

    @Test
    public void verifyInvalidAutomatonTypeProducesError() {
        final Document resultDocument = getWebUtilService().post(
                "http://localhost:8081/autoedit/neaepsilon2nea", invalidTypeDocument);

        final WebServiceResultStatus result = getXmlUtilService().getResultStatus(resultDocument);

        Assert.assertEquals(WebServiceResultStatus.ERROR, result);
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
        final Set<Transition> actualTransitions = nea1.getTransitionsFrom("q_0");

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
        final Set<Transition> actualTransitions = nea1.getTransitionsFrom("q_1");

        final Set<Transition> expectedTransitions = new HashSet<>();
        expectedTransitions.add(new Transition("q_1", "q_4", "a"));
        expectedTransitions.add(new Transition("q_1", "q_5", "a"));
        expectedTransitions.add(new Transition("q_1", "q_2", "b"));

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ2ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFrom("q_2");

        final Set<Transition> expectedTransitions = new HashSet<>();

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ3ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFrom("q_3");

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
        final Set<Transition> actualTransitions = nea1.getTransitionsFrom("q_4");

        final Set<Transition> expectedTransitions = new HashSet<>();

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

    @Test
    public void testTransitionsFromQ5ForNea1() {
        final Set<Transition> actualTransitions = nea1.getTransitionsFrom("q_5");

        final Set<Transition> expectedTransitions = new HashSet<>();

        Assert.assertEquals(expectedTransitions, actualTransitions);
    }

}
