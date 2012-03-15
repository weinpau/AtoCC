package de.hszg.atocc.autoedit.export.grammar.internal;

import de.hszg.atocc.core.util.CollectionHelper;
import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.List;

public final class Grammar {

    private StringBuilder grammar = new StringBuilder();
    
    public void appendRule(String lhs, List<String> rhs) {
        grammar.append(String.format("%s -> %s\n", lhs, CollectionHelper.makeString(rhs, " | ")));
    }
    
    public void appendRule(String lhs, String rhs) {
        grammar.append(String.format("%s -> %s\n", lhs, rhs));
    }
    
    public void appendEpsilonRule(String lhs) {
        grammar.append(String.format("%s -> %s", lhs, Automaton.EPSILON));
    }
    
    @Override
    public String toString() {
        return grammar.toString();
    }
}
