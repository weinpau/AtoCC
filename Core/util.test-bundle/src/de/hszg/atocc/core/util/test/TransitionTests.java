package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Transition;

import org.junit.Assert;
import org.junit.Test;

public final class TransitionTests {

    private static final String Q2 = "q2";
    private static final String A = "a";
    private static final String Q1 = "q1";
    private static final String Q0 = "q0";

    @Test
    public void testConstructor() {
        final Transition transition = new Transition(Q0, Q1, A);

        Assert.assertEquals(Q0, transition.getSource());
        Assert.assertEquals(Q1, transition.getTarget());
        Assert.assertEquals(A, transition.getCharacterToRead());
    }

    @Test
    public void testToString() {
        final Transition transition = new Transition(Q0, Q1, A);

        Assert.assertEquals("(q0, a) = q1", transition.toString());
    }

    @Test
    public void equalsShouldReturnTrueIfAllPropertiesAreEqual() {
        final Transition t1 = new Transition(Q0, Q1, A);
        final Transition t2 = new Transition(Q0, Q1, A);

        Assert.assertTrue(t1.equals(t2));
    }

    @Test
    public void equalsShouldReturnFalsIsSourceStatesAreNotEqual() {
        final Transition t1 = new Transition(Q0, Q1, A);
        final Transition t2 = new Transition(Q2, Q1, A);

        Assert.assertFalse(t1.equals(t2));
    }

    @Test
    public void equalsShouldReturnFalsIsTargetStatesAreNotEqual() {
        final Transition t1 = new Transition(Q0, Q1, A);
        final Transition t2 = new Transition(Q0, Q2, A);

        Assert.assertFalse(t1.equals(t2));
    }

    @Test
    public void equalsShouldReturnFalsIsCharactersToReadAreNotEqual() {
        final Transition t1 = new Transition(Q0, Q1, A);
        final Transition t2 = new Transition(Q0, Q1, "b");

        Assert.assertFalse(t1.equals(t2));
    }

    @Test
    public void equalsShouldReturnFalseIfOtherObjectIsNoTransition() {
        final Transition transition = new Transition(Q0, Q1, A);

        Assert.assertFalse(transition.equals(0));
    }

}
