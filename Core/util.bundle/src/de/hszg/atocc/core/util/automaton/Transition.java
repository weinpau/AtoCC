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

    @Override
    public String toString() {
        return String.format("(%s, %s) = %s", source, characterToRead, target);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((characterToRead == null) ? 0 : characterToRead.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((target == null) ? 0 : target.hashCode());
        
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Transition)) {
            return false;
        }

        final Transition transition = (Transition) obj;

        return source.equals(transition.source) && target.equals(transition.target)
                && characterToRead.equals(transition.characterToRead);
    }
    
}
