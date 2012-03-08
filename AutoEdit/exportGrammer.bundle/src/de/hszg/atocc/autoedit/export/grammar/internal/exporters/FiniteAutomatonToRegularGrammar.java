package de.hszg.atocc.autoedit.export.grammar.internal.exporters;

import de.hszg.atocc.autoedit.export.grammar.internal.Grammar;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FiniteAutomatonToRegularGrammar implements Exporter {

    @Override
    public Grammar export(Automaton automaton) {
        Grammar grammar = new Grammar();

        for (String state : automaton.getStates()) {
            final Set<Transition> transitions = automaton.getTransitionsFrom(state);

            final List<String> righthandSides = new LinkedList<>();

            for (Transition transition : transitions) {
                String target = transition.getTarget();

                righthandSides.add(transition.getCharacterToRead() + " " + target);

                if (automaton.isFinalState(target)) {
                    righthandSides.add(transition.getCharacterToRead());
                }
            }

            Collections.sort(righthandSides);

            grammar.appendRule(state, righthandSides);
        }

        if (automaton.isFinalState(automaton.getInitialState())) {
            grammar.appendEpsilonRule(automaton.getInitialState());
        }

        return grammar;
    }

}
