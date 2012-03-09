package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidAlphabetCharacterException;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.Transition;

import org.junit.Assert;
import org.junit.Test;

public final class AutomatonHashCodeAndEqualsTests extends AbstractAutomatonTest {

    @Test
    public void equalsShouldReturnTrueForEqualAutomatons() throws Exception {
        final Automaton a1 = createTestAutomatonPda();

        final Automaton a2 = createTestAutomatonPda();

        Assert.assertTrue(a1.equals(a2));
    }

    @Test
    public void hashCodeShouldBeTheSameForEqualAutomatons() throws Exception {
        final Automaton a1 = createTestAutomatonPda();
        final Automaton a2 = createTestAutomatonPda();

        Assert.assertTrue(a1.hashCode() == a2.hashCode());
    }

    @Test
    public void hashCodeShouldDifferForDifferentAutomatons() throws Exception {
        final Automaton a1 = createTestAutomatonNfa();

        final Automaton a2 = new Automaton(AutomatonType.NEA);
        a2.addState(Q0);
        a2.addState(Q1);
        a2.addState(Q2);
        a2.addFinalState(Q1);
        a2.addAlphabetItem(A);
        a2.setInitialState(Q0);
        a2.addTransition(new Transition(Q0, Q1, A));

        Assert.assertFalse(a1.hashCode() == a2.hashCode());
    }

    @Test
    public void equalsShouldReturnFalseIfOtherObjectIsNoAutomaton() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);

        Assert.assertFalse(automaton.equals(new Transition(Q0, Q1, A)));
    }

    @Test
    public void equalsShouldReturnFalseIfTypesAreNotEqual() {
        final Automaton a1 = new Automaton(AutomatonType.NEA);
        final Automaton a2 = new Automaton(AutomatonType.DEA);

        Assert.assertFalse(a1.equals(a2));
    }

    @Test
    public void equalsShouldReturnFalseIfAlphabetsAreNotEqual()
            throws InvalidAlphabetCharacterException {
        final Automaton a1 = new Automaton(AutomatonType.NEA);
        a1.addAlphabetItem(A);
        a1.addAlphabetItem(B);

        final Automaton a2 = new Automaton(AutomatonType.NEA);
        a2.addAlphabetItem(B);

        Assert.assertFalse(a1.equals(a2));
    }
    
    @Test
    public void equalsShouldReturnFalseIfInitialStatesAreNotEqual() throws InvalidStateException {
        final Automaton a1 = new Automaton(AutomatonType.NEA);
        a1.addState(Q0);
        a1.setInitialState(Q0);

        final Automaton a2 = new Automaton(AutomatonType.NEA);
        a2.addState(Q0);

        Assert.assertFalse(a1.equals(a2));
    }

    @Test
    public void equalsShouldReturnFalseIfFinalStatesAreNotEqual() throws InvalidStateException {
        final Automaton a1 = new Automaton(AutomatonType.NEA);
        a1.addState(Q0);
        a1.addState(Q1);
        a1.addFinalState(Q1);

        final Automaton a2 = new Automaton(AutomatonType.DEA);

        Assert.assertFalse(a1.equals(a2));
    }
    
    @Test
    public void equalsShouldReturnFalseIfStatesAreNotEqual() throws InvalidStateException {
        final Automaton a1 = new Automaton(AutomatonType.NEA);
        a1.addState(Q0);
        a1.addState(Q1);

        final Automaton a2 = new Automaton(AutomatonType.NEA);
        a2.addState(Q0);

        Assert.assertFalse(a1.equals(a2));
    }

    @Test
    public void equalsShouldFailIfTransitionsAreNotEqual() throws Exception {
        final Automaton a1 = new Automaton(AutomatonType.NEA);
        a1.addState(Q0);
        a1.addState(Q1);
        a1.addAlphabetItem(A);
        a1.addTransition(new Transition(Q0, Q1, A));

        final Automaton a2 = new Automaton(AutomatonType.NEA);

        Assert.assertFalse(a1.equals(a2));
    }

}
