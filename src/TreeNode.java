/**
 * Created by liam on 4/9/15.
 */

import java.util.ArrayList;

public class TreeNode {

    public enum Type {
        BLOCK, STAR, PLUS, OR, AND, CHAR
    }

    public char value;
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
}

