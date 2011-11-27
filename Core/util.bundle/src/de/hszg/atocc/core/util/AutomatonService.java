package de.hszg.atocc.core.util;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public interface AutomatonService {

    NodeList getStatesFrom(Document automaton);
    
    Set<String> getStateNamesFrom(Document automaton);
    Set<String> getStateNamesFrom(NodeList states);
    
    Set<String> getNamesOfFinalStatesFrom(Document automaton);
    String getNameOfInitialStateFrom(Document automaton);
    
    Set<Set<String>> getStatePowerSetFrom(Document automaton);
    
    Set<String> getAlphabetFrom(Document nea);
    
    Set<String> getTargetsOf(Document automaton, String stateName,
            String alphabetCharacter);
    
    boolean containsEpsilonRules(Document automaton);
    
    Set<String> getEpsilonHull(Document automaton, String stateName);
    
}
