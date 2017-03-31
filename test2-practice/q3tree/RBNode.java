package q3tree;

import java.util.Stack;

public class RBNode {
    public final RBNode left, right;
    public final String key;
    public final boolean isRed;
    public int blackHeight;
    public RBNode(RBNode left, String key, 
                  RBNode right, boolean isRed) {
        this.left = left;
        this.key = key;
        this.right = right;
        this.isRed = isRed;
    }
    
    public static RBNode rbFactory(String description) {
        Stack<RBNode> subtrees = new Stack<RBNode>();
        Stack<String> keys = new Stack<String>();
        for (char c : description.toCharArray())
            switch(c) {
            case '.' : subtrees.push(null); break;
            case '(': case '[': break;
            case ')':
                RBNode right = subtrees.pop(),
                       left = subtrees.pop();
                String key = keys.pop();
                subtrees.push(new RBNode(left, key, right, false));
                break;
            case ']':
                right = subtrees.pop();
                left = subtrees.pop();
                key = keys.pop();
                subtrees.push(new RBNode(left, key, right, true));
                break;
            default: keys.push(c + "");
            }
        return subtrees.pop();
    }

}
