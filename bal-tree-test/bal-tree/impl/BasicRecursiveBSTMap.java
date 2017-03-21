package impl;

/**
 * BasicRecursiveBSTMap
 * 
 * An unbalanced, naive BST tree. This class simply inherits code
 * from AbstractRecursiveBSTMap with dummy implementations for
 * the verify and fixup operations.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public class BasicRecursiveBSTMap<K extends Comparable<K>, V> extends AbstractRecursiveBSTMap<K, V, BasicRecursiveBSTMap.BasicNode<K, V>> {

    /**
     * Supertype for the Node child classes of this tree map class
     */
    protected interface BasicNode<KK extends Comparable<KK>, VV> extends AbstractRecursiveBSTMap.Node<KK, VV, BasicNode<KK, VV>>{ }

    /**
     * Class for real, "non-null" nodes.
     */
    public class BasicRealNode extends AbstractRecursiveBSTMap.AbstractRealNode<K, V, BasicRecursiveBSTMap.BasicNode<K, V>>implements BasicNode<K, V> {

        public BasicRealNode(K key, V val, BasicNode<K, V> left, BasicNode<K, V> right) {
            super(key, val, left, right);
        }

        protected BasicNode<K, V> self() {
            return this;
        }

        // do nothing
        protected BasicNode<K, V> fixup() {
            return this;
        }

        // do nothing
        public void verify() {
        }
    }

    /**
     * Class for null objects for this tree map
     */
    protected class BasicNullNode extends AbstractRecursiveBSTMap.AbstractNullNode<K, V, BasicRecursiveBSTMap.BasicNode<K, V>> implements  BasicRecursiveBSTMap.BasicNode<K, V>{
        public BasicNode<K, V> put(K key, V val) {
            return new BasicRealNode(key, val, nully, nully);
        }
    }

    // ---------- The BasicRecursiveBSTMap class proper starts here -----------
    // (and ends soon hereafter)
    
    /**
     * A null node object. This is for efficiency to reduce
     * the number of object. This effectively makes BasicNullNode
     * a singleton for this class.
     */
    private BasicNullNode nully;

    /**
     * Basic constructor for an empty map
     */
    public BasicRecursiveBSTMap() {
        root = nully = new BasicNullNode();
    }
    
}
