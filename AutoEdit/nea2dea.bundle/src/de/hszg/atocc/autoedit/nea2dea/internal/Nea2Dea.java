package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.util.XmlUtils;
import de.hszg.atocc.core.util.automata.AutomataUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class Nea2Dea extends ServerResource {

    private static final String VALUE = "value";
    private static final String TYPE = "TYPE";
    private static final String FINALSTATE = "finalstate";
    
    private Document nea;
    private Set<Set<String>> neaStatePowerSet;
    private String neaInitialState;

    private Set<String> alphabet;

    private Document dea;
    private Element deaAutomatonElement;

    private Map<String, Set<String>> deaStateNameMap;
    private Map<String, Element> deaStateElementMap;

    private Set<String> deaFinalStates;
    private String deaInitialState;

    @Put
    public Document transform(final Document aNea) {
        this.nea = aNea;

        try {
            // TODO: validateAgainstSchema(nea);
            checkAutomatonType();

            initializeDeaDocument();
            extractDataFromNea(nea);
            generateDeaData();
            createDeaDocument();

            return XmlUtils.createResult(dea);
        } catch (final RuntimeException e) {
            return XmlUtils.createResultWithError("TRANSFORM_FAILED", e);
        }

    }

    private void generateDeaData() {
        generateNewStatesFrom(neaStatePowerSet);
        generateNewFinalStates();
        generateNewInitialState();
    }

    private void extractDataFromNea(final Document aNea) {
        neaStatePowerSet = AutomataUtils.getStatePowerSetFrom(aNea);
        neaInitialState = AutomataUtils.getNameOfInitialStateFrom(aNea);
        alphabet = AutomataUtils.getAlphabetFrom(aNea);
    }

    private void createDeaDocument() {
        createAutomatonElement();
        createTypeElement();
        createAlphabetElement();
        createNewTransitions();
        createInitialStateElement();
    }

    private void checkAutomatonType() {
        final Element automatonElement = nea.getDocumentElement();
        final Element typeElement = (Element) automatonElement.getElementsByTagName(TYPE).item(0);

        if (!"NEA".equals(typeElement.getAttribute(VALUE))) {
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }

    private void initializeDeaDocument() {
        dea = XmlUtils.createEmptyDocument();
    }

    private void createAutomatonElement() {
        deaAutomatonElement = dea.createElement("AUTOMATON");
        dea.appendChild(deaAutomatonElement);
    }

    private void createTypeElement() {
        final Element typeElement = dea.createElement(TYPE);
        typeElement.setAttribute(VALUE, "DEA");
        deaAutomatonElement.appendChild(typeElement);
    }

    private void createAlphabetElement() {
        final Element alphabetElement = dea.createElement("ALPHABET");
        deaAutomatonElement.appendChild(alphabetElement);

        for (String alphabetItem : alphabet) {
            final Element itemElement = dea.createElement("ITEM");
            itemElement.setAttribute(VALUE, alphabetItem);
            alphabetElement.appendChild(itemElement);
        }
    }

    private void generateNewStatesFrom(final Set<Set<String>> statePowerSet) {
        deaStateNameMap = new HashMap<String, Set<String>>();
        deaStateElementMap = new HashMap<String, Element>();

        int i = 0;
        for (Set<String> element : statePowerSet) {
            final String newName = generateNewStateName(i);
            deaStateNameMap.put(newName, element);
            deaStateElementMap.put(newName, createStateElement(newName));
            ++i;
        }
    }

    private String generateNewStateName(final int i) {
        return String.format("z_%d", i);
    }

    private Element createStateElement(final String name) {
        final Element stateElement = dea.createElement("STATE");
        stateElement.setAttribute("name", name);

        return stateElement;
    }

    private void generateNewFinalStates() {
        deaFinalStates = new HashSet<String>();
        final Set<String> neaFinalStates = AutomataUtils.getNamesOfFinalStatesFrom(nea);

        for (Entry<String, Set<String>> deaState : deaStateNameMap.entrySet()) {
            addToFinalStatesIfNeeded(neaFinalStates, deaState);
        }

        for (Entry<String, Element> deaState : deaStateElementMap.entrySet()) {
            if (isFinalState(deaState.getKey())) {
                deaState.getValue().setAttribute(FINALSTATE, "true");
            } else {
                deaState.getValue().setAttribute(FINALSTATE, "false");
            }
        }
    }

    private boolean isFinalState(final String name) {
        return deaFinalStates.contains(name);
    }

    private void addToFinalStatesIfNeeded(final Set<String> neaFinalStates,
            final Entry<String, Set<String>> newState) {
        for (String neaFinalState : neaFinalStates) {
            if (newState.getValue().contains(neaFinalState)) {
                deaFinalStates.add(newState.getKey());
            }
        }
    }

    private void generateNewInitialState() {
        final Set<String> setToSeach = new HashSet<String>();
        setToSeach.add(neaInitialState);

        for (Entry<String, Set<String>> deaState : deaStateNameMap.entrySet()) {
            if (deaState.getValue().equals(setToSeach)) {
                deaInitialState = deaState.getKey();

                return;
            }
        }
    }

    private void createInitialStateElement() {
        final Element initialStateElement = dea.createElement("INITIALSTATE");
        initialStateElement.setAttribute(VALUE, deaInitialState);
        deaAutomatonElement.appendChild(initialStateElement);
    }

    private void createNewTransitions() {
        final Set<String> processedStates = new HashSet<String>();
        final Set<String> remainingStates = new HashSet<String>();

        String currentState = deaInitialState;
        Set<String> originalStates = null;
        boolean finished = false;

        while (!finished) {
            originalStates = deaStateNameMap.get(currentState);

            for (String character : alphabet) {

                final Set<String> allOriginalStates = getTargets(originalStates, character);
                final String newState = getNewStateFor(allOriginalStates);

                final Element transitionElement = createTransitionElement(currentState, newState);
                createLabelElement(character, transitionElement);

                remainingStates.add(newState);
            }

            processedStates.add(currentState);

            // get next unprocessed state
            boolean foundNext = false;
            for (String s : remainingStates) {

                if (!processedStates.contains(s)) {
                    currentState = s;
                    foundNext = true;
                    break;
                }
            }

            if (!foundNext) {
                finished = true;
            }
        }

        for (String state : remainingStates) {
            final Element stateElement = deaStateElementMap.get(state);
            deaAutomatonElement.appendChild(stateElement);
        }
    }

    private String getNewStateFor(final Set<String> allOriginalStates) {
        String newState = "";

        for (Entry<String, Set<String>> entry : deaStateNameMap.entrySet()) {
            if (entry.getValue().equals(allOriginalStates)) {
                newState = entry.getKey();

                break;
            }
        }

        return newState;
    }

    private void createLabelElement(final String character, final Element transitionElement) {
        final Element labelElement = dea.createElement("LABEL");
        labelElement.setAttribute("read", character);
        transitionElement.appendChild(labelElement);
    }

    private Element createTransitionElement(final String currentState, final String newState) {
        final Element transitionElement = dea.createElement("TRANSITION");
        transitionElement.setAttribute("target", newState);
        deaStateElementMap.get(currentState).appendChild(transitionElement);
        return transitionElement;
    }

    private Set<String> getTargets(final Set<String> originalStates, final String character) {
        final Set<String> allOriginalStates = new HashSet<String>();

        for (String originalState : originalStates) {
            final Set<String> targets = AutomataUtils.getTargetsOf(nea, originalState, character);
            allOriginalStates.addAll(targets);
        }

        return allOriginalStates;
    }
}
