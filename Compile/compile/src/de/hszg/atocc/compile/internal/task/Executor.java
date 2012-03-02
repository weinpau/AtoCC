package de.hszg.atocc.compile.internal.task;

import java.util.zip.ZipOutputStream;

public interface Executor {

    void execute(TaskDefinition task, ZipOutputStream stream);
    
}
