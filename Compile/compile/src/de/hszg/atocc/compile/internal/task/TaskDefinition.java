package de.hszg.atocc.compile.internal.task;

import de.hszg.atocc.compile.internal.util.TaskDefinitionDeserializer;
import de.hszg.atocc.core.util.SerializationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

public class TaskDefinition {

    private String language;
    private Map<String, String> sources = new HashMap<>();
    
    public static TaskDefinition from(Document document) throws SerializationException {
        return TaskDefinitionDeserializer.deserialize(document);
    }

    public void setLanguage(String lang) {
        language = lang;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void addSource(String filename, String source) {
        sources.put(filename, source);
    }
    
    public Map<String, String> getSources() {
        return Collections.unmodifiableMap(sources);
    }
    
}
