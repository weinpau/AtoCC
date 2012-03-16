package de.hszg.atocc.autoedit.export.grammar.internal;

import java.util.ArrayList;
import java.util.Collection;

public class GrammarOptimizer {

    private Grammar grammar;

    public GrammarOptimizer(Grammar g) {
        grammar = g;
    }

    public void optimize() {
        optimize1();
        optimize2();
        optimize1();
    }

    private void optimize1() {
        for (String rhs : grammar.getAllRightHandSides()) {
            String[] parts = rhs.split(" ");

            for (String part : parts) {
                if (part.matches("\\[.*\\]")) {
                    if (!grammar.containsLeftHandSide(part)) {
                        grammar.removeRightHandSide(rhs);
                    }
                }
            }
        }
    }

    private void optimize2() {
        final Collection<String> lhsToRemove = new ArrayList<>();

        for (String lhs : grammar.getLeftHandSides()) {
            boolean removeRule = true;

            final Collection<String> rightHandSides = grammar.getRightHandSidesFor(lhs);

            for (String rhs : rightHandSides) {
                if (!rhs.contains(lhs)) {
                    removeRule = false;
                }
            }

            if (removeRule) {
                lhsToRemove.add(lhs);
            }
        }

        for (String lhs : lhsToRemove) {
            grammar.remove(lhs);
        }
    }
}
