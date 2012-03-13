package de.hszg.atocc.autoedit.pda7topda6.internal;

import de.hszg.atocc.core.util.AbstractXmlTransformationService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlTransormationException;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidAlphabetCharacterException;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Pda7ToPda6 extends AbstractXmlTransformationService {

    private AutomatonService automatonUtils;
    private Automaton pda7;
    private Automaton pda6;

    private Map<String, String> stateNameMappings = new HashMap<>();

    @Override
    protected void transform() throws XmlTransormationException {
        tryToGetRequiredServices();

        try {
            validateInput("AUTOMATON");

            pda7 = automatonUtils.automatonFrom(getInput());
            pda7ToPda6();
        } catch (final XmlValidationException | SerializationException e) {
            throw new XmlTransormationException("Pda7ToPda6|INVALID_INPUT", e);
        } catch (InvalidStateException | InvalidAlphabetCharacterException
                | InvalidTransitionException e) {
            throw new XmlTransormationException("Pda7ToPda6|TRANSFORMATION_ERROR", e);
        }

        try {
            setOutput(automatonUtils.automatonToXml(pda6));
            validateOutput("AUTOMATON");
        } catch (final SerializationException | XmlValidationException e) {
            throw new XmlTransormationException("Pda7ToPda6|INVALID_OUTPUT", e);
        }

    }

    private void tryToGetRequiredServices() {
        automatonUtils = getService(AutomatonService.class);
    }

    private void pda7ToPda6() throws InvalidStateException, InvalidAlphabetCharacterException,
            InvalidTransitionException {
        pda6 = new Automaton(AutomatonType.NKA);

        generateStateNameMappings();

        createNewStateSet();
        createNewAlphabet();
        createNewStackAlphabet();
        createNewTransitions();

    }

    private void generateStateNameMappings() {

        int i = 0;
        for (String stateName : pda7.getSortedStates()) {
            String newName = String.format("%s_%d", firstCharacterDifferentTo(stateName.charAt(0)),
                    i);

            stateNameMappings.put(stateName, newName);
            ++i;
        }
    }

    private void createNewStateSet() throws InvalidStateException {
        pda6.addState("q_0");
        pda6.setInitialState("q_0");

        pda6.addState("q_e");
        pda6.addFinalState("q_e");

        for (String stateName : pda7.getStates()) {
            pda6.addState(stateNameMappings.get(stateName));
        }
    }

    private void createNewAlphabet() {
        pda6.setAlphabet(pda7.getAlphabet());
    }

    private void createNewStackAlphabet() throws InvalidAlphabetCharacterException {
        pda6.setStackAlphabet(pda7.getStackAlphabet());

        final char newInitialStackSymbol = characterDifferentToAnyOf(pda7.getStackAlphabet());

        final String symbolName = String.valueOf(newInitialStackSymbol);
        pda6.addStackAlphabetItem(symbolName);
        pda6.setInitialStackSymbol(symbolName);
    }

    private void createNewTransitions() throws InvalidTransitionException {
        
        for (String finalState : pda7.getFinalStates()) {
            for (String stackSymbol : pda6.getStackAlphabet()) {
                final Transition transition = new Transition(stateNameMappings.get(finalState),
                        "q_e", "EPSILON", stackSymbol, "EPSILON");

                pda6.addTransition(transition);
            }
        }

        for (String stackSymbol : pda7.getStackAlphabet()) {
            pda6.addTransition(new Transition("q_e", "q_e", "EPSILON", stackSymbol, "EPSILON"));
        }

        final String oldInitialStateName = pda7.getInitialState();
        pda6.addTransition(new Transition("q_0", stateNameMappings.get(oldInitialStateName),
                "EPSILON", pda6.getInitialStackSymbol(), String.format("%s%s",
                        pda7.getInitialStackSymbol(), pda6.getInitialStackSymbol())));

        for (String oldStateName : pda7.getStates()) {
            for (Transition transition : pda7.getTransitionsFrom(oldStateName)) {
                final String target = transition.getTarget();

                final Transition newTransition = new Transition(
                        stateNameMappings.get(oldStateName), stateNameMappings.get(target),
                        transition.getCharacterToRead(), transition.getTopOfStack(),
                        transition.getCharacterToWrite());
                
                pda6.addTransition(newTransition);
            }
        }

    }

    private char firstCharacterDifferentTo(char c) {
        for (int i = 'a'; i <= 'z'; ++i) {
            if (c != (char) i) {
                return (char) i;
            }
        }

        throw new RuntimeException("Should not reach this point");
    }

    private char characterDifferentToAnyOf(Set<String> stackAlphabet) {

        for (char c = '!'; c <= '~'; ++c) {
            if (!stackAlphabet.contains(String.valueOf(c))) {
                return c;
            }
        }

        throw new RuntimeException("No character found which is not in set");
    }
}
