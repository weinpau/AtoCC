package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidAlphabetCharacterException;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Collections;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public final class AutomatonModifyTests extends AbstractAutomatonTest {

    @Test
    public void addStateShouldNotFailForDuplicateState() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q0);
    }

    @Test(expected = InvalidStateException.class)
    public void addStateShouldFailForNullState() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(null);
    }

    @Test
    public void testAddAlphabetItem() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);

        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());

        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);

        Assert.assertEquals(getAlphabetAB(), automaton.getAlphabet());
    }

    @Test
    public void testAddStackAlphabetItem() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);

        Assert.assertEquals(Collections.emptySet(), automaton.getStackAlphabet());

        automaton.addStackAlphabetItem(A);
        automaton.addStackAlphabetItem(B);

        Assert.assertEquals(getAlphabetAB(), automaton.getStackAlphabet());
    }

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void addAlphabetItemShouldFailForNull() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem(null);
    }

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void addStackAlphabetItemShouldFailForNull() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        automaton.addStackAlphabetItem(null);
    }

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void addAlphabetItemShouldFailForEmptyString() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem("");
    }

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void addStackAlphabetItemShouldFailForEmptyString()
            throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        automaton.addAlphabetItem("");
    }

    @Test(expected = InvalidStateException.class)
    public void addStateShouldFailForEmptyString() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getStatesShouldReturnUnmodifiableSet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);

        automaton.getStates().add("q3");
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfSourceStateDoesNotExists()
            throws InvalidTransitionException, InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q1);
        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfTargetStateDoesNotExists()
            throws InvalidTransitionException, InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addTransitionShouldFailIfAlphabetCharacterIsInvalid()
            throws InvalidTransitionException, InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q1);
        automaton.addTransition(new Transition(Q0, Q1, "invalid"));
    }

    @Test(expected = InvalidTransitionException.class)
    public void addSpontaniousTransitionShouldFailForDea() throws InvalidTransitionException,
            InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.addState(Q0);
        automaton.addState(Q1);
        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
    }

    @Test(expected = InvalidStateException.class)
    public void addFinalStateShouldFailIfStateDoesNotExist() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addFinalState(Q0);
    }

    @Test
    public void removeStateShouldRemoveStateFromStates() throws Exception {
        final Automaton automaton = createTestAutomatonNfa();

        Assert.assertTrue(automaton.getStates().contains(Q0));
        automaton.removeState(Q0);
        Assert.assertFalse(automaton.getStates().contains(Q0));
    }

    @Test
    public void removeStateShouldDeleteTransitionsFromState() throws Exception {
        final Automaton automaton = createTestAutomatonNfa();

        final Set<Transition> transitions1 = automaton.getTransitionsFrom(Q0);
        Assert.assertFalse(transitions1.isEmpty());

        automaton.removeState(Q0);

        final Set<Transition> transitions2 = automaton.getTransitionsFrom(Q0);
        Assert.assertTrue(transitions2.isEmpty());
    }

    @Test
    public void removeStateShouldDeleteTransitionsToState() throws Exception {
        final Automaton automaton = createTestAutomatonNfa();

        final Set<Transition> transitions1 = automaton.getTransitionsTo(Q1);
        Assert.assertFalse(transitions1.isEmpty());

        automaton.removeState(Q1);

        final Set<Transition> transitions2 = automaton.getTransitionsTo(Q1);
        Assert.assertTrue(transitions2.isEmpty());
    }

    @Test
    public void removeStateShouldRemoveStateFromFinalStates() throws Exception {
        final Automaton automaton = createTestAutomatonNfa();

        Assert.assertTrue(automaton.getFinalStates().contains(Q1));

        automaton.removeState(Q1);

        Assert.assertFalse(automaton.getFinalStates().contains(Q1));
    }

    @Test
    public void removeStateShouldUnsetInitialState() throws Exception {
        final Automaton automaton = createTestAutomatonNfa();

        Assert.assertTrue(automaton.getInitialState().equals(Q0));

        automaton.removeState(Q0);

        Assert.assertFalse(automaton.getInitialState().equals(Q0));
    }
}
