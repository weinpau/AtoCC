package de.hszg.atocc.compile;

import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class CompilationException extends Exception {

    private static final long serialVersionUID = 1L;

    public CompilationException(Throwable cause) {
        super(cause);
    }
    
    public CompilationException(List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        super(diagnostics.toString());
    }
    
}