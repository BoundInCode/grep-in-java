package graphex;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by liam on 4/7/15.
 */

public class NFA extends FiniteAutomaton {

    private boolean needsName;

    NFA(){
        super();
    }

    NFA(Character c){
        super(c);
    }

    // a|b
    void unionNFA(NFA n2){
        if (states.size() == 0) {
            states = n2.states;
            startState = n2.startState;
            return;
        }
        State newStartState = new State();
        states.add(newStartState);

        newStartState.addTransition(startState);
        newStartState.addTransition(n2.startState);

        startState = newStartState;
        states.addAll(n2.states);
    }

    // a*
    void starNFA(){
        State newStartState = new State(true);
        newStartState.addTransition(startState);

        for (State s : states) {
            if (s.accepting){
                s.addTransition(startState);
            }
        }
        states.add(newStartState);
        startState = newStartState;
    }

    // ab
    void concatNFA(NFA n2){
        if (states.size() == 0) {
            states = n2.states;
            startState = n2.startState;
            return;
        }
        for (State s : states) {
            if (s.accepting){
                s.addTransition(n2.startState);
                s.accepting = false;
            }
        }
        states.addAll(n2.states);
    }

    public HashSet<Character> getAlphabet() {
        HashSet<Character> set = new HashSet<Character>();
        for (State s: states){
            for (Transition t: s.transitions){
                if (t.symbol.equals('\u03B5')) continue;
                set.add(t.symbol);
            }
        }
        return set;
    }

    public void assignNames(){
        needsName = false;
        for (int i = 0; i < states.size(); i++) {
            states.get(i).name = "q" + i;
        }
    }

    public String printAcceptStates(){
        String acceptStates = "";
        if(needsName) {
            assignNames();
        }
        for (State s : states) {
            if (s.accepting) {
                if (acceptStates.length() > 0) {
                    acceptStates += ", ";
                }
                acceptStates += s.name;
            }
        }
        return "node [shape = doublecircle]; " + acceptStates + ";\n";
    }

    public String printStates(){
        String str = "node [shape = circle];\n";
        if(needsName) {
            assignNames();
        }
        str += "\"\" -> " + startState.name + ";\n";

        // Print states
        for (State s : states) {
            for (Transition t : s.transitions) {
                str += s.name + " -> " + t.destination.name + "[label = \""+ t.symbol + "\" ];\n";
            }
        }
        return str;
    }
}