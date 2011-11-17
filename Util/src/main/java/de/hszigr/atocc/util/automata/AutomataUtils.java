package de.hszigr.atocc.util.automata;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public final class AutomataUtils {

    public static NodeList getStates(final Document automaton) {
        return automaton.getElementsByTagName("STATE");
    }
    
}
