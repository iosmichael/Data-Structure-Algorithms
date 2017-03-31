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
    	if(root == null){
    		return null;
    	}
    	AVLNode left = root.left;
    	AVLNode right = root.right;
    	RBNode rbLeft = avl2rb(left);
    	RBNode rbRight = avl2rb(right);
    	if(root.balance < 0){
    		if(root.right.balance == 0) rbRight.redden();
    	}else if(root.balance > 0){
    		if(root.left.balance == 0) rbLeft.redden();
    	}
    	RBNode rbRoot = new RBNode(rbLeft,root.key,rbRight);
        return rbRoot;
    }
    
}
