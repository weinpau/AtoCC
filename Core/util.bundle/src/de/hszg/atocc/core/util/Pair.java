package de.hszg.atocc.core.util;

public final class Pair<T, U> {

    private T first;
    private U second;
    
    public Pair(T firstObject, U secondObject) {
        this.first = firstObject;
        this.second = secondObject;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
    
}
