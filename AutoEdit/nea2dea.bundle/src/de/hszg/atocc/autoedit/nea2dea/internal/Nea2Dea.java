package de.hszg.atocc.autoedit.nea2dea.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.DeserializationException;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.WebUtilService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.XmlValidatorService;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.restlet.resource.Post;
import org.w3c.dom.Document;

public final class Nea2Dea extends RestfulWebService {

    private static final String INVALID_INPUT = "Nea2Dea|INVALID_INPUT";
    private static final String TRANSFORM_FAILED = "Nea2Dea|TRANSFORM_FAILED";

    private static final String AUTOMATON = "AUTOMATON";

    private Automaton nea;
    private Automaton dea;
    private Document deaDocument;

    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;
    private XmlValidatorService xmlValidator;
    private WebUtilService webUtils;

    private Set<String> processedStates;

    private Document result;

    private Map<String, Set<String>> deaToNeaStateMap;

    private String currentState;

    @Post
    public Document transform(final Document neaDocument) {
        tryToGetRequiredServices();

        try {
            initialize(neaDocument);

            transformAutomaton();

            xmlValidator.validate(deaDocument, AUTOMATON);

            result = xmlUtils.createResult(deaDocument);
        } catch (final RuntimeException | InvalidTransitionException | InvalidStateException e) {
            result = xmlUtils.createResultWithError(TRANSFORM_FAILED, e, getLocale());
        } catch (final DeserializationException | XmlValidationException e) {
            result = xmlUtils.createResultWithError(INVALID_INPUT, e, getLocale());
        }

        return result;
    }

    private void initialize(final Document neaDocument) throws XmlValidationException,
            DeserializationException {
        xmlValidator.validate(neaDocument, AUTOMATON);
        nea = automatonUtils.automatonFrom(neaDocument);
        dea = new Automaton(AutomatonType.DEA);
        dea.setAlphabet(nea.getAlphabet());

        checkAutomatonType();

        removeEpsilonRulesIfNeeded();
    }

    private void removeEpsilonRulesIfNeeded() throws DeserializationException {
        System.out.println("EPSILON: " + nea.containsEpsilonRules());
        if (nea.containsEpsilonRules()) {
            final Document neaDocument = automatonUtils.automatonToXml(nea);

            final Document serviceResult = webUtils.post(
                    "http://localhost:8081/autoedit/neaepsilon2nea", neaDocument);

            final Document resultNeaDocument = xmlUtils.getContent(serviceResult);

            nea = automatonUtils.automatonFrom(resultNeaDocument);
        }
    }

    private void transformAutomaton() throws InvalidTransitionException, InvalidStateException {
        generateNewStatesFrom(automatonUtils.getStatePowerSetFrom(nea));

        generateNewInitialState();
        createNewTransitions();
        generateNewFinalStates();

        deaDocument = automatonUtils.automatonToXml(dea);
    }

    private void checkAutomatonType() {
        if (nea.getType() != AutomatonType.NEA) {
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }

    private void generateNewStatesFrom(final Set<Set<String>> statePowerSet) {
        deaToNeaStateMap = new HashMap<>();

        int i = 0;
        for (Set<String> elementFromPowerset : statePowerSet) {
            final String newName = generateNewStateName(i);
            deaToNeaStateMap.put(newName, elementFromPowerset);
            ++i;
        }
    }

    private String generateNewStateName(final int i) {
        return String.format("z_%d", i);
    }

    private void generateNewFinalStates() throws InvalidStateException {
        for (Entry<String, Set<String>> deaState : deaToNeaStateMap.entrySet()) {
            addToFinalStatesIfNeeded(deaState);
        }
    }

    private void addToFinalStatesIfNeeded(Entry<String, Set<String>> newState)
            throws InvalidStateException {
        for (String neaFinalState : nea.getFinalStates()) {
            if (newState.getValue().contains(neaFinalState)
                    && dea.getStates().contains(newState.getKey())) {
                dea.addFinalState(newState.getKey());
            }
        }
    }

    private void generateNewInitialState() {
        final Set<String> setToSearch = new HashSet<>();
        setToSearch.add(nea.getInitialState());

        for (Entry<String, Set<String>> deaState : deaToNeaStateMap.entrySet()) {
            if (deaState.getValue().equals(setToSearch)) {
                dea.setInitialState(deaState.getKey());

                return;
            }
        }
    }

    private void createNewTransitions() throws InvalidTransitionException {
        processedStates = new HashSet<>();

        currentState = dea.getInitialState();
        Set<String> originalStates = null;
        boolean finished = false;

        while (!finished) {
            originalStates = deaToNeaStateMap.get(currentState);

            for (String character : nea.getAlphabet()) {
                final Set<String> allOriginalStates = getTargets(originalStates, character);
                final String newState = getNewStateFor(allOriginalStates);

                dea.addState(currentState);
                dea.addState(newState);
                dea.addTransition(new Transition(currentState, newState, character));
            }

            processedStates.add(currentState);

            finished = getNextUnprocessedState();
        }
    }

    private boolean getNextUnprocessedState() {
        boolean finished = false;
        boolean foundNext = false;

        for (String s : dea.getStates()) {

            if (!processedStates.contains(s)) {
                currentState = s;
                foundNext = true;

                break;
            }
        }

        if (!foundNext) {
            finished = true;
        }

        return finished;
    }

    private String getNewStateFor(final Set<String> allOriginalStates) {
        String newState = "";

        for (Entry<String, Set<String>> entry : deaToNeaStateMap.entrySet()) {
            if (entry.getValue().equals(allOriginalStates)) {
                newState = entry.getKey();

                break;
            }
        }

        return newState;
    }

    private Set<String> getTargets(final Set<String> originalStates, final String character) {
        final Set<String> allOriginalStates = new HashSet<>();

        for (String originalState : originalStates) {
            final Set<String> targets = nea.getTargetsFor(originalState, character);
            allOriginalStates.addAll(targets);
        }

        return allOriginalStates;
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
        automatonUtils = getService(AutomatonService.class);
        xmlValidator = getService(XmlValidatorService.class);
        webUtils = getService(WebUtilService.class);
    }
}
