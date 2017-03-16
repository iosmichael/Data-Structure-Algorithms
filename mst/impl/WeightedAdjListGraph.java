package impl;

import java.util.Iterator;
import adt.Set;
import adt.WeightedGraph;

public class WeightedAdjListGraph implements WeightedGraph {

    
    /**
     * For each vertex u, the list of vertices v for
     * which there exists and edge from u to v.
     */
    private Set<WeightedEdge>[] adjSets;

    private Set<WeightedEdge> allEdges;
    
    private int numEdges;
    
    /**
     * Plain constructor.
     */
    @SuppressWarnings("unchecked")
    private WeightedAdjListGraph(int numVertices) {
        adjSets = (Set<WeightedEdge>[]) new Set[numVertices];
        for (int i = 0; i < numVertices; i++)
        	adjSets[i] = new ListSet<WeightedEdge>();
        allEdges = new ListSet<WeightedEdge>();
    }

    /**
     * Builder for WeightedAdjListGraphs. This allows
     * a graph to be made by adding edges, but enforces
     * a graph to be immutable once construction is
     * finished. If, after a call to getGraph(), either
     * connect() is called or getGraph() is called again,
     * then a NullPointerException will be thrown (not an 
     * ideal exception for this, but unfortunately Java
     * doesn't seem to have a standard exception to use for
     * an expired operation).
     */
    public static class WALGBuilder {
        private WeightedAdjListGraph graph;
        public WALGBuilder(int numVertices) {
            graph = new WeightedAdjListGraph(numVertices);
        }
        public void connect(int u, int v, double weight) {
        	WeightedEdge e = new WeightedEdge(u, v, weight);
        	graph.adjSets[u].add(e);
        	graph.allEdges.add(e);
        	graph.numEdges++;
        }
        public void connectUndirected(int u, int v, double weight) {
        	WeightedEdge e = new WeightedEdge(u, v, weight);
        	graph.adjSets[u].add(e);
        	graph.adjSets[v].add(e);
        	graph.allEdges.add(e);
        	graph.numEdges++;
        }
        public WeightedAdjListGraph getGraph() {
            WeightedAdjListGraph toReturn = graph;
            graph = null;
            return toReturn;
        }
    }
   
    
    /**
     * The number of vertices in the graph.
     */
    public int numVertices() {
        return adjSets.length;
    }
   
   /**
    * Iterate through the vertices adjacent to the given
    * vertex. Note that in a directed graph, these are
    * the vertices such that an edge exists from the
    * given one to them. 
    */
   public Iterable<Integer> adjacents(final int v) {
       return new Iterable<Integer>() {
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                Iterator<WeightedEdge> internal = adjSets[v].iterator();
                public boolean hasNext() {
                    return internal.hasNext();
                }
                public Integer next() {
                	WeightedEdge e = internal.next();
                	if (e.first == v)
                		return e.second;
                	else 
                		return e.first;
                }
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
       };
   }

   /**
    * Determine adjacency between two given vertices.
    * For undirected graphs, this relationship is
    * symmetric and so this method returns the same
    * result whatever order the parameters are given.
    * For directed graphs, this method indicates whether
    * or not there exists an edge from u to v.
    */
    public boolean adjacent(int u, int v) {
        boolean foundIt = false;
        for (Iterator<WeightedEdge> it = adjSets[u].iterator(); 
                !foundIt && it.hasNext(); )
            foundIt |= it.next().second == v;
        return foundIt;
    }

    /**
     * Iterate through the edges in this graph.
     */
   public Iterable<WeightedEdge> edges() {
	   /*
       int j = 0;
       while (j < adjSets.length && adjSets[j].isEmpty()) j++;
       final int finalJ = j;
       
       return new Iterable<WeightedEdge>() {
           public Iterator<WeightedEdge> iterator() {
               return new Iterator<WeightedEdge>() {
                   int i = finalJ;
                   Iterator<WeightedEdge> internal = adjSets[i].iterator();
                   public boolean hasNext() {
                       return i < adjSets.length;
                   }
                   public WeightedEdge next() {
                       WeightedEdge toReturn = internal.next();
                       if (! internal.hasNext()) {
                           do i++; while (i < adjSets.length && adjSets[i].isEmpty());
                           if (i < adjSets.length)
                        	   internal = adjSets[i].iterator();
                       }
                       return toReturn;
                   }
                   public void remove() {
                       throw new UnsupportedOperationException();
                   }
               };
           }
          };
          */
	   return allEdges;
   }

    /**
     * Report the weight of the edge from u to v, if any. 
     * If v is not adjacent to u, this returns infinity.
    * For undirected graphs, this relationship is
     * symmetric and so this method returns the same
     * result whatever order the parameters are given.
     */
   public double weight(int u, int v) {
       double foundWeight = Double.POSITIVE_INFINITY;
       for (Iterator<WeightedEdge> it = adjSets[u].iterator();
               foundWeight == Double.POSITIVE_INFINITY && it.hasNext(); ) {
           WeightedEdge current = it.next();
           if (current.first == v || current.second == v) 
               foundWeight = current.weight;
       }
       return foundWeight;
   }

    public int numEdges() {
        return numEdges;
    }

    
}
