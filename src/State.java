import java.util.ArrayList;

/**
 * Created by liam on 4/7/15.
 */
public class State {
    ArrayList<Transition> transitions = new ArrayList<Transition>();
    boolean accepting = false;

    State(boolean isAcceptState){
        accepting = isAcceptState;
    }

    public void addTransition(State dest){
        transitions.add(new Transition(dest));
    }
}
