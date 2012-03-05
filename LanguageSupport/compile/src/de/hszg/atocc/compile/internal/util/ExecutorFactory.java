package de.hszg.atocc.compile.internal.util;

import de.hszg.atocc.compile.LanguageNotSupportedException;
import de.hszg.atocc.compile.internal.task.Executor;

public final class ExecutorFactory {

    private static final String PACKAGE = "de.hszg.atocc.compile";
    
    private ExecutorFactory() {
        
    }
    
    public static Executor createExecutorFor(String language) throws LanguageNotSupportedException {
        Executor executor = null;
        
        try {
            final String className = String.format("%s.%sExecutor", PACKAGE, language);
            
            Class<? extends Executor> executorClass = Class.forName(className).asSubclass(Executor.class);
            executor = executorClass.newInstance();
            
        } catch (ClassNotFoundException e) {
            throw new LanguageNotSupportedException(language);
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate executor");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access executor constructor");
        }
        
        return executor;
    }
}
