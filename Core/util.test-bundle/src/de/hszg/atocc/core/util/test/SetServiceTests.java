package de.hszg.atocc.core.util.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public final class SetServiceTests extends AbstractTestHelper {

    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int EIGHT = 8;
    private static final int NINE = 9;
    private static final int SEVENTEEN = 17;
    private static final int TWENTY_THREE = 23;

    @Test
    public void powerSetFromSetShouldContainOneElement() {
        final Set<String> input = new HashSet<>();

        final Set<Set<String>> output = getSetService().powerSetFrom(input);

        Assert.assertEquals(1, output.size());
    }

    @Test
    public void powerSetFromSetShouldContainEmptySet() {
        final Set<String> input = new HashSet<>();

        final Set<Set<String>> output = getSetService().powerSetFrom(input);

        Assert.assertTrue(output.contains(new HashSet<>()));
    }

    @Test
    public void powerSetFromThreeElements() {
        final Set<Integer> input = new HashSet<>();
        input.add(1);
        input.add(TWO);
        input.add(FIVE);

        final Set<Set<Integer>> output = getSetService().powerSetFrom(input);

        Assert.assertEquals(EIGHT, output.size());

        Assert.assertTrue(output.contains(new HashSet<>()));
        Assert.assertTrue(output.contains(getSetService().createSetWith(ONE)));
        Assert.assertTrue(output.contains(getSetService().createSetWith(TWO)));
        Assert.assertTrue(output.contains(getSetService().createSetWith(FIVE)));
        Assert.assertTrue(output.contains(getSetService().createSetWith(ONE, TWO)));
        Assert.assertTrue(output.contains(getSetService().createSetWith(ONE, FIVE)));
        Assert.assertTrue(output.contains(getSetService().createSetWith(TWO, FIVE)));
        Assert.assertTrue(output.contains(getSetService().createSetWith(ONE, TWO, FIVE)));
    }

    @Test
    public void testContainsAnyOfWithDistinctSets() {
        final Set<Integer> set1 = getSetService().createSetWith(ONE, TWO, FOUR);
        final Set<Integer> set2 = getSetService().createSetWith(FIVE, NINE);
        
        Assert.assertFalse(getSetService().containsAnyOf(set1, set2));
    }
    
    @Test
    public void testContainsAnyOf() {
        final Set<Integer> set1 = getSetService().createSetWith(ONE, TWO, FOUR);
        final Set<Integer> set2 = getSetService().createSetWith(FIVE, NINE, TWO);
        
        Assert.assertTrue(getSetService().containsAnyOf(set1, set2));
    }

    @Test
    public void testCreateSet() {
        final Set<Integer> set = getSetService().createSetWith(FIVE, NINE, TWENTY_THREE, SEVENTEEN);

        Assert.assertEquals(FOUR, set.size());
        Assert.assertTrue(set.contains(FIVE));
        Assert.assertTrue(set.contains(NINE));
        Assert.assertTrue(set.contains(TWENTY_THREE));
        Assert.assertTrue(set.contains(SEVENTEEN));
    }
}
