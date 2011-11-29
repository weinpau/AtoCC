package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlUtilsException;
import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;

public final class TestAutomatons {

    public static final int NUMBER_OF_STATES_IN_NEA1 = 6;
    public static final int NUMBER_OF_STATES_IN_NEA2 = 3;
    public static final int NUMBER_OF_STATES_IN_NEA3 = 2;
    
    public static final String STATE = "STATE";
    
    public static final String Z1 = "Z1";
    public static final String Z2 = "Z2";
    public static final String Z3 = "Z3";
    public static final String Z4 = "Z4";
    public static final String Z5 = "Z5";
    public static final String Z6 = "Z6";
    
    private static final String B = "b";
    private static final String A = "a";
    private static final String C = "c";
    
    private static XmlUtilService xmlService;
    private static AutomatonService automatonService;

    private static final String Q0 = "q0";
    private static final String Q1 = "q1";
    private static final String Q2 = "q2";

    private static final String Q_0 = "q_0";
    private static final String Q_1 = "q_1";

    private Automaton nea1;
    private Automaton nea2;
    private Automaton nea3;

    private Set<String> stateNamesOfNea1;
    private Set<String> stateNamesOfNea2;
    private Set<String> stateNamesOfNea3;

    private Set<String> finalStatesOfNea1;
    private Set<String> finalStatesOfNea2;
    private Set<String> finalStatesOfNea3;

    private String initialStateOfNea1;
    private String initialStateOfNea2;
    private String initialStateOfNea3;
    
    private Set<Set<String>> powerSetOfStatesFromNea2;
    private Set<Set<String>> powerSetOfStatesFromNea3;
    
    private Set<String> alphabetOfNea1;
    private Set<String> alphabetOfNea2;
    private Set<String> alphabetOfNea3;

    public TestAutomatons() throws XmlUtilsException {
        initializeNea1();
        initializeNea2();
        initializeNea3();
    }
    
    public static void setXmlService(XmlUtilService service) {
        xmlService = service;
    }
    
    public static void setAutomatonService(AutomatonService service) {
        automatonService = service;
    }

    public Automaton getNea1() {
        return nea1;
    }

    public Automaton getNea2() {
        return nea2;
    }

    public Automaton getNea3() {
        return nea3;
    }
    
    public Set<Set<String>> getPowerSetOfStatesFromNea2() {
        return powerSetOfStatesFromNea2;
    }

    public Set<Set<String>> getPowerSetOfStatesFromNea3() {
        return powerSetOfStatesFromNea3;
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
        final Document doc = xmlService.documentFromFile("nea1.xml");
        nea1 = automatonService.automatonFrom(doc);

        initializeStateNamesOfNea1();

        finalStatesOfNea1 = new HashSet<String>();
        finalStatesOfNea1.add(Z3);

        initialStateOfNea1 = Z1;
        
        alphabetOfNea1 = new HashSet<String>();
        alphabetOfNea1.add(A);
        alphabetOfNea1.add(B);
    }

    private void initializeStateNamesOfNea1() {
        stateNamesOfNea1 = new HashSet<String>();
        stateNamesOfNea1.add(Z1);
        stateNamesOfNea1.add(Z2);
        stateNamesOfNea1.add(Z3);
        stateNamesOfNea1.add(Z4);
        stateNamesOfNea1.add(Z5);
        stateNamesOfNea1.add(Z6);
    }

    private void initializeNea2() throws XmlUtilsException {
        final Document doc = xmlService.documentFromFile("nea2.xml");
        nea2 = automatonService.automatonFrom(doc);

        initializeStateNamesOfNea2();

        finalStatesOfNea2 = new HashSet<String>();
        finalStatesOfNea2.add(Q2);

        initialStateOfNea2 = Q0;
        
        initializeStatePowerSetOfNea2();
        initializeAlphabetOfNea2();
    }

    private void initializeAlphabetOfNea2() {
        alphabetOfNea2 = new HashSet<String>();
        alphabetOfNea2.add(A);
        alphabetOfNea2.add(B);
        alphabetOfNea2.add(C);
    }

