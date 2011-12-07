package de.hszg.atocc.core.util.automaton;

public class InvalidStateException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidStateException(String stateName) {
        super(stateName);
    }
}
