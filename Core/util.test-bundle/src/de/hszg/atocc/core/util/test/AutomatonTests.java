package de.hszg.atocc.core.util.test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import org.junit.Assert;
import org.junit.Test;

public final class AutomatonTests {

    @Test
    public void constructorShouldSetType() {
        final Automaton automaton1 = new Automaton(AutomatonType.DEA);
        Assert.assertEquals(AutomatonType.DEA, automaton1.getType());

        final Automaton automaton2 = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(AutomatonType.NEA, automaton2.getType());
    }

    @Test
    public void testContainsEpsilonRules() throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.addState("q0");
        automaton.addState("q1");
        
        Assert.assertFalse(automaton.containsEpsilonRules());

        automaton.addTransition(new Transition("q0", "q1", "EPSILON"));
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
        automaton.addAlphabetItem("a");
        automaton.addAlphabetItem("b");
        
        final Set<String> expected = new HashSet<String>();
        expected.add("a");
        expected.add("b");
        
        Assert.assertEquals(expected, automaton.getAlphabet());
    }
    
    @Test(expected = UnsupportedOperationException.class)
    public void getAlphabetShouldReturnUnmodifiableSet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem("a");
        automaton.addAlphabetItem("b");
        
        final Set<String> expected = new HashSet<String>();
        expected.add("a");
        expected.add("b");
        
        final Set<String> alphabet = automaton.getAlphabet();
        alphabet.add("c");
    }

    @Test
    public void testAddAlphabetItem() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        
        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());
        
        automaton.addAlphabetItem("a");
        automaton.addAlphabetItem("b");
        
        final Set<String> expected = new HashSet<String>();
        expected.add("a");
        expected.add("b");
        
        Assert.assertEquals(expected, automaton.getAlphabet());
    }

    @Test
    public void testSetAlphabet() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetStates() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testAddState() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testSetStates() {
        Assert.fail("Not yet implemented");
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
        automaton.addState("q1");
        automaton.addTransition(new Transition("q0", "q1", "EPSILON"));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfTargetStateDoesNotExists()
        throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState("q0");
        automaton.addTransition(new Transition("q0", "q1", "EPSILON"));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfAlphabetCharacterIsInvalid()
        throws InvalidTransitionException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState("q0");
        automaton.addState("q1");
        automaton.addTransition(new Transition("q0", "q1", "invalid"));
    }

    @Test
    public void testGetInitialState() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialState("q0");
        Assert.assertEquals("q0", automaton.getInitialState());
    }

    @Test
    public void testSetInitialState() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialState("q0");
        
        Assert.assertEquals("", automaton.getInitialState());
        
        Assert.assertEquals("q0", automaton.getInitialState());
    }

    @Test
    public void testGetFinalStates() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testAddFinalState() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testEqualsObject() {
        Assert.fail("Not yet implemented");
    }

}
