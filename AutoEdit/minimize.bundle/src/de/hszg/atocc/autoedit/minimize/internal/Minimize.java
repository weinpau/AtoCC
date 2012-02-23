package de.hszg.atocc.autoedit.minimize.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.XmlValidatorService;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.InvalidStateException;
import de.hszg.atocc.core.util.automaton.InvalidTransitionException;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.SortedSet;

import org.restlet.resource.Post;
import org.w3c.dom.Document;

public final class Minimize extends RestfulWebService {

    private static final String INVALID_INPUT = "Minimize|INVALID_INPUT";
    private static final String TRANSFORM_FAILED = "Minimize|TRANSFORM_FAILED";

    private XmlUtilService xmlUtils;
    private AutomatonService automatonUtils;
    private XmlValidatorService xmlValidator;

    private Document minimalDeaDocument;
    private Automaton dea;
    private Automaton minimalDea;

    private Document result;

    private String[] states;
    private int[][] stateMatrix;

    private static final int INVALID = -1;
    private static final int MERGABLE = 0;
    private static final int UNMERGABLE = 1;

    @Post
    public Document minimize(Document deaDocument) {
        tryToGetRequiredServices();

        try {
            initialize(deaDocument);

            minimalDea = minimize();

            minimalDeaDocument = automatonUtils.automatonToXml(minimalDea);

            xmlValidator.validate(minimalDeaDocument, "AUTOMATON");

            result = xmlUtils.createResult(minimalDeaDocument);
        } catch (final RuntimeException e) {
            result = xmlUtils.createResultWithError(TRANSFORM_FAILED, e, getLocale());
        } catch (final SerializationException | XmlValidationException | InvalidStateException
                | InvalidTransitionException e) {
            result = xmlUtils.createResultWithError(INVALID_INPUT, e, getLocale());
        }

        return result;
    }

    public Automaton minimize() throws InvalidStateException, InvalidTransitionException {
        minimalDea = new Automaton(dea);

        step1();
        step2();
        step3();
        step4();

        mergeStates();

        return minimalDea;
    }

    private void initialize(final Document deaDocument) throws XmlValidationException,
            SerializationException {
        xmlValidator.validate(deaDocument, "AUTOMATON");

        dea = automatonUtils.automatonFrom(deaDocument);
        checkAutomatonType();
    }

    private void step1() {
        initializeStateMatrix();
    }

    private void step2() {
        for (int i = 0; i < states.length; ++i) {
            for (int j = i; j < states.length; ++j) {
                final String state1 = states[i];
                final String state2 = states[j];

                if (isUnmergable(state1, state2)) {
                    stateMatrix[i][j] = UNMERGABLE;
                }
            }
        }
    }

    private int step3() {
        int numChanges = 0;
        for (int i = 0; i < states.length; ++i) {
            for (int j = i; j < states.length; ++j) {
                if (stateMatrix[i][j] == MERGABLE) {
                    final String state1 = states[i];
                    final String state2 = states[j];

                    for (String character : dea.getAlphabet()) {
                        final String target1 = dea.getTargetsFor(state1, character).iterator()
                                .next();
                        final String target2 = dea.getTargetsFor(state2, character).iterator()
                                .next();

                        final int index1 = getIndexOf(target1);
                        final int index2 = getIndexOf(target2);

                        if (stateMatrix[index1][index2] == UNMERGABLE) {
                            if (stateMatrix[i][j] != UNMERGABLE) {
                                numChanges++;
                            }

                            stateMatrix[i][j] = UNMERGABLE;
                        }
                    }
                }
            }
        }

        return numChanges;
    }

    private void step4() {
        int numChanges = 0;
        do {
            numChanges = step3();
        } while (numChanges > 0);
    }

    private void initializeStateMatrix() {
        final SortedSet<String> stateSet = dea.getSortedStates();

        states = stateSet.toArray(new String[stateSet.size()]);
        stateMatrix = new int[states.length][states.length];

        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j <= i; ++j) {
                stateMatrix[i][j] = INVALID;
            }
        }
    }

    // private void printStateMatrix() {
    // System.out.println("StateMatrix");
    // for (int i = 0; i < states.length; ++i) {
    // for (int j = 0; j < states.length; ++j) {
    // String s = " ";
    //
    // if (stateMatrix[i][j] == INVALID) {
    // s = "!";
    // } else if (stateMatrix[i][j] == UNMERGABLE) {
    // s = "X";
    // }
    //
    // System.out.print(" " + s + " |");
    // }
    //
    // System.out.println();
    // }
    // }

    private void mergeStates() throws InvalidStateException, InvalidTransitionException {

        for (int i = 0; i < states.length; ++i) {
            for (int j = i; j < states.length; ++j) {
                if (stateMatrix[i][j] == MERGABLE) {
                    final Set<Transition> transitionsTo1 = dea.getTransitionsTo(states[i]);
                    final Set<Transition> transitionsTo2 = dea.getTransitionsTo(states[j]);

                    Collection<Transition> transitions = new LinkedList<>();
                    transitions.addAll(transitionsTo1);
                    transitions.addAll(transitionsTo2);

                    final boolean newStateIsFinalState = dea.getFinalStates().contains(states[i])
                            && dea.getFinalStates().contains(states[j]);

                    minimalDea.removeState(states[i]);
                    minimalDea.removeState(states[j]);

                    String newStateName = states[i] + states[j];
                    minimalDea.addState(newStateName);

                    if (newStateIsFinalState) {
                        minimalDea.addFinalState(newStateName);
                    }

                    for (Transition currentTransition : transitions) {
                        String sourceName = currentTransition.getSource();

                        if (sourceName.equals(states[i]) || sourceName.equals(states[j])) {
                            sourceName = newStateName;
                        }

                        final Transition newTransition = new Transition(sourceName, newStateName,
                                currentTransition.getCharacterToRead());
                        minimalDea.addTransition(newTransition);
                    }
                }
            }
        }

        System.out.println(minimalDea);
    }

    private boolean isUnmergable(String state1, String state2) {
        final Set<String> finalStates = dea.getFinalStates();

        if (finalStates.contains(state1) && !finalStates.contains(state2)
                || finalStates.contains(state2) && !finalStates.contains(state1)) {
            return true;
        }

        return false;
    }

    private void checkAutomatonType() {
        if (dea.getType() != AutomatonType.DEA) {
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }

    private int getIndexOf(String state) {
        for (int i = 0; i < states.length; ++i) {
            if (states[i].equals(state)) {
                return i;
            }
        }

        return INVALID;
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
        automatonUtils = getService(AutomatonService.class);
        xmlValidator = getService(XmlValidatorService.class);
    }
}
