package impl;

public interface AbstractRBNode <KK extends Comparable<KK>, VV> extends AbstractRecursiveBSTMap.Node<KK, VV, RBNode<KK, VV>>{ 
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