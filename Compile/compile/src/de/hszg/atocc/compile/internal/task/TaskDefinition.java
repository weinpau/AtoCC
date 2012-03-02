package de.hszg.atocc.compile.internal.task;

import de.hszg.atocc.compile.internal.util.TaskDefinitionDeserializer;

import org.w3c.dom.Document;

public class TaskDefinition {

    private String language = "Java";
    
    public static TaskDefinition from(Document document) {
        return TaskDefinitionDeserializer.deserialize(document);
    }

    public String getLanguage() {
        return language;
    }
    
}
