package de.hszg.atocc.compile.internal;

import de.hszg.atocc.compile.LanguageNotSupportedException;
import de.hszg.atocc.compile.internal.task.Executor;
import de.hszg.atocc.compile.internal.task.TaskDefinition;
import de.hszg.atocc.compile.internal.util.Base64Utils;
import de.hszg.atocc.compile.internal.util.ExecutorFactory;
import de.hszg.atocc.core.util.AbstractXmlTransformationService;
import de.hszg.atocc.core.util.XmlTransormationException;
import de.hszg.atocc.core.util.XmlUtilService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Compile extends AbstractXmlTransformationService {

    private XmlUtilService xmlUtils;

    private TaskDefinition task;
    private byte[] base64EncodedData;

    @Override
    protected void transform() throws XmlTransormationException {
        tryToGetRequiredServices();

        try {
//            validateInput("COMPILE_TASK");

            parseTaskDefinition();

            executeTask();

            prepareOutputDocument();

//        } catch (XmlValidationException e) {
//            throw new XmlTransormationException("Compile|INVALID_INPUT", e);
        } catch (IOException e) {
            throw new XmlTransormationException("Compile|EXECUTE_TASK_FAILED", e);
        } catch (LanguageNotSupportedException e) {
            throw new XmlTransormationException("Compile|UNSUPPORTED_LANGUAGE", e);
        }
    }

    private void parseTaskDefinition() {
        task = TaskDefinition.from(getInput());
    }

    private void executeTask() throws IOException, LanguageNotSupportedException {
        File zipFile = File.createTempFile("atocc.compile_result", ".zip");

        try (ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            final Executor executor = ExecutorFactory.createExecutorFor(task.getLanguage());

            executor.execute(task, stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FATAL ERROR: Could not find temp file");
        }
        
        base64EncodedData = Base64Utils.encode(zipFile);
    }

    private void prepareOutputDocument() {
        Document document = xmlUtils.createEmptyDocument();

        Element binaryElement = document.createElement("binary");
        binaryElement.setTextContent(new String(base64EncodedData));
        document.appendChild(binaryElement);

        setOutput(document);
    }

    private void tryToGetRequiredServices() {
        xmlUtils = getService(XmlUtilService.class);
    }
}
