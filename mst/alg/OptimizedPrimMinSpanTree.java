package alg;

import impl.BasicHashSet;
import impl.OptimizedHeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * OptimizedPrimMinSpanTree
 * 
 * Implementation of Prim's algorithm for computing
 * the minimum spanning tree of a graph, using a
 * more heavily optimized priority queue.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 25, 2015
 */
public class OptimizedPrimMinSpanTree implements MinSpanTree {


    public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
        HPAVertexRecord[] records = new HPAVertexRecord[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            records[i] = new HPAVertexRecord(i, Double.POSITIVE_INFINITY);
        PriorityQueue<HPAVertexRecord> pq = 
                new OptimizedHeapPriorityQueue<HPAVertexRecord>(records, new HPAVertexRecord.VRComparator());
        Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
        int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
            parents[i] = -1;
        
        // add code here in part 7
        
        return mstEdges;
    }
    
}
