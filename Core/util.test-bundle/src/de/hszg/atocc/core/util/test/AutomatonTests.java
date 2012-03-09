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

public final class AutomatonTests extends AbstractAutomatonTest {

    @Test
    public void constructorShouldSetType() {
        final Automaton automaton1 = new Automaton(AutomatonType.DEA);
        Assert.assertEquals(AutomatonType.DEA, automaton1.getType());

        final Automaton automaton2 = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(AutomatonType.NEA, automaton2.getType());

        final Automaton automaton3 = new Automaton(AutomatonType.NKA);
        Assert.assertEquals(AutomatonType.NKA, automaton3.getType());
    }

    @Test
    public void containsEpsilonRules() throws InvalidTransitionException, InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q1);

        Assert.assertFalse(automaton.containsEpsilonRules());

        automaton.addTransition(new Transition(Q0, Q1, EPSILON));
        Assert.assertTrue(automaton.containsEpsilonRules());
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

    @Test
    public void testAddAndGetState() throws InvalidStateException {
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
    public void testToString() throws Exception {
        final Automaton automaton = createTestAutomatonNfa();

        Assert.assertEquals("NEA = ([q0, q1], [a], {q1=[], q0=[(q0, a) = q1]}, q0, [q1])",
                automaton.toString());
    }

    // CHECKSTYLE:OFF
    @Test
    public void testToStringForPda() throws Exception {
        final Automaton automaton = createTestAutomatonPda();

        Assert.assertEquals(
                "NKA = ([q0, q1], [a], [a], {q1=[], q0=[(q0, a, EPSILON) = (q1, a)]}, q0, a, [q1])",
                automaton.toString());
    }
    // CHECKSTYLE:ON

}
