package impl;

import impl.AbstractAVLBSTMap.AVLNode;
import impl.AbstractAVLBSTMap.AbstractAVLRealNode;

/**
 * AVLBSTMap
 * 
 * A BST map using the AVL approach for maintaining a balanced
 * tree. This inherits most of the code for manipulating the
 * BST from AbstractRecursiveBSTMap and inherits the code for
 * verifying the AVL property from AbstractAVLBSTMap. 
 * This class's purpose is to house the code for fixing up an
 * AVL tree when out of balance. See documentation for 
 * AbstractAVLBSTMap.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * July 1, 2015
 * @param <K> The key type
 * @param <V> The value type
 * @param <N> A super type of nodes in whatever child class is refining this class
 */
public class AVLBSTMap<K extends Comparable<K>, V> extends
        AbstractAVLBSTMap<K, V> {

    
    /**
     * Class for real, "non-null" nodes, containing the code for
     * enforcing the AVL property.
     */
   private class AVLRealNode extends AbstractAVLRealNode {

       /**
        * Plain constructor.
        */
        public AVLRealNode(K key, V val,
                AVLNode<K,V> left,
                AVLNode<K,V> right) {
            super(key, val, left, right);
        }

        /**
         * Fix this subtree to conform to the constraints of
         * AVL trees. 
         * PRECONDITION: The subtrees rooted at the left and
         * right each satisfy the AVL constraints.
         * POSTCONDITION: This tree has been modified to
         * contain the same information but also to satisfy the
         * AVL constraints. The node on which this method is called,
         * currently the root of this subtree, might no longer be
         * the root; the root of the modified tree is returned.
         * @return The root of the tree like this one but
         * satisfying the constraints.
         */
       protected AVLNode<K,V> fixup() {
            AbstractAVLRealNode replace = this;
            this.softRecompute();
            this.left.softRecompute();
            this.right.softRecompute();
            //Four Cases:
            //Right Right, Right Left, Left Left, Left Right
            int leftBalance = this.left.balance();
            int rightBalance = this.right.balance();
            //Since balance = left - right
            if(this.balance > 1){
            	//left heavy
            	if(leftBalance > 0){
            		//left left
            		//perform single right rotation
            		replace = RR(replace);
            	}else{
            		//left right
            		//perform double right rotation
            		replace = LR(replace);
            	}
            }else if(this.balance < -1){
            	//right heavy
            	if(rightBalance < 0){
            		//right right
            		//perform single left rotation
            		replace = LL(replace);
            	}else{
            		//right left
            		//perform double left rotation
            		replace = RL(replace);
            	}
            }            
            replace.softRecompute();
            return replace;
        }
        
       /**
        * Left Rotation
        * a                b
        *  \              / \
        *   b       =>   a   c
        *    \          
        *     c
        *   c             a
        *    \       =>  /   
        *     a         c
        * @return newChild
        */
       private AbstractAVLRealNode LL(AbstractAVLRealNode a){
    	   assert(a.right != null);
    	   AbstractAVLRealNode b = (AbstractAVLRealNode) a.right;
    	   a.right = b.left;
    	   b.left = a;
    	   a.softRecompute();
    	   b.softRecompute();
    	   return b;
       }
       
       /**
        * Right Rotation
        *     c            b
        *    /            / \
        *   b       =>   a   c
        *  /          
        * a
        * 
        *   c             a
        *  /        =>     \ 
        * a                 c
        * @return newChild
        */
       private AbstractAVLRealNode RR(AbstractAVLRealNode c){
    	   assert(c.left != null);
    	   AbstractAVLRealNode b = (AbstractAVLRealNode) c.left;
    	   c.left = b.right;
    	   b.right = c;
    	   c.softRecompute();
    	   b.softRecompute();
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
       private AbstractAVLRealNode RL(AbstractAVLRealNode a){
    	   assert(a.right != null);
    	   AbstractAVLRealNode c = (AbstractAVLRealNode) a.right;
    	   //single left rotation
    	   AbstractAVLRealNode b = RR(c);
    	   a.right = b.left;
    	   b.left = a;
    	   a.softRecompute();
    	   b.softRecompute();
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
       private AbstractAVLRealNode LR(AbstractAVLRealNode c){
    	   assert(c.left != null);
    	   AbstractAVLRealNode a = (AbstractAVLRealNode) c.left;
    	   //Single right rotation
    	   AbstractAVLRealNode b = LL(a);
    	   c.left = b.right;
    	   b.right = c;
    	   c.softRecompute();
    	   b.softRecompute();
    	   return b;
       }
        
    }
    
   /**
    * Factory method for making new real nodes, used by the
    * code in the parent class which does not have direct access
    * to the class AVLRealNode defined here.
    */
    protected AVLNode<K,V> realNodeFactory(K key, V val,
            AVLNode<K, V> left,
            AVLNode<K, V> right) {
        return new AVLRealNode(key, val, left, right);
    }

    
}
