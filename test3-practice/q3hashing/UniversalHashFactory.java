package q3hashing;

import java.util.Random;

/**
 * UniversalHashFactory
 * 
 * Class to contain static factory methods for hash functions from
 * the Hpm families, commonly called "universal hash functions."
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * Revised Apr 12, 2016
 */

public class UniversalHashFactory {

    /**
     * Random number generator
     */
    private static Random randy = new Random();
    
    /**
     * Make a universal hash function with the given p and m parameters
     * for objects, using the object's inherent hashCode() method.
     * @param p A prime number
     * @param m The exclusive upper bound on the range of hash values
     * @return A hash function
     */
    public static HashFunction<Object> makeHashFunction(final int p, final int m) {
        final int a = m <= 1 ? 0 : randy.nextInt(p-1) + 1;
        final int b = m <= 1 ? 0 : randy.nextInt(p);
        return new HashFunction<Object>() {
            public int hash(Object key) {
                return ((a * (key.hashCode() & 0x7fffffff) + b) % p) % m;
            }
        };
    }
    
    /**
     * Make a universal hash function with the given p and m parameters
     * for objects, using the object's inherent hashCode() method,
     * but mod the keys' inherent hash codes by a given factor f (keeps
     * them smaller, avoids overflow).
     * @param p A prime number
     * @param m The exclusive upper bound on the range of hash values
     * @param f An exclusive upper bound on the objects' original
     * hash code values.
     * @return A hash function
     */
    public static HashFunction<Object> makeHashFunction(final int p, final int m,
            final int f) {
        final int a = m <= 1 ? 0 : randy.nextInt(p-1) + 1;
        final int b = m <= 1 ? 0 : randy.nextInt(p);
        return new HashFunction<Object>() {
            public int hash(Object key) {
                return ((a * ((key.hashCode() & 0x7fffffff) % f) + b) % p) % m;
            }
        };
    }
}
