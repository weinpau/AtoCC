package de.hszg.atocc.autoedit.minimize.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.RestfulWebService;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.XmlValidatorService;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;

import java.util.Set;

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

            minimalDea = minimize(dea);
            
            minimalDeaDocument = automatonUtils.automatonToXml(minimalDea);

            xmlValidator.validate(minimalDeaDocument, "AUTOMATON");

            result = xmlUtils.createResult(deaDocument);
        } catch (final RuntimeException e) {
            result = xmlUtils.createResultWithError(TRANSFORM_FAILED, e, getLocale());
        } catch (final SerializationException | XmlValidationException e) {
            result = xmlUtils.createResultWithError(INVALID_INPUT, e, getLocale());
        }

        return result;
    }
    
    public Automaton minimize(Automaton dea) {
        minimalDea = new Automaton(AutomatonType.DEA);
        
        initializeStateMatrix();

        markUnmergableStates();
        mergeStates();
        
        return minimalDea;
    }

    private void initialize(final Document deaDocument) throws XmlValidationException,
            SerializationException {
        xmlValidator.validate(deaDocument, "AUTOMATON");

        dea = automatonUtils.automatonFrom(deaDocument);
        checkAutomatonType();

        minimalDea = new Automaton(AutomatonType.DEA);
    }

    private void initializeStateMatrix() {
        final Set<String> stateSet = dea.getStates();
        
        states = stateSet.toArray(new String[stateSet.size()]);
        stateMatrix = new int[states.length][states.length];

        for (int i = 0; i < states.length; ++i) {
            for (int j = 0; j <= i; ++j) {
                stateMatrix[i][j] = INVALID;
            }
        }
        
        mergeStates();
    }

    private void markUnmergableStates() {
        for(int i = 0; i < states.length; ++i) {
            for(int j = i; j < states.length; ++j) {
                final String state1 = states[i];
                final String state2 = states[j];
                
                if(isUnmergable(state1, state2)) {
                    stateMatrix[i][j] = UNMERGABLE;
                }
            }
        }
        
        mergeStates();
        
        int numChanges = 0;
        
        do
        {
            numChanges = 0;
            for(int i = 0; i < states.length; ++i) {
                for(int j = i; j < states.length; ++j) {
                    if(stateMatrix[i][j] == MERGABLE) {
                        final String state1 = states[i];
                        final String state2 = states[j];
                        
                        for(String character : dea.getAlphabet()) {
                            final String target1 = dea.getTargetsFor(state1, character).iterator().next();
                            final String target2 = dea.getTargetsFor(state2, character).iterator().next();
                            
                            final int index1 = getIndexOf(target1);
                            final int index2 = getIndexOf(target2);
                            
                            if(stateMatrix[index1][index2] == UNMERGABLE) {
                                if(stateMatrix[i][j] != UNMERGABLE) {
                                    numChanges++;
                                }
                                
                                stateMatrix[i][j] = UNMERGABLE;
                            }
                        }
                    }
                }
            }
            
            mergeStates();
        } while(numChanges != 0);
    }
    
    private void mergeStates() {
        for(int i = 0; i < states.length; ++i) {
            for(int j = i; j < states.length; ++j) {
                if(stateMatrix[i][j] == MERGABLE) {
                    System.out.println("Merge: " + states[i] + " and " + states[j]);
                }
            }
        }
        System.out.println("--------------");
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
