package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

public final class TestAutomatons {

    public static final int NUMBER_OF_STATES_IN_NEA1 = 6;
    public static final int NUMBER_OF_STATES_IN_NEA2 = 3;
    public static final int NUMBER_OF_STATES_IN_NEA3 = 2;
    
    public static final String STATE = "STATE";
    
    private static XmlUtilService xmlService;
    
    private static final String Z1 = "Z1";
    private static final String Z3 = "Z3";

    private static final String Q0 = "q0";
    private static final String Q2 = "q2";

    private static final String Q_0 = "q_0";
    private static final String Q_1 = "q_1";

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

    public TestAutomatons() throws XmlUtilsException {
        initializeNea1();
        initializeNea2();
        initializeNea3();
    }
    
    public static void setXmlService(XmlUtilService service) {
        xmlService = service;
    }

    public Document getNea1() {
        return nea1;
    }

    public Document getNea2() {
        return nea2;
    }

    public Document getNea3() {
        return nea3;
    }
    
    public Set<String> getStateNamesOfNea1() {
        return stateNamesOfNea1;
    }

    public Set<String> getStateNamesOfNea2() {
        return stateNamesOfNea2;
    }

    public Set<String> getStateNamesOfNea3() {
        return stateNamesOfNea3;
    }

    public Set<String> getFinalStatesOfNea1() {
        return finalStatesOfNea1;
    }

    public Set<String> getFinalStatesOfNea2() {
        return finalStatesOfNea2;
    }

    public Set<String> getFinalStatesOfNea3() {
        return finalStatesOfNea3;
    }

    public String getInitialStateOfNea1() {
        return initialStateOfNea1;
    }

    public String getInitialStateOfNea2() {
        return initialStateOfNea2;
    }

    public String getInitialStateOfNea3() {
        return initialStateOfNea3;
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
