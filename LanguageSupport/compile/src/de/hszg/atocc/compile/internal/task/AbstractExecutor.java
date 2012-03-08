package de.hszg.atocc.compile.internal.task;

import de.hszg.atocc.compile.CompilationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class AbstractExecutor implements Executor {

    private ZipOutputStream zip;
    private Path tempDirectory;
    private TaskDefinition task;

    @Override
    public final void execute(TaskDefinition taskDefinition, ZipOutputStream stream)
            throws CompilationException, IOException {
        zip = stream;
        task = taskDefinition;

        initialize();
        execute();
        cleanUp();
    }

    protected final void addFileToZip(File file) throws IOException {
        byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        zip.putNextEntry(new ZipEntry(file.getName()));
        zip.write(data);
        zip.closeEntry();
    }

    protected final TaskDefinition getTask() {
        return task;
    }

    protected final Path getTempDirectory() {
        return tempDirectory;
    }

    protected void initialize() throws IOException {
        tempDirectory = Files.createTempDirectory("atocc.compile");
    }

    protected abstract void execute() throws CompilationException;

    protected void cleanUp() throws IOException {
        deleteTempDirectory();
    }

    private void deleteTempDirectory() throws IOException {
        Files.walkFileTree(tempDirectory, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {

                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

                if (exc == null) {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw exc;
                }
            }

        });
    }
}
