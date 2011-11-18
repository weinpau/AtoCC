package de.hszigr.atocc.nea2dea.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.hszigr.atocc.util.XmlUtils;
import de.hszigr.atocc.util.automata.AutomataUtils;

public class Nea2Dea extends ServerResource {

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
    public Document transform(Document nea) {
        this.nea = nea;

        try {
            // TODO: validateAgainstSchema(nea);
            checkAutomatonType();

            initializeDeaDocument();

            neaStatePowerSet = AutomataUtils.getStatePowerSetFrom(nea);
            neaInitialState = AutomataUtils.getNameOfInitialStateFrom(nea);
            alphabet = AutomataUtils.getAlphabetFrom(nea);

            generateNewStatesFrom(neaStatePowerSet);
            generateNewFinalStates();
            generateNewInitialState();
            
            createAutomatonElement();
            createTypeElement();
            createAlphabetElement();
            createNewTransitions();
            createInitialStateElement();

            return XmlUtils.createResult(dea);
        } catch (final Exception e) {
            return XmlUtils.createResultWithError("TRANSFORM_FAILED", e.getMessage());
        }

    }

    private void checkAutomatonType() throws Exception {
        final Element automatonElement = nea.getDocumentElement();
        final Element typeElement = (Element) automatonElement.getElementsByTagName("TYPE").item(0);

        if (!"NEA".equals(typeElement.getAttribute("value"))) {
            throw new Exception("INVALID_AUTOMATON_TYPE");
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
        final Element typeElement = dea.createElement("TYPE");
        typeElement.setAttribute("value", "DEA");
        deaAutomatonElement.appendChild(typeElement);
    }
    
    private void createAlphabetElement() {
        final Element alphabetElement = dea.createElement("ALPHABET");
        deaAutomatonElement.appendChild(alphabetElement);
        
        for(String alphabetItem : alphabet) {
            final Element itemElement = dea.createElement("ITEM");
            itemElement.setAttribute("value", alphabetItem);
            alphabetElement.appendChild(itemElement);
        }
    }

    private void generateNewStatesFrom(Set<Set<String>> statePowerSet) {
        deaStateNameMap = new HashMap<>();
        deaStateElementMap = new HashMap<>();

        int i = 0;
        for (Set<String> element : statePowerSet) {
            final String newName = generateNewStateName(i);
            deaStateNameMap.put(newName, element);
            deaStateElementMap.put(newName, createStateElement(newName));
            ++i;
        }
    }

    private String generateNewStateName(int i) {
        return String.format("z_%d", i);
    }

    private Element createStateElement(String name) {
        final Element stateElement = dea.createElement("STATE");
        stateElement.setAttribute("name", name);
        // deaAutomatonElement.appendChild(stateElement);

        return stateElement;
    }

    private void generateNewFinalStates() {
        deaFinalStates = new HashSet<>();
        final Set<String> neaFinalStates = AutomataUtils.getNamesOfFinalStatesFrom(nea);

        for (Entry<String, Set<String>> deaState : deaStateNameMap.entrySet()) {
            addToFinalStatesIfNeeded(neaFinalStates, deaState);
        }

        for (Entry<String, Element> deaState : deaStateElementMap.entrySet()) {
            if (isFinalState(deaState.getKey())) {
                deaState.getValue().setAttribute("finalstate", "true");
            } else {
                deaState.getValue().setAttribute("finalstate", "false");
            }
        }
    }

    private boolean isFinalState(final String name) {
        return deaFinalStates.contains(name);
    }

    private void addToFinalStatesIfNeeded(final Set<String> neaFinalStates,
            Entry<String, Set<String>> newState) {
        for (String neaFinalState : neaFinalStates) {
            if (newState.getValue().contains(neaFinalState)) {
                deaFinalStates.add(newState.getKey());
            }
        }
    }

    private void generateNewInitialState() {
        final Set<String> setToSeach = new HashSet<>();
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
        initialStateElement.setAttribute("value", deaInitialState);
        deaAutomatonElement.appendChild(initialStateElement);
    }

    private void createNewTransitions() {
        final Set<String> processedStates = new HashSet<String>();
        final Set<String> remainingStates = new HashSet<String>();

        String currentState = deaInitialState;
        Set<String> qs = null;
        boolean finished = false;

        while (!finished) {
            qs = deaStateNameMap.get(currentState);
            for (String character : alphabet) {
                System.out.println(String.format("d'(%s,%s) = d'(%s,%s)", currentState, character,
                        qs, character));

                final Set<String> originalStates = new HashSet<>();

                for (String q : qs) {
                    final Set<String> targets = AutomataUtils.getTargetsOf(nea, q, character);
                    originalStates.addAll(targets);
                }

                String newState = "";

                for (Entry<String, Set<String>> entry : deaStateNameMap.entrySet()) {
                    if (entry.getValue().equals(originalStates)) {
                        if (!originalStates.isEmpty()) {
                            newState = entry.getKey();
                            break;
                        }

                    }
                }
                
                // create transition element
                final Element transitionElement = dea.createElement("TRANSITION");
                transitionElement.setAttribute("target", newState);
                deaStateElementMap.get(currentState).appendChild(transitionElement);
                
                final Element labelElement = dea.createElement("LABEL");
                labelElement.setAttribute("read", character);
                transitionElement.appendChild(labelElement);

                System.out.println(String.format("%s = %s", originalStates, newState));
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
            
            if(!foundNext) {
                finished = true;
            }
        }
        
        for(String state : remainingStates) {
            final Element stateElement = deaStateElementMap.get(state);
            deaAutomatonElement.appendChild(stateElement);
        }
    }
}
