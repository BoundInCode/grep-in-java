package graphex;

import java.io.*;

/**
 * Created by liam on 4/7/15.
 */
public class Grep {

    public static void main(String args[]) throws IOException {
        String regex = null;
        String inputFile = null;

        boolean shouldPrintNFA = false;
        boolean shouldPrintDFA = false;

        String nfaFileName = null;
        String dfaFileName = null;

        for (int i = 0; i < args.length; i++){
            if(args[i].equals("-n")){
                shouldPrintNFA = true;
                nfaFileName = args[i+1];
                i++;
            } else if(args[i].equals("-d")){
                shouldPrintDFA = true;
                dfaFileName = args[i+1];
                i++;
            } else if (regex == null){
                regex = args[i];
            } else {
                inputFile = args[i];
            }
        }

//        String regex = jct.getNamelessParams().get(0);
//        String inputFile = jct.getNamelessParams().get(1);

//        System.out.println("Input File: " + inputFile);
//        System.out.println("Regex: " + regex);
        NFAParser nfaParser = new NFAParser();
        NFA nfa = nfaParser.parse(regex);
        nfa.assignNames();
//        nfaParser.print();

        if (shouldPrintNFA) {
            nfaFileName = nfaFileName.replaceFirst("^~",System.getProperty("user.home"));
            PrintWriter nfaWriter = new PrintWriter(nfaFileName, "UTF-8");
            nfaWriter.println("digraph nfa {");
            nfaWriter.println("rankdir=LR;\nnode [shape = none]; \"\";");
            nfaWriter.println(nfa.printAcceptStates());
            nfaWriter.println(nfa.printStates());
            nfaWriter.println("}");
            nfaWriter.close();
        }


        DFAParser dfaParser = new DFAParser();
        DFA dfa = dfaParser.parse(nfa);
        if (shouldPrintDFA) {
            dfaFileName = dfaFileName.replaceFirst("^~",System.getProperty("user.home"));
            PrintWriter dfaWriter = new PrintWriter(dfaFileName, "UTF-8");
            dfaWriter.println("digraph dfa {");
            dfaWriter.println("rankdir=LR;\nnode [shape = none]; \"\";");
            dfaWriter.println(dfa.printAcceptStates());
            dfaWriter.println(dfa.printStates());
            dfaWriter.println("}");
            dfaWriter.close();
        }

        if (inputFile == null) {
            System.out.println("Input file not specified.");
        } else {
            inputFile = inputFile.replaceFirst("^~",System.getProperty("user.home"));
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;

            while ((line = reader.readLine()) != null) {
                if (dfa.acceptsString(line)){
                    System.out.println(line);
                }
            }
        }
    }



}
