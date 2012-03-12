package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class AutomatonSerializer {

    private static final String ITEM = "ITEM";

    private static final String FINALSTATE = "finalstate";

    private static final String VALUE = "value";

    private XmlUtilService xmlUtils;
    private final Document document;
    private final Element automatonElement;

    private Automaton automaton;

    public AutomatonSerializer(XmlUtilService xmlUtilService) {
        xmlUtils = xmlUtilService;

        document = xmlUtils.createEmptyDocument();

        automatonElement = document.createElement("AUTOMATON");
        document.appendChild(automatonElement);
    }

    public Document serialize(Automaton anAutomaton) throws SerializationException {
        automaton = anAutomaton;

        createTypeElement();
        createAlphabetElement();
        createStackAlphabetElement();
        createStateElements();

        try {
            createInitialStateElement();
        } catch (InvalidStateException e) {
            throw new SerializationException(e);
        }
        
        createInitialStackSymbolElement();

        return document;
    }

    private void createTypeElement() {
        final Element typeElement = document.createElement("TYPE");
        typeElement.setAttribute(VALUE, automaton.getType().name());
        automatonElement.appendChild(typeElement);
    }

    private void createAlphabetElement() {
        final Element alphabetElement = document.createElement("ALPHABET");
        automatonElement.appendChild(alphabetElement);

        for (String alphabetCharacter : automaton.getAlphabet()) {
            final Element itemElement = document.createElement(ITEM);
            itemElement.setAttribute(VALUE, alphabetCharacter);
            alphabetElement.appendChild(itemElement);
        }
    }

    private void createStackAlphabetElement() {
        if (automaton.getType() == AutomatonType.NKA) {
            final Element stackAlphabetElement = document.createElement("STACKALPHABET");
            automatonElement.appendChild(stackAlphabetElement);

            for (String stackAlphabetCharacter : automaton.getStackAlphabet()) {
                final Element itemElement = document.createElement(ITEM);
                itemElement.setAttribute(VALUE, stackAlphabetCharacter);
                stackAlphabetElement.appendChild(itemElement);
            }
        }
    }
    
    private void createInitialStackSymbolElement() {
        if (automaton.getType() == AutomatonType.NKA) {
            final Element initialStackSymbolElement = document.createElement("STACKINITIALCHAR");
            initialStackSymbolElement.setAttribute(VALUE, automaton.getInitialStackSymbol());
            automatonElement.appendChild(initialStackSymbolElement);
        }
    }

    private void createStateElements() {
        for (String state : automaton.getStates()) {
            createStateElement(state);
        }
    }

    private void createStateElement(String state) {
        final Element stateElement = document.createElement("STATE");
        stateElement.setAttribute("name", state);

        if (isFinalState(state)) {
            stateElement.setAttribute(FINALSTATE, "true");
        } else {
            stateElement.setAttribute(FINALSTATE, "false");
        }

        createTransitionElements(state, stateElement);

        automatonElement.appendChild(stateElement);
    }

    private void createTransitionElements(String state, final Element stateElement) {
        final Set<Transition> transitions = automaton.getTransitionsFrom(state);

        for (Transition transition : transitions) {
            final Element transitionElement = document.createElement("TRANSITION");
            transitionElement.setAttribute("target", transition.getTarget());

            final Element labelElement = document.createElement("LABEL");
            labelElement.setAttribute("read", transition.getCharacterToRead());
            
            if (automaton.getType() == AutomatonType.NKA) {
                labelElement.setAttribute("topofstack", transition.getTopOfStack());
                labelElement.setAttribute("write", transition.getCharacterToWrite());
            }
            
            transitionElement.appendChild(labelElement);

            stateElement.appendChild(transitionElement);
        }
    }

    private void createInitialStateElement() throws InvalidStateException {
        final String initialState = automaton.getInitialState();

        if (initialState == null || "".equals(initialState)) {
            throw new InvalidStateException("INITIAL_STATE_NOT_SET");
        }

        final Element initialStateElement = document.createElement("INITIALSTATE");
        initialStateElement.setAttribute(VALUE, automaton.getInitialState());
        automatonElement.appendChild(initialStateElement);
    }

    private boolean isFinalState(String state) {
        return automaton.getFinalStates().contains(state);
    }

}
