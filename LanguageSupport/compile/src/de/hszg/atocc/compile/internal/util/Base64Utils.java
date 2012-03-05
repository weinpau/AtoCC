package de.hszg.atocc.compile.internal.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.restlet.engine.util.Base64;

public final class Base64Utils {

    public static String encode(File file) throws FileNotFoundException, IOException {

        final byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));

        return Base64.encode(data, false);

    }

}
