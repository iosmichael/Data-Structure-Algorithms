package alg;

import impl.BasicHashSet;
import impl.GraphFactory;
import impl.HeapPriorityQueue;
import impl.OptimizedQuickUnionDisjointSet;
import adt.DisjointSet;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

import java.util.Comparator;

/**
 * KruskalMinSpanTree
 * 
 * Implementation of Kruskal's algorithm for computing
 * the minimum spanning tree of a graph.
 * 
 * @author Thomas VanDrunen, Michael Liu
 * CSCI 345, Wheaton College
 * June 23, 2015
 */
public class KruskalMinSpanTree implements MinSpanTree {

    /**
     * Compute the minimum spanning tree of a given graph.
     * @param g The given graph
     * @return A set of the edges in the minimum spanning tree
     */
    public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
        Set<WeightedEdge> treeEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
        PriorityQueue<WeightedEdge> allEdges = 
                new HeapPriorityQueue<WeightedEdge>(g.edges(), g.numEdges(), new Comparator<WeightedEdge>() {
                    public int compare(WeightedEdge o1, WeightedEdge o2) {
                        return o2.compareTo(o1);
                    }
                });
        DisjointSet vertexConnections = new OptimizedQuickUnionDisjointSet(g.numVertices());
       // Initialize A to 0
        // make a disjoint-set data structure with each vertex its own set sort the edges by weight
        //for each edge (u,v)
        //  if findSet(u) ≠ findSet(v)
        //    add(u,v) to A
        //    union(u,v)
        while(!allEdges.isEmpty()){
        	WeightedEdge edge = allEdges.extractMax();
        	if(vertexConnections.find(edge.first) != vertexConnections.find(edge.second)){
        		treeEdges.add(edge);
        		vertexConnections.union(edge.first, edge.second);
        	}
        }
        return treeEdges;
    }

    public static void main(String[] args) {

        	WeightedGraph g = GraphFactory.weightedUndirectedALGraphFromFile("tinyEWG.txt");
            double totalWeight = 0.0;
            KruskalMinSpanTree kmst = new KruskalMinSpanTree();
            for (WeightedEdge e : kmst.minSpanTree(g)) {
                totalWeight += e.weight;
                System.out.println(e);
            }
            System.out.println(totalWeight);

    }    
    
}
