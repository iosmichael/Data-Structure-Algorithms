package impl;

import java.util.Iterator;
import java.util.Stack;

import adt.Map;

/**
 * BTreeMap
 * 
 * This implementation is based on the description in
 * CLRS, chapter 18.
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 16, 2014
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */

public class BTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    /**
     * All internal nodes (except the root in special
     * underflow cases) must have at least this many
     * children--and no more than twice this many children.
     * This is equivalent to t in CLRS.
     */
    private final int minDegree;
    
    
    // --------- Classes for nodes in the tree --------------
    
    /**
     * Things common between leaves and internals
     */
    abstract class BNode {
        /**
         * Array of keys, partially filled
         */
        K[] keys;

        /**
         * Array of vals, partially filled
         */
        V[] vals;

        /**
         * The number of pairs currently stored; 
         * this will also be one less than the number of
         * children in internal nodes.
         */
        int n;

        @SuppressWarnings("unchecked")
        BNode() {
            keys = (K[]) new Comparable[2 * minDegree - 1];
            vals = (V[]) new Object[2 * minDegree - 1];
            n = 0;
        }

        boolean isFull() { return n == keys.length;  }
        
        /**
         * In the given node, find the location (index) of the key
         * or where it would go (the index of smallest greater than
         * or equal to key)
         * @param key
         * @return The index in keys where the key is, if anywhere,
         * or otherwise the index in children (for leaves, the hypothetical
         * index that would be in children) indicating the subtree
         * where the key would be found. Note the range is [0, n]; note
         * especially that this could return n which is not a valid index
         * into keys.  
         */
        int binarySearchKeys(K key) {
            // special case for empty tree
            if (n == 0)
                return 0;
            
            // Invariants:
            // The given key comes after all the keys in [0, start)
            // and before all the keys in [stop, n].
            // mid = floor((stop + start) / 2).
            // comparison = the result of comparing the key at mid to 
            // the given key
            int start = 0,
                stop = n,
                mid = n / 2; 
            int comparison = keys[mid].compareTo(key);
        
            // when the range contains only one position,
            // start will equal mid
            while (comparison != 0 && start != mid) {
                if (comparison < 0) 
                    start = mid;
                else {
                    assert(comparison > 0);
                    stop = mid;
                }
                mid = (start + stop) / 2;
                comparison = keys[mid].compareTo(key);
            }
            // if mid is less than the given key,
            // return one greater than mid, to comply with
            // "index of smallest greater than or equal to"
            if (comparison < 0)
                return mid + 1;
            else
                return mid;
        }

        /**
         * Splits this node into two nodes. Valid only if
         * this node is full.
         * @return The new sibling to the right.
         * PRECONDITION: This node is full
         */
        public BNode split() {
            assert isFull();
            // Make the actual node (depends on what
            // kind of node this is).
            BNode sibling = makeSibling();
            // copy about half the associations
            // to the sibling. 
            // Invariant: the j largest-indexed key-val 
            // pairs have been copied to the first j
            // positions in the new sibling
            for (int j = 0; j < sibling.n; j++) {
                sibling.keys[j] = keys[j+minDegree];
                sibling.vals[j] = vals[j+minDegree];
            }
            // reduce this node's number of associations
            n = minDegree - 1;
            return sibling;
        }

        // --- method signatures--leaves and internals handle differently ---
        
        /**
         * Find the location (node and index) where a key is,
         * or where it would go.
         * @param key
         * @return
         */
        abstract Found search(K key);

        /**
         * Contains the part of split() that is specific to Leaves
         * and Internals
         * @return The new sibling to the right
         */
        abstract BNode makeSibling();

        /**
         * Insert the key and value into the subtree rooted here.
         * PRECONDITION: This node is not full.
         * @param key
         * @param val
         */
        abstract void insertNonFull(K key, V val);
        
        // The next two are for use in the iterator

        /**
         * Is the given index a valid index into the children array?
         */
        abstract boolean canDescend(int i);

        /**
         * Retrieve the child node at the given index, if it exists;
         * throw an exception otherwise. Don't call this unless
         * canDescend() returns true.
         */
        abstract BNode descend(int i);

        // The "biggie" for this assignment
        
        /**
         * Delete the given key (and its val) from the subtree
         * rooted here, if that association exists.
         * Do not call unless this node is known to be the root
         * or to have more than the minimum number of keys (and
         * hence can spare a key). Note that the minimum number
         * of keys is one less than the minimum number of children,
         * so if n == minDegree, there are more than the minimum
         * number of keys.
         * @param key
         */
        abstract void delete(K key);

        // The following five are recommended as helper
        // methods

        /**
         * Delete the greatest key (and its val) from the
         * subtree rooted here. This is used for deleting the
         * predecessor of the key that comes right after this subtree.
         * @return The association that is the predecessor
         */
        abstract Association deletePred();

        /**
         * Delete the least key (and its val) from the subtree rooted here.
         * This is used for deleting the successor of the key that
         * comes right before this subtree.
         * @return The association that is the successor
         */
        abstract Association deleteSucc();

        /**
         * Absorb the given sibling plus key/val from the parent.
         * This must be called on the left of the two siblings, that
         * is, the given sibling must be to this node's right.
         * @param sibling The sibling to absorb; must be a right sibling
         * @param key A key from the parent
         * @param key A val from the parent
         */
        abstract void merge(BTreeMap<K, V>.BNode bNode, K key, V val);

        /**
         * Shuffle an association from a right sibling. The
         * right sibling loses one association (and, if internal,
         * a child). The lost association goes up to the parent and
         * replaces an association that is gained by this node.
         * The lost child is gained by this node.
         * @param sibling The sibling to this node's right
         * @param key The key from the parent
         * @param val The val from the parent
         * @return The association given up by the right sibling.
         */
        abstract BTreeMap<K, V>.Association shareRight(
                BTreeMap<K, V>.BNode sibling, K key, V val);


        /**
         * Shuffle an association from a left sibling. The
         * left sibling loses one association (and, if internal,
         * a child). The lost association goes up to the parent and
         * replaces an association that is gained by this node.
         * The lost child is gained by this node.
         * @param sibling The sibling to this node's left
         * @param key The key from the parent
         * @param val The val from the parent
         * @return The association given up by the right sibling.
         */
        abstract BTreeMap<K, V>.Association shareLeft(
                BTreeMap<K, V>.BNode sibling, K key, V val);
        
    }
    

    /**
     * Leaves have no extra attributes, but have different implementations
     * for the operations.
     */
    class Leaf extends BNode {
        Leaf() { 
            super();
        }
        
        /**
         * Find the location (node and index) where a key is,
         * or where it would go. For leaves we binary search
         * for the key, and it's either there or it isn't.
         * @param key
         * @return
         */        
        Found search(K key) {
            // Special case that happens only in the root.
            if (n == 0) {
                assert  this == root;
                return new Found(this, 0, false);
            }
               
            int pos = binarySearchKeys(key);
        
            return new Found(this, pos, 
                        pos < n && keys[pos].compareTo(key) == 0);
       }


        /**
         * Do the part of split() that is specific to Leaves;
         * specifically, don't copy any children, since there are none.
         * @return The new sibling
         */
        BNode makeSibling() {
            Leaf sibling = new Leaf();
            sibling.n = minDegree - 1;
            return sibling;
        }


        /**
         * Insert the key and value into the subtree rooted here.
         * PRECONDITION: This node is not full.
         * Move everything over to make room  
         * @param key
         * @param val
         */
        void insertNonFull(K key, V val) {
            assert ! isFull();
            int i = n-1;
            // Invariant: Position i + 1 is available
            // to be written to; keys in positions
            // [i+2, n] are greater than the key
            // we want to insert
            while (i >= 0 && key.compareTo(keys[i])<0) {
                keys[i+1] = keys[i];
                vals[i+1] = vals[i];
                i--;
           }
            keys[i+1] = key;
            vals[i+1] = val;
            n++;
        }

        /**
         * Is the given index a valid index into the children array?
         * Since this is a leaf (no children), the answer is always
         * no.
         */
        boolean canDescend(int i) {
            return false;
        }

        /**
         * Retrieve the child node at the given index, if it exists;
         * throw an exception otherwise. Don't call this unless
         * canDescend() returns true. Since this is a leaf,
         * this will always throw an exception.
         */
        BTreeMap<K, V>.BNode descend(int i) {
            throw new UnsupportedOperationException();
        }

        /**
         * Delete the given key (and its val) from the subtree
         * rooted here, if that association exists.
         * Do not call unless this node is known to be the root
         * or to have more than the minimum number of keys (and
         * hence can spare a key). Note that the minimum number
         * of keys is one less than the minimum number of children,
         * so if n == minDegree, there are more than the minimum
         * number of keys. If the key is here, shift things over
         * to remove it.
         * @param key
         */
        void delete(K key) {
            assert this == root || n >= minDegree;

            // TODO
            // 8 LOC
            
        }


        /**
         * Delete the greatest key (and its val) from the
         * subtree rooted here. This is used for deleting the
         * predecessor of the key that comes right after this subtree.
         * This must be the last key/val, so remove and return it.
         * @return The association that is the predecessor
         */
       BTreeMap<K, V>.Association deletePred() {

           // TODO (recommended)
           // 3 LOC
           
           return null;
       }

       /**
        * Delete the least key (and its val) from the subtree rooted here.
        * This is used for deleting the successor of the key that
        * comes right before this subtree. This must be the first key/val,
        * so remove and return it.
        * @return The association that is the successor
        */
        BTreeMap<K, V>.Association deleteSucc() {

            // TODO (recommended)
            // 7 LOC

            return null;
        }

        /**
         * Absorb the given sibling plus key/val from the parent.
         * This must be called on the left of the two siblings, that
         * is, the given sibling must be to this node's right.
         * @param sibling The sibling to absorb; must be a right sibling
         * @param key A key from the parent
         * @param key A val from the parent
         */
        void merge(BTreeMap<K, V>.BNode sibling, K key, V val) {
            assert n == minDegree - 1 && sibling.n == minDegree - 1
                    && sibling instanceof BTreeMap.Leaf;

            // TODO (recommended)
            // 8 LOC
            
            
        }

        /**
         * Shuffle an association from a right sibling. The
         * right sibling loses one association. The lost association 
         * goes up to the parent and replaces an association that is 
         * gained by this node.
         * @param sibling The sibling to this node's right
         * @param key The key from the parent
         * @param val The val from the parent
         * @return The association given up by the right sibling.
         */
        BTreeMap<K, V>.Association shareRight(BTreeMap<K, V>.BNode sibling,
                K key, V val) {
            assert n == minDegree - 1 && sibling.n >= minDegree
                    && sibling instanceof BTreeMap.Leaf;

            // TODO (recommended)
            // 10 LOC

            return null;
        }

        /**
         * Shuffle an association from a left sibling. The
         * left sibling loses one association. 
         * The lost association goes up to the parent and
         * replaces an association that is gained by this node.
         * @param sibling The sibling to this node's left
         * @param key The key from the parent
         * @param val The val from the parent
         * @return The association given up by the right sibling.
         */
        BTreeMap<K, V>.Association shareLeft(BTreeMap<K, V>.BNode sibling,
                K key, V val) {
            assert n == minDegree - 1 && sibling.n >= minDegree
                    && sibling instanceof BTreeMap.Leaf;

            
            // TODO (recommended)
            // 10 LOC

            return null;
        }
    }

    /**
     * Internals differ from leaves in that they also have children.
     */
    private class Internal extends BNode {

        /**
         * Array for children, partially filled (one more
         * than keys and vals).
         */
        BNode[] children;

        // suppresswarnings needed because of arrays and generics
        // not playing nicely with each other
        @SuppressWarnings("unchecked") 
        Internal() {
            children = new BTreeMap.BNode[2 * minDegree];
        }

        
        /**
         * Find the location (node and index) where a key is,
         * or where it would go. For internals, if the key isn't
         * here we call search recursively on the child where
         * it might be.
         * @param key
         * @return
         */        
        Found search(K key) {
            int pos = binarySearchKeys(key);

            if (pos < n && keys[pos].compareTo(key) == 0)
                return new Found(this, pos, true);
            else
                return children[pos].search(key);
        }

        /**
         * Do the part of split() that is specific to internals;
         * specifically, copy the children.
         * @return The new sibling
         */        
        BTreeMap<K, V>.BNode makeSibling() {
            Internal sibling = new Internal();
            sibling.n = minDegree - 1;

            // Invariant: Positions [0, j) in the sibling contain
            // children [minDegree, minDigree+j) in this
            for (int j = 0; j < sibling.n + 1; j++) 
                sibling.children[j] = children[j+minDegree];
            return sibling;
        }

        /**
         * Insert the key and value into the subtree rooted here.
         * PRECONDITION: This node is not full.
         * Find the child where it would go. If that one is
         * full, split it. Either way, insert into that subtree
         * (if a split, the correct subtree might be either of
         * the nodes from the split---need to check the key
         * between them).
         * @param key
         * @param val
         */
        void insertNonFull(K key, V val) {
        	assert !isFull();
        	
            int i = binarySearchKeys(key);
            if (children[i].isFull()) {
                splitChild(i);
                if (key.compareTo(keys[i])>0)
                    i++;
            }
            children[i].insertNonFull(key, val);
        }

        /**
         * Turn a child node into two new children.
         * Valid only in a non-full node but with a 
         * full child.
         * PRECONDITION: This node is not full but the
         * indicated child is full
         * POSTCONDITION: The node has one more child than
         * before.
         * @param pos The position of the child to split.
         */
        void splitChild(int pos) {
            assert ! isFull();
            BNode child = children[pos],    // old child
                    sibling = child.split();   // new child
            assert child.isFull();
            // Move the children over to make room
            for (int j = n; j >= pos+1; j--)
                children[j+1] = children[j];
            // insert new child
            children[pos+1] = sibling;
            // Move the corresponding keys and values
            for (int j = n-1; j >= pos; j--) {
                keys[pos+1] = keys[pos];
                vals[pos+1] = vals[pos];
            }
            // bring up the last pair in the origina
            // child to differentiate it from the new sibling
            keys[pos] = child.keys[minDegree - 1];
            vals[pos] = child.vals[minDegree - 1];
            n++;
        }

        /**
         * Is the given index a valid index into the children array?
         * Check to see if it is in range.
         */
         boolean canDescend(int i) {
            return i >= 0 && i <= n;
        }

         /**
          * Retrieve the child node at the given index, if it exists;
          * throw an exception otherwise. Don't call this unless
          * canDescend() returns true. If the index is bad, 
          * an IndexOutOfBoundsException will be thrown.
          */
         BTreeMap<K, V>.BNode descend(int i) {
            if (! canDescend(i))
                throw new IndexOutOfBoundsException();
            else
                return children[i];
        }

         /**
          * Delete the given key (and its val) from the subtree
          * rooted here, if that association exists.
          * Do not call unless this node is known to be the root
          * or to have more than the minimum number of keys (and
          * hence can spare a key). Note that the minimum number
          * of keys is one less than the minimum number of children,
          * so if n == minDegree, there are more than the minimum
          * number of keys. Very very complicated.
          * @param key
          */
         void delete(K key) {
            assert this == root || n >= minDegree;

            // Find the position in this node where the key
            // either is or would be
            int pos = binarySearchKeys(key);

            // TODO (biggie)

            // 60 LOC
            
            // Based on the cases presented in CLRS pg 500-501 
            
            // 1. Is the key here at this node?
                // 1a. Does the child before the key have
                // a key/val to spare?
                // 1b. If not, does the child after the key have
                // a key/val to spare?
                // 1c. Ok, neither child on either side of the key
                // have a key/val to spare.
            // 2. Ok, if the key is anywhere in the tree, 
            // it's in the indicated child
                // 2a. Does that child have at least minDegree keys?
                // 2b. Ok, that child does not have minDegree keys
                    // 2bi. Does the sibling to the left have at least minDegree keys?
                    // 2bii. If not, does the sibling to the right have at least minDegree keys?
                    // 2biii. If not, is this child NOT the last child?
                    // 2biv. Ok, neither sibling can spare a key/val and this 
                    // is the last child. (Note it can't be the zeroth child.)
                        //assert pos > 0;
                    // In ALL 2b cases, delete the key from the subtree
                    // rooted in the indicated child
            
        }

         // I'm giving you the next five helpers.
         // (Feeling generous)
         
         /**
          * Delete the greatest key (and its val) from the
          * subtree rooted here. This is used for deleting the
          * predecessor of the key that comes right after this subtree.
          * Search in the last subtree
          * @return The association that is the predecessor
          */
        BTreeMap<K, V>.Association deletePred() {
            return children[n].deletePred();
        }

        /**
         * Delete the least key (and its val) from the subtree rooted here.
         * This is used for deleting the successor of the key that
         * comes right before this subtree. Search in the first (zeroth)
         * subtree.
         * @return The association that is the successor
         */
        BTreeMap<K, V>.Association deleteSucc() {
            return children[0].deleteSucc();
        }
        
        /**
         * Absorb the given sibling plus key/val from the parent.
         * This must be called on the left of the two siblings, that
         * is, the given sibling must be to this node's right.
         * @param sibling The sibling to absorb; must be a right sibling
         * @param key A key from the parent
         * @param key A val from the parent
         */
        void merge(BTreeMap<K, V>.BNode sibling, K key, V val) {
            assert n == minDegree - 1 && sibling.n == minDegree - 1
                    && sibling instanceof BTreeMap.Internal;

            @SuppressWarnings("unchecked")
            Internal sib = (Internal) sibling;
            keys[n] = key;
            vals[n] = val;
            n++;
            // Invariant: children, keys, and values from 
            // positions [0, i) in the sibling have been copied
            // to positions [n, i+n) here.
            for (int i = 0; i < sibling.n; i++) {
                children[i + n] = sib.children[i];
                keys[i + n] = sibling.keys[i];
                vals[i + n] = sibling.vals[i];
            }
            children[sibling.n + n] = sib.children[sibling.n];
            n += sibling.n;
        }

        /**
         * Shuffle an association from a right sibling. The
         * right sibling loses one association (and, if internal,
         * a child). The lost association goes up to the parent and
         * replaces an association that is gained by this node.
         * The lost child is gained by this node.
         * @param sibling The sibling to this node's right
         * @param key The key from the parent
         * @param val The val from the parent
         * @return The association given up by the right sibling.
         */
        BTreeMap<K, V>.Association shareRight(BTreeMap<K, V>.BNode sibling,
                K key, V val) {
            assert n == minDegree - 1 && sibling.n >= minDegree
                    && sibling instanceof BTreeMap.Internal;
            @SuppressWarnings("unchecked")
            Internal sib = (Internal) sibling;
            keys[n] = key;
            vals[n] = val;
            children[n+1] = sib.children[0];
            Association toReturn = new Association(sibling.keys[0], sibling.vals[0]);
            // Invariant: children, keys, and values from
            // positions [1, i) in the sibling have been copied
            // to positions [0, i-1).
            for (int i = 1; i < sibling.n; i++) {
                sibling.keys[i-1] = sibling.keys[i];
                sibling.vals[i-1] = sibling.vals[i];
                sib.children[i-1] = sib.children[i];
            }
            // move also the trailing child
            sib.children[sibling.n-1] = sib.children[sibling.n];
            // the sibling (sharer) loses one
            sibling.n--;
            // this node (receiver--more ways than one) gains one
            n++;
            return toReturn;
        }

        /**
         * Shuffle an association from a left sibling. The
         * left sibling loses one association (and, if internal,
         * a child). The lost association goes up to the parent and
         * replaces an association that is gained by this node.
         * The lost child is gained by this node.
         * @param sibling The sibling to this node's left
         * @param key The key from the parent
         * @param val The val from the parent
         * @return The association given up by the right sibling.
         */
        BTreeMap<K, V>.Association shareLeft(BTreeMap<K, V>.BNode sibling,
                K key, V val) {
            assert n == minDegree - 1 && sibling.n >= minDegree
                    && sibling instanceof BTreeMap.Internal;
            @SuppressWarnings("unchecked")
            Internal sib = (Internal) sibling;
            children[n+1] = children[n];
            for (int i = n; i > 0; i--) {
                keys[i] = keys[i-1];
                vals[i] = vals[i-1];
                children[i] = children[i-1];
            }
            keys[0] = key;
            vals[0] = val;
            children[0] = sib.children[sibling.n];
            Association toReturn = new Association(sibling.keys[n-1], sibling.vals[n-1]);
            n++;
            sibling.n--;
            return toReturn;
        }
    }

    
    // ---- The B-Tree class itself begins here ----
    

    /**
     * The root of the tree, which may violate the
     * B-tree properties if it is a leaf and not full.
     */
    BNode root;
    
    /**
     * To initialize a BTree, specify a minimum degree.
     * The root is initially an empty leaf.
     * @param minDegree
     */
    public BTreeMap(int minDegree) {
        this.minDegree = minDegree;
        root = new Leaf();
    }

    /**
     * Simple class to encapsulate the result of a search
     * for a key: Was it found, and, if so, where is it?
     */
    private class Found {
        BNode location;  // which node is it in?
        int index;       // where is it in that node?
        boolean found;   // wait, was it found at all?
        public Found(BNode location, int index, boolean found) {
            this.location = location;
            this.index = index;
            this.found = found;
        }
    }

    /**
     * Simple class to encapsulate a key and its value in the
     * map. This is useful for method that need to return
     * both a key and a value, such as when nodes share to 
     * the right or to the left.
     */
    private class Association {
        K key;
        V val;
        Association(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
    
    
    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
        // search for the key
        Found result = root.search(key);
        // if it's already there, update the value
        if (result.found)
            result.location.vals[result.index] = val;
        // otherwise, insert
        else {
            // Taking the speculatively-insert approach,
            // we split a full node before we enter it.
            // Handle a full root here.
            
            // This appears in CLRS pg 495 as "B-Tree_Insert"
            if (root.isFull()) {
                Internal newRoot = new Internal();
                newRoot.children[0] = root;
                root = newRoot;
                newRoot.splitChild(0);
            }
            root.insertNonFull(key, val);
        }
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
    public V get(K key) {
        Found result = root.search(key);
        if (result.found)
            return result.location.vals[result.index];
        else 
            return null;
    }

    
    /**
     * Test if this map contains an association for this key.
     * @param key The key to test.
     * @return true if there is an association for this key, false otherwise
     */
    public boolean containsKey(K key) {
        return root.search(key).found;
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
        root.delete(key);
    }

    /**
     * Simple class to act as a breadcrumb for our
     * descent as we traverse the B-tree.
     * (Suggested for capturing the state of the iteration)
     */
    private class IteratorRecord {
        BNode node;
        // we have visited the children up through (including)
        // pos, so pos is the next key to return
        int pos;
        IteratorRecord(BNode node) {
            this.node = node;
            pos = 0;
        }
    }

    /**
     * Iterate over the keys in this map in order.
     * (This implies an in-order depth-first traversal 
     * of the B tree.)
     * @return An iterator for the keys in this map.
     */
    public Iterator<K> iterator() {

        // TODO
        
        // 25 LOC
        
        return null;
        
    }

}
