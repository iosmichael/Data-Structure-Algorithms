package alg;

import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * MinSpanTree
 * 
 * Interface to define the protocol for algorithms computing
 * the minimum spanning tree of a graph.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 23, 2015
 */
public interface MinSpanTree {

    /**
     * Compute the minimum spanning tree of a given graph.
     * @param g The given graph
     * @return A set of the edges in the minimum spanning tree
     */
    Set<WeightedEdge> minSpanTree(WeightedGraph g);
    
}
