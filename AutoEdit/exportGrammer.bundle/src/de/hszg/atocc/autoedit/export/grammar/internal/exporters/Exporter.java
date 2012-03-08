package de.hszg.atocc.autoedit.export.grammar.internal.exporters;

import de.hszg.atocc.autoedit.export.grammar.internal.Grammar;
import de.hszg.atocc.core.util.automaton.Automaton;

public interface Exporter {

    Grammar export(Automaton automaton);
    
}
