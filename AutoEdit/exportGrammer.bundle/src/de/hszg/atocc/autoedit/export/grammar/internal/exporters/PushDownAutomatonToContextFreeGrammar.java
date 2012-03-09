package de.hszg.atocc.autoedit.export.grammar.internal.exporters;

import de.hszg.atocc.autoedit.export.grammar.internal.Grammar;
import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PushDownAutomatonToContextFreeGrammar implements Exporter {

    private Grammar grammar = new Grammar();
    private Automaton automaton;

    @Override
    public Grammar export(Automaton anAutomaton) {
        automaton = anAutomaton;

        createStartingPointRules();

        return grammar;
    }

    private void createStartingPointRules() {
        final Set<String> states = automaton.getStates();

        List<String> rhs = new ArrayList<>(states.size());

        for (String state : states) {
            rhs.add(String.format("[%s,%s,%s]", 
                    automaton.getInitialState(),
                    "", //automaton.getInitialStackSymbol(),
                    state));
        }

        grammar.appendRule("s", rhs);
    }
}
