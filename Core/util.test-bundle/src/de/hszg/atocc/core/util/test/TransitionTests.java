package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Transition;

import org.junit.Assert;
import org.junit.Test;

public final class TransitionTests {

    @Test
    public void testConstructor() {
        Transition transition = new Transition("q0", "q1", "a");
        
        Assert.assertEquals("q0", transition.getSource());
        Assert.assertEquals("q1", transition.getTarget());
        Assert.assertEquals("a", transition.getCharacterToRead());
    }

    @Test
    public void testToString() {
        Transition transition = new Transition("q0", "q1", "a");
        
        Assert.assertEquals("(q0, a) = q1", transition.toString());
    }

    @Test
    public void equalsShouldReturnTrueIfAllPropertiesAreEqual() {
        Transition t1 = new Transition("q0", "q1", "a");
        Transition t2 = new Transition("q0", "q1", "a");
        
        Assert.assertTrue(t1.equals(t2));
    }
    
    @Test
    public void equalsShouldReturnFalsIsSourceStatesAreNotEqual() {
        Transition t1 = new Transition("q0", "q1", "a");
        Transition t2 = new Transition("q2", "q1", "a");
        
        Assert.assertFalse(t1.equals(t2));
    }
    
    @Test
    public void equalsShouldReturnFalsIsTargetStatesAreNotEqual() {
        Transition t1 = new Transition("q0", "q1", "a");
        Transition t2 = new Transition("q0", "q2", "a");
        
        Assert.assertFalse(t1.equals(t2));
    }
    
    @Test
    public void equalsShouldReturnFalsIsCharactersToReadAreNotEqual() {
        Transition t1 = new Transition("q0", "q1", "a");
        Transition t2 = new Transition("q0", "q1", "b");
        
        Assert.assertFalse(t1.equals(t2));
    }
    
    @Test
    public void equalsShouldReturnFalseIfOtherObjectIsNoTransition() {
        Transition transition = new Transition("q0", "q1", "a");
        
        Assert.assertFalse(transition.equals("other"));
    }

}
