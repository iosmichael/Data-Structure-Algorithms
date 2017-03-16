package alg;

import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * SSSP
 * 
 * Interface to define the protocol for algorithms
 * computing the single source shortest path of
 * a graph and source. 
 * 
 * Pardon the inconsistency in naming this with 
 * initials but "MinSpanTree" with abbrevs. I tried
 * things like "SingSrcShrtstPath" but everything 
 * was too long and looked funny.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 26, 2015
 */

public interface SSSP {

    /**
     * Compute the shortest paths in a given tree from
     * a given source to all over vertices.
     * @param g The given graph
     * @param source The vertex from which to compute paths
     * @return A set of edges constituting the tree of shortest
     * paths.
     */
    Set<WeightedEdge> sssp(WeightedGraph g, int source);
}