    private void initializeStatePowerSetOfNea2() {
        powerSetOfStatesFromNea2 = new HashSet<Set<String>>();
        
        powerSetElementsWithoutElementsOfNea2();
        createPowerSetElementsWithOneElementofNea2();
        createPowerSetElementsWithTwoElementsOfNea2();
        createPowerSetElementsWithThreeElementsOfNea2();
    }

    private void createPowerSetElementsWithThreeElementsOfNea2() {
        final Set<String> q0q1q2Set = new HashSet<String>();
        q0q1q2Set.add(Q0);
        q0q1q2Set.add(Q1);
        q0q1q2Set.add(Q2);
        
        powerSetOfStatesFromNea2.add(q0q1q2Set);
    }

    private void createPowerSetElementsWithTwoElementsOfNea2() {
        final Set<String> q0q1Set = new HashSet<String>();
        q0q1Set.add(Q0);
        q0q1Set.add(Q1);
        
        final Set<String> q0q2Set = new HashSet<String>();
        q0q2Set.add(Q0);
        q0q2Set.add(Q2);
        
        final Set<String> q1q2Set = new HashSet<String>();
        q1q2Set.add(Q1);
        q1q2Set.add(Q2);
        
        powerSetOfStatesFromNea2.add(q0q1Set);
        powerSetOfStatesFromNea2.add(q0q2Set);
        powerSetOfStatesFromNea2.add(q1q2Set);
    }

    private void createPowerSetElementsWithOneElementofNea2() {
        final Set<String> q0Set = new HashSet<String>();
        q0Set.add(Q0);
        final Set<String> q1Set = new HashSet<String>();
        q1Set.add(Q1);
        final Set<String> q2Set = new HashSet<String>();
        q2Set.add(Q2);
        
        powerSetOfStatesFromNea2.add(q0Set);
        powerSetOfStatesFromNea2.add(q1Set);
        powerSetOfStatesFromNea2.add(q2Set);
    }

    private void powerSetElementsWithoutElementsOfNea2() {
        final Set<String> emptySet = new HashSet<String>();
        powerSetOfStatesFromNea2.add(emptySet);
    }

    private void initializeStateNamesOfNea2() {
        stateNamesOfNea2 = new HashSet<String>();
        stateNamesOfNea2.add(Q0);
        stateNamesOfNea2.add(Q1);
        stateNamesOfNea2.add(Q2);
    }

    private void initializeNea3() throws XmlUtilsException {
        final Document doc = xmlService.documentFromFile("nea3.xml");
        nea3 = automatonService.automatonFrom(doc);

        initializeStateNamesOfNea3();

        finalStatesOfNea3 = new HashSet<String>();
        finalStatesOfNea3.add(Q_1);

        initialStateOfNea3 = Q_0;
        
        initializeStatePowerSetOfNea3();
        initializeAlphabetOfNea3();
    }

    private void initializeAlphabetOfNea3() {
        alphabetOfNea3 = new HashSet<String>();
        alphabetOfNea3.add(A);
        alphabetOfNea3.add(B);
        alphabetOfNea3.add(C);
    }

    public Set<String> getAlphabetOfNea1() {
        return alphabetOfNea1;
    }

    public Set<String> getAlphabetOfNea2() {
        return alphabetOfNea2;
    }

    public Set<String> getAlphabetOfNea3() {
        return alphabetOfNea3;
    }

    private void initializeStatePowerSetOfNea3() {
        powerSetOfStatesFromNea3 = new HashSet<Set<String>>();
        
        final Set<String> emptySet = new HashSet<String>();
        
        final Set<String> q0Set = new HashSet<String>();
        q0Set.add(Q_0);
        
        final Set<String> q1Set = new HashSet<String>();
        q1Set.add(Q_1);
        
        final Set<String> q0q1Set = new HashSet<String>();
        q0q1Set.add(Q_0);
        q0q1Set.add(Q_1);
        
        powerSetOfStatesFromNea3.add(emptySet);
        powerSetOfStatesFromNea3.add(q0Set);
        powerSetOfStatesFromNea3.add(q1Set);
        powerSetOfStatesFromNea3.add(q0q1Set);
    }

    private void initializeStateNamesOfNea3() {
        stateNamesOfNea3 = new HashSet<String>();
        stateNamesOfNea3.add(Q_0);
        stateNamesOfNea3.add(Q_1);
    }
}
