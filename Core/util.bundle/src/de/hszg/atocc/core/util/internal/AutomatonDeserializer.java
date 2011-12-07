package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
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

    private static final String EPSILON = "EPSILON";

    private static final String NAME = "name";

    private Document document;
    private Automaton automaton;

    public Automaton deserialize(Document aDocument) {
        document = aDocument;

        try {
            setType();
            setAlphabet();
            setStates();
            setTransitions();
            setFinalStates();
            setInitialState();
        } catch (final InvalidTransitionException e) {
            // TODO: throw DeserializationException
        }

        return automaton;
    }

    private void setType() {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final String type = (String) xpath.evaluate("//TYPE/@value", document,
                    XPathConstants.STRING);

            automaton = new Automaton(AutomatonType.valueOf(type));
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    private void setAlphabet() {
        final Element alphabetElement = (Element) document.getDocumentElement()
                .getElementsByTagName("ALPHABET").item(0);

        final NodeList itemElements = alphabetElement.getElementsByTagName("ITEM");

        for (int i = 0; i < itemElements.getLength(); ++i) {
            final Element itemElement = (Element) itemElements.item(i);
            automaton.addAlphabetItem(itemElement.getAttribute("value"));
        }
    }

    private void setStates() {
        final NodeList states = document.getElementsByTagName("STATE");

        for (int i = 0; i < states.getLength(); ++i) {
            final Element state = (Element) states.item(i);
            automaton.addState(state.getAttribute(NAME));
        }
    }

    private void setTransitions() throws InvalidTransitionException {
        final Set<Transition> transitions = new HashSet<>();

        for (String state : automaton.getStates()) {
            transitions.addAll(getTransitionsFromDocumentFor(state));
        }

        automaton.setTransitions(transitions);
    }

    private void setFinalStates() {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final NodeList finalStates = (NodeList) xpath.evaluate("//STATE[@finalstate='true']",
                    document, XPathConstants.NODESET);

            for (String state : getStateNamesFrom(finalStates)) {
                automaton.addFinalState(state);
            }
        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    private void setInitialState() {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final String state = (String) xpath.evaluate("//INITIALSTATE/@value", document,
                    XPathConstants.STRING);

            automaton.setInitialState(state);
        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Transition> getTransitionsFromDocumentFor(String state) {
        final Set<Transition> transitions = new HashSet<>();

        automaton.addAlphabetItem(EPSILON);
        for (String alphabetCharacter : automaton.getAlphabet()) {
            final Set<String> targets = getTargetsOf(state, alphabetCharacter);

            for (String target : targets) {
                transitions.add(new Transition(state, target, alphabetCharacter));
            }
        }
        automaton.getAlphabet().remove(EPSILON);

        return transitions;
    }

    private Set<String> getTargetsOf(String stateName, String alphabetCharacter) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final Set<String> targets = new HashSet<>();

            final NodeList targetNodes = (NodeList) xpath.evaluate(String.format(
                    "//STATE[@name='%s']/TRANSITION/LABEL[@read='%s']/../@target", stateName,
                    alphabetCharacter), document, XPathConstants.NODESET);

            for (int i = 0; i < targetNodes.getLength(); ++i) {
                targets.add(targetNodes.item(i).getTextContent());
            }

            return targets;

        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
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
