package de.hszg.atocc.compile.internal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public final class Base64Utils {

    public static byte[] encode(File file) throws FileNotFoundException, IOException {
        
        final int fileSize = (int) file.length();
        
        try (FileInputStream fis = new FileInputStream(file)) {
            final byte[] data = new byte[fileSize];
            final int bytesRead = fis.read(data, 0, fileSize);
            
            if(bytesRead != fileSize) {
                throw new RuntimeException("Could not read whole file: " + file.getAbsolutePath());
            }
            
            return Base64.encodeBase64(data);
        }

    }

}
