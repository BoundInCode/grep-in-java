package graphex;

import java.util.ArrayList;

/**
 * Created by liam on 4/7/15.
 */
public class State {
    ArrayList<Transition> transitions = new ArrayList<Transition>();
    boolean accepting = false;
    String name;

    State(boolean isAcceptState){
        accepting = isAcceptState;
    }

    State(){
        accepting = false;
    }

    public void addTransition(Character c, State dest){
        transitions.add(new Transition(c, dest));
    }

    public void addTransition(State dest){
        transitions.add(new Transition(dest));
    }
}
