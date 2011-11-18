package de.hszigr.atocc.util.automata;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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

        return getStateNamesFrom(states);
    }

    public static Set<String> getStateNamesFrom(final NodeList states) {
        final Set<String> stateNames = new HashSet<>();

        for (int i = 0; i < states.getLength(); ++i) {
            final Element state = (Element) states.item(i);
            stateNames.add(state.getAttribute("name"));
        }

        return stateNames;
    }

    public static Set<String> getNamesOfFinalStatesFrom(final Document automaton) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final NodeList finalStates = (NodeList) xpath.evaluate("//STATE[@finalstate='true']",
                    automaton, XPathConstants.NODESET);

            return getStateNamesFrom(finalStates);

        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNameOfInitialStateFrom(final Document automaton) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            return (String) xpath.evaluate("//INITIALSTATE/@value", automaton,
                    XPathConstants.STRING);
        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<Set<String>> getStatePowerSetFrom(final Document automaton) {
        final Set<String> stateNames = getStateNamesFrom(automaton);

        return PowerSet.from(stateNames);
    }

    public static Set<String> getAlphabetFrom(final Document nea) {
        final Set<String> alphabet = new HashSet<>();

        final Element alphabetElement = (Element) nea.getDocumentElement()
                .getElementsByTagName("ALPHABET").item(0);

        final NodeList itemElements = alphabetElement.getElementsByTagName("ITEM");

        for (int i = 0; i < itemElements.getLength(); ++i) {
            final Element itemElement = (Element) itemElements.item(i);
            alphabet.add(itemElement.getAttribute("value"));
        }

        return alphabet;
    }

    public static Set<String> getTargetsOf(final Document automaton, final String stateName,
            final String alphabetCharacter) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final Set<String> targets = new HashSet<>();

            final NodeList targetNodes = (NodeList) xpath.evaluate(String.format(
                    "//STATE[@name='%s']/TRANSITION/LABEL[@read='%s']/../@target", stateName,
                    alphabetCharacter), automaton, XPathConstants.NODESET);

            for (int i = 0; i < targetNodes.getLength(); ++i) {
                targets.add(targetNodes.item(i).getTextContent());
            }

            return targets;

        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

}
