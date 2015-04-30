package graphex;

import java.util.ArrayList;

/**
 * Created by liam on 4/7/15.
 */

public class NFA {

    private ArrayList<State> states;
    private State startState;

    private void setup(){
        states = new ArrayList<State>();
        startState = null;
    }

    NFA(){
        setup();
    }

    NFA(Character c){
        setup();
        State s1 = new State();
        states.add(s1);
        State s2 = new State(true);
        states.add(s2);
        s1.addTransition(c, s2);
    }

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

    void starNFA(){
        State newStartState = new State(true);
        states.add(newStartState);

        newStartState.addTransition(startState);
        for (State s : states) {
            if (s.accepting){
                s.addTransition(startState);
            }
        }
        startState = newStartState;
    }

    // ((  )) --> ( S )
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

    public String print(){
        String str = "";
        for (int i = 0; i < states.size(); i++) {
            states.get(i).name = "q" + i;
        }
        for (State s : states) {
            str += s.name + s.print();
        }
        return str;
    }
}