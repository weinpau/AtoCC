package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public final class AutomatonTests {
    
    private static final String EPSILON = "EPSILON";
    
    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";
    
    private static final String Q0 = "q0";
    private static final String Q1 = "q1";
    private static final String Q2 = "q2";

    @Test
    public void constructorShouldSetType() {
        final Automaton automaton1 = new Automaton(AutomatonType.DEA);
        Assert.assertEquals(AutomatonType.DEA, automaton1.getType());

        final Automaton automaton2 = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(AutomatonType.NEA, automaton2.getType());
    }

    @Test
    public void testContainsEpsilonRules() throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q1);

        Assert.assertFalse(automaton.containsEpsilonRules());

        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
        Assert.assertTrue(automaton.containsEpsilonRules());
    }

    @Test
    public void testGetType() {
        final Automaton automaton1 = new Automaton(AutomatonType.DEA);
        Assert.assertEquals(AutomatonType.DEA, automaton1.getType());

        final Automaton automaton2 = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(AutomatonType.NEA, automaton2.getType());
    }

    @Test
    public void testGetAlphabet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);

        final Set<String> expected = new HashSet<String>();
        expected.add(A);
        expected.add(B);

        Assert.assertEquals(expected, automaton.getAlphabet());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAlphabetShouldReturnUnmodifiableSet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);

        final Set<String> expected = new HashSet<String>();
        expected.add(A);
        expected.add(B);

        final Set<String> alphabet = automaton.getAlphabet();
        alphabet.add(C);
    }

    @Test
    public void testAddAlphabetItem() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);

        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());

        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);

        final Set<String> expected = new HashSet<String>();
        expected.add(A);
        expected.add(B);

        Assert.assertEquals(expected, automaton.getAlphabet());
    }

    @Test
    public void testSetAlphabet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());
        
        final Set<String> expected = new HashSet<String>();
        expected.add(A);
        expected.add(B);
        
        automaton.setAlphabet(expected);
        
        Assert.assertEquals(expected, automaton.getAlphabet());
    }
    
    @Test
    public void setAlphabetShouldCopyValues() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());
        
        final Set<String> expected = new HashSet<String>();
        expected.add(A);
        expected.add(B);
        
        automaton.setAlphabet(expected);
        
        expected.add(C);
        
        Assert.assertFalse(automaton.getAlphabet().contains(C));
    }

    @Test
    public void testAddAndGetState() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        
        Assert.assertEquals(Collections.emptySet(), automaton.getStates());
        automaton.addState(Q0);
        automaton.addState(Q1);
        
        final Set<String> expected = new HashSet<String>();
        expected.add(Q0);
        expected.add(Q1);
        
        Assert.assertEquals(expected, automaton.getStates());
    }
    
    @Test
    public void addStateShouldNotFailForDuplicateState() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q0);
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getStatesShouldReturnUnmodifiableSet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        
        automaton.getStates().add("q3");
    }

    @Test
    public void testSetStates() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void setStatesShouldCopyValues() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(Collections.emptySet(), automaton.getStates());
        
        final Set<String> expected = new HashSet<String>();
        expected.add(Q0);
        expected.add(Q1);
        
        automaton.setStates(expected);
        
        expected.add(Q2);
        
        Assert.assertFalse(automaton.getStates().contains(Q2));
    }
    
    @Test
    public void testGetTransitionsFor() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetTargetsFor() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testSetTransitions() {
        Assert.fail("Not yet implemented");
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfSourceStateDoesNotExists()
        throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q1);
        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfTargetStateDoesNotExists()
        throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfAlphabetCharacterIsInvalid()
        throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q1);
        automaton.addTransition(new Transition(Q0, Q1, "invalid"));
    }
    
    @Test(expected = InvalidTransitionException.class)
    public void addSpontaniousTransitionShouldFailForDea() throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.addState(Q0);
        automaton.addState(Q1);
        automaton.addTransition(new Transition(Q0, Q1, "EPSILON"));
    }

    @Test
    public void testGetInitialState() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialState(Q0);
        Assert.assertEquals(Q0, automaton.getInitialState());
    }

    @Test
    public void testSetInitialState() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        
        Assert.assertEquals("", automaton.getInitialState());
        
        automaton.setInitialState(Q0);

        Assert.assertEquals(Q0, automaton.getInitialState());
    }

    @Test
    public void testAddAndGetFinalStates() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        
        Assert.assertEquals(Collections.emptySet(), automaton.getFinalStates());
        automaton.addState(Q0);
        automaton.addState(Q1);
        
        automaton.addFinalState(Q0);
        automaton.addFinalState(Q1);
        
        final Set<String> expected = new HashSet<String>();
        expected.add(Q0);
        expected.add(Q1);
        
        Assert.assertEquals(expected, automaton.getFinalStates());
    }
    
    @Test(expected = InvalidStateException.class)
    public void addFinalStateShouldFailIfStateDoesNotExist() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addFinalState(Q0);
    }

    @Test
    public void testEquals() {
        Assert.fail("Not yet implemented");
    }

}
