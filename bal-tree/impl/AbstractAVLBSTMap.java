package impl;

/**
 * AbstractAVLBSTMap
 * 
 * A class defining the verification code for AVL trees, but not
 * the fixup code, which is deferred to a child class. The reason
 * for this seemingly gratuitous extra level of hierarchy is
 * for project set-up: The code checking for AVL-tree compliance
 * is provided in this class; the code for enforcing compliance
 * is assigned in the finishing of its child class.
 * 
 * One critical, practical aspect of AVL implementation is that
 * rebalancing (and verification) requires information about the 
 * attributes of a whole subtree, attributes that could change with
 * insertions and other subsequent rebalancing. These must not
 * be computed from scratch (if we can help it). Thus the height,
 * size, and balance of a subtree are stored in that subtree's root
 * node. They need to be updated when things change, but not by
 * descending the whole tree. See softRecompute() and hardRecompute().
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public abstract class AbstractAVLBSTMap<K extends Comparable<K>, V> extends AbstractRecursiveBSTMap<K, V, AbstractAVLBSTMap.AVLNode<K, V>> {
    /**
     * Are we in debugging mode? 
     */
    public static boolean DEBUG = true;

    /**
     * To be thrown if a violation of the "AVL" condition is detected, that is,
     * if there is a node the heights of whose subtrees differ by more than one.
     */
    public static class ImbalanceException extends RuntimeException {
        private static final long serialVersionUID = 2040394654668392655L;
        public ImbalanceException(String msg) { super(msg); }
    }


    /**
     * Supertype for the Node child classes of this tree map class
     * defining distinctive operations to help in verifying
     * AVL nodes.
     */
    protected interface AVLNode<KK extends Comparable<KK>, VV> extends AbstractRecursiveBSTMap.Node<KK, VV, AVLNode<KK, VV>>{ 
        /**
         * Recompute the attributes of this node and the subtree 
         * rooted here without descending the tree but instead
         * assuming the stored attributes of the children are
         * correct.
         */
        void softRecompute();

        /**
         * Recompute the attributes of this node and the subtree
         * rooted here from scratch, recursively descending the
         * tree to recompute the attributes of all descendants.
         */
        void hardRecompute();

        /**
         * The height of this tree, as stored.
         */
        int height();

        /**
         * The total number of associations in the subtree rooted here,
         * as stored.
         */
        int size();

        /**
         * The balance of the subtree rooted here, as stored, expressed
         * as the difference between the height of the right subtree
         * and the height of the left subtree. Trees with a higher left
         * subtree have negative balance, with a higher right subtree have
         * positive balance, and being perfectly balanced have a 0 balance.
         */
        int balance();
    }

    /**
     * Class for real, "non-null" nodes, containing the code for
     * verifying the AVL property.
     */
    public abstract class AbstractAVLRealNode extends AbstractRecursiveBSTMap.AbstractRealNode<K, V, AbstractAVLBSTMap.AVLNode<K, V>>implements AVLNode<K, V> {

        /**
         * The total number of associations in the subtree rooted here.
         */
        int size; 

        /**
         * The height of the subtree rooted here
         * (longest distance from here to any leaf below)
         */
        int height; 

        /**
         * The difference between the left height and the right
         * height. If that value is other than -1, 0, or 1, then the
         * tree is in violation. Since there are only three non-violation
         * values this variable can take on, this really could be stored
         * in only two bits (the cost of a shave and a haircut), as
         * Knuth notes in TAOCP 6.2.3 (v. III p. 459). We do use it
         * here to store violation values to trigger a fix-up, but even
         * in our case three bits will be plenty.
         */
        protected int balance;

        /**
         * Constructor: inherited fields are passed in, whereas 
         * fields for this class are computed by softRecompute().
         */
        public AbstractAVLRealNode(K key, V val, AVLNode<K, V> left, AVLNode<K, V> right) {
            super(key, val, left, right);
            softRecompute();
        }

        /**
         * Verify that the subtree rooted here meets all the constraints
         * of the variety of balanced BST that it is in. 
         * Verify the children (which implicitly does a hard recompute)
         * and then recompute here (using softRecompute()).
         * Check the balance.
         */
       public void verify() {
            if (DEBUG) {
                left.verify();
                right.verify();
                softRecompute();  
                if (balance < -1 || 1 < balance)
                    throw new ImbalanceException(left + " | (" + key + ") | " + right + " [" + balance + "]");
            }
        }
     
       /**
        * Recompute the attributes of this node and the subtree 
        * rooted here without descending the tree but instead
        * assuming the stored attributes of the children are
        * correct.
        */
       public void softRecompute() {
            int leftHeight = left.height();
            int rightHeight = right.height();
            balance = leftHeight - rightHeight;
            size = left.size() + right.size() + 1;
            height = (leftHeight > rightHeight? leftHeight : rightHeight) + 1;
        }

       /**
        * Recompute the attributes of this node and the subtree
        * rooted here from scratch, recursively descending the
        * tree to recompute the attributes of all descendants.
        */
        public void hardRecompute() {
            // we should never do a hard recompute except
            // when verifying.
            assert DEBUG;
            if (left != null) left.hardRecompute();
            if (right != null) right.hardRecompute();
            softRecompute();
        }

        // getter methods for attributes
        
        public int height() {
            return height;
        }

        public int size() {
            return size;
        }

        public int balance() {
            return balance;
        }

    }

    /**
     * Produce a new real node for a given association.
     * Because the full implementation of these nodes is deferred to
     * a child class, we need to use the Factory Pattern
     * in order for the code in this class to make new nodes.
     * The code here does not have access to the concrete node classes
     * and thus can't instantiate them directly.
     * @return A real (non-null) node for the tree.
     */
    protected abstract AVLNode<K,V> realNodeFactory(K key, V val, AVLNode<K, V> left, AVLNode<K, V> right);

    /**
     * Class for null objects for this tree map---mainly do-nothing except
     * that the put method makes and returns a new real node.
     */    
    protected class AVLNullNode extends AbstractRecursiveBSTMap.AbstractNullNode<K, V, AbstractAVLBSTMap.AVLNode<K, V>> implements AbstractAVLBSTMap.AVLNode<K, V>{
        
        public AVLNode<K, V> put(K key, V val) {
            return realNodeFactory(key, val, nully, nully);
        }

        public void softRecompute() { }

        public void hardRecompute() { }

        public int height() { return 0; }

        public int size() { return 0;  }

        public int balance() {
            return 0;
        }
        
    }
       
    /**
     * A null node object. This is for efficiency to reduce
     * the number of object. This effectively makes BasicNullNode
     * a singleton for this class.
     */
    private AVLNullNode nully;
    
    /**
     * Basic constructor for an empty map
     */
   public AbstractAVLBSTMap() {
        root = nully = new AVLNullNode();
    }
    
}
