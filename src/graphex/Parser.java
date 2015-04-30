package graphex;

/**
 * Created by liam on 4/7/15.
 */

public class Parser {

    private String regex;
    private RegexTree tree;

    private int index = 0;
    private Character currentChar;
    private Character lookahead;

    Parser(){
        // Constructor
    }

    public NFA parse(String regex){
        tree = new RegexTree();
        this.regex = regex;

        start();

        return tree.root.toNFA();
    }

    // Return to parent
    private void rtp(){
        tree.curr = tree.curr.parent;
    }

    private void start(){
        index = 0;
        if(regex.length() > 0){
            currentChar = regex.charAt(0);
            lookahead = (regex.length() > 1) ?regex.charAt(1) :null;
            expression();
        } // else epsilon
    }


    private void matchChars(Character c, Character ahead){
        match('c');
        if(ahead != null && ahead == '*') {
            match('*');
            tree.newStar();
            tree.newChar(c);
            rtp();
        } else if(ahead != null && ahead == '+') {
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
        //Todo: a** check for multiple stars
        //TODO: Support non-characters

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
        } else { //if (tree.curr.type == graphex.TreeNode.Type.BLOCK) {
            tree.newAnd();
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