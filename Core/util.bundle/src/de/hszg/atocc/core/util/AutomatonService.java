package de.hszg.atocc.core.util;

import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.Set;

import org.w3c.dom.Document;

public interface AutomatonService {

    Automaton automatonFrom(Document document);

    Document automatonToXml(Automaton automaton);

    Set<Set<String>> getStatePowerSetFrom(Automaton automaton);

    Set<String> getEpsilonHull(Automaton automaton, Set<String> states);

    Set<String> getEpsilonHull(Automaton automaton, String stateName);

}
