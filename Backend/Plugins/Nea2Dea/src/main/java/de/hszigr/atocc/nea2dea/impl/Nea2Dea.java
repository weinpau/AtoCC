package de.hszigr.atocc.nea2dea.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.hszigr.atocc.util.XmlUtils;
import de.hszigr.atocc.util.automata.AutomataUtils;

public class Nea2Dea extends ServerResource {
    
    private Document nea;
    
    private Document dea;
    private Element deaAutomatonElement;
    
    private Map<String, Set<String>> newStates;

    @Put
    public Document transform(Document nea) {
        this.nea = nea;
        
        try {
            // TODO: validateAgainstSchema(nea);
            checkAutomatonType();
            
            initializeDeaDocument();
            
            Set<Set<String>> statePowerSet = AutomataUtils.getStatePowerSetFrom(nea);
            generateNewStatesFrom(statePowerSet);
            
            //createStateElements(newStateNames);
            
            return XmlUtils.createResult(dea);
        } catch (final Exception e) {
            return XmlUtils.createResultWithError("TRANSFORM_FAILED", e.getMessage());
        }

    }

    private void checkAutomatonType() throws Exception {
        final Element automatonElement = nea.getDocumentElement();
        final Element typeElement = (Element) automatonElement.getElementsByTagName("TYPE").item(0);
        
        if(!"NEA".equals(typeElement.getAttribute("value"))) {
            throw new Exception("INVALID_AUTOMATON_TYPE");
        }
    }
    
    private void initializeDeaDocument() {
        dea = XmlUtils.createEmptyDocument();
        
        createAutomatonElement();
        createTypeElement();
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
    
    private void generateNewStatesFrom(Set<Set<String>> statePowerSet) {
        newStates = new HashMap<>();
        
        int i = 0;
        for(Set<String> element : statePowerSet) {
            newStates.put(generateNewStateName(i), element);
            ++i;
        }
    }
    
    private String generateNewStateName(int i) {
        return String.format("z_%d", i);
    }

    private void createStateElements() {
        for(Entry<String, Set<String>> entry : newStates.entrySet()) {
            createStateElement(entry);
        }
    }

    private void createStateElement(Entry<String, Set<String>> entry) {
        final Element stateElement = dea.createElement("STATE");
        stateElement.setAttribute("name", entry.getKey());
        deaAutomatonElement.appendChild(stateElement);
    }
}
