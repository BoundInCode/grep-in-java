import java.util.ArrayList;

/**
 * Created by liam on 4/7/15.
 */

public class NFA {

//    public enum Type {
//        CHAR, PAREN_L, PAREN_R, STAR, PLUS
//    }

    private ArrayList<State> states;
    private String regex;
    private RegexTree tree;

    private int index = 0;
    private char currentChar;
    private char lookahead;

    NFA(String regex){
        // Constructor
        states = new ArrayList<State>();
        tree = new RegexTree();
        this.regex = regex;

        start();
    }

    // Return to parent
    private void rtp(){
        tree.curr = tree.curr.parent;
    }

    private void start(){
        index = 0;
        if(regex.length() > 0){
            currentChar = regex.charAt(0);
            lookahead   = regex.charAt(1);
            expression();
        } // else epsilon
    }

//    private void block(){
//        match('(');
//        tree.newBlock();
//        expression();
//        match(')');
//
//        if(lookahead == '+'){
//            tree.newBlockPlus();
//            match('+');
//        } else if(lookahead == '*') {
//            tree.newBlockStar();
//            match('*');
//        }
//        rtp();
//    }

//    private void newOr(){
//        match('|');
//        tree.newOr();
//
//    }

//    private void newAnd(){
//        tree.newAnd();
//
//    }

    private void matchChars(char c, char ahead){
        match('c');
        if(ahead == '*') {
            match('*');
            tree.newStar();
            tree.newChar(c);
            rtp();
        } else if(ahead == '+') {
            match('+');
            tree.newPlus();
            tree.newChar(c);
            rtp();
        } else {
            tree.newChar(c);
        }
    }

    private void expression(){
        if(index >= regex.length()){
            return;
        }
        if(currentChar == ')'){
            return;
        }
        //System.out.println(index);

        //todo: check if curr is out-of-bounds

        if (tree.curr.type == TreeNode.Type.AND) {
            if(currentChar == '|') {
                match('|');
                rtp();
                rtp();
            } else if(Character.isLetter(currentChar)) {
                matchChars(currentChar, lookahead);
            } else if(currentChar == '(') {
                match('(');
                tree.newBlock();
                tree.newOr();
                expression();
                match(')');
                rtp();
                if(currentChar =='*'){
                    match('*');
                    tree.curr.type = TreeNode.Type.STAR;
                } else if(currentChar =='+'){
                    match('+');
                    tree.curr.type = TreeNode.Type.PLUS;
                }
                rtp();
            }
        } else { //if (tree.curr.type == TreeNode.Type.BLOCK) {
            tree.newAnd();
            /*if (Character.isLetter(currentChar)){
                if(lookahead == '|'){
                    // a|b
                    match('c');
                    match('|');
                    tree.newChar(currentChar);

                } else if(lookahead == '+'){
                    // a+
                    match('c');
                    match('+');
                    tree.newPlus();
                    tree.newChar(currentChar);

                } else if(lookahead == '*') {
                    // a*
                    match('c');
                    match('*');
                    tree.newStar();
                    tree.newChar(currentChar);

                } else { // char or new block
                    // ab
                    match('c');
                    tree.newAnd();
                    tree.newChar(currentChar);
                }
            } else if (currentChar == '('){
                match('(');
                tree.newAnd();
                tree.newBlock();
                tree.newOr();
                expression();
                match(')');
                rtp();
                if(currentChar =='*'){
                    match('*');
                    tree.curr.type = TreeNode.Type.STAR;
                } else if(currentChar =='+'){
                    match('+');
                    tree.curr.type = TreeNode.Type.PLUS;
                }
                rtp();
            }*/
            // should not be necessary.
            /*else if(currentChar == '+'){
                // ()+
                tree.curr.type = TreeNode.Type.PLUS;
                rtp();
            } else if(currentChar == '*'){
                // ()*
                tree.curr.type = TreeNode.Type.STAR;
                rtp();
            }*/
        }
        expression();
    }

    public void print(){
        tree.print();
    }

    private void match(char type){
        System.out.print("Matching: " + type);
        if (currentChar == type || (type == 'c' && Character.isLetter(currentChar))){
            System.out.println("\tFound: " + currentChar);
            index++;
            if(index < regex.length()){
                currentChar = regex.charAt(index);
                if(index < regex.length() - 1){
                    lookahead = regex.charAt(index+1);
                }
            }
        } else {
            System.out.println("Match Error.");
            System.out.println("Expecting: " + type);
            System.out.println("Received: " + currentChar);
        }
    }

}