package impl;

import java.util.Arrays;
import java.util.Collections;

import impl.OptimalBSTMap.Internal;
import impl.OptimalBSTMap.Node;

import static impl.OptimalBSTMap.dummy;

/**
 * OptimalBSTMapFactory
 * 
 * Build an optimal BST, given the keys, values, key probabilities
 * and miss probabilities.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * Feb 25, 2015
 */

public class OptimalBSTMapFactory {

    /**
     * Exception to throw if the input to building an optimal BST
     * is not right: either the number of keys, values, key probs,
     * and miss probs aren't consistent, or the total probability
     * is not 1.
     */
    public static class BadOptimalBSTInputException extends RuntimeException {
        private static final long serialVersionUID = -444687298513060315L;

        private BadOptimalBSTInputException(String msg) {
            super(msg);
        }
    }
    
    /**
     * Build an optimal BST from given raw data, passed as a single object.
     * A convenient overloading of the other buildOptimalBST().
     * @param rawData The collection of data for building this BST
     * @return A BST with the given keys and values, optimal with the
     * given probabilities.
     */
    public static OptimalBSTMap buildOptimalBST(OptimalBSTData rawData) {
        return buildOptimalBST(rawData.keys, rawData.values, rawData.keyProbs, rawData.missProbs);
    }
    
    /**
     * Build an optimal BST from given raw data, passed as individual arrays.
     * @param rawData The collection of data for building this BST
     * @return A BST with the given keys and values, optimal with the
     * given probabilities.
     */
    public static OptimalBSTMap buildOptimalBST(String[] keys, String[] values, double[] keyProbs,
            double[] missProbs) {
        
        // keep these checks
        checkLengths(keys, values, keyProbs, missProbs);
        checkProbs(keyProbs, missProbs);        
        
        // The number of keys (so we don't need to say keys.length every time)
        int n = keys.length;
        double [][] T = new double[n][n];
        double [][] C = new double[n][n];
        Node [][] R = new Node[n][n];
        for(int i = 0; i<n; i++){
        	// initialize cells(0,0) through (n-1, n-1)
        	T[i][i] = keyProbs[i]+ missProbs[i]+missProbs[i+1];
        	C[i][i] = keyProbs[i]+ 2*missProbs[i]+2*missProbs[i+1];
        	R[i][i] = new OptimalBSTMap.Internal(dummy,keys[i],values[i],dummy);
        }
        
        for(int internal = 1; internal < n; internal++){
        	for(int i = 0; i + internal<n;i++){ // 0 <= i < n-1
        		int j = i + internal; // 0 < j < n
        		for(int r = i; r <= j; r++){
        			double tVal;
        			double cVal;
        			OptimalBSTMap.Internal node;
        			if(r == i){
            			//ki
                		tVal = missProbs[i] + keyProbs[i] + T[i+1][j];
                		cVal = missProbs[i] + tVal + C[i+1][j];
                		node = new OptimalBSTMap.Internal(dummy, keys[i], values[i], R[i+1][j]);
            		}else if(r == j){
            			//kj
            			tVal = missProbs[j+1] + keyProbs[j] + T[i][j-1];
            			cVal = missProbs[j+1] + tVal + C[i][j-1];
            			node = new OptimalBSTMap.Internal(R[i][j-1], keys[j], values[j], dummy);
            		}else{
            			//kr
            			tVal = T[i][r-1] + keyProbs[r] + T[r+1][j];
            			cVal = C[i][r-1] + tVal + C[r+1][j];
            			node = new OptimalBSTMap.Internal(R[i][r-1], keys[r], values[r], R[r+1][j]);
            		}
        			
        			if(cVal < C[i][j] || C[i][j] == 0){  // minimize cost
        				T[i][j] = tVal;
        				C[i][j] = cVal;
        				R[i][j] = node;
        			}
        		}
        	}
        }
        
        // ----- tree-building algorithm goes here -----
        
        return new OptimalBSTMap(R[0][n-1]);
    }

    /**
     * Check that the given probabilities sum to 1, throw an
     * exception if not.
     * @param keyProbs 
     * @param missProbs
     */
    public static void checkProbs(double[] keyProbs, double[] missProbs) {
        double[] allProbs = new double[keyProbs.length + missProbs.length];
        int i = 0;
        for (double keyProb : keyProbs)
            allProbs[i++] = keyProb;
        for (double missProb : missProbs)
            allProbs[i++] = missProb;
        // When summing doubles, sum from smallest to greatest
        // to reduce round-off error.
        Arrays.sort(allProbs);
        double totalProb = 0;
        for (double prob : allProbs)
            totalProb += prob;
        // Don't compare doubles for equality directly. Check that their
        // difference is less than some epsilon.
        if (Math.abs(1.0 - totalProb) > .0001)
            throw new BadOptimalBSTInputException("Probabilities total to " + totalProb);
    }

    /**
     * Check that the arrays have appropriate lengths (keys, values, and
     * keyProbs all the same, missProbs one extra), throw an exception
     * if not.
     * @param keys
     * @param values
     * @param keyProbs
     * @param missProbs
     */
    public static void checkLengths(String[] keys, String[] values,
            double[] keyProbs, double[] missProbs) {
        int n = keys.length;
        if (values.length != n || keyProbs.length != n || missProbs.length != n+1)
            throw new BadOptimalBSTInputException(n + "keys, " + values.length + " values, " +
                    keyProbs.length + " key probs, and " + missProbs.length + " miss probs");
    }
    
}
