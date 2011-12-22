package de.hszg.atocc.core.util.automaton;

public class InvalidAlphabetCharacterException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidAlphabetCharacterException(String msg) {
        super(msg);
    }
}
