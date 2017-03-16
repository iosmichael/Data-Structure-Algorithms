package impl;

/**
 * OptimizedQuickUnionDisjointSet
 * 
 * A variation of the QuickUnion implementation that
 * uses the optimizations from both the weighted version
 * and the compressing-find version. Unlike those classes,
 * this one does not inherit from QuickUnionDisjointSet,
 * since it would end up overriding both methods that
 * class defines.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 1, 2015
 */
public class OptimizedQuickUnionDisjointSet extends AbstractDisjointSet {

    private int[] sizes;
    
    public OptimizedQuickUnionDisjointSet(int size) {
        super(size);
        sizes = new int[size];
        for (int i = 0; i < sizes.length; i++)
            sizes[i] = 1;
    }

    /**
     * Find the leader of this element's set.
     * @param p An element of the universe.
     * @return The representative leader of the element's set
     * @throws NoSuchElementException If the given element is
     * beyond the bounds of this universe
     */
    public int find(int p) {
        checkIndex(p);
        if (id[p] == p) return p;
        else return id[p] = find(id[p]);        
    }
    
    /**
     * Merge the sets of these two elements.
     * @throws NoSuchElementException If either element is
     * beyond the bounds of this universe
     */
    public void union(int p, int q) {
        checkIndex(p);
        checkIndex(q);
        int pLeader = find(p);
        int qLeader = find(q);
        if (pLeader != qLeader) {
            if (sizes[pLeader] < sizes[qLeader]) {
                id[pLeader] = id[qLeader];
                sizes[qLeader] += sizes[pLeader];
            }
            else {
                id[qLeader] = id[pLeader];
                sizes[pLeader] += sizes[qLeader];
            }
            count--;
        }

    }
    
    
}
