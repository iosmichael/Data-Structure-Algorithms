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
        	
            // Write this in the Traditional RB Tree project
        	//Four cases: RR, RL, LL, LR, RCL
        	if(this.left.isRed() && this.isRedLL()){
        		if(this.right.isRed()) return this.recolor();
        		return this.rotateRight();
        	}else if(this.left.isRed() && this.isRedLR()){
        		if(this.right.isRed()) return this.recolor();
        		return this.LR();
        	}else if(this.right.isRed() && this.isRedRR()){
        		if(this.left.isRed()) return this.recolor();
        		return this.rotateLeft();
        	}else if(this.right.isRed() && this.isRedRL()){
        		if(this.left.isRed()) return this.recolor();
        		return this.RL();
        	}
            return this;
        }

        
        // ------------------------------------------------
        // The following two methods are suggested helper methods
        // (which you would have to write) for fixup()
        // -----------------------------------------------
        
        /**
         * Rotate this tree to the left.
         * @return The node that is newly the root
         * a (b)            b(r)
         *  \              / \
         *   b (r)     => a(b)c(b)
         *    \
         *     c  (r)
         */
        private RBNode<K, V> rotateLeft() {
        	TradRBRealNode b = (TradRBRealNode) this.right;
        	this.right = b.left();
        	b.left = this;
        	b.right.blacken();
            return b;
        }
        
        /**
         * Rotate this tree to the right.
         * @return The node that is newly the root
         *    c (b)          b (r)
         *   /              / \
         *  b (r)     =>   a(b)c(b)
         * /
         *a  (r)
         */
        private RBNode<K, V> rotateRight() {
        	TradRBRealNode b = (TradRBRealNode)this.left;
        	this.left = b.right();
        	b.right = this;
        	b.left.blacken();
            return b;
        }
        
        /**
         * Right-Left Rotation / Double Right Rotation
         * a(b)        a(b)            b(r)
         *  \           \             / \
         *   c(r)   =>   b(r)   =>   a(b)c(b)
         *  /             \ 
         * b(r)            c(r)
         */
        private RBNode<K, V> RL() {
        	TradRBRealNode c = (TradRBRealNode)this.right;
        	TradRBRealNode b = (TradRBRealNode)c.rotateRight();
        	this.right = b.left;
        	b.left = this;
        	c.blacken();
        	b.redden();
        	this.blacken();
        	return b;
        }
        
        /**
         * Left-Right Rotation / Double Left Rotation
         *   c(b)           c(b)        b(r)
         *  /              /           / \
         * a(r)      =>   b(r)    =>  a(b)c(b)
         *  \            /  
         *   b(r)       a(r)
         */
        private RBNode<K, V> LR() {
        	TradRBRealNode a = (TradRBRealNode)this.left;
        	TradRBRealNode b = (TradRBRealNode)a.rotateLeft();
        	this.left = b.right;
        	b.right = this;
        	a.blacken();
        	b.redden();
        	this.blacken();
        	return b;
        }
        
        /**
         * Rotate this tree to the right.
         * @return The node that is newly the root
         *    c (b)           c (r)
         *   /  \            / \
         *  b (r)d(r) =>   b(b)d(b)
         * /              /
         *a  (r)         a(r)
         */
        private RBNode<K, V> recolor(){
        	TradRBRealNode b = (TradRBRealNode)this.left;
        	TradRBRealNode d = (TradRBRealNode)this.right;
        	this.redden();
        	b.blacken();
        	d.blacken();
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
