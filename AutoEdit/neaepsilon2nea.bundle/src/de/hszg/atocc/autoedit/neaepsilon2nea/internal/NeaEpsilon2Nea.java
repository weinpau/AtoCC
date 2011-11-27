package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.XmlValidatorService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.restlet.resource.Post;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class NeaEpsilon2Nea extends RestfulWebService {

    private static final String AUTOMATON = "AUTOMATON";
    private static final String TYPE = "TYPE";
    private static final String FINALSTATE = "finalstate";
    private static final String VALUE = "value";
    private static final String NEA = "NEA";

    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;
    private XmlValidatorService xmlValidator;

    private Document neaEpsilon;
    private Document result;

    private Set<String> neaEpsilonFinalStates;
    private Set<String> alphabet;
    private Set<String> stateNames;
    private Set<String> neaFinalStates;

    private Document nea;
    private Element automatonElement;
    private Map<String, Element> stateElements;

    @Post
    public Document transform(Document aNeaEpsilon) {
        neaEpsilon = aNeaEpsilon;

        try {
            tryToGetRequiredServices();
            validateInput();
            extractDataFromInput();

            initializeOutputDocument();
            createNewDeltaRules();
            finishOutputDocument();

            result = xmlUtils.createResult(nea);
        } catch (final XmlValidationException e) {
            result = xmlUtils.createResultWithError("INVALID_INPUT", e);
        }

        return result;
    }

    private void initializeOutputDocument() {
        nea = xmlUtils.createEmptyDocument();

        automatonElement = nea.createElement(AUTOMATON);
        nea.appendChild(automatonElement);

        createAutomatonTypeElement();
        createAlphabetElement();
        initializeStateElement();

        final Element initialStateElement = nea.createElement("initialstate");
        initialStateElement.setAttribute(VALUE,
                automatonUtils.getNameOfInitialStateFrom(neaEpsilon));
        automatonElement.appendChild(initialStateElement);
    }

    private void createAutomatonTypeElement() {
        final Element typeElement = nea.createElement(TYPE);
        typeElement.setAttribute(VALUE, NEA);
        automatonElement.appendChild(typeElement);
    }

    private void createAlphabetElement() {
        final Element alphabetElement = nea.createElement("ALPHABET");
        automatonElement.appendChild(alphabetElement);

        for (String alphabetCharacter : alphabet) {
            final Element itemElement = nea.createElement("ITEM");
            itemElement.setAttribute(VALUE, alphabetCharacter);
            alphabetElement.appendChild(itemElement);
        }
    }

    private void initializeStateElement() {
        stateElements = new HashMap<String, Element>();

        for (String stateName : stateNames) {
            final Element stateElement = nea.createElement("STATE");
            stateElement.setAttribute("name", stateName);
            stateElement.setAttribute(FINALSTATE, "false");
            automatonElement.appendChild(stateElement);

            stateElements.put(stateName, stateElement);
        }
    }

    private void finishOutputDocument() {
        for (String stateName : neaFinalStates) {
            stateElements.get(stateName).setAttribute(FINALSTATE, "true");
        }
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
        automatonUtils = getService(AutomatonService.class);
        xmlValidator = getService(XmlValidatorService.class);
    }

    private void validateInput() throws XmlValidationException {
        xmlValidator.validate(neaEpsilon, AUTOMATON);
        checkAutomatonType();
    }

    private void checkAutomatonType() {
        final Element automatonElement = neaEpsilon.getDocumentElement();
        final Element typeElement = (Element) automatonElement.getElementsByTagName(TYPE).item(0);

        if (!NEA.equals(typeElement.getAttribute(VALUE))) {
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }

    private void extractDataFromInput() {
        neaEpsilonFinalStates = automatonUtils.getNamesOfFinalStatesFrom(neaEpsilon);

        alphabet = automatonUtils.getAlphabetFrom(neaEpsilon);
        stateNames = automatonUtils.getStateNamesFrom(neaEpsilon);

        neaFinalStates = new TreeSet<String>();
    }

    private void createNewDeltaRules() {
        for (String stateName : stateNames) {
            for (String character : alphabet) {
                createNewDeltaRuleFor(stateName, character);
            }
        }
    }

    private void createNewDeltaRuleFor(String stateName, String character) {
        final Set<String> epsilonHull = automatonUtils.getEpsilonHull(neaEpsilon, stateName);

        if (containsAnyOf(epsilonHull, neaEpsilonFinalStates)) {
            neaFinalStates.add(stateName);
        }

        final Set<String> d = new TreeSet<String>();

        for (String state : epsilonHull) {
            d.addAll(automatonUtils.getTargetsOf(neaEpsilon, state, character));
        }

        createTransitionElements(stateName, character, d);
    }

    private void createTransitionElements(String stateName, String character, final Set<String> d) {
        for (String stateForNewRule : automatonUtils.getEpsilonHull(neaEpsilon, d)) {
            final Element transitionElement = nea.createElement("TRANSITION");
            transitionElement.setAttribute("target", stateForNewRule);

            final Element labelElement = nea.createElement("LABEL");
            labelElement.setAttribute("read", character);
            transitionElement.appendChild(labelElement);

            stateElements.get(stateName).appendChild(transitionElement);
        }
    }

    private boolean containsAnyOf(Set<String> epsilonHull, Set<String> states) {
        for (String state : states) {
            if (epsilonHull.contains(state)) {
                return true;
            }
        }

        return false;
    }
}
