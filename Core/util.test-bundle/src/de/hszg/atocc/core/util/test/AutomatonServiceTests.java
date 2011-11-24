package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.XmlUtilsException;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NodeList;

public final class AutomatonServiceTests {

    private static AutomatonService automatonService;

    private TestAutomatons automatons;

    public static void setAutomatonService(AutomatonService service) {
        automatonService = service;
    }

    @Before
    public void setUp() throws XmlUtilsException {
        automatons = new TestAutomatons();
    }

    @Test
    public void testGetStatesFrom() {
        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_NEA1, automatonService
                .getStatesFrom(automatons.getNea1()).getLength());

        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_NEA2, automatonService
                .getStatesFrom(automatons.getNea2()).getLength());

        Assert.assertEquals(TestAutomatons.NUMBER_OF_STATES_IN_NEA3, automatonService
                .getStatesFrom(automatons.getNea3()).getLength());
    }

    @Test
    public void testGetStateNamesFromDocument1() {
        final Set<String> actualNames = automatonService.getStateNamesFrom(automatons.getNea1());

        Assert.assertEquals(automatons.getStateNamesOfNea1(), actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument2() {
        final Set<String> actualNames = automatonService.getStateNamesFrom(automatons.getNea2());

        Assert.assertEquals(automatons.getStateNamesOfNea2(), actualNames);
    }

    @Test
    public void testGetStateNamesFromDocument3() {
        final Set<String> actualNames = automatonService.getStateNamesFrom(automatons.getNea3());

        Assert.assertEquals(automatons.getStateNamesOfNea3(), actualNames);
    }

    @Test
    public void testGetStateNamesFromNodeList1() {
        final NodeList states = automatons.getNea1().getElementsByTagName(TestAutomatons.STATE);

        Assert.assertEquals(automatons.getStateNamesOfNea1(),
                automatonService.getStateNamesFrom(states));
    }

    @Test
    public void testGetStateNamesFromNodeList2() {
        final NodeList states = automatons.getNea2().getElementsByTagName(TestAutomatons.STATE);

        Assert.assertEquals(automatons.getStateNamesOfNea2(),
                automatonService.getStateNamesFrom(states));
    }

    @Test
    public void testGetStateNamesFromNodeList3() {
        final NodeList states = automatons.getNea3().getElementsByTagName(TestAutomatons.STATE);

        Assert.assertEquals(automatons.getStateNamesOfNea3(),
                automatonService.getStateNamesFrom(states));
    }

    @Test
    public void testGetNamesOfFinalStatesFrom1() {
        final Set<String> actualFinalStates =
                automatonService.getNamesOfFinalStatesFrom(automatons.getNea1());

        Assert.assertEquals(automatons.getFinalStatesOfNea1(), actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom2() {
        final Set<String> actualFinalStates =
                automatonService.getNamesOfFinalStatesFrom(automatons.getNea2());

        Assert.assertEquals(automatons.getFinalStatesOfNea2(), actualFinalStates);
    }

    @Test
    public void testGetNamesOfFinalStatesFrom3() {
        final Set<String> actualFinalStates =
                automatonService.getNamesOfFinalStatesFrom(automatons.getNea3());

        Assert.assertEquals(automatons.getFinalStatesOfNea3(), actualFinalStates);
    }

    @Test
    public void testGetNameOfInitialStateFrom1() {
        final String actualInitialState =
                automatonService.getNameOfInitialStateFrom(automatons.getNea1());

        Assert.assertEquals(automatons.getInitialStateOfNea1(), actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom2() {
        final String actualInitialState =
                automatonService.getNameOfInitialStateFrom(automatons.getNea2());

        Assert.assertEquals(automatons.getInitialStateOfNea2(), actualInitialState);
    }

    @Test
    public void testGetNameOfInitialStateFrom3() {
        final String actualInitialState =
                automatonService.getNameOfInitialStateFrom(automatons.getNea3());

        Assert.assertEquals(automatons.getInitialStateOfNea3(), actualInitialState);
    }

    @Test
    public void testGetStatePowerSetFrom2() {
        final Set<Set<String>> actualPowerset =
                automatonService.getStatePowerSetFrom(automatons.getNea2());

        Assert.assertEquals(automatons.getPowerSetOfStatesFromNea2(), actualPowerset);
    }

    @Test
    public void testGetStatePowerSetFrom3() {
        final Set<Set<String>> actualPowerset =
                automatonService.getStatePowerSetFrom(automatons.getNea3());

        Assert.assertEquals(automatons.getPowerSetOfStatesFromNea3(), actualPowerset);
    }

    @Test
    public void testGetAlphabetFrom1() {
        final Set<String> acutalAlphabet = automatonService.getAlphabetFrom(automatons.getNea1());

        Assert.assertEquals(automatons.getAlphabetOfNea1(), acutalAlphabet);
    }

    @Test
    public void testGetAlphabetFrom2() {
        final Set<String> acutalAlphabet = automatonService.getAlphabetFrom(automatons.getNea2());

        Assert.assertEquals(automatons.getAlphabetOfNea2(), acutalAlphabet);
    }

    @Test
    public void testGetAlphabetFrom3() {
        final Set<String> acutalAlphabet = automatonService.getAlphabetFrom(automatons.getNea3());

        Assert.assertEquals(automatons.getAlphabetOfNea3(), acutalAlphabet);
    }

    @Test
    public void testGetTargetsOf1() {
        final Set<String> emptySet = new HashSet<String>();

        final Set<String> z1 = new HashSet<String>();
        z1.add(TestAutomatons.Z1);

        final Set<String> z2 = new HashSet<String>();
        z2.add(TestAutomatons.Z2);

        final Set<String> z3 = new HashSet<String>();
        z3.add(TestAutomatons.Z3);

        final Set<String> z4 = new HashSet<String>();
        z4.add(TestAutomatons.Z4);

        final Set<String> z5 = new HashSet<String>();
        z5.add(TestAutomatons.Z5);

        final Set<String> z6 = new HashSet<String>();
        z6.add(TestAutomatons.Z6);

        Assert.assertEquals(z4,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z1, "a"));
        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z1, "b"));
        Assert.assertEquals(z2,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z1, "EPSILON"));

        Assert.assertEquals(z5,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z2, "a"));
        Assert.assertEquals(z3,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z2, "b"));
        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z2, "EPSILON"));

        Assert.assertEquals(new HashSet<String>(),
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z3, "a"));
        Assert.assertEquals(new HashSet<String>(),
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z3, "b"));
        Assert.assertEquals(new HashSet<String>(),
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z3, "EPSILON"));

        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z4, "a"));
        Assert.assertEquals(z5,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z4, "b"));
        Assert.assertEquals(z2,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z4, "EPSILON"));

        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z5, "a"));
        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z5, "b"));
        Assert.assertEquals(z6,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z5, "EPSILON"));

        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z6, "a"));
        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z6, "b"));
        Assert.assertEquals(emptySet,
                automatonService.getTargetsOf(automatons.getNea1(), TestAutomatons.Z6, "EPSILON"));
    }

}
