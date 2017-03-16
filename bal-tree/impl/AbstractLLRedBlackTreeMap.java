package impl;

/**
 * AbstractLLRedBlackTreeMap
 * 
 * A class refining the restrictions on red-black trees by
 * extending the verification code, now to check that red nodes
 * must be left children. This continues to defer the fixup code.
 * 
  * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 2, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public abstract class AbstractLLRedBlackTreeMap<K extends Comparable<K>, V> extends
        AbstractRedBlackTreeMap<K, V> {

    /**
     * Exception to indicate that a red node is a right child.
     */
    public static class RedRightException extends RuntimeException {

        private static final long serialVersionUID = 3766304873228252316L;

        public RedRightException(String msg) { super(msg); }
    }


    /**
     * Refinement of the abstract class for real RB nodes.
     * The verification code add a check for a right red.
     */
    protected abstract class AbstractLLRBRealNode extends AbstractRBRealNode {

        protected AbstractLLRBRealNode(K key, V val,
                RBNode<K, V> left,
                RBNode<K, V> right) {
            super(key, val, left, right);
        }

        @Override
        public void verify() {
            super.verify();
            if (right.isRed())
                throw new RedRightException(key + " has red right");
        }
    }
    

}
