package de.hszg.atocc.compile.internal.task;

import de.hszg.atocc.compile.CompilationException;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

public interface Executor {

    void execute(TaskDefinition task, ZipOutputStream stream) throws CompilationException, IOException;
    
}
