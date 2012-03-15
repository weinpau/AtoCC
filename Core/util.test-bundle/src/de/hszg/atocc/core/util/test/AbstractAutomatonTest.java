package de.hszg.atocc.core.util.test;

import de.hszg.atocc.core.util.automaton.Automaton;
import de.hszg.atocc.core.util.automaton.AutomatonType;
import de.hszg.atocc.core.util.automaton.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;

public abstract class AbstractAutomatonTest {

    protected static final String NOT_YET_IMPLEMENTED = "Not yet implemented";

    protected static final String A = "a";
    protected static final String B = "b";
    protected static final String C = "c";

    protected static final String Q0 = "q0";
    protected static final String Q1 = "q1";
    protected static final String Q2 = "q2";

    private static Set<String> alphabetAB;

    @BeforeClass
    public static void init() {
        alphabetAB = new HashSet<String>();
        alphabetAB.add(A);
        alphabetAB.add(B);
    }

    protected final Set<String> getAlphabetAB() {
        return alphabetAB;
    }
    
    protected final Automaton createTestAutomatonNfa() throws Exception {
        final Automaton automaton = new Automaton(AutomatonType.NEA);
        automaton.addState(Q0);
        automaton.addState(Q1);
        automaton.addFinalState(Q1);
        automaton.addAlphabetItem(A);
        automaton.setInitialState(Q0);
        automaton.addTransition(new Transition(Q0, Q1, A));
        return automaton;
    }

    protected final Automaton createTestAutomatonPda() throws Exception {
        final Automaton automaton = new Automaton(AutomatonType.NKA);
        automaton.addState(Q0);
        automaton.addState(Q1);
        automaton.addFinalState(Q1);
        automaton.addAlphabetItem(A);
        automaton.addStackAlphabetItem(A);
        automaton.setInitialState(Q0);
        automaton.setInitialStackSymbol(A);
        automaton.addTransition(new Transition(Q0, Q1, A, Automaton.EPSILON, A));
        return automaton;
    }

}
