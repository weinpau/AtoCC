package de.hszg.atocc.core.util.automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class PowerSet {

    private PowerSet() {
        
    }
    
    public static <T> Set<Set<T>> from(final Set<T> originalSet) {
        
        final Set<Set<T>> sets = new HashSet<Set<T>>();
        
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<T>());
            return sets;
        }
        
        final List<T> list = new ArrayList<T>(originalSet);
        
        final T head = list.get(0);
        
        final Set<T> rest = new HashSet<T>(list.subList(1, list.size()));
        
        for (Set<T> set : PowerSet.from(rest)) {
            final Set<T> newSet = new HashSet<T>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        
        return sets;
    }

}
