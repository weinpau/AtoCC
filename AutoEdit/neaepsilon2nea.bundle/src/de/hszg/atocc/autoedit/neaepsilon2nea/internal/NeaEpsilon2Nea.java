package de.hszg.atocc.autoedit.neaepsilon2nea.internal;

import de.hszg.atocc.core.util.AbstractRestfulWebService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.XmlValidatorService;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Set;
import java.util.TreeSet;

import org.restlet.resource.Post;
import org.w3c.dom.Document;

public final class NeaEpsilon2Nea extends AbstractRestfulWebService {

    private static final String TRANSFORM_FAILED = "NeaEpsilon2Nea|TRANSFORM_FAILED";
    private static final String INVALID_INPUT = "NeaEpsilon2Nea|INVALID_INPUT";
    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;
    private XmlValidatorService xmlValidator;
    private SetService setService;

    private Document result;

    private Automaton neaEpsilon;
    private Automaton nea;

    @Post
    public Document transform(Document neaEpsilonDocument) {
        try {
            tryToGetRequiredServices();

            xmlValidator.validate(neaEpsilonDocument, "AUTOMATON");
            neaEpsilon = automatonUtils.automatonFrom(neaEpsilonDocument);
            checkAutomatonType();

            initializeOutputAutomaton();

            createNewDeltaRules();

            result = xmlUtils.createResult(automatonUtils.automatonToXml(nea));
        } catch (final SerializationException | XmlValidationException e) {
            result = xmlUtils.createResultWithError(INVALID_INPUT, e, getLocale());
        } catch (final RuntimeException | InvalidTransitionException | InvalidStateException e) {
            result = xmlUtils.createResultWithError(TRANSFORM_FAILED, e, getLocale());
        }

        return result;
    }

    private void initializeOutputAutomaton() throws InvalidStateException {
        nea = new Automaton(AutomatonType.NEA);
        nea.setAlphabet(neaEpsilon.getAlphabet());
        nea.setStates(neaEpsilon.getStates());
        nea.setInitialState(neaEpsilon.getInitialState());
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
        automatonUtils = getService(AutomatonService.class);
        xmlValidator = getService(XmlValidatorService.class);
        setService = getService(SetService.class);
    }

    private void checkAutomatonType() {
        if (neaEpsilon.getType() != AutomatonType.NEA) {
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }

    private void createNewDeltaRules() throws InvalidTransitionException, InvalidStateException {
        for (String stateName : neaEpsilon.getStates()) {
            for (String character : neaEpsilon.getAlphabet()) {
                createNewDeltaRuleFor(stateName, character);
            }
        }
    }

    private void createNewDeltaRuleFor(String stateName, String character)
            throws InvalidTransitionException, InvalidStateException {
        final Set<String> epsilonHull = automatonUtils.getEpsilonHull(neaEpsilon, stateName);

        if (setService.containsAnyOf(epsilonHull, neaEpsilon.getFinalStates())) {
            nea.addFinalState(stateName);
        }

        final Set<String> d = new TreeSet<>();

        for (String state : epsilonHull) {
            d.addAll(neaEpsilon.getTargetsFor(state, character));
        }

        createTransitions(stateName, character, d);
    }

    private void createTransitions(String stateName, String character, Set<String> d)
            throws InvalidTransitionException {
        for (String stateForNewRule : automatonUtils.getEpsilonHull(neaEpsilon, d)) {
            final Transition transition = new Transition(stateName, stateForNewRule, character);

            nea.addTransition(transition);
        }
    }
}
