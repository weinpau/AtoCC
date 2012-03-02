package de.hszg.atocc.compile;

import de.hszg.atocc.compile.internal.JavaSourceFromString;
import de.hszg.atocc.compile.internal.task.Executor;
import de.hszg.atocc.compile.internal.task.TaskDefinition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

public class JavaExecutor implements Executor {

    private JavaCompiler compiler;
    private DiagnosticCollector<JavaFileObject> diagnostics;

    private Collection<JavaFileObject> compilationUnits = new LinkedList<>();

    private Path tempDirectory;

    private ZipOutputStream zip;

    @Override
    public void execute(TaskDefinition task, ZipOutputStream stream) throws CompilationException,
            IOException {
        zip = stream;

        initialize();

        final Map<String, String> sources = task.getSources();
        final Set<String> filenames = sources.keySet();

        for (String filename : filenames) {
            compilationUnits.add(new JavaSourceFromString(filename, sources.get(filename)));
        }

        compileSources();

        packageFiles();

        cleanUp();
    }

    private void initialize() throws IOException {
        compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new RuntimeException("No java compiler found");
        }

        diagnostics = new DiagnosticCollector<JavaFileObject>();

        tempDirectory = Files.createTempDirectory("atocc.compile.java");
    }

    private void cleanUp() {

    }

    private void compileSources() throws CompilationException {
        final Collection<String> compilerOptions = new ArrayList<>();
        compilerOptions.add("-d");
        compilerOptions.add(tempDirectory.toAbsolutePath().toString());

        final CompilationTask compilationTask = compiler.getTask(null, null, diagnostics,
                compilerOptions, null, compilationUnits);

        boolean result = compilationTask.call();

        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            System.out.println(diagnostic.getCode());
            System.out.println(diagnostic.getKind());
            System.out.println(diagnostic.getPosition());
            System.out.println(diagnostic.getStartPosition());
            System.out.println(diagnostic.getEndPosition());
            System.out.println(diagnostic.getSource());
            System.out.println(diagnostic.getMessage(null));
        }

        if (!result) {
            throw new CompilationException(diagnostics.getDiagnostics());
        }
    }

    private void packageFiles() {
        final File dir = new File(tempDirectory.toAbsolutePath().toString());
        final File[] files = dir.listFiles();

        for (File file : files) {
            try {
                byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
                zip.putNextEntry(new ZipEntry(file.getName()));
                zip.write(data);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
