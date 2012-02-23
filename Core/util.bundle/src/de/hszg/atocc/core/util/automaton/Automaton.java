package de.hszg.atocc.core.util.automaton;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Automaton {

    private static final String EPSILON = "EPSILON";

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
        return Collections.unmodifiableSet(alphabet);
    }

    public void addAlphabetItem(String alphabetCharacter) throws InvalidAlphabetCharacterException {
        if (alphabetCharacter == null || "".equals(alphabetCharacter)) {
            throw new InvalidAlphabetCharacterException(
                    "null or empty string not allowed as alphabet item");
        }

        alphabet.add(alphabetCharacter);
    }

    public void setAlphabet(Set<String> alphabetCharacters) {
        alphabet.clear();
        alphabet.addAll(alphabetCharacters);
    }

    public Set<String> getStates() {
        return Collections.unmodifiableSet(states);
    }
    
    public SortedSet<String> getSortedStates() {
        return new TreeSet<String>(states);
    }

    public void addState(String state) throws InvalidStateException {
        if (state == null || "".equals(state)) {
            throw new InvalidStateException("null or empty string");
        }

        states.add(state);

        if (!transitions.containsKey(state)) {
            transitions.put(state, new HashSet<Transition>());
        }
    }

    public void setStates(Set<String> stateNames) throws InvalidStateException {
        states.clear();

        for (String state : stateNames) {
            addState(state);
        }
    }
    
    public void removeState(String state) {
        states.remove(state);
        
        for (Transition transition : getTransitionsFrom(state)) {
            transitions.get(state).remove(transition);
        }
        
        for (Transition transition : getTransitionsTo(state)) {
            transitions.get(transition.getSource()).remove(transition);
        }
        
        finalStates.remove(state);
        
        if (initialState.equals(state)) {
            initialState = "";
        }
    }

    public Set<Transition> getTransitionsFrom(String state) {
        return transitions.get(state);
    }
    
    public Set<Transition> getTransitionsTo(String state) {
        final Set<Transition> transitionsToState = new HashSet<>();
        
        for (String s : states) {
            for (Transition t : transitions.get(s)) {
                if (t.getTarget().equals(state)) {
                    transitionsToState.add(t);
                }
            }
        }
        
        return transitionsToState;
    }

    public Set<String> getTargetsFor(String state, String read) {
        final Set<String> targets = new HashSet<>();

        for (Transition transition : getTransitionsFrom(state)) {
            if (transition.getCharacterToRead().equals(read)) {
                targets.add(transition.getTarget());
            }
        }

        return targets;
    }

    public void setTransitions(Set<Transition> transitionSet) throws InvalidTransitionException {
        for (Transition transition : transitionSet) {
            addTransition(transition);
        }
    }

    public void addTransition(Transition transition) throws InvalidTransitionException {
        try {
            verifyStateExists(transition.getSource());
            verifyStateExists(transition.getTarget());
            verifyAlphabetCharacterExists(transition.getCharacterToRead());

            transitions.get(transition.getSource()).add(transition);

            if (EPSILON.equals(transition.getCharacterToRead())) {
                containsEpsilonRules = true;
            }
        } catch (final InvalidStateException | InvalidAlphabetCharacterException e) {
            throw new InvalidTransitionException(e);
        }
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String state) throws InvalidStateException {
        initialState = state;
        addState(state);
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void addFinalState(String state) throws InvalidStateException {
        if (!states.contains(state)) {
            throw new InvalidStateException(state);
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

    // CHECKSTYLE:OFF
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Automaton)) {
            return false;
        }

        final Automaton other = (Automaton) obj;

        return type == other.type && alphabet.equals(other.alphabet)
                && finalStates.equals(other.finalStates) && initialState.equals(other.initialState)
                && states.equals(other.states) && transitions.equals(other.transitions);
    }

    // CHECKSTYLE:ON

    @Override
    public String toString() {
        return String.format("%s = (%s, %s, %s, %s, %s)", type.name(), states, alphabet,
                transitions, initialState, finalStates);
    }

    private void verifyStateExists(String state) throws InvalidStateException {
        if (!states.contains(state)) {
            throw new InvalidStateException(state);
        }
    }

    private void verifyAlphabetCharacterExists(String character)
            throws InvalidAlphabetCharacterException {
        if (EPSILON.equals(character) && type == AutomatonType.NEA) {
            return;
        }

        if (!alphabet.contains(character)) {
            throw new InvalidAlphabetCharacterException(character);
        }
    }
}
