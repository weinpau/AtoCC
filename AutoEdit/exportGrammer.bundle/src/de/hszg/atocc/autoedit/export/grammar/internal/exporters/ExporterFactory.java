package de.hszg.atocc.autoedit.export.grammar.internal.exporters;

import de.hszg.atocc.core.util.automaton.AutomatonType;

public final class ExporterFactory {

    private ExporterFactory() {

    }

    public static Exporter create(AutomatonType type) {
        switch (type) {
        case DEA:
        case NEA:
            return new FiniteAutomatonToRegularGrammar();

        default:
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }
}
