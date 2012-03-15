package de.hszg.atocc.autoedit.pda7topda6.internal;

import de.hszg.atocc.core.util.AbstractXmlTransformationService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.CharacterHelper;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.StateNameHelper;
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

public class Pda7ToPda6 extends AbstractXmlTransformationService {

    private AutomatonService automatonUtils;
    private Automaton pda7;
    private Automaton pda6;

    private boolean pda7StatesAreEnumerated;

    private Map<String, String> stateNameMappings = new HashMap<>();
    
    private String newInitialState;
    private String newFinalState;

    @Override
    protected void transform() throws XmlTransormationException {
        tryToGetRequiredServices();

        try {
            validateInput("AUTOMATON");

            pda7 = automatonUtils.automatonFrom(getInput());
            pda7StatesAreEnumerated = StateNameHelper.areStatesEnumerated(pda7.getStates());
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
        if (pda7StatesAreEnumerated) {
            for (String stateName : pda7.getSortedStates()) {
                stateNameMappings.put(stateName, stateName);
            }
        } else {
            int i = 0;
            for (String stateName : pda7.getSortedStates()) {
                String newName = String.format("%s_%d",
                        CharacterHelper.firstCharacterDifferentTo(stateName.charAt(0)), i);

                stateNameMappings.put(stateName, newName);
                ++i;
            }
        }
    }

    private void createNewStateSet() throws InvalidStateException {
        for (String stateName : pda7.getStates()) {
            pda6.addState(stateNameMappings.get(stateName));
        }
        
        if (pda7StatesAreEnumerated) {
            newInitialState = StateNameHelper.generateNextState(pda6.getStates());
            pda6.addState(newInitialState);
            pda6.setInitialState(newInitialState);
            
            newFinalState = StateNameHelper.generateNextState(pda6.getStates());

            pda6.addState(newFinalState);
            pda6.addFinalState(newFinalState);
        } else {
            newInitialState = "q_0";
            pda6.addState(newInitialState);
            pda6.setInitialState(newInitialState);

            newFinalState = "q_e";
            pda6.addState(newFinalState);
            pda6.addFinalState(newFinalState);
        }
        
    }

    private void createNewAlphabet() {
        pda6.setAlphabet(pda7.getAlphabet());
    }

    private void createNewStackAlphabet() throws InvalidAlphabetCharacterException {
        pda6.setStackAlphabet(pda7.getStackAlphabet());

        final char newInitialStackSymbol = CharacterHelper.characterDifferentToAnyOf(pda7
                .getStackAlphabet());

        final String symbolName = String.valueOf(newInitialStackSymbol);
        pda6.addStackAlphabetItem(symbolName);
        pda6.setInitialStackSymbol(symbolName);
    }

    private void createNewTransitions() throws InvalidTransitionException {

        for (String finalState : pda7.getFinalStates()) {
            for (String stackSymbol : pda6.getStackAlphabet()) {
                final Transition transition = new Transition(stateNameMappings.get(finalState),
                        newFinalState, Automaton.EPSILON, stackSymbol, Automaton.EPSILON);

                pda6.addTransition(transition);
            }
        }

        for (String stackSymbol : pda6.getStackAlphabet()) {
            pda6.addTransition(new Transition(newFinalState, newFinalState, Automaton.EPSILON, stackSymbol,
                    Automaton.EPSILON));
        }

        final String oldInitialStateName = pda7.getInitialState();
        pda6.addTransition(new Transition(newInitialState, stateNameMappings.get(oldInitialStateName),
                Automaton.EPSILON, pda6.getInitialStackSymbol(), String.format("%s%s",
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

}
