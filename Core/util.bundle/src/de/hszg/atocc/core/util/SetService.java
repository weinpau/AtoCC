package de.hszg.atocc.core.util;

import java.util.Set;

public interface SetService {

    <T> Set<Set<T>> powerSetFrom(Set<T> originalSet);

    /**
     * Checks if the first set contains any elements of the second set.
     * 
     * @param s1
     *            - the first set
     * @param s2
     *            - the second set
     * @return true if s1 contains one or more elements from s2, false otherwise
     */
    <T> boolean containsAnyOf(Set<T> s1, Set<T> s2);

    <T> Set<T> createSetWith(@SuppressWarnings("unchecked") T... args);

}
