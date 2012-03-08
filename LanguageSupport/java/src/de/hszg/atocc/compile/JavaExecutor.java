package de.hszg.atocc.compile;

import de.hszg.atocc.compile.internal.JavaSourceFromString;
import de.hszg.atocc.compile.internal.task.AbstractExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class JavaExecutor extends AbstractExecutor {

    private JavaCompiler compiler;
    private DiagnosticCollector<JavaFileObject> diagnostics;

    private Collection<JavaFileObject> compilationUnits = new LinkedList<>();

    @Override
    protected void initialize() throws IOException {
        super.initialize();

        compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new RuntimeException("No java compiler found");
        }

        diagnostics = new DiagnosticCollector<JavaFileObject>();
    }

    @Override
    protected void execute() throws CompilationException {

        final Map<String, String> sources = getTask().getSources();
        final Set<String> filenames = sources.keySet();

        for (String filename : filenames) {
            compilationUnits.add(new JavaSourceFromString(filename, sources.get(filename)));
        }

        compileSources();

        try {
            packageFiles();
        } catch (IOException ex) {
            throw new CompilationException(ex);
        }

    }

    private void compileSources() throws CompilationException {
        final Collection<String> compilerOptions = new ArrayList<>();
        compilerOptions.add("-d");
        compilerOptions.add(getTempDirectory().toAbsolutePath().toString());

        final CompilationTask compilationTask = compiler.getTask(null, null, diagnostics,
                compilerOptions, null, compilationUnits);

        if (!compilationTask.call()) {
            throw new CompilationException(diagnostics.getDiagnostics());
        }
    }

    private void packageFiles() throws IOException {
        final File dir = new File(getTempDirectory().toAbsolutePath().toString());
        final File[] files = dir.listFiles();

        for (File file : files) {
            addFileToZip(file);
        }
    }
}
