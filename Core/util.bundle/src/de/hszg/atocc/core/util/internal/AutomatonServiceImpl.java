package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.AutomatonService;
import de.hszg.atocc.core.util.DeserializationException;
import de.hszg.atocc.core.util.SetService;
import de.hszg.atocc.core.util.XmlUtilService;
import de.hszg.atocc.core.util.automaton.Automaton;

import java.util.Set;
import java.util.TreeSet;

import org.w3c.dom.Document;

public final class AutomatonServiceImpl implements AutomatonService {

    private SetService setService;
    private XmlUtilService xmlUtils;

    @Override
    public Automaton automatonFrom(Document document) throws DeserializationException {
        final AutomatonDeserializer deserializer = new AutomatonDeserializer();

        return deserializer.deserialize(document);
    }

    @Override
    public Document automatonToXml(Automaton automaton) {
        final AutomatonSerializer serializer = new AutomatonSerializer(xmlUtils);

        return serializer.serialize(automaton);
    }

    @Override
    public Set<Set<String>> getStatePowerSetFrom(Automaton automaton) {
        return setService.powerSetFrom(automaton.getStates());
    }

    @Override
    public Set<String> getEpsilonHull(Automaton automaton, Set<String> states) {
        final Set<String> epsilonHull = new TreeSet<>();

        for (String state : states) {
            epsilonHull.addAll(getEpsilonHull(automaton, state));
        }

        return epsilonHull;
    }

    @Override
    public Set<String> getEpsilonHull(Automaton automaton, String stateName) {
        final Set<String> epsilonHull = new TreeSet<>();

        epsilonHull.add(stateName);

        for (String target : automaton.getTargetsFor(stateName, "EPSILON")) {
            epsilonHull.add(target);
        }

        return epsilonHull;
    }

    public synchronized void setSetService(SetService service) {
        setService = service;
    }

    public synchronized void unsetSetService(SetService service) {
        if (setService == service) {
            setService = null;
        }
    }

    public synchronized void setXmlUtilService(XmlUtilService service) {
        xmlUtils = service;
    }

    public synchronized void unsetXmlUtilService(XmlUtilService service) {
        if (xmlUtils == service) {
            xmlUtils = null;
        }
    }
}
