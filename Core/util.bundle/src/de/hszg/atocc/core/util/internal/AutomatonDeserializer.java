package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidAlphabetCharacterException;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class AutomatonDeserializer {

    private static final String VALUE = "value";

    private static final String ITEM = "ITEM";

    private static final String NAME = "name";

    private Document document;
    private Automaton automaton;

    public Automaton deserialize(Document aDocument) throws SerializationException {
        assert aDocument != null;

        document = aDocument;

        try {
            setType();
            setAlphabet();
            setStackAlphabet();
            setStates();
            setTransitions();
            setFinalStates();
            setInitialState();
            setInitialStackSymbol();
        } catch (final InvalidTransitionException | IllegalArgumentException
                | XPathExpressionException | InvalidStateException
                | InvalidAlphabetCharacterException e) {
            throw new SerializationException(e);
        }

        return automaton;
    }

    private void setType() throws XPathExpressionException {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        final String type = (String) xpath.evaluate("//TYPE/@value", document,
                XPathConstants.STRING);

        automaton = new Automaton(AutomatonType.valueOf(type));
    }

    private void setAlphabet() throws InvalidAlphabetCharacterException {
        final Element alphabetElement = (Element) document.getDocumentElement()
                .getElementsByTagName("ALPHABET").item(0);

        final NodeList itemElements = alphabetElement.getElementsByTagName(ITEM);

        for (int i = 0; i < itemElements.getLength(); ++i) {
            final Element itemElement = (Element) itemElements.item(i);
            automaton.addAlphabetItem(itemElement.getAttribute(VALUE));
        }
    }

    private void setStackAlphabet() throws InvalidAlphabetCharacterException {
        if (automaton.getType() == AutomatonType.NKA) {
            final Element alphabetElement = (Element) document.getDocumentElement()
                    .getElementsByTagName("STACKALPHABET").item(0);

            final NodeList itemElements = alphabetElement.getElementsByTagName(ITEM);

            for (int i = 0; i < itemElements.getLength(); ++i) {
                final Element itemElement = (Element) itemElements.item(i);
                automaton.addStackAlphabetItem(itemElement.getAttribute(VALUE));
            }
        }
    }

    private void setStates() throws InvalidStateException {
        final NodeList states = document.getElementsByTagName("STATE");

        for (int i = 0; i < states.getLength(); ++i) {
            final Element state = (Element) states.item(i);
            automaton.addState(state.getAttribute(NAME));
        }
    }

    private void setTransitions() throws InvalidTransitionException, XPathExpressionException {
        final Set<Transition> transitions = new HashSet<>();

        for (String state : automaton.getStates()) {
            transitions.addAll(getTransitionsFromDocumentFor(state));
        }

        automaton.setTransitions(transitions);
    }

    private void setFinalStates() throws XPathExpressionException, InvalidStateException {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        final NodeList finalStates = (NodeList) xpath.evaluate("//STATE[@finalstate='true']",
                document, XPathConstants.NODESET);

        for (String state : getStateNamesFrom(finalStates)) {
            automaton.addFinalState(state);
        }
    }

    private void setInitialState() throws XPathExpressionException, InvalidStateException {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        final String state = (String) xpath.evaluate("//INITIALSTATE/@value", document,
                XPathConstants.STRING);

        automaton.setInitialState(state);
    }

    private void setInitialStackSymbol() throws XPathExpressionException,
            InvalidAlphabetCharacterException {
        if (automaton.getType() == AutomatonType.NKA) {
            final XPath xpath = XPathFactory.newInstance().newXPath();

            final String state = (String) xpath.evaluate("//STACKINITIALCHAR/@value", document,
                    XPathConstants.STRING);

            automaton.setInitialStackSymbol(state);
        }
    }

    private Set<Transition> getTransitionsFromDocumentFor(String state)
            throws XPathExpressionException {
        final Set<Transition> transitions = new HashSet<>();

        final XPath xpath = XPathFactory.newInstance().newXPath();
        final NodeList transitionNodes = (NodeList) xpath.evaluate(
                String.format("//STATE[@name='%s']/TRANSITION", state), document,
                XPathConstants.NODESET);

        for (int i = 0; i < transitionNodes.getLength(); ++i) {
            final Element transition = (Element) transitionNodes.item(i);

            final String target = transition.getAttribute("target");

            final NodeList labelNodes = transition.getElementsByTagName("LABEL");

            for (int j = 0; j < labelNodes.getLength(); ++j) {
                final Element label = (Element) labelNodes.item(j);

                final String read = label.getAttribute("read");

                if (automaton.getType() == AutomatonType.NKA) {
                    final String topOfStack = label.getAttribute("topofstack");
                    final String write = label.getAttribute("write");

                    transitions.add(new Transition(state, target, read, topOfStack, write));
                } else {
                    transitions.add(new Transition(state, target, read));
                }
            }
        }

        return transitions;
    }

    private Set<String> getStateNamesFrom(NodeList states) {
        final Set<String> stateNames = new HashSet<>();

        for (int i = 0; i < states.getLength(); ++i) {
            final Element state = (Element) states.item(i);
            stateNames.add(state.getAttribute(NAME));
        }

        return stateNames;
    }
}
