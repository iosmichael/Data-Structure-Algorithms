package impl;


import java.util.Iterator;
import java.util.NoSuchElementException;
import adt.Map;
import adt.Stack;

/**
 * AbstractRecursiveBSTMap
 * 
 * Class to where the commonality among various recursive binary
 * search tree classes can be pulled up. This is to capture most
 * of the functionality of a binary search tree in such a way that
 * the machinery for monitoring and rebalancing the trees can
 * be added on by child classes.
 * 
 * This and all child classes are set up to have "null objects"
 * in place of where null links would be. Thus methods can be
 * called on these place holders, avoiding what would otherwise
 * be many checks for null links.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public abstract class AbstractRecursiveBSTMap<K extends Comparable<K>, V, N extends AbstractRecursiveBSTMap.Node<K, V, N>> 
        implements Map<K, V> {

    /**
     * Basic specification of what a node supports.
     */
    protected interface Node<KK extends Comparable<KK>, VV, NN extends AbstractRecursiveBSTMap.Node<KK,VV, NN>> {
        // support for map operations which will be called recursively on the nodes

        /**
         * Insert or overwrite a value for a key, possibly
         * altering the tree.
         * @return The root of the new tree resulting from this
         * insertion and any rebalancing that is triggered by it.
         */
        NN put(KK key, VV val);

        boolean containsKey(KK key);

        VV get(KK key);

        // getter methods for content of the nodes; used in rotations
        NN left();
        NN right();
        KK key();
        
        //  miscellaneous support, especially for monitoring size and balance

        /**
         * What is the height of the subtree rooted here, that is,
         * the maximum number of links from here to any leaf?
         * The name of the method is to distinguish it from the
         * blackheight of red-black trees.
         */
        int realHeight();

        /**
         * How many leaves are there in the subtree rooted at this 
         * node? Its name suggests that this information is not stored
         * in the node but would need to be calculated on demand, which
         * is expensive.
         */
        int countLeaves();

        /**
         * Compute the contribution this subtree makes to the total
         * depth of the entire tree. "Total depth" is the sum of
         * all nodes' depths. 
         * @param yourDepth The depth of this node, as indicated
         * by the context in which it is called, presumably its
         * depth in the entire tree.
         * @return This node's depth (as given) plus the depths of
         * all nodes descendant from this one.
         */
        int totalDepth(int yourDepth);

        /**
         * Is this node a null object?
         */
        boolean isNull();

        /**
         * Verify that the subtree rooted here meets all the constraints
         * of the variety of balanced BST that it is in. If verification
         * fails, this method will throw an exception indicating the
         * kind of violation. This method will recalculate the attributes
         * of the tree from scratch. Thus it will be expensive and should 
         * be called only for debugging (not in performance testing).
         */
        void verify();
    }
    
    /**
     * Class to provide functionality for "null-object" nodes
     * for all varieties of balanced trees refining the enclosing class.
     */
    protected abstract static class AbstractNullNode<KK extends Comparable<KK>, VV, NN extends Node<KK, VV, NN>> implements Node<KK, VV, NN> {
        // put() is deferred to the child classes, which will 
        // insert new items into the tree in a way consistent
        // with that variety of balanced tree's constraints.
        
        /**
         * No key is contained here
         */
        public boolean containsKey(KK key) {
            return false;
        }

        /**
         * No key is contained here
         */
        public VV get(KK key) {
            return null;
        }

        // These getters are unsupported.
        // In a way it is "inelegant" that these methods exist in the
        // supertype Node, but they are needed by the rebalancing code
        public NN left() { throw new UnsupportedOperationException(); }
        public NN right() { throw new UnsupportedOperationException(); }
        public KK key() { throw new UnsupportedOperationException(); }

        /**
         * What is the height of the subtree rooted here, that is,
         * the maximum number of links from here to any leaf?
         * This is not a real node, so it doesn't really have
         * a height. By returning -1, this will help the
         * height-calculating code in other real node classes
         * calcualte correct answers for leaves.
         */
        public int realHeight() { return -1; }

        /**
         * How many leaves are there in the subtree rooted at this 
         * node? There are no leaves here
         */
        public int countLeaves() { return 0; }

        /**
         * Compute the contribution this subtree makes to the total
         * depth of the entire tree. This is not a real node, so it 
         * contributes nothing to the total depth.
         */
        public int totalDepth(int yourDepth) { return 0; }

        /**
         * Is this node a null object? Yes.
         */
        public boolean isNull() { return true; }

        
        /**
         * Verify that the subtree rooted here meets all the constraints
         * of the variety of balanced BST that it is in. Nothing needs
         * to be done.
         */
        public void verify() { } 
        
        
        public String toString() {
            return "(:)";
        }


    }

    /**
     * Class to provide functionality common to all "real" nodes, that
     * is, nodes that are not null objects. The intent is to provide all
     * functionality for finding keys or where keys are supposed to be,
     * deferring code for verification and rebalancing.
     */
    protected abstract static class AbstractRealNode<KK extends Comparable<KK>, VV, NN extends Node<KK, VV, NN>> implements Node<KK, VV, NN>{

        /**
         * The key of the pair at this node.
         */
        protected KK key;

        /**
         * The value of the pair at this node.
         */
        protected VV value;

        /**
         * The left child
         */
        protected NN left;

        /**
         * The right child
         */
        protected NN right;
        
        /**
         * Basic convenience constructor.
         */
        protected AbstractRealNode(KK key, VV val, NN left, NN right) {
            this.key = key;
            this.value = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "(" + left + " " + key + " " + right + ")";
        }
        

        /**
         * Insert or overwrite a value for a key.
         * This triggers a fixup.
         * PRECONDITION: This subtree satisfies the constraints
         * of variety of balanced tree modeled by the child class
         * and is part of a larger tree that also satisfies those
         * constraints.
         * POSTCONDITION: The subtree whose root is returned,
         * representing the same information as this subtree 
         * except for the modification indicated by invoking this
         * method, satisfies the constraints except 
         * @return The node at the root of the subtree
         * that results from the insertion and any
         * rebalancing that it triggers.
         */
        public NN put(KK key, VV val) {
            int compare = key.compareTo(this.key);
            if (compare < 0)
                left = left.put(key, val);
            else if (compare == 0)
                this.value = val;
            else // if (compare > 0) 
                right = right.put(key, val);
            return fixup();
        }

        /**
         * Fix this subtree to conform to the constraints of
         * this variety of balanced tree.
         * @return The root of the tree like this one but
         * satisfying the constraints.
         */
        protected abstract NN fixup();
        
        // Other map operations straightforward....
        
        public boolean containsKey(KK key) {
            int compare = key.compareTo(this.key);
            if (compare < 0) 
                return left.containsKey(key);
            else if (compare == 0) 
                return true;
            else  // if (compare > 0)
                return right.containsKey(key);
        }

        public VV get(KK key) {
            int compare = key.compareTo(this.key);
            if (compare < 0) 
                return left.get(key);
            else if (compare == 0) 
                return value;
            else  // if (compare > 0)
                return right.get(key);
        }

        // getter method straightforward....
        
        public NN left() { return left; }

        public NN right() { return right; }

        public KK key() { return key; }

        
        /**
         * What is the height of the subtree rooted here, that is,
         * the maximum number of links from here to any leaf?
         * One plus the larger height of the two subtrees.
         */
        public int realHeight() {
            int leftHeight = left.realHeight();
            int rightHeight = right.realHeight();
            return 1 + (leftHeight > rightHeight? leftHeight : rightHeight);
        }

        /**
         * How many leaves are there in the subtree rooted at this 
         * node? This is a leaf if it has two null children,
         * else count the leaves of the children.
         */
       public int countLeaves() {
            if (left.isNull() && right.isNull()) return 1;
            else return left.countLeaves() + right.countLeaves();
        }

       /**
        * Compute the contribution this subtree makes to the total
        * depth of the entire tree. "Total depth" is the sum of
        * all nodes' depths. The depth of the (immediate) children
        * is one more than this node's depth.
        * @param yourDepth The depth of this node, as indicated
        * by the context in which it is called, presumably its
        * depth in the entire tree.
        * @return This node's depth (as given) plus the depths of
        * all nodes descendant from this one.
        */
        public int totalDepth(int yourDepth) {
            return yourDepth + left.totalDepth(yourDepth + 1) 
                    + right.totalDepth(yourDepth + 1);
        }

        /**
         * Is this node a null object? No.
         */
        public boolean isNull() {
            return false;
        }
    }

    // ---------- AbstractRecursiveBSTMap class proper starts here
    

    /**
     * The root of this tree. Invariant: this is never null;
     * when the tree is empty, this should refer to a "null
     * object" (instance of child class of AbstractNullNode)
     */
    protected N root;
    
    
    // The constructor (of any child class) should set root
    // to a null object.
    
    
    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
        // The recursive implementation of put in the
        // nodes returns the root of the transformed
        // tree.
        root = root.put(key, val);
        // The verify method is called no matter what.
        // Child classes should have a debugging mode
        // which, when turned off, will turn this into a
        // dummy call.
        root.verify();
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
   public V get(K key) {
        return root.get(key);
    }

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public boolean containsKey(K key) {
        return root.containsKey(key);
    }
    
    /**
     * Iterate over the elements of this map "BST" order, which
     * corresponds to a pre-order depth-first traversal.
     */
    public Iterator<K> iterator() {
        // The stack contains the left-link lineage of the 
        // the next node, including the next node itself;
        // the next node is the top element
        final Stack<N> st = new ListStackTopFront<N>();
        for (N current = root; ! current.isNull();
                current = current.left())
            st.push(current);

        return new Iterator<K>() {
            public boolean hasNext() {
                return ! st.isEmpty();
            }

            public K next() {
                if (st.isEmpty())
                    throw new NoSuchElementException();
                else {
                    N nextNode = st.pop();
                    for (N current = nextNode.right(); ! current.isNull(); 
                            current = current.left())
                        st.push(current);
                    return nextNode.key();
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
            
        };
    }

    public int height() { return root.realHeight(); }
    
    @Override
    public String toString() {
        return root.toString();
    }

}

