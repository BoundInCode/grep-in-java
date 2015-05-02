package graphex;

import java.util.*;

/**
 * Created by liam on 4/30/15.
 */
public class DFA {

    public HashMap<String, StateSet> stateSets;
    public StateSet startStates;
    public StateSet failStateSet;
    public HashSet<Character> alphabet;

    DFA(){
        stateSets = new HashMap<String, StateSet>();
        startStates = null;

        failStateSet = new StateSet();

        State failState = new State();
        failState.name = "Fail";
        failStateSet.states.add(failState);
    }

    private StateSet followTransition(State s, Character c){
        for(Transition t: s.transitions) {
            if (t.symbol.equals(c)) {
                return (StateSet)t.destination;
            }
        }
        return null;
    }

    public boolean acceptsString(String s){
        StateSet currentState = startStates;
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            currentState = followTransition(currentState, c);
            if (currentState == null) {
                return false;
            }
        }
        return currentState.isAccepting();
    }

    public String printAcceptStates(){
        String acceptStates = "";

        for (StateSet set: stateSets.values()) {
            if (set.isAccepting()){
                if (acceptStates.length() > 0) {
                    acceptStates += ", ";
                }
                acceptStates += set.getName();
            }
        }
        return "node [shape = doublecircle]; " + acceptStates + ";\n";
    }

    public String printStates(){
        String str = "node [shape = circle];\n";

        str += "\"\" -> " + startStates.getName() + ";\n";

        // Print states
        for (StateSet set: stateSets.values()) {
//            for (State s : set.states) {
                for (Transition t : set.transitions) {
                    str += set.getName() + " -> " + ((StateSet)t.destination).getName() + "[label = \"" + t.symbol + "\" ];\n";
                }
//            }
        }
        return str;
    }

}
