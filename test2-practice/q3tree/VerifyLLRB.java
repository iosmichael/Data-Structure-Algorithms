package q3tree;

/**
 * VerifyLLRB
 * 
 * Placeholder class for the static method verifyLLRB() which
 * takes a tree of red-black nodes and verifies that the tree
 * satisfies the constraints of a left-leaning red-black tree.
 * Do not assume that any red-black properties hold; the point of
 * the method is to check whether they hold.
 * 
 * CSCI 345
 * Test 2 Practice Problem 3.
 */
public class VerifyLLRB {

    /**
     * Determine whether a given tree made up of
     * RBNodes satisfies the left-learning red-black tree
     * properties (except that it does not check that
     * the absolute root is black). The blackHeight fields
     * of the nodes are not assumed to be correct before this
     * method is called, but, as a side affect, this method
     * sets the blackHeight field of every node in the tree
     * rooted at the given node to the correct blackHeight
     * value. Null references are valid (trivial) left-leaning
     * red-black trees.
     * @param root The root of the (sub)tree being verified
     * @return true if the tree rooted at root is a left-leaning
     * red-black tree, false otherwise.
     * POSTCONDITION: The blackHeight of every node in the
     * tree is set to the right value if the tree verifies as
     * a left-leaning red-black tree
     */
    public static boolean verifyLLRB(RBNode root) {
    	
    	//check root to child is same blackHeight
    	//check root right is not red
    	
    	RBNode left = root.left;
    	RBNode right = root.right;
    	if(verifyLLRB(left)&&verifyLLRB(right)){
    		if(left.blackHeight == right.blackHeight){
    			root.blackHeight = left.blackHeight + 1;
    		}else{
    			return false;
    		}
    		if(!right.isRed){
    			return true;
    		}
    	}
        return false;
    }
}
