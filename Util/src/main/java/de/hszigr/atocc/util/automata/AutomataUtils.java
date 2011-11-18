package de.hszigr.atocc.util.automata;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class AutomataUtils {

    private AutomataUtils() {

    }

    public static NodeList getStatesFrom(final Document automaton) {
        return automaton.getElementsByTagName("STATE");
    }

    public static Set<String> getStateNamesFrom(final Document automaton) {
        final NodeList states = getStatesFrom(automaton);
        final Set<String> stateNames = new HashSet<>();

        for (int i = 0; i < states.getLength(); ++i) {
            final Element state = (Element) states.item(i);
            stateNames.add(state.getAttribute("name"));
        }

        return stateNames;
    }

    public static Set<Set<String>> getStatePowerSetFrom(final Document automaton) {
        final Set<String> stateNames = getStateNamesFrom(automaton);

        return PowerSet.from(stateNames);
    }

}
