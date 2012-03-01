package de.hszg.atocc.autoedit.export.grammar.internal;

import de.hszg.atocc.core.util.AbstractXmlTransformationService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.CollectionHelper;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlTransormationException;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportGrammar extends AbstractXmlTransformationService {

    private AutomatonService automatonUtils;
    private XmlUtilService xmlUtils;

    private Automaton automaton;
    private StringBuilder grammar = new StringBuilder();

    @Override
    protected void transform() throws XmlTransormationException {
        tryToGetRequiredServices();

        try {
            validateInput("AUTOMATON");
            
            automaton = automatonUtils.automatonFrom(getInput());
            checkAutomatonType();

            automatonToGrammar();

            createGrammarDocument();

        } catch (final XmlValidationException | SerializationException | RuntimeException e) {
            throw new XmlTransormationException("ExportGrammar|INVALID_INPUT", e);
        }
    }

    private void tryToGetRequiredServices() {
        automatonUtils = getService(AutomatonService.class);
        xmlUtils = getService(XmlUtilService.class);
    }
    
    private void checkAutomatonType() {
        if(automaton.getType() != AutomatonType.DEA && automaton.getType() != AutomatonType.NEA) {
            throw new RuntimeException("INVALID_AUTOMATON_TYPE");
        }
    }

    private void automatonToGrammar() {
        for (String state : automaton.getStates()) {
            final Set<Transition> transitions = automaton.getTransitionsFrom(state);

            final List<String> righthandSides = new LinkedList<>();

            for (Transition transition : transitions) {
                String target = transition.getTarget();

                righthandSides.add(transition.getCharacterToRead() + " " + target);

                if (automaton.isFinalState(target)) {
                    righthandSides.add(transition.getCharacterToRead());
                }
            }
            
            Collections.sort(righthandSides);
            
            grammar.append(String.format("%s -> %s\n", state, CollectionHelper.makeString(righthandSides, " | ")));
        }
        
        if (automaton.isFinalState(automaton.getInitialState())) {
            grammar.append(String.format("%s -> EPSILON", automaton.getInitialState()));
        }
    }

    private void createGrammarDocument() {
        final Document document = xmlUtils.createEmptyDocument();

        Element grammarElement = document.createElement("grammar");
        grammarElement.setTextContent(grammar.toString());
        document.appendChild(grammarElement);
        
        System.out.println(grammar.toString());

        setOutput(document);
    }
}
