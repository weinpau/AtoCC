package de.hszg.atocc.core.util.automaton;

public final class Transition {

    private String source;
    private String target;
    private String characterToRead;

    public Transition(String sourceState, String targetState, String character) {
        source = sourceState;
        target = targetState;
        characterToRead = character;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getCharacterToRead() {
        return characterToRead;
    }

}
