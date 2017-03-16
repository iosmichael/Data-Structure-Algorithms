package alg;

import impl.BasicHashSet;
import impl.OptimizedHeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * OptimizedDijkstraSSSP
 * 
 * An implementation of Dijkstra's algorithm for
 * computing the single-source shortest paths of a graph
 * given a source.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 29, 2015
 */
public class OptimizedDijkstraSSSP implements SSSP {

    
    /**
     * Compute the shortest paths in a given tree from
     * a given source to all over vertices.
     * @param g The given graph
     * @param source The vertex from which to compute paths
     * @return A set of edges constituting the tree of shortest
     * paths.
     */
    public Set<WeightedEdge> sssp(WeightedGraph g, int source) {
        HPAVertexRecord[] distanceBounds = new HPAVertexRecord[g.numVertices()];
        int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++) {
            distanceBounds[i] = new HPAVertexRecord(i, Double.POSITIVE_INFINITY);
            parents[i] = -1;
        }
        distanceBounds[source].setDistance(0);
        PriorityQueue<HPAVertexRecord> pq = 
                new OptimizedHeapPriorityQueue<HPAVertexRecord>(distanceBounds, new HPAVertexRecord.VRComparator());
        
        // add code here in part 5
        
        Set<WeightedEdge> treeEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
        
        for (int v = 0; v < g.numVertices(); v++) {
            int u = parents[v];
            if (u != -1)
                treeEdges.add(new WeightedEdge(u, v, g.weight(u, v)));
        }

        return treeEdges;       
    }

}
