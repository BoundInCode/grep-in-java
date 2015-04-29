/**
 * Created by liam on 4/9/15.
 */
public class RegexTree {

    public TreeNode root = null;
    public TreeNode curr = null;

    RegexTree(){
        root = new TreeNode(TreeNode.Type.OR);
        curr = root;
    }

    public void newBlock(){
        TreeNode node = new TreeNode(TreeNode.Type.BLOCK);
        curr.adopt(node);
        curr = node;
    }

    public void newChar(char val){
        TreeNode node = new TreeNode(val);
        curr.adopt(node);
    }

    public void newPlus(){
        TreeNode node = new TreeNode(TreeNode.Type.PLUS);
        curr.adopt(node);
        curr = node;
    }
    public void newStar(){
        TreeNode node = new TreeNode(TreeNode.Type.STAR);
        curr.adopt(node);
        curr = node;
    }
    public void newOr(){
        TreeNode node = new TreeNode(TreeNode.Type.OR);
        curr.adopt(node);
        curr = node;
    }

    public void newAnd(){
        TreeNode node = new TreeNode(TreeNode.Type.AND);
        curr.adopt(node);
        curr = node;
    }

    public void print(){
        print(root, 0);
    }

    private void print(TreeNode node, int depth){
        for (int i = 0; i < depth; i++){
            System.out.print("-");
        }
        if (node.children == null || node.children.size() == 0){
            System.out.println("[" + node.value + "]");
            //System.out.println("\n");
        } else {
            System.out.println("<" + node.type + ">");
            for (int i = 0; i < node.children.size(); i++) {
                print(node.children.get(i), depth + 1);
            }
        }
    }
}
