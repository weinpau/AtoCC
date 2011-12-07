package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.SetService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SetServiceImpl implements SetService {

    public <T> Set<Set<T>> powerSetFrom(final Set<T> originalSet) {
        if (originalSet.isEmpty()) {
            return setOnlyContainingEmptySet();
        } else {
            return powerSetFromNonEmptySet(originalSet);
        }
    }
    
    public <T> boolean containsAnyOf(Set<T> s1, Set<T> s2) {
        for (T element : s2) {
            if (s1.contains(element)) {
                return true;
            }
        }

        return false;
    }

    public <T> Set<T> createSetWith(@SuppressWarnings("unchecked") T ... args) {
        final Set<T> set = new HashSet<>();
        
        set.addAll(Arrays.asList(args));
        
        return set;
    };
    
    private <T> Set<Set<T>> powerSetFromNonEmptySet(final Set<T> originalSet) {
        final Set<Set<T>> sets = new HashSet<>();

        final List<T> list = new ArrayList<>(originalSet);

        final T head = list.get(0);

        final Set<T> rest = new HashSet<>(list.subList(1, list.size()));

        for (Set<T> set : powerSetFrom(rest)) {
            final Set<T> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }

        return sets;
    }

    private <T> Set<Set<T>> setOnlyContainingEmptySet() {
        final Set<Set<T>> set = new HashSet<>();
        set.add(new HashSet<T>());

        return set;
    }

}
