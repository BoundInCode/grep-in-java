package graphex;

/**
 * Created by liam on 4/7/15.
 */
public class Transition {
    Character symbol;
    State destination;

    Transition(State destination){
        this.destination = destination;
        this.symbol = '\u03B5'; // epsilon transition
    }

    Transition(Character symbol, State destination){
        this.destination = destination;
        this.symbol = symbol;
    }

    public String print(){
        if (destination == null)
            return "--\n";
        return "--" + destination.name + "[label="+ symbol + "]\n";
    }
}
