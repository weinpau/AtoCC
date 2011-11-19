package de.hszigr.atocc.util.test;

import de.hszigr.atocc.util.automata.PowerSet;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PowerSetTest {

    private static final String Q1 = "q1";
    private static final String Q2 = "q2";
    private static final String Q3 = "q3";

    private Set<String> states;

    @Before
    public final void setUp() {
        states = new HashSet<String>();
        states.add(Q1);
        states.add(Q2);
        states.add(Q3);
    }

    @Test
    public final void testPowerSetHasCorrectNumberOfElements() {
        final Set<Set<String>> powerset = PowerSet.from(states);

        Assert.assertEquals((int) Math.pow(2, states.size()), powerset.size());
    }

    @Test
    public final void testPowerSetContainsCorrectElements() {
        final Set<Set<String>> powerset = PowerSet.from(states);

        Assert.assertTrue(powerset.contains(new HashSet<String>()));

        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q1})));
        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q2})));
        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q3})));

        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q1, Q2})));
        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q1, Q3})));
        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q2, Q3})));

        Assert.assertTrue(powerset.contains(createSetFrom(new String[] {Q1, Q2, Q3})));
    }

    private Set<String> createSetFrom(final String[] strings) {
        final Set<String> set = new HashSet<String>();

        for (String s : strings) {
            set.add(s);
        }

        return set;
    }

}
