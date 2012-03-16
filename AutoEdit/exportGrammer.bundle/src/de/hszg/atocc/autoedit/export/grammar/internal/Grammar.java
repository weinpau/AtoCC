package de.hszg.atocc.autoedit.export.grammar.internal;

import de.hszg.atocc.core.util.CollectionHelper;
import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Grammar {

    private Map<String, List<String>> rules = new HashMap<>();

    public void appendRule(String lhs, List<String> rhs) {
        for (String r : rhs) {
            appendRule(lhs, r);
        }
    }

    public void appendRule(String lhs, String rhs) {
        if(!rules.containsKey(lhs)) {
            rules.put(lhs, new ArrayList<String>());
        }
        
        rules.get(lhs).add(rhs);
    }

    public void appendEpsilonRule(String lhs) {
        appendRule(lhs, Automaton.EPSILON);
    }

    @Override
    public String toString() {
        final StringBuilder grammar = new StringBuilder();

        for (String lhs : rules.keySet()) {
            final List<String> rhs = rules.get(lhs);
            grammar.append(String.format("%s -> %s\n", lhs, CollectionHelper.makeString(rhs, " | ")));
        }

        return grammar.toString();
    }
}
