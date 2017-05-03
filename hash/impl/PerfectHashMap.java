package impl;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import adt.Map;
import adt.Set;
import impl.ListSet;

/**
 * PerfectHashMap
 * 
 * Implementation of perfect hashing, that is, when the keys are known
 * ahead of time. Note that containsKey and get will work as expected
 * if used with a key that doesn't exist. However, we assume put
 * will never be called using a key that isn't supplied to the
 * constructor; behavior is unspecified otherwise.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * March 17, 2015
 * @param <K> The key-type of the map
 * @param <V>The value-type of the map
 */

public class PerfectHashMap<K, V> implements Map<K, V> {

 
    /**
     * Secondary maps for the buckets
     */
    private class SecondaryMap implements Map<K, V> {

        /**
         * The keys in this secondary map. This is necessary to
         * check when get and containsKey are called on spurious keys
         * and also for the iterator.
         */
        K[] keys;  

        /**
         * The values in the secondary map.
         */
        V[] values;

        /**
         * The number of slots in the arrays, computed as the square
         * of the number of keys that can go here.
         */
        int m;

        /**
         * The hash function, drawn from class Hpm
         */
        HashFunction<Object> h;
        
        /**
         * Constructor. Given a set of keys, make appropriately
         * size arrays and a hash set that makes no collisions.
         * @param givenKeys
         */
        SecondaryMap(Set<K> givenKeys) {
            // TODO
        	m = givenKeys.size() * givenKeys.size();
        	keys = (K[]) new Object[m];
        	values = (V[]) new Object[m];
        	
        	UniversalHashFactory hFactory = new UniversalHashFactory();
//        	int p = new PrimeSource().nextOrEqPrime(findMax(givenKeys));
        	int p = new PrimeSource().nextOrEqPrime(100*100);
        	boolean hasCollision = true;
        	while(hasCollision){
        		hasCollision = false;
        		boolean[] collision = new boolean[m];
        		h = hFactory.makeHashFunction(p, m, 100);
        		Iterator<K> it = givenKeys.iterator();
        		while(it.hasNext()){
        			K key = it.next();
        			if(collision[(h.hash(key)& 0x7fffffff) % m]){
        				hasCollision = true;
        				break;
        			}else{
        				collision[(h.hash(key)& 0x7fffffff) % m] = true;
        			}
        		}
        	}
        }
        
        private int findMax(Set<K> givenKeys){
        	int max = Integer.MIN_VALUE;
        	for(K key: givenKeys){
        		if((key.hashCode() & 0x7fffffff) % 100 > max){
        			max = (key.hashCode() & 0x7fffffff) % 100; 
        		}
        	}
        	return max;
        }
        
        /**
         * Add an association to the map. We assume the given
         * key was known ahead of time.
         * @param key The key to this association
         * @param val The value to which this key is associated
         */
             
        public void put(K key, V val) {
            int pos = h.hash(key);
            keys[pos] = key;
            values[pos] = val;
        }

        /**
         * Get the value for a key.
         * @param key The key whose value we're retrieving.
         * @return The value associated with this key, null if none exists
         */
       public V get(K key) {
           // special case that will apply only on spurious keys
           if (! containsKey(key)) return null;
           return values[h.hash(key)];
       }

       /**
        * Test if this map contains an association for this key.
        * @param key The key to test.
        * @return true if there is an association for this key, false otherwise
        */
       public boolean containsKey(K key) {
           // special case that will apply only on spurious keys
           if (m == 0) return false;
           int pos = h.hash(key);
           return keys[pos] != null 
                   // next part necessary only if we assume
                   // keys that can't be put may be tested
                   && keys[pos].equals(key);
       }

       /**
        * Remove the association for this key, if it exists.
        * @param key The key to remove
        */
       public void remove(K key) {
           // special case that will apply only on spurious keys
           if (containsKey(key)) 
               //keys[hash(key,a,b,p,m)] = null;
               keys[h.hash(key)] = null;
       }

       /**
        * The iterator for this portion of the map.
        */
        public Iterator<K> iterator() {
            
            // TODO

            // In theory you don't need to write this; all you need
            // to support is PerfectHashMap.iterator().
            // However, in my version the iterator for PerfectHashMap
            // relied on iterators of the secondary maps.
            // You could use a different approach.
        	return new Iterator<K>(){
            	int pos = 0;
    			public boolean hasNext() {
    				// TODO 
    				if(pos >= keys.length) return false;
    				while(keys[pos] == null) {
    					pos++;
    					if(pos >= keys.length){
    						return false;
    					}
    				}
    				return true;
    			}

    			public K next() {
    				// TODO 
    				return keys[pos++];
    			}
            	
            };
        }
        
    }

    /**
     * Secondary maps
     */
    private SecondaryMap[] secondaries;

    /**
     * A prime number greater than the greatest hash value
     */
    private int p;

    /**
     * A parameter to the hash function; here, the number of keys
     * known ahead of time.
     */
    private int m;

    /**
     * The hash function
     */
    private HashFunction<Object> h;
    
    /**
     * Constructor. Takes the keys (all known ahead of time) to
     * set things up to guarantee no collisions.
     * @param keys
     */
    @SuppressWarnings("unchecked")
    public PerfectHashMap(K[] keys) {
        // TODO
    	int m = keys.length;
    	int p = new PrimeSource().nextOrEqPrime(findMax(keys));
    	UniversalHashFactory hFactory = new UniversalHashFactory();
    	h = hFactory.makeHashFunction(p, m, 100);
    	secondaries = new PerfectHashMap.SecondaryMap[m];
    	Set<K>[] list = new Set[m];
    	for(int i = 0; i<list.length; i++){
    		list[i] = new ListSet<K>();
    	}
    	for(int i = 0; i< keys.length; i++){
    		list[h.hash(keys[i]) % m].add(keys[i]);
    	}
    	for(int i = 0; i<list.length; i++){
    		secondaries[i] = new SecondaryMap(list[i]);
    	}
    }
    
    private int findMax(K[] keys){
    	int max = Integer.MIN_VALUE;
    	for(int i = 0; i < keys.length; i++){
    		if((keys[i].hashCode() & 0x7fffffff) % 100 > max){
    			max = (keys[i].hashCode() & 0x7fffffff) % 100; 
    		}
    	}
    	return max;
    }
    
    /**
     * Add an association to the map. We assume the given
     * key was known ahead of time.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
        secondaries[h.hash(key)].put(key, val);
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
   public V get(K key) {
       return secondaries[h.hash(key)].get(key);
   }

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public boolean containsKey(K key) {
        return secondaries[h.hash(key)].containsKey(key);
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
        secondaries[h.hash(key)].remove(key);
    }
    
    /**
     * Return an iterator over this map
     */
    public Iterator<K> iterator() {
    	
    	if(secondaries.length == 0){
    		return new Iterator<K>(){
    			public boolean hasNext() {
    				return false;
    			}
    			public K next() {
    				return null;
    			}
            };
    	}
    	
        return new Iterator<K>(){
        	int index = 0;
        	Iterator<K> it = secondaries[0].iterator();
			public boolean hasNext() {
				if(index >= secondaries.length){
					System.out.println("index: "+index);
					return false;
				}
				while(!it.hasNext()){
					index++;
					if(index >= secondaries.length) return false;
					it = secondaries[index].iterator();
				}
				return true;
			}

			@Override
			public K next() {
				return it.next();
			}
        	
        };
    }
    
}
