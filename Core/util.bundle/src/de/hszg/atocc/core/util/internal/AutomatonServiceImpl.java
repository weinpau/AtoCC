package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SetService;

import java.util.HashSet;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class AutomatonServiceImpl implements AutomatonService {

    private SetService setService;

    public NodeList getStatesFrom(final Document automaton) {
        return automaton.getElementsByTagName("STATE");
    }

    public Set<String> getStateNamesFrom(final Document automaton) {
        final NodeList states = getStatesFrom(automaton);

        return getStateNamesFrom(states);
    }

    public Set<String> getStateNamesFrom(final NodeList states) {
        final Set<String> stateNames = new HashSet<String>();

        for (int i = 0; i < states.getLength(); ++i) {
            final Element state = (Element) states.item(i);
            stateNames.add(state.getAttribute("name"));
        }

        return stateNames;
    }

    public Set<String> getNamesOfFinalStatesFrom(final Document automaton) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final NodeList finalStates =
                    (NodeList) xpath.evaluate("//STATE[@finalstate='true']", automaton,
                            XPathConstants.NODESET);

            return getStateNamesFrom(finalStates);

        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNameOfInitialStateFrom(final Document automaton) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            return (String) xpath.evaluate("//INITIALSTATE/@value", automaton,
                    XPathConstants.STRING);
        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Set<String>> getStatePowerSetFrom(final Document automaton) {
        final Set<String> stateNames = getStateNamesFrom(automaton);

        return setService.powerSetFrom(stateNames);
    }

    public Set<String> getAlphabetFrom(final Document nea) {
        final Set<String> alphabet = new HashSet<String>();

        final Element alphabetElement =
                (Element) nea.getDocumentElement().getElementsByTagName("ALPHABET").item(0);

        final NodeList itemElements = alphabetElement.getElementsByTagName("ITEM");

        for (int i = 0; i < itemElements.getLength(); ++i) {
            final Element itemElement = (Element) itemElements.item(i);
            alphabet.add(itemElement.getAttribute("value"));
        }

        return alphabet;
    }

    public Set<String> getTargetsOf(final Document automaton, final String stateName,
            final String alphabetCharacter) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final Set<String> targets = new HashSet<String>();

            final NodeList targetNodes =
                    (NodeList) xpath.evaluate(String.format(
                            "//STATE[@name='%s']/TRANSITION/LABEL[@read='%s']/../@target",
                            stateName, alphabetCharacter), automaton, XPathConstants.NODESET);

            for (int i = 0; i < targetNodes.getLength(); ++i) {
                targets.add(targetNodes.item(i).getTextContent());
            }

            return targets;

        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsEpsilonRules(Document automaton) {
        final XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            final NodeList nodes =
                    (NodeList) xpath.evaluate("//LABEL[@read='EPSILON']", automaton,
                            XPathConstants.NODESET);

            return nodes.getLength() > 0;
        } catch (final XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void setSetService(SetService service) {
        setService = service;
    }

    public synchronized void unsetSetService(SetService service) {
        if (setService == service) {
            setService = null;
        }
    }

}
