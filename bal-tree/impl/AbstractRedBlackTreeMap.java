package impl;

/**
 * AbstractRedBlackTreeMap
 * 
 * A class defining the verification code for red-black trees, but not
 * the fixup code, which is deferred to child classes. Specifically
 * the verification code here is for the general red-black tree 
 * definition. This verification is further refined for left-leaning
 * red-black trees in a child class. The reason
 * for these seemingly gratuitous extra level of hierarchy is
 * for project set-up: The code checking for RB-tree compliance
 * is provided in this class; the code for enforcing compliance
 * is assigned in the finishing of its child class.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 2, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public abstract class AbstractRedBlackTreeMap<K extends Comparable<K>, V>
        extends AbstractRecursiveBSTMap<K, V, AbstractRedBlackTreeMap.RBNode<K, V>> {

    // --------- Exceptions thrown by the verification process---------------

    /**
     * The RBNode interface gives the signature for a redden() operation.
     * However, null object nodes must be black. If the fixup code attempts
     * to redden a null object, this exception is thrown.
     */
    public static class RedNullException extends RuntimeException {

        private static final long serialVersionUID = 2816002625772829146L;

        public RedNullException() {
            super("Attempt to redden a null node");
        }
    }

    /**
     * Exception to indicate two adjacent red nodes, that is,
     * a parent and a child are both red.
     */
    public static class DoubleRedException extends RuntimeException {

        private static final long serialVersionUID = -9146048206806015395L;
  
        public DoubleRedException(String msg) {
            super(msg);
        }

    }

    /**
     * Exception class to indicate that a node's two subtrees have
     * different black heights.
     */
    private static class InconsistentBlackHeightException extends RuntimeException {

        private static final long serialVersionUID = 5624546038065294822L;
        private String keyRep;
        private int leftHeight, rightHeight;

        public InconsistentBlackHeightException(String keyRep, int leftHeight, int rightHeight) {
            this.keyRep = keyRep;
            this.leftHeight = leftHeight;
            this.rightHeight = rightHeight;
        }

        @Override
        public String getMessage() {
            return keyRep + " has left height " + leftHeight + 
                    " and right height " + rightHeight + ".";
        }
    }

    
    /**
     * Are we in debugging mode? 
     */
    public static boolean DEBUG = true;

    
    /**
     * Supertype for the Node child classes of this tree map class
     * defining the distinctive operations to help verifying
     * and fixing RB nodes.
     * @param <VV>
     */
    protected interface RBNode<KK extends Comparable<KK>, VV> extends AbstractRecursiveBSTMap.Node<KK, VV, RBNode<KK, VV>>{ 
        /**
         * Is this node red? We don't have an isBlack() 
         * operation, which would be the logical negation of
         * this method's result.
         */
        boolean isRed();

        /**
         * What is the the number of black nodes on the
         * route from this node to any leaf (or null object),
         * as stored here? This includes this node itself and 
         * any leaf, but not the null objects (which are considered 
         * black but not counted in the black height). In a 
         * correct RB tree, the black height at any node is consistent 
         * in both its subtrees.
         */
        int blackHeight();

        /**
         * Recompute the black height of this node and the
         * subtree rooted here without descending the tree but
         * assuming the stored black heights of the children are
         * correct.
         */
        int softRecomputeBlackHeight();

        /**
         * Recompute the black height of this node and the
         * subtree rooted here from scratch, recursively descending the
         * tree to recompute the black heights of all descendants.
         */
        int hardRecomputeBlackHeight();

        /**
         * Set this node to be black.
         */
        void blacken();

        /**
         * Set this node to be red.
         */
        void redden();
    } 
    
    /**
     * Class for real, "non-null" nodes, containing the code for
     * verifying the general RB property.
     */
    public abstract class AbstractRBRealNode extends AbstractRecursiveBSTMap.AbstractRealNode<K, V, RBNode<K, V>>implements RBNode<K, V> {

        /**
         * The red/black attribute stored as true = red, false = black.
         */
        private boolean isRed;

        /**
         * The number of black nodes on any route from here
         * to any leaf.
         */
        private int blackHeight;
        
        
        /**
         * Constructor: inherited fields are passed in, but new nodes
         * are assumed red, and black height is soft-(re)computed.
         */
        protected AbstractRBRealNode(K key, V val, RBNode<K, V> left,
                RBNode<K, V> right) {
            super(key, val, left, right);
            isRed = true;
            softRecomputeBlackHeight();
        }
        
        public boolean isRed() { return isRed; }

        public int blackHeight() { return blackHeight; }
        
        public void blacken() { isRed = false; }
        
        public void redden() { isRed = true; }
        
        // recomputing the black height---soft or hard---will
        // check for black height consistency and throw an exception
        // if inconsistency is found.
        
        public int softRecomputeBlackHeight() {
            int leftBlackHeight = left.blackHeight();
            int rightBlackHeight = right.blackHeight();
            if (leftBlackHeight != rightBlackHeight) {
                throw new InconsistentBlackHeightException(key.toString(), leftBlackHeight, rightBlackHeight);
            }
            blackHeight = leftBlackHeight + (isRed? 0 : 1);
            return blackHeight;
        }
        
        public int hardRecomputeBlackHeight() {
            assert DEBUG;
            int leftBlackHeight = left.hardRecomputeBlackHeight();
            int rightBlackHeight = right.hardRecomputeBlackHeight();
            if (leftBlackHeight != rightBlackHeight)
                throw new InconsistentBlackHeightException(key.toString(), leftBlackHeight, rightBlackHeight);
            blackHeight = leftBlackHeight + (isRed? 0 : 1);
            return blackHeight;
        }
        
        /**
         * Verify that the subtree rooted here meets all the constraints
         * of the variety of balanced BST that it is in. 
         * Verify the children (which implicitly does a hard recompute)
         * and then recompute here (using softRecompute()).
         * The recomputing of the black heights will throw an exception
         * if they are not found consistent. Check for double reds between
         * this node and its children, throwing an exception if found.
         */
        public void verify() {
            if (DEBUG) {
                left.verify();
                right.verify();
                softRecomputeBlackHeight();
                if (isRed) {
                    if (left.isRed())
                        throw new DoubleRedException("Left, " + key);
                    else if (right.isRed())
                        throw new DoubleRedException("Right, " + key);
                }
            }
        }

        // convenient methods for testing and retrieving grandchildren
        
        protected boolean isRedLL() { return ((AbstractRBRealNode) left).left.isRed(); }
        protected boolean isRedLR() { return ((AbstractRBRealNode) left).right.isRed(); }
        protected boolean isRedRL() { return ((AbstractRBRealNode) right).left.isRed(); }
        protected boolean isRedRR() { return ((AbstractRBRealNode) right).right.isRed(); }
        protected AbstractRBRealNode getLL() {
            return (AbstractRBRealNode) ((AbstractRBRealNode) left).left;
        }
        protected AbstractRBRealNode getLR() {
            return (AbstractRBRealNode) ((AbstractRBRealNode) left).right;
        }
        protected AbstractRBRealNode getRL() {
            return (AbstractRBRealNode) ((AbstractRBRealNode) right).left;
        }
        protected AbstractRBRealNode getRR() {
            return (AbstractRBRealNode) ((AbstractRBRealNode) right).right;
        }
       
        @Override
        public String toString() {
            return "(" + left + " " + 
                     (isRed? "$" : "") + key + (isRed? "$" : "") +
                     " " + right + ")";
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
    protected abstract RBNode<K,V> realNodeFactory(K key, V val, RBNode<K, V> left, RBNode<K, V> right);

    /**
     * Class for null objects for this tree map---mainly do-nothing except
     * that the put method makes and returns a new real node.
     */    
    protected class RBNullNode extends AbstractRecursiveBSTMap.AbstractNullNode<K, V, RBNode<K,V>> implements RBNode<K, V> {

        public RBNode<K, V> put(K key, V val) {
            return realNodeFactory(key, val, nully, nully);
        }

        public boolean isRed() {
            return false;
        }

        public int blackHeight() {
            return 1;
        }

        public int softRecomputeBlackHeight() {
            return 1;
        }

        public int hardRecomputeBlackHeight() {
            return 1;
        }

        public void blacken() {}

        public void redden() {
            throw new RedNullException();
        }
    }

    /**
     * A null node object. This is for efficiency to reduce
     * the number of object. This effectively makes BasicNullNode
     * a singleton for this class.
     */
    protected RBNullNode nully;

    /**
     * Basic constructor for an empty map
     */
    public AbstractRedBlackTreeMap() {
        root = nully = new RBNullNode();
    }

    /**
     * Just like inherited put but ensures the root is
     * black.
     */
    @Override
    public void put(K key, V val) {
        super.put(key, val);
        root.blacken();
    }
}
