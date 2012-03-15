package de.hszg.atocc.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StateNameHelper {

    private StateNameHelper() {

    }

    /**
     * Checks if a set of states match the pattern "%s_%d" with %s being the
     * common prefix and %d being an integer.
     * 
     * @param states
     *            The set of states
     * @return true if the states match the pattern, false otherwise
     */
    public static boolean areStatesEnumerated(Set<String> states) {
        final String commonPrefix = findCommonPrefix(states);

        final String pattern = String.format("%s[\\d]+", commonPrefix);

        for (String state : states) {
            if (!state.matches(pattern)) {
                return false;
            }
        }

        return !commonPrefix.isEmpty();
    }

    public static String generateNextState(Set<String> states) {
        if (areStatesEnumerated(states)) {
            final String commonPrefix = findCommonPrefix(states);

            final Pattern pattern = Pattern.compile(String.format("%s(\\d+)", commonPrefix));

            final List<Integer> numbers = new ArrayList<>();

            for (String state : states) {
                final Matcher matcher = pattern.matcher(state);
                matcher.find();

                numbers.add(Integer.parseInt(matcher.group(1)));
            }
            
            return String.format("%s%d", commonPrefix, Collections.max(numbers) + 1);
        } 

        throw new RuntimeException("States are not enumerated");
    }

    public static String findCommonPrefix(Set<String> states) {
        if (states.isEmpty()) {
            return "";
        }

        final String firstState = states.iterator().next();
        final StringBuilder commonPrefix = new StringBuilder();

        for (int i = 0; i < findMinimumStateNameLength(states); ++i) {
            if (isCommonCharacter(states, i)) {
                commonPrefix.append(firstState.charAt(i));
            } else {
                break;
            }
        }

        return commonPrefix.toString();
    }

    private static boolean isCommonCharacter(Set<String> states, int index) {
        final char c = states.iterator().next().charAt(index);

        for (String state : states) {
            if (state.charAt(index) != c) {
                return false;
            }
        }

        return true;
    }

    private static int findMinimumStateNameLength(Set<String> states) {
        int min = Integer.MAX_VALUE;

        for (String state : states) {
            if (state.length() < min) {
                min = state.length();
            }
        }

        return min;
    }
}
