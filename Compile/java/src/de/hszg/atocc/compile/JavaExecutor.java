package de.hszg.atocc.compile;

import de.hszg.atocc.compile.internal.task.Executor;
import de.hszg.atocc.compile.internal.task.TaskDefinition;

import java.util.zip.ZipOutputStream;

public class JavaExecutor implements Executor {

    @Override
    public void execute(TaskDefinition task, ZipOutputStream stream) {
        System.out.println("Executing java stuff");

    }

}
