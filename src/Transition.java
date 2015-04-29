/**
 * Created by liam on 4/7/15.
 */
public class Transition {
    String symbol;
    State destination;

    Transition(State destination){
        this.destination = destination;
        this.symbol = ""; // epsilon transition
    }

    Transition(State destination, String symbol){
        this.destination = destination;
        this.symbol = symbol;
    }

    public State getDestination(){
        return destination;
    }

    public void setDestination(State dest){
        destination = dest;
    }
}
