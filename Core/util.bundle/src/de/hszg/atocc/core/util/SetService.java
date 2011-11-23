package de.hszg.atocc.core.util;

import java.util.Set;

public interface SetService {

    <T> Set<Set<T>> powerSetFrom(Set<T> originalSet);
    
}
