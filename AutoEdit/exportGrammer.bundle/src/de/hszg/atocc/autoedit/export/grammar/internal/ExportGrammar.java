package de.hszg.atocc.autoedit.export.grammar.internal;

import de.hszg.atocc.autoedit.export.grammar.internal.exporters.Exporter;
import de.hszg.atocc.autoedit.export.grammar.internal.exporters.ExporterFactory;
import de.hszg.atocc.core.util.AbstractXmlTransformationService;
import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.SerializationException;
import de.hszg.atocc.core.util.XmlTransormationException;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.XmlValidationException;
import de.hszg.atocc.core.util.automaton.Automaton;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportGrammar extends AbstractXmlTransformationService {

    private AutomatonService automatonUtils;
    private XmlUtilService xmlUtils;

    private Automaton automaton;
    private Exporter exporter;
    private Grammar grammar;

    @Override
    protected void transform() throws XmlTransormationException {
        tryToGetRequiredServices();

        try {
            validateInput("AUTOMATON");
            
            automaton = automatonUtils.automatonFrom(getInput());
            createExporter();

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
    
    private void createExporter() {
        exporter = ExporterFactory.create(automaton.getType());
    }

    private void automatonToGrammar() {
        grammar = exporter.export(automaton);
    }

    private void createGrammarDocument() {
        final Document document = xmlUtils.createEmptyDocument();

        Element grammarElement = document.createElement("grammar");
        grammarElement.setTextContent(grammar.toString());
        document.appendChild(grammarElement);

        setOutput(document);
    }
}
