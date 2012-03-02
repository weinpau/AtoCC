package de.hszg.atocc.compile.internal.util;

import de.hszg.atocc.compile.internal.task.TaskDefinition;

import org.w3c.dom.Document;

public final class TaskDefinitionDeserializer {

    private TaskDefinitionDeserializer() {
        
    }
    
    public static TaskDefinition deserialize(Document document) {
        return new TaskDefinition();
    }
    
}
