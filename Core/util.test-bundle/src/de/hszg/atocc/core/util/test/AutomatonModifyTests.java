package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidAlphabetCharacterException;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Collections;

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

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void addAlphabetItemShouldFailForNull() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem(null);
    }

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void addAlphabetItemShouldFailForEmptyString() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
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

}
