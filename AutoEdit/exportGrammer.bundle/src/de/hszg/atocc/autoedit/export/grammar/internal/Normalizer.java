package de.hszg.atocc.autoedit.export.grammar.internal;

import de.hszg.atocc.core.util.CharacterHelper;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashSet;
import java.util.Set;

public class Normalizer {

    private static final int K = 2;

    private Automaton automaton;
    private Set<Transition> transitions;
    private Set<Transition> transitionsToNormalize;

    public Normalizer(Automaton anAutomaton) {
        automaton = anAutomaton;
        transitions = automaton.getTransitions();
    }

    public boolean isNormalized() {
        return findTransitionsToNormalize().isEmpty();
    }

    public void normalize() throws InvalidStateException, InvalidTransitionException {
        transitionsToNormalize = findTransitionsToNormalize();

        automaton.removeTransitions(transitionsToNormalize);

        createNewStatesAndTransitions();
    }

    private void createNewStatesAndTransitions() throws InvalidStateException,
            InvalidTransitionException {
        for (Transition transition : transitionsToNormalize) {
            final String write = transition.getCharacterToWrite();
            final int k = write.length();

            final char c = CharacterHelper.letterDifferentToAnyBeginningOf(automaton.getStates());

            for (int i = 1; i <= k - K; ++i) {
                automaton.addState(String.format("%s_%d", c, i));
            }

            createNewBaseTransitionFor(transition, c);
            createNewLastTransitionFor(transition, c);
            createEpsilonTransitionsFor(transition, c);
        }
    }

    private void createNewBaseTransitionFor(Transition transition, char c)
            throws InvalidTransitionException {
        final String write = transition.getCharacterToWrite();
        final int k = write.length();

        final char[] characters = write.toCharArray();

        final Transition newTransition = new Transition(transition.getSource(), String.format(
                "%s_1", c), transition.getCharacterToRead(), transition.getTopOfStack(),
                String.format("%s%s", characters[(k - 1) - 1], characters[(k) - 1]));

        automaton.addTransition(newTransition);
    }

    private void createNewLastTransitionFor(Transition transition, char c)
            throws InvalidTransitionException {
        final String write = transition.getCharacterToWrite();
        final int k = write.length();

        final char[] characters = write.toCharArray();

        final Transition newTransition = new Transition(String.format("%s_%d", c, k - 2),
                transition.getTarget(), Automaton.EPSILON, String.valueOf(characters[(K) - 1]),
                String.format("%s%s", characters[0], characters[1]));

        automaton.addTransition(newTransition);
    }

    private void createEpsilonTransitionsFor(Transition transition, char c) throws InvalidTransitionException {
        final String write = transition.getCharacterToWrite();
        final int k = write.length();

        final char[] characters = write.toCharArray();

        for (int i = 1; i < k - 2; ++i) {
            final Transition newTransition = new Transition(String.format("%s_%d", c, i),
                    String.format("%s_%d", c, i + 1), Automaton.EPSILON,
                    String.valueOf(characters[(k - i) - 1]), String.format("%s%s", characters[(k-(i+1))-1], characters[(k-i)-1]));
            
            automaton.addTransition(newTransition);
        }
    }

    private Set<Transition> findTransitionsToNormalize() {
        final Set<Transition> transitionsToNormalize = new HashSet<>();

        for (Transition transition : transitions) {
            if (needsNormalization(transition)) {
                transitionsToNormalize.add(transition);
            }
        }

        return transitionsToNormalize;
    }

    private boolean needsNormalization(Transition transition) {
        final String write = transition.getCharacterToWrite();

        if (!Automaton.EPSILON.equals(write) && write.length() > K) {
            return true;
        }

        return false;
    }

}
