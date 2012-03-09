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

        final Automaton automaton3 = new Automaton(AutomatonType.NKA);
        Assert.assertEquals(AutomatonType.NKA, automaton3.getType());
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

    @Test(expected = UnsupportedOperationException.class)
    public void getStackAlphabetShouldFailForNFA() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.getStackAlphabet();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getStackAlphabetShouldFailForDFA() {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.getStackAlphabet();
    }

    @Test
    public void testGetStackAlphabet() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        automaton.addStackAlphabetItem(A);
        automaton.addStackAlphabetItem(B);

        Assert.assertEquals(getAlphabetAB(), automaton.getStackAlphabet());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getStackAlphabetShouldReturnUnmodifiableSet()
            throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        automaton.addStackAlphabetItem(A);
        automaton.addStackAlphabetItem(B);

        final Set<String> alphabet = automaton.getStackAlphabet();
        alphabet.add(C);
    }

    @Test
    public void testSetStackAlphabet() {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        Assert.assertEquals(Collections.emptySet(), automaton.getStackAlphabet());

        automaton.setStackAlphabet(getAlphabetAB());

        Assert.assertEquals(getAlphabetAB(), automaton.getStackAlphabet());
    }

    @Test
    public void setStackAlphabetShouldCopyValues() {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        Assert.assertEquals(Collections.emptySet(), automaton.getStackAlphabet());

        final Set<String> expected = new HashSet<String>();
        expected.addAll(getAlphabetAB());

        automaton.setStackAlphabet(expected);

        expected.add(C);

        Assert.assertFalse(automaton.getStackAlphabet().contains(C));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setStackAlphabetShouldFailForNFA() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setStackAlphabet(getAlphabetAB());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setStackAlphabetShouldFailForDFA() {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.setStackAlphabet(getAlphabetAB());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getInitialStackSymbolShouldFailForNFA() {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.getInitialStackSymbol();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getInitialStackSymbolShouldFailForDFA() {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.getInitialStackSymbol();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setInitialStackSymbolShouldFailForNFA() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.setInitialStackSymbol(A);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setInitialStackSymbolShouldFailForDFA() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.DEA);
        automaton.setInitialStackSymbol(A);
    }

    @Test(expected = InvalidAlphabetCharacterException.class)
    public void setInitialStackSymbolShouldFailIfSymbolIsNotInStackAlphabet()
            throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);

        Assert.assertFalse(automaton.getStackAlphabet().contains(A));
        automaton.setInitialStackSymbol(A);
    }

    @Test
    public void testGetInitialStackSymbol() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        automaton.addStackAlphabetItem(A);
        automaton.setInitialStackSymbol(A);
        Assert.assertEquals(A, automaton.getInitialStackSymbol());
    }

    @Test
    public void testSetInitialStackSymbol() throws InvalidAlphabetCharacterException {
        final Automaton automaton = new Automaton(AutomatonType.NKA);

        Assert.assertEquals("", automaton.getInitialStackSymbol());
        automaton.addStackAlphabetItem(A);
        automaton.setInitialStackSymbol(A);

        Assert.assertEquals(A, automaton.getInitialStackSymbol());
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
