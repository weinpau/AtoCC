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

    }

    private void popTwoElementRules() {
        final Set<Transition> transitions = automaton.getTransitions();

        for (String qTick : automaton.getStates()) {
            for (String q2 : automaton.getStates()) {
                for (Transition transition : transitions) {

                    final String write = transition.getCharacterToWrite();
                    final char[] charactersToWrite = write.toCharArray();

                    if (charactersToWrite.length == 2) {
                        final String read = transition.getCharacterToRead();
                        final String top = transition.getTopOfStack();
                        final String source = transition.getSource();
                        final String target = transition.getTarget();

                        final String lhs = String.format("[%s,%s,%s]", source, top, qTick);
                        final String rhs = String.format("%s[%s,%s,%s][%s,%s,%s]", read, target,
                                charactersToWrite[0], q2, q2, charactersToWrite[1], qTick);

                        grammar.appendRule(lhs, rhs);
                    }
                }
            }
        }
    }
}
