package q2tree;

/**
 * AVLToRB
 * 
 * Placeholder class for the static method avl2rb() which converts
 * an AVL tree to a red-black tree.
 * 
 * CSCI 345
 * Test 2 Practice Problem 1.
 */
public class AVLToRB {

    /**
     * Convert an AVL tree represented by its root to an equivalent
     * red-black tree, also represented by its root. If the given
     * tree is null, the result tree also should be null.
     * Assume that the height and balance as stored in each AVL node
     * is correct for that node.
     * @param root The root of the AVL (sub-)tree
     * @return The root of an equivalent red-black tree.
     */
    public static RBNode avl2rb(AVLNode root) {
        if (root == null) return null;
        else {
            assert -2 < root.balance && root.balance < 2;
            RBNode left = avl2rb(root.left);
            RBNode right = avl2rb(root.right);
            int lbh = left == null? 0 : left.blackHeight();
            int rbh = right == null? 0 : right.blackHeight();
            if (lbh > rbh) 
                left.redden();
            else if (rbh > lbh)
                right.redden();
            return new RBNode(left, root.key, right);
        }
    }
    
}
