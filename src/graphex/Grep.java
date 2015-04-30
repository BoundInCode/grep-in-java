package graphex;

import java.io.*;

import com.beust.jcommander.JCommander;

/**
 * Created by liam on 4/7/15.
 */
public class Grep {

    private String getAlphabet(String input) {
        return "abcedfghijklmnopqrstuvwxyz";
    }

    public static void main(String args[]) throws IOException{
        JCommanderHelper jct = new JCommanderHelper();
        JCommander jCommander = new JCommander(jct, args);

        String regex = jct.getNamelessParams().get(0);
        String inputFile = jct.getNamelessParams().get(1);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        System.out.println("Input File: " + inputFile);
        System.out.println("Regex: " + regex);
        Parser parser = new Parser();

        NFA nfa = parser.parse(regex);
        parser.print();

        PrintWriter writer = new PrintWriter("nfa.gv", "UTF-8");
        writer.println(nfa.print());
        writer.close();

        DFA dfa = new DFA(nfa);


    }



}
