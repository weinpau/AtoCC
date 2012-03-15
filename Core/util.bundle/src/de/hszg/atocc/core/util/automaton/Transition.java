package de.hszg.atocc.core.util.automaton;

public final class Transition {

    private String source;
    private String target;
    private String characterToRead;
    private String topOfStack;
    private String characterToWrite;

    public Transition(String sourceState, String targetState, String read) {
        source = sourceState;
        target = targetState;
        characterToRead = read;
    }

    // CHECKSTYLE:OFF
    public Transition(String sourceState, String targetState, String read, String top,
            String write) {
        source = sourceState;
        target = targetState;
        characterToRead = read;
        topOfStack = top;
        characterToWrite = write;
    }

    // CHECKSTYLE:ON

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public String getCharacterToRead() {
        return characterToRead;
    }

    public String getTopOfStack() {
        if (topOfStack == null) {
            throw new UnsupportedOperationException();
        }

        return topOfStack;
    }

    public String getCharacterToWrite() {
        if (characterToWrite == null) {
            throw new UnsupportedOperationException();
        }

        return characterToWrite;
    }

    @Override
    public String toString() {
        if (topOfStack == null) {
            return String.format("(%s, %s) = %s", source, characterToRead, target);
        } else {
            return String.format("(%s, %s, %s) = (%s, %s)", source, characterToRead, topOfStack,
                    target, characterToWrite);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + characterToRead.hashCode();
        result = prime * result + source.hashCode();
        result = prime * result + target.hashCode();

        if (topOfStack != null) {
            result = prime * result + topOfStack.hashCode();
            result = prime * result + characterToWrite.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Transition)) {
            return false;
        }

        final Transition transition = (Transition) obj;

        return source.equals(transition.source) && target.equals(transition.target)
                && characterToRead.equals(transition.characterToRead);
    }

}
