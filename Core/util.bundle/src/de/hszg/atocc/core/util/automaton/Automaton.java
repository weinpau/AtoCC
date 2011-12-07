package de.hszg.atocc.core.util.automaton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Automaton {

    private AutomatonType type = AutomatonType.DEA;
    private Set<String> alphabet = new HashSet<>();
    private Set<String> states = new HashSet<>();
    private Map<String, Set<Transition>> transitions = new HashMap<>();

    private String initialState = "";
    private Set<String> finalStates = new HashSet<>();

    private boolean containsEpsilonRules;

    public Automaton(AutomatonType automatonType) {
        type = automatonType;
    }

    public boolean containsEpsilonRules() {
        return containsEpsilonRules;
    }

    public AutomatonType getType() {
        return type;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void addAlphabetItem(String alphabetCharacter) {
        alphabet.add(alphabetCharacter);
    }

    public void setAlphabet(Set<String> alphabetCharacters) {
        alphabet.clear();
        alphabet.addAll(alphabetCharacters);
    }

    public Set<String> getStates() {
        return states;
    }

    public void addState(String state) {
        states.add(state);

        if (!transitions.containsKey(state)) {
            transitions.put(state, new HashSet<Transition>());
        }
    }

    public void setStates(Set<String> stateNames) {
        states.clear();

        for (String state : stateNames) {
            addState(state);
        }
    }

    public Set<Transition> getTransitionsFor(String state) {
        return transitions.get(state);
    }

    public Set<String> getTargetsFor(String state, String read) {
        final Set<String> targets = new HashSet<>();

        for (Transition transition : getTransitionsFor(state)) {
            if (transition.getCharacterToRead().equals(read)) {
                targets.add(transition.getTarget());
            }
        }

        return targets;
    }

    public void setTransitions(Set<Transition> transitionSet) {
        for (Transition transition : transitionSet) {
            addTransition(transition);
        }
    }

    public void addTransition(Transition transition) {
        transitions.get(transition.getSource()).add(transition);

        if ("EPSILON".equals(transition.getCharacterToRead())) {
            containsEpsilonRules = true;
        }
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String state) {
        initialState = state;
        states.add(state);
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void addFinalState(String state) {
        if (!states.contains(state)) {
            throw new IllegalArgumentException("State not found: " + state);
        }

        finalStates.add(state);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + alphabet.hashCode();
        result = prime * result + finalStates.hashCode();
        result = prime * result + initialState.hashCode();
        result = prime * result + states.hashCode();
        result = prime * result + transitions.hashCode();
        result = prime * result + type.hashCode();

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Automaton)) {
            return false;
        }

        final Automaton other = (Automaton) obj;

        return alphabet.equals(other.alphabet) && finalStates.equals(other.finalStates)
                && initialState.equals(other.initialState) && states.equals(other.states)
                && transitions.equals(other.transitions);
    }

}
