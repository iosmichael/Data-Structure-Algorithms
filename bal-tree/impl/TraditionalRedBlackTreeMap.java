package impl;

/**
 * TraditionalRedBlackTreeMap
 * 
 * A BST map using the (traditional) red-black approach for
 * maintaining a balanced tree. This inherits most of the code for 
 * manipulating the BST from AbstractRecursiveBSTMap and inherits the 
 * code for verifying the red-black property from AbstractRBTreeMap. 
 * This class's purpose is to house the code for fixing up an
 * RB tree when the red-black property is violated. See documentation for 
 * AbstractRBTreeMap.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 2, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public class TraditionalRedBlackTreeMap<K extends Comparable<K>, V> extends
        AbstractRedBlackTreeMap<K, V> {

    /**
     * Class for real, "non-null" nodes, containing the code for
     * fixing up the tree when red-black tree properties are violated.
     */
    private class TradRBRealNode extends AbstractRBRealNode {

        /**
         * Plain constructor
         */
        protected TradRBRealNode(K key, V val,
                RBNode<K, V> left,
                RBNode<K, V> right) {
            super(key, val, left, right);
        }

        /**
         * Fix this subtree to conform to the constraints of RB trees.
         * PRECONDITION: left and right subtrees are red-black trees
         * except that (1) their roots (left and right themselves) may be red,
         * and (2) at most one of them may have a double red that involves
         * their root. Exception (2) would happen only if this node is
         * black.
         * POSTCONDITION: This tree has been modified to contain the 
         * same information as before but also to satisfy the RB 
         * constraints, again with the exception that the root of
         * this subtree may be red. The node on which this method is called,
         * currently the root of this subtree, might no longer be
         * the root; the root of the modified tree is returned.
         * @return The root of the tree like this one but
         * satisfying the constraints.
         */
        protected RBNode<K, V> fixup() {
        	RBNode<K, V> left = this.left;
        	RBNode<K, V> right = this.right;
        	RBNode<K, V> replace = this;
        	if(right.isRed() && left.isRed()){
        		replace = this.RCL();
        	}
        	if(!right.isNull() && right.isRed()){
        		RBNode<K, V> rr = right.right();
        		RBNode<K, V> ll = right.left();
        		if(!rr.isNull() && rr.isRed()){
        			replace = this.rotateLeft();
        		}
        		if(!ll.isNull() && ll.isRed()){
        			replace = this.RL();
        		}
        	}
        	if(!left.isNull() && left.isRed()){
        		RBNode<K, V> rr = right.right();
        		RBNode<K, V> ll = right.left();
        		if(!rr.isNull() && rr.isRed()){
        			replace = this.rotateRight();
        		}
        		if(!ll.isNull() && ll.isRed()){
        			replace = this.LR();
        		}
        	}
            return replace;
        }

        
        // ------------------------------------------------
        // The following two methods are suggested helper methods
        // (which you would have to write) for fixup()
        // -----------------------------------------------
        
        /**
         * Rotate this tree to the left.
         * @return The node that is newly the root
         * Left Rotation
         * a (b)            b (b)
         *  \              / \
         *   b (r)   =>   a(r)c(r)
         *    \          
         *     c (r)
         */
        private RBNode<K, V> rotateLeft() {
        	AbstractRBRealNode b = (AbstractRBRealNode) this.right();
            this.right = b.left();
            b.left = this;
            b.left.redden();  // redden a
            b.blacken();  // blacken b
        	return b;
        }
        
        /**
         * Rotate this tree to the right.
         * @return The node that is newly the root
         * Right Rotation
         *     c (b)        b (b)
         *    /            / \
         *   b (r)   =>   a(r)c(r)
         *  /          
         * a (r)
         */
        private RBNode<K, V> rotateRight() {
        	AbstractRBRealNode b = (AbstractRBRealNode) this.left();
            this.left = b.right();
            b.left = this;
            b.right.redden();  // redden c
            b.blacken();     // blacken b
        	return b;
        }
        
        /**
         * Right-Left Rotation / Double Right Rotation
         * a        a            b
         *  \        \          / \
         *   c   =>   b   =>   a   c
         *  /          \ 
         * b            c
         * @return newChild
         */
        private RBNode<K, V> RL(){
           TradRBRealNode c = (TradRBRealNode) this.right;
     	   //single left rotation
           TradRBRealNode b = (TradRBRealNode) c.rotateRight();
     	   this.right = b.left;
     	   b.left = this;
     	   this.redden();  // redden a
     	   b.blacken();  // blacken b
     	   return b;
        }
        
        /**
         * Left-Right Rotation / Double Left Rotation
         *   c           c        b
         *  /           /        / \
         * a      =>   b    =>  a   c
         *  \         /  
         *   b       a
         * @return newChild
         */
        private RBNode<K, V> LR(){
     	   TradRBRealNode a = (TradRBRealNode) this.left;
     	   //Single right rotation
     	   TradRBRealNode b = (TradRBRealNode) a.rotateLeft();
     	   this.left = b.right;
     	   b.right = this;
     	   this.redden();  // redden c
     	   b.blacken();    // blacken b
     	   return b;
        }
        /**
         * Red Uncle Case / Re-Color (No Rotation)
         *    b
         *   / \
         *  a   c
         */
        private RBNode<K,V> RCL(){
        	RBNode<K,V> a = this.left;
        	RBNode<K,V> c = this.right;
        	this.redden();   // redden b
        	a.blacken();   // blacken a
        	c.blacken();   // blacken c
        	return this;
        }

    }
    
    /**
     * Factory method for making new real nodes, used by the
     * code in the parent class which does not have direct access
     * to the class RBRealNode defined here.
     */
   protected RBNode<K, V> realNodeFactory(K key,
            V val, RBNode<K, V> left,
            RBNode<K, V> right) {
        return new TradRBRealNode(key, val, left, right);
    }

}
