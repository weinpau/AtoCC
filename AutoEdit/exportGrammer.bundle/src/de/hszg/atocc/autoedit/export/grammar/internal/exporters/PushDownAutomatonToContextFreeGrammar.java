package de.hszg.atocc.autoedit.export.grammar.internal.exporters;

import de.hszg.atocc.autoedit.export.grammar.internal.Grammar;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.Transition;

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
        purelyPopRules();
        popOneElementRules();
        popTwoElementRules();

        return grammar;
    }

    private void createStartingPointRules() {
        final Set<String> states = automaton.getStates();

        List<String> rhs = new ArrayList<>(states.size());

        for (String state : states) {
            rhs.add(String.format("[%s,%s,%s]", automaton.getInitialState(),
                    automaton.getInitialStackSymbol(), state));
        }

        grammar.appendRule("s", rhs);
    }

    private void purelyPopRules() {
        for (String state : automaton.getStates()) {
            final Set<Transition> transitions = automaton.getTransitionsFrom(state);

            for (Transition transition : transitions) {
                if ("EPSILON".equals(transition.getCharacterToWrite())) {
                    grammar.appendRule(
                            String.format("[%s,%s,%s]", transition.getSource(),
                                    transition.getTopOfStack(), transition.getTarget()),
                            transition.getCharacterToRead());
                }
            }
        }
    }

    private void popOneElementRules() {
        for (String state : automaton.getStates()) {
            final Set<Transition> transitions = automaton.getTransitionsFrom(state);

            for (Transition transition : transitions) {
                final String target = transition.getTarget();
                final String write = transition.getCharacterToWrite();

                if ("q_1".equals(target) && !"EPSILON".equals(write)) {
                    for (String qTick : automaton.getStates()) {
                        grammar.appendRule(String.format("[%s,%s,%s]", state,
                                transition.getTopOfStack(), qTick), String.format("%s[q_1,%s,%s]",
                                transition.getCharacterToRead(), write, qTick));
                    }

                }
            }
        }
    }

    private void popTwoElementRules() {
        for (String state : automaton.getStates()) {
            final Set<Transition> transitions = automaton.getTransitionsFrom(state);

            for (Transition transition : transitions) {
                final String target = transition.getTarget();
                final String write = transition.getCharacterToWrite();

                final char[] charactersToWrite = write.toCharArray();

                assert charactersToWrite.length == 2;

                if ("q_1".equals(target) && !"EPSILON".equals(write)) {
                    for (String qTick : automaton.getStates()) {
                        grammar.appendRule(String.format("[%s,%s,%s]", state,
                                transition.getTopOfStack(), qTick), String.format(
                                "%s[q_1,%s,q_2][q_2,%s,%s]", transition.getCharacterToRead(),
                                charactersToWrite[0], charactersToWrite[1], qTick));
                    }

                }
            }
        }
    }
}
