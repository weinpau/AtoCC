package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.XmlUtils;
import de.hszg.atocc.core.util.automata.AutomataUtils;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public final class AutomataUtilsTests {

    private static final int NUMBER_OF_STATES_IN_NEA1 = 6;
    private static final int NUMBER_OF_STATES_IN_NEA2 = 3;
    private static final int NUMBER_OF_STATES_IN_NEA3 = 2;

    private static Document nea1;
    private static Document nea2;
    private static Document nea3;

    private static Set<String> stateNamesOfNea1;
    private static Set<String> stateNamesOfNea2;
    private static Set<String> stateNamesOfNea3;

    private static Set<String> finalStatesOfNea1;
    private static Set<String> finalStatesOfNea2;
    private static Set<String> finalStatesOfNea3;

    private static String initialStateOfNea1;
    private static String initialStateOfNea2;
    private static String initialStateOfNea3;

    @BeforeClass
    public static void initialize() {
        initializeNea1();
        initializeNea2();
        initializeNea3();
    }

    @Test
    public void testGetStatesFrom() {
        Assert.assertEquals(NUMBER_OF_STATES_IN_NEA1, 
                AutomataUtils.getStatesFrom(nea1).getLength());
        Assert.assertEquals(NUMBER_OF_STATES_IN_NEA2, 
                AutomataUtils.getStatesFrom(nea2).getLength());
        Assert.assertEquals(NUMBER_OF_STATES_IN_NEA3, 
                AutomataUtils.getStatesFrom(nea3).getLength());
    }

    @Test
    public void testGetStateNamesFromDocument1() {
        final Set<String> actualNames = AutomataUtils.getStateNamesFrom(nea1);

        Assert.assertEquals(stateNamesOfNea1, actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument2() {
        final Set<String> actualNames = AutomataUtils.getStateNamesFrom(nea2);

        Assert.assertEquals(stateNamesOfNea2, actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument3() {
        final Set<String> actualNames = AutomataUtils.getStateNamesFrom(nea3);

        Assert.assertEquals(stateNamesOfNea3, actualNames);
    }

    @Test
    public void testGetStateNamesFromNodeList1() {
        final NodeList states = nea1.getElementsByTagName("STATE");

        Assert.assertEquals(stateNamesOfNea1, AutomataUtils.getStateNamesFrom(states));
    }

    @Test
    public void testGetStateNamesFromNodeList2() {
        final NodeList states = nea2.getElementsByTagName("STATE");

        Assert.assertEquals(stateNamesOfNea2, AutomataUtils.getStateNamesFrom(states));
    }

    @Test
    public void testGetStateNamesFromNodeList3() {
        final NodeList states = nea3.getElementsByTagName("STATE");

        Assert.assertEquals(stateNamesOfNea3, AutomataUtils.getStateNamesFrom(states));
    }

    @Test
    public void testGetNamesOfFinalStatesFrom1() {
        final Set<String> actualFinalStates = AutomataUtils.getNamesOfFinalStatesFrom(nea1);

        Assert.assertEquals(finalStatesOfNea1, actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom2() {
        final Set<String> actualFinalStates = AutomataUtils.getNamesOfFinalStatesFrom(nea2);

        Assert.assertEquals(finalStatesOfNea2, actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom3() {
        final Set<String> actualFinalStates = AutomataUtils.getNamesOfFinalStatesFrom(nea3);

        Assert.assertEquals(finalStatesOfNea3, actualFinalStates);
    }

    @Test
    public void testGetNameOfInitialStateFrom1() {
        final String actualInitialState = AutomataUtils.getNameOfInitialStateFrom(nea1);

        Assert.assertEquals(initialStateOfNea1, actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom2() {
        final String actualInitialState = AutomataUtils.getNameOfInitialStateFrom(nea2);

        Assert.assertEquals(initialStateOfNea2, actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom3() {
        final String actualInitialState = AutomataUtils.getNameOfInitialStateFrom(nea3);

        Assert.assertEquals(initialStateOfNea3, actualInitialState);
    }

    @Test
    public void testGetStatePowerSetFrom() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetAlphabetFrom() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetTargetsOf() {
        Assert.fail("Not yet implemented");
    }

    private static void initializeNea1() {
        nea1 = XmlUtils.documentFromFile("nea1.xml");

        initializeStateNamesOfNea1();

        finalStatesOfNea1 = new HashSet<String>();
        finalStatesOfNea1.add("Z3");

        initialStateOfNea1 = "Z1";
    }

    private static void initializeStateNamesOfNea1() {
        stateNamesOfNea1 = new HashSet<String>();
        stateNamesOfNea1.add("Z1");
        stateNamesOfNea1.add("Z2");
        stateNamesOfNea1.add("Z3");
        stateNamesOfNea1.add("Z4");
        stateNamesOfNea1.add("Z5");
        stateNamesOfNea1.add("Z6");
    }

    private static void initializeNea2() {
        nea2 = XmlUtils.documentFromFile("nea2.xml");

        initializeStateNamesOfNea2();

        finalStatesOfNea2 = new HashSet<String>();
        finalStatesOfNea2.add("q2");

        initialStateOfNea2 = "q0";
    }

    private static void initializeStateNamesOfNea2() {
        stateNamesOfNea2 = new HashSet<String>();
        stateNamesOfNea2.add("q0");
        stateNamesOfNea2.add("q1");
        stateNamesOfNea2.add("q2");
    }

    private static void initializeNea3() {
        nea3 = XmlUtils.documentFromFile("nea3.xml");

        initializeStateNamesOfNea3();

        finalStatesOfNea3 = new HashSet<String>();
        finalStatesOfNea3.add("q_1");

        initialStateOfNea3 = "q_0";
    }

    private static void initializeStateNamesOfNea3() {
        stateNamesOfNea3 = new HashSet<String>();
        stateNamesOfNea3.add("q_0");
        stateNamesOfNea3.add("q_1");
    }

}
