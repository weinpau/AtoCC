package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidAlphabetCharacterException;
import de.hszg.atocc.core.util.automaton.InvalidStateException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public final class AutomatonGetterAndSetterTests extends AbstractAutomatonTest {

    @Test
    public void testGetType() {
        final Automaton automaton1 = new Automaton(AutomatonType.DEA);
        Assert.assertEquals(AutomatonType.DEA, automaton1.getType());

        final Automaton automaton2 = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(AutomatonType.NEA, automaton2.getType());
    }

    @Test
    public void testGetAlphabet() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);

        Assert.assertEquals(getAlphabetAB(), automaton.getAlphabet());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAlphabetShouldReturnUnmodifiableSet() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addAlphabetItem(A);
        automaton.addAlphabetItem(B);

        final Set<String> alphabet = automaton.getAlphabet();
        alphabet.add(C);
    }

    @Test
    public void testSetAlphabet() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());

        automaton.setAlphabet(getAlphabetAB());

        Assert.assertEquals(getAlphabetAB(), automaton.getAlphabet());
    }

    @Test
    public void setAlphabetShouldCopyValues() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        Assert.assertEquals(Collections.emptySet(), automaton.getAlphabet());

        final Set<String> expected = new HashSet<String>();
        expected.addAll(getAlphabetAB());

        automaton.setAlphabet(expected);

        expected.add(C);

        Assert.assertFalse(automaton.getAlphabet().contains(C));
    }

    @Test
    public void setStatesShouldCopyValues() throws InvalidStateException {
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
        Assert.fail(NOT_YET_IMPLEMENTED);
    }

    @Test
    public void testGetTargetsFor() {
        Assert.fail(NOT_YET_IMPLEMENTED);
    }

    @Test
    public void testSetTransitions() {
        Assert.fail(NOT_YET_IMPLEMENTED);
    }
    
    @Test
    public void testGetInitialState() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialState(Q0);
        Assert.assertEquals(Q0, automaton.getInitialState());
    }

    @Test
    public void testSetInitialState() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);

        Assert.assertEquals("", automaton.getInitialState());

        automaton.setInitialState(Q0);

        Assert.assertEquals(Q0, automaton.getInitialState());
    }
    
    @Test(expected = InvalidStateException.class)
    public void setInitialStateShouldFailForNull() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialState(null);
    }
    
    @Test(expected = InvalidStateException.class)
    public void setInitialStateShouldFailForEmptyString() throws InvalidStateException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialState("");
    }

}
