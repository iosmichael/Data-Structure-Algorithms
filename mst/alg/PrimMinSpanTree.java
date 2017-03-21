package alg;

import impl.BasicHashSet;
import impl.HeapPriorityQueue;
import adt.PriorityQueue;
import adt.Set;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * PrimMinSpanTree
 * 
 * Implementation of Prim's algorithm for computing
 * the minimum spanning tree of a graph.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 24, 2015
 */
public class PrimMinSpanTree implements MinSpanTree {

    /**
     * Compute the minimum spanning tree of a given graph.
     * @param g The given graph
     * @return A set of the edges in the minimum spanning tree
     */
	public Set<WeightedEdge> minSpanTree(WeightedGraph g) {
		VertexRecord[] records = new VertexRecord[g.numVertices()];
		for (int i = 0; i < g.numVertices(); i++)
			records[i] = new VertexRecord(i, Double.POSITIVE_INFINITY);
		PriorityQueue<VertexRecord> pq = 
				new HeapPriorityQueue<VertexRecord>(records, new VertexRecord.VRComparator());
        Set<WeightedEdge> mstEdges = new BasicHashSet<WeightedEdge>(g.numVertices());
		int[] parents = new int[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++)
        	parents[i] = -1;
		
        while(!pq.isEmpty()){
        	VertexRecord u = pq.extractMax();
        	if(parents[u.id] != -1){
        		//add (u.p, u) to A
        		WeightedEdge item = new WeightedEdge(parents[u.id], u.id, u.getDistance());
        		mstEdges.add(item);
        	}
        	//for each v in u.adj
        	for(int v : g.adjacents(u.id)){
				VertexRecord vRecord = records[v];
				VertexRecord uRecord = records[u.id];
				
				double w = g.weight(u.id, v);
        		if(pq.contains(vRecord) && w < vRecord.getDistance()){
        			parents[v] = u.id;
        			vRecord.setDistance(w);
        			pq.increaseKey(vRecord);
        		}
        	}
        }
        
        return mstEdges;
	}

}
