package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.StateNameHelper;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public final class StateNameHelperTests extends AbstractTestHelper {

    private static final String A = "a";
    private static final String B = "b";
    private static final String C = "c";

    private static final String Q = "q_";
    private static final String Q1 = "q_1";
    private static final String Q2 = "q_2";
    private static final String Q3 = "q_3";
    private static final String Q33 = "q_33";

    @Test
    public void commonPrefixForEmptySetShouldBeEmptyString() {
        final Set<String> states = getSetService().createSetWith();

        final String commonPrefix = StateNameHelper.findCommonPrefix(states);

        Assert.assertEquals("", commonPrefix);
    }

    @Test
    public void commonPrefixShouldBeEmptyIfOneElementIsEmptyString() {
        final Set<String> states = getSetService().createSetWith(Q1, "", Q2);

        final String commonPrefix = StateNameHelper.findCommonPrefix(states);

        Assert.assertEquals("", commonPrefix);
    }

    @Test
    public void commonPrefixShouldBeEmpty() {
        final Set<String> states = getSetService().createSetWith(A, B, C);

        final String commonPrefix = StateNameHelper.findCommonPrefix(states);

        Assert.assertEquals("", commonPrefix);
    }

    @Test
    public void testCommonPrefixForStringsOfEqualLength() {
        final Set<String> states = getSetService().createSetWith(Q1, Q2, Q3);

        final String commonPrefix = StateNameHelper.findCommonPrefix(states);

        Assert.assertEquals(Q, commonPrefix);
    }

    @Test
    public void testCommonPrefix() {
        final Set<String> states = getSetService().createSetWith(Q1, Q2, Q3, Q33);

        final String commonPrefix = StateNameHelper.findCommonPrefix(states);

        Assert.assertEquals(Q, commonPrefix);
    }

    @Test
    public void areStatesEnumerated1() {
        final Set<String> states = getSetService().createSetWith();

        Assert.assertFalse(StateNameHelper.areStatesEnumerated(states));
    }

    @Test
    public void areStatesEnumerated2() {
        final Set<String> states = getSetService().createSetWith(Q1, "", Q2);

        Assert.assertFalse(StateNameHelper.areStatesEnumerated(states));
    }

    @Test
    public void areStatesEnumerated3() {
        final Set<String> states = getSetService().createSetWith(A, B, C);

        Assert.assertFalse(StateNameHelper.areStatesEnumerated(states));
    }

    @Test
    public void areStatesEnumerated4() {
        final Set<String> states = getSetService().createSetWith(Q1, Q2, Q3);

        Assert.assertTrue(StateNameHelper.areStatesEnumerated(states));
    }

    @Test
    public void areStatesEnumerated5() {
        final Set<String> states = getSetService().createSetWith(Q1, Q2, Q3, Q33);

        Assert.assertTrue(StateNameHelper.areStatesEnumerated(states));
    }
    
    @Test(expected = RuntimeException.class)
    public void generateNextState1() {
        final Set<String> states = getSetService().createSetWith();

        StateNameHelper.generateNextState(states);
    }

    @Test(expected = RuntimeException.class)
    public void generateNextState2() {
        final Set<String> states = getSetService().createSetWith(Q1, "", Q2);

        StateNameHelper.generateNextState(states);
    }

    @Test(expected = RuntimeException.class)
    public void generateNextState3() {
        final Set<String> states = getSetService().createSetWith(A, B, C);

        StateNameHelper.generateNextState(states);
    }

    @Test
    public void generateNextState4() {
        final Set<String> states = getSetService().createSetWith(Q1, Q2, Q3);

        Assert.assertEquals("q_4", StateNameHelper.generateNextState(states));
    }

    @Test
    public void generateNextState5() {
        final Set<String> states = getSetService().createSetWith(Q1, Q2, Q3, Q33);

        Assert.assertEquals("q_34", StateNameHelper.generateNextState(states));
    }
}
