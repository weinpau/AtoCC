package de.hszg.atocc.core.util.internal;

import de.hszg.atocc.core.util.SetService;

import java.util.ArrayList;
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

    private <T> Set<Set<T>> powerSetFromNonEmptySet(final Set<T> originalSet) {
        final Set<Set<T>> sets = new HashSet<Set<T>>();

        final List<T> list = new ArrayList<T>(originalSet);

        final T head = list.get(0);

        final Set<T> rest = new HashSet<T>(list.subList(1, list.size()));

        for (Set<T> set : powerSetFrom(rest)) {
            final Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }

        return sets;
    }

    private <T> Set<Set<T>> setOnlyContainingEmptySet() {
        final Set<Set<T>> set = new HashSet<Set<T>>();
        set.add(new HashSet<T>());

        return set;
    }

}
