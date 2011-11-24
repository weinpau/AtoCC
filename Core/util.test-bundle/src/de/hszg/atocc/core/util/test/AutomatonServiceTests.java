package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class AutomatonServiceTests {

    private static final String Z1 = "Z1";
    private static final String Z3 = "Z3";

    private static final String STATE = "STATE";

    private static final String Q0 = "q0";
    private static final String Q2 = "q2";

    private static final String Q_0 = "q_0";
    private static final String Q_1 = "q_1";

    private static final int NUMBER_OF_STATES_IN_NEA1 = 6;
    private static final int NUMBER_OF_STATES_IN_NEA2 = 3;
    private static final int NUMBER_OF_STATES_IN_NEA3 = 2;

    private Document nea1;
    private Document nea2;
    private Document nea3;

    private Set<String> stateNamesOfNea1;
    private Set<String> stateNamesOfNea2;
    private Set<String> stateNamesOfNea3;

    private Set<String> finalStatesOfNea1;
    private Set<String> finalStatesOfNea2;
    private Set<String> finalStatesOfNea3;

    private String initialStateOfNea1;
    private String initialStateOfNea2;
    private String initialStateOfNea3;

    public static AutomatonService automatonService;
    public static XmlUtilService xmlService;

    @Before
    public void setUp() throws XmlUtilsException {
        initializeNea1();
        initializeNea2();
        initializeNea3();
    }

    @Test
    public void testGetStatesFrom() {
        Assert.assertEquals(NUMBER_OF_STATES_IN_NEA1, automatonService.getStatesFrom(nea1)
                .getLength());
        Assert.assertEquals(NUMBER_OF_STATES_IN_NEA2, automatonService.getStatesFrom(nea2)
                .getLength());
        Assert.assertEquals(NUMBER_OF_STATES_IN_NEA3, automatonService.getStatesFrom(nea3)
                .getLength());
    }

    @Test
    public void testGetStateNamesFromDocument1() {
        final Set<String> actualNames = automatonService.getStateNamesFrom(nea1);

        Assert.assertEquals(stateNamesOfNea1, actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument2() {
        final Set<String> actualNames = automatonService.getStateNamesFrom(nea2);

        Assert.assertEquals(stateNamesOfNea2, actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument3() {
        final Set<String> actualNames = automatonService.getStateNamesFrom(nea3);

        Assert.assertEquals(stateNamesOfNea3, actualNames);
    }

    @Test
    public void testGetStateNamesFromNodeList1() {
        final NodeList states = nea1.getElementsByTagName(STATE);

        Assert.assertEquals(stateNamesOfNea1, automatonService.getStateNamesFrom(states));
    }

    @Test
    public void testGetStateNamesFromNodeList2() {
        final NodeList states = nea2.getElementsByTagName(STATE);

        Assert.assertEquals(stateNamesOfNea2, automatonService.getStateNamesFrom(states));
    }

    @Test
    public void testGetStateNamesFromNodeList3() {
        final NodeList states = nea3.getElementsByTagName(STATE);

        Assert.assertEquals(stateNamesOfNea3, automatonService.getStateNamesFrom(states));
    }

    @Test
    public void testGetNamesOfFinalStatesFrom1() {
        final Set<String> actualFinalStates = automatonService.getNamesOfFinalStatesFrom(nea1);

        Assert.assertEquals(finalStatesOfNea1, actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom2() {
        final Set<String> actualFinalStates = automatonService.getNamesOfFinalStatesFrom(nea2);

        Assert.assertEquals(finalStatesOfNea2, actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom3() {
        final Set<String> actualFinalStates = automatonService.getNamesOfFinalStatesFrom(nea3);

        Assert.assertEquals(finalStatesOfNea3, actualFinalStates);
    }

    @Test
    public void testGetNameOfInitialStateFrom1() {
        final String actualInitialState = automatonService.getNameOfInitialStateFrom(nea1);

        Assert.assertEquals(initialStateOfNea1, actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom2() {
        final String actualInitialState = automatonService.getNameOfInitialStateFrom(nea2);

        Assert.assertEquals(initialStateOfNea2, actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom3() {
        final String actualInitialState = automatonService.getNameOfInitialStateFrom(nea3);

        Assert.assertEquals(initialStateOfNea3, actualInitialState);
    }

    @Test
    public void testGetStatePowerSetFrom2() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetStatePowerSetFrom3() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetAlphabetFrom1() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetAlphabetFrom2() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetAlphabetFrom3() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetTargetsOf1() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetTargetsOf2() {
        Assert.fail("Not yet implemented");
    }

    @Test
    public void testGetTargetsOf3() {
        Assert.fail("Not yet implemented");
    }

    private void initializeNea1() throws XmlUtilsException {
        nea1 = xmlService.documentFromFile("nea1.xml");

        initializeStateNamesOfNea1();

        finalStatesOfNea1 = new HashSet<String>();
        finalStatesOfNea1.add(Z3);

        initialStateOfNea1 = Z1;
    }

    private void initializeStateNamesOfNea1() {
        stateNamesOfNea1 = new HashSet<String>();
        stateNamesOfNea1.add(Z1);
        stateNamesOfNea1.add("Z2");
        stateNamesOfNea1.add(Z3);
        stateNamesOfNea1.add("Z4");
        stateNamesOfNea1.add("Z5");
        stateNamesOfNea1.add("Z6");
    }

    private void initializeNea2() throws XmlUtilsException {
        nea2 = xmlService.documentFromFile("nea2.xml");

        initializeStateNamesOfNea2();

        finalStatesOfNea2 = new HashSet<String>();
        finalStatesOfNea2.add(Q2);

        initialStateOfNea2 = Q0;
    }

    private void initializeStateNamesOfNea2() {
        stateNamesOfNea2 = new HashSet<String>();
        stateNamesOfNea2.add(Q0);
        stateNamesOfNea2.add("q1");
        stateNamesOfNea2.add(Q2);
    }

    private void initializeNea3() throws XmlUtilsException {
        nea3 = xmlService.documentFromFile("nea3.xml");

        initializeStateNamesOfNea3();

        finalStatesOfNea3 = new HashSet<String>();
        finalStatesOfNea3.add(Q_1);

        initialStateOfNea3 = Q_0;
    }

    private void initializeStateNamesOfNea3() {
        stateNamesOfNea3 = new HashSet<String>();
        stateNamesOfNea3.add(Q_0);
        stateNamesOfNea3.add(Q_1);
    }

}
