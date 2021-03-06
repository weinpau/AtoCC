package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlUtilsException;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

public final class AutomatonServiceTests extends AbstractTestHelper {

    private static final String Q5 = "q5";
    private static final String Q4 = "q4";
    private static final String Q3 = "q3";
    private static final String Q2 = "q2";
    private static final String Q1 = "q1";
    private static final String Q0 = "q0";
    private static final String B = "b";
    private static final String A = "a";

    private TestAutomatons automatons;

    private final Set<String> emptySet = new HashSet<>();

    private Set<String> z2;
    private Set<String> z3;
    private Set<String> z4;
    private Set<String> z5;
    private Set<String> z6;

    @Before
    public void setUp() throws XmlUtilsException, SerializationException {
        automatons = new TestAutomatons();

        z2 = getSetService().createSetWith(TestAutomatons.Z2);
        z3 = getSetService().createSetWith(TestAutomatons.Z3);
        z4 = getSetService().createSetWith(TestAutomatons.Z4);
        z5 = getSetService().createSetWith(TestAutomatons.Z5);
        z6 = getSetService().createSetWith(TestAutomatons.Z6);
    }

    @Test
    public void testGetStatesFrom() {
        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_NEA1, automatons.getNea1()
                .getStates().size());

        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_NEA2, automatons.getNea2()
                .getStates().size());

        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_NEA3, automatons.getNea3()
                .getStates().size());

        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_PDA, automatons.getPda().getStates()
                .size());
    }

    @Test
    public void testGetStateNamesFromDocument1() {
        final Set<String> actualNames = automatons.getNea1().getStates();

        Assert.assertEquals(automatons.getStateNamesOfNea1(), actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument2() {
        final Set<String> actualNames = automatons.getNea2().getStates();

        Assert.assertEquals(automatons.getStateNamesOfNea2(), actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument3() {
        final Set<String> actualNames = automatons.getNea3().getStates();

        Assert.assertEquals(automatons.getStateNamesOfNea3(), actualNames);
    }

    @Test
    public void testGetStateNamesFromPdaDocument() {
        final Set<String> actualnames = automatons.getPda().getStates();

        Assert.assertEquals(automatons.getStateNamesOfPda(), actualnames);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom1() {
        final Set<String> actualFinalStates = automatons.getNea1().getFinalStates();

        Assert.assertEquals(automatons.getFinalStatesOfNea1(), actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom2() {
        final Set<String> actualFinalStates = automatons.getNea2().getFinalStates();

        Assert.assertEquals(automatons.getFinalStatesOfNea2(), actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom3() {
        final Set<String> actualFinalStates = automatons.getNea3().getFinalStates();

        Assert.assertEquals(automatons.getFinalStatesOfNea3(), actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFromPda() {
        final Set<String> actualFinalStates = automatons.getPda().getFinalStates();

        Assert.assertEquals(automatons.getFinalStatesOfPda(), actualFinalStates);
    }

    @Test
    public void testGetNameOfInitialStateFrom1() {
        final String actualInitialState = automatons.getNea1().getInitialState();

        Assert.assertEquals(automatons.getInitialStateOfNea1(), actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom2() {
        final String actualInitialState = automatons.getNea2().getInitialState();

        Assert.assertEquals(automatons.getInitialStateOfNea2(), actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom3() {
        final String actualInitialState = automatons.getNea3().getInitialState();

        Assert.assertEquals(automatons.getInitialStateOfNea3(), actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFromPda() {
        final String actualInitialState = automatons.getPda().getInitialState();

        Assert.assertEquals(automatons.getInitialStateOfPda(), actualInitialState);
    }

    @Test
    public void testGetStatePowerSetFrom2() {
        final Set<Set<String>> actualPowerset = getAutomatonService().getStatePowerSetFrom(
                automatons.getNea2());

        Assert.assertEquals(automatons.getPowerSetOfStatesFromNea2(), actualPowerset);
    }

    @Test
    public void testGetStatePowerSetFrom3() {
        final Set<Set<String>> actualPowerset = getAutomatonService().getStatePowerSetFrom(
                automatons.getNea3());

        Assert.assertEquals(automatons.getPowerSetOfStatesFromNea3(), actualPowerset);
    }

    @Test
    public void testGetAlphabetFrom1() {
        final Set<String> actualAlphabet = automatons.getNea1().getAlphabet();

        Assert.assertEquals(automatons.getAlphabetOfNea1(), actualAlphabet);
    }

    @Test
    public void testGetAlphabetFrom2() {
        final Set<String> actualAlphabet = automatons.getNea2().getAlphabet();

        Assert.assertEquals(automatons.getAlphabetOfNea2(), actualAlphabet);
    }

    @Test
    public void testGetAlphabetFrom3() {
        final Set<String> actualAlphabet = automatons.getNea3().getAlphabet();

        Assert.assertEquals(automatons.getAlphabetOfNea3(), actualAlphabet);
    }

    @Test
    public void testGetAlphabetFromPda() {
        final Set<String> actualAlphabet = automatons.getPda().getAlphabet();

        Assert.assertEquals(automatons.getAlphabetOfPda(), actualAlphabet);
    }

    // @Test
    // public void testGetStackAlphabetFromPda() {
    // final Set<String> actualAlphabet =
    // automatons.getPda().getStackAlphabet();
    //
    // Assert.assertEquals(automatons.getStackAlphabetOfPda(), actualAlphabet);
    // }

    // @Test
    // public void testGetInitialStackSymbolFromPda() {
    // final String actualSymbol = automatons.getPda().getInitialStackSymbol();
    //
    // Assert.assertEquals(automatons.getInitialStackSymbolOfPda(),
    // actualSymbol);
    // }

    @Test
    public void testGetTargetsOfNea1ForZ1() {
        Assert.assertEquals(z4, automatons.getNea1().getTargetsFor(TestAutomatons.Z1, A));
        Assert.assertEquals(emptySet, automatons.getNea1().getTargetsFor(TestAutomatons.Z1, B));
        Assert.assertEquals(z2,
                automatons.getNea1().getTargetsFor(TestAutomatons.Z1, Automaton.EPSILON));
    }

    @Test
    public void testGetTargetsOfNea1ForZ2() {
        Assert.assertEquals(z5, automatons.getNea1().getTargetsFor(TestAutomatons.Z2, A));
        Assert.assertEquals(z3, automatons.getNea1().getTargetsFor(TestAutomatons.Z2, B));
        Assert.assertEquals(emptySet,
                automatons.getNea1().getTargetsFor(TestAutomatons.Z2, Automaton.EPSILON));
    }

    @Test
    public void testGetTargetsOfNea1ForZ3() {
        Assert.assertEquals(new HashSet<String>(),
                automatons.getNea1().getTargetsFor(TestAutomatons.Z3, A));
        Assert.assertEquals(new HashSet<String>(),
                automatons.getNea1().getTargetsFor(TestAutomatons.Z3, B));
        Assert.assertEquals(new HashSet<String>(),
                automatons.getNea1().getTargetsFor(TestAutomatons.Z3, Automaton.EPSILON));
    }

    @Test
    public void testGetTargetsOfNea1ForZ4() {
        Assert.assertEquals(emptySet, automatons.getNea1().getTargetsFor(TestAutomatons.Z4, A));
        Assert.assertEquals(z5, automatons.getNea1().getTargetsFor(TestAutomatons.Z4, B));
        Assert.assertEquals(z2,
                automatons.getNea1().getTargetsFor(TestAutomatons.Z4, Automaton.EPSILON));
    }

    @Test
    public void testGetTargetsOfNea1ForZ5() {
        Assert.assertEquals(emptySet, automatons.getNea1().getTargetsFor(TestAutomatons.Z5, A));
        Assert.assertEquals(emptySet, automatons.getNea1().getTargetsFor(TestAutomatons.Z5, B));
        Assert.assertEquals(z6,
                automatons.getNea1().getTargetsFor(TestAutomatons.Z5, Automaton.EPSILON));
    }

    @Test
    public void testGetTargetsOfNea1ForZ6() {
        Assert.assertEquals(emptySet, automatons.getNea1().getTargetsFor(TestAutomatons.Z6, A));
        Assert.assertEquals(emptySet, automatons.getNea1().getTargetsFor(TestAutomatons.Z6, B));
        Assert.assertEquals(emptySet,
                automatons.getNea1().getTargetsFor(TestAutomatons.Z6, Automaton.EPSILON));
    }

    @Test(expected = SerializationException.class)
    public void serializationShouldFailIfNoInitialStateIsSet() throws SerializationException {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);
        getAutomatonService().automatonToXml(sourceAutomaton);
    }

    @Test(expected = SerializationException.class)
    public void testSerializationOnlyWithType() throws Exception {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);

        assertSerializationYieldsSameAutomaton(sourceAutomaton);
    }

    @Test
    public void testSerializationWithAlphabet() throws Exception {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);
        sourceAutomaton.setInitialState(Q0);
        sourceAutomaton.addAlphabetItem(A);

        assertSerializationYieldsSameAutomaton(sourceAutomaton);
    }

    private void assertSerializationYieldsSameAutomaton(final Automaton sourceAutomaton)
            throws SerializationException {
        final Document document = getAutomatonService().automatonToXml(sourceAutomaton);
        final Automaton destination = getAutomatonService().automatonFrom(document);

        Assert.assertEquals(sourceAutomaton, destination);
    }

    @Test
    public void testSerializationWithStates() throws Exception {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);
        sourceAutomaton.setInitialState(Q0);
        sourceAutomaton.addState(Q1);

        assertSerializationYieldsSameAutomaton(sourceAutomaton);
    }

    @Test
    public void testSerializationWithInitialState() throws Exception {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);
        sourceAutomaton.setInitialState(Q0);

        assertSerializationYieldsSameAutomaton(sourceAutomaton);
    }

    @Test
    public void testSerializationWithFinalState() throws Exception {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);
        sourceAutomaton.setInitialState(Q0);
        sourceAutomaton.addState(Q1);
        sourceAutomaton.addFinalState(Q1);

        assertSerializationYieldsSameAutomaton(sourceAutomaton);
    }

    @Test
    public void testSerializationWithTransitions() throws Exception {
        final Automaton sourceAutomaton = new Automaton(AutomatonType.NEA);
        sourceAutomaton.addAlphabetItem(A);
        sourceAutomaton.setInitialState(Q0);
        sourceAutomaton.addState(Q1);
        sourceAutomaton.addFinalState(Q1);
        sourceAutomaton.addTransition(new Transition(Q0, Q1, A));

        assertSerializationYieldsSameAutomaton(sourceAutomaton);
    }

    @Test(expected = SerializationException.class)
    public void deserializationShouldFailForInvalidInput() throws XmlUtilsException,
            SerializationException {
        final Document document = getXmlService().documentFromFile(
                "automaton_with_invalid_type.xml");

        getAutomatonService().automatonFrom(document);
    }

    @Test
    public void testEpsilonHull() throws Exception {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setStates(getSetService().createSetWith(Q0, Q1, Q2, Q3, Q4, Q5));
        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);
        automaton.addTransition(new Transition(Q0, Q1, Automaton.EPSILON));
        automaton.addTransition(new Transition(Q0, Q3, A));
        automaton.addTransition(new Transition(Q1, Q2, B));
        automaton.addTransition(new Transition(Q1, Q4, A));
        automaton.addTransition(new Transition(Q3, Q1, Automaton.EPSILON));
        automaton.addTransition(new Transition(Q3, Q4, B));
        automaton.addTransition(new Transition(Q4, Q5, Automaton.EPSILON));

        final Set<String> expected1 = getSetService().createSetWith(Q0, Q1);
        Assert.assertEquals(expected1, getAutomatonService().getEpsilonHull(automaton, Q0));

        final Set<String> expected2 = getSetService().createSetWith(Q2);
        Assert.assertEquals(expected2,
                getAutomatonService().getEpsilonHull(automaton, getSetService().createSetWith(Q2)));
    }
}
