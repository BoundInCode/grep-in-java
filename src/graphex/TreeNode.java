package graphex; /**
 * Created by liam on 4/9/15.
 */

import java.util.ArrayList;

public class TreeNode {

    public enum Type {
        BLOCK, STAR, PLUS, OR, AND, CHAR
    }

    public Character value;
    public Type type;
    public TreeNode parent;
    public ArrayList<TreeNode> children;


    public TreeNode (Type type){
        this.type = type;
        this.parent = null;
        this.children = new ArrayList<TreeNode>();
    }
    public TreeNode (char value){
        this.value = value;
        this.type = Type.CHAR;
        this.parent = null;
        this.children = new ArrayList<TreeNode>();
    }

    public void adopt(TreeNode child){
        children.add(child);
        child.parent = this;
    }

    public NFA toNFA(){
        NFA base;

        switch (type) {
            case BLOCK:
                return children.get(0).toNFA();
            case STAR:
                if (children.size() == 1) {
                    return children.get(0).toNFA();
                }
                NFA currNFA = children.get(0).toNFA();
                currNFA.starNFA();
            case PLUS:
                if (children.size() == 1) {
                    return children.get(0).toNFA();
                }
                System.out.println("not done yet");
                break;
            case OR:
                if (children.size() == 1) {
                    return children.get(0).toNFA();
                }
                base = new NFA();
                for (TreeNode n : children){
                    base.unionNFA(n.toNFA());
                }
                return base;
            case AND:
                base = new NFA();
                for (TreeNode n : children){
                    base.concatNFA(n.toNFA());
                }
                return base;
            case CHAR:
                return new NFA(value);
        }
        return new NFA();
    }
}

