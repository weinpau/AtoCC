package de.hszg.atocc.core.util;

import java.util.HashSet;
import java.util.Set;

public final class CharacterHelper {

    private CharacterHelper() {

    }

    public static char firstCharacterDifferentTo(char c) {

        for (int i = 'a'; i <= 'z'; ++i) {
            if (c != (char) i) {
                return (char) i;
            }
        }

        throw new RuntimeException("Should not reach this point");

    }

    public static char characterDifferentToAnyOf(Set<String> alphabet) {

        for (char c = '!'; c <= '~'; ++c) {
            if (!alphabet.contains(String.valueOf(c))) {
                return c;
            }
        }

        throw new RuntimeException("No character found which is not in set");

    }

    public static char letterDifferentToAnyBeginningOf(Set<String> alphabet) {
        final Set<Character> beginnings = beginningsOf(alphabet);

        for (char c = 'a'; c <= 'z'; ++c) {
            if (!beginnings.contains(c)) {
                return c;
            }
        }

        throw new RuntimeException("No character found which is not beginning of set-element");

    }

    private static Set<Character> beginningsOf(Set<String> alphabet) {
        final Set<Character> beginnings = new HashSet<>();

        for (String s : alphabet) {
            beginnings.add(s.charAt(0));
        }

        return beginnings;
    }
}
