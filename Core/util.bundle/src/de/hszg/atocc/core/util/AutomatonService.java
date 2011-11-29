package de.hszg.atocc.core.util;

import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.Set;

import org.w3c.dom.Document;

public interface AutomatonService {

    Automaton automatonFrom(Document document);

    Document automatonToXml(Automaton automaton);

    // NodeList getStatesFrom(Document automaton);
    //
    // Set<String> getStateNamesFrom(Document automaton);
    // Set<String> getStateNamesFrom(NodeList states);
    //
    // Set<String> getNamesOfFinalStatesFrom(Document automaton);
    // String getNameOfInitialStateFrom(Document automaton);
    //
    Set<Set<String>> getStatePowerSetFrom(Automaton automaton);

    //
    // Set<String> getAlphabetFrom(Document automaton);
    //
    // Set<String> getTargetsOf(Document automaton, String stateName,
    // String alphabetCharacter);
    //
    // boolean containsEpsilonRules(Document automaton);
    //
    Set<String> getEpsilonHull(Automaton automaton, Set<String> states);

    Set<String> getEpsilonHull(Automaton automaton, String stateName);

}
