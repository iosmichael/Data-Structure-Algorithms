package impl;


import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import adt.List;
import adt.WeightedGraph;

public class WeightedAdjMatrixGraph implements WeightedGraph {

    
    /**
     * Representation of the edges such that edges[i][j] 
     * iff there is an edge from i to j.
     */
    private double[][] edges;

    /**
     * The number of edges
     */
    private int numEdges;
    
    /**
     * Hack.... needed by the edges iterator.
     */
    private boolean isDirected;
    
    /**
     * Plain constructor, called only by nested class
     * GraphBuilder.
     */
    private WeightedAdjMatrixGraph(int numVertices) {
        edges = new double[numVertices][numVertices];
        numEdges = 0;
    }

    
    /**
     * Builder for AdjMatrixGraphs. This allows
     * a graph to be made by adding edges, but enforces
     * a graph to be immutable once construction is
     * finished. If, after a call to getGraph(), either
     * connect() is called or getGraph() is called again,
     * then a NullPointerException will be thrown (not an 
     * ideal exception for this, but unfortunately Java
     * doesn't seem to have a standard exception to use for
     * an expired operation).
     */
    public static class WAMGBuilder {
        private WeightedAdjMatrixGraph graph;
        public WAMGBuilder(int numVertices) {
            graph = new WeightedAdjMatrixGraph(numVertices);
            for (int i = 0; i < numVertices; i++)
                for (int j = 0; j < numVertices; j++)
                    graph.edges[i][j] = Double.POSITIVE_INFINITY;
        }
        public void connect(int u, int v, double weight) {
            graph.edges[u][v] = weight;
            graph.numEdges++;
            graph.isDirected = true;
        }
        public void connectUndirected(int u, int v, double weight) {
            graph.edges[u][v] = weight;
            graph.edges[v][u] = weight;
            graph.numEdges++;
            graph.isDirected = false;
        }
        public WeightedAdjMatrixGraph getGraph() {
            WeightedAdjMatrixGraph toReturn = graph;
            graph = null;
            return toReturn;
        }
    }

    
    /**
     * The number of vertices in the graph.
     */
    public int numVertices() {
        return edges.length;
    }


    /**
     * Iterate through the vertices adjacent to the given
     * vertex. Note that in a directed graph, these are
     * the vertices such that an edge exists from the
     * given one to them. 
     */
   public Iterable<Integer> adjacents(final int v) {
       int j = 0;
       while (j < edges.length && edges[v][j] != Double.POSITIVE_INFINITY) j++;
       final int finalJ = j;
       return new Iterable<Integer>() {
        public Iterator<Integer> iterator() {
            return new Iterator<Integer>() {
                int i = finalJ;
                public boolean hasNext() {
                    return i < edges.length;
                }
                public Integer next() {
                    if (! hasNext())
                        throw new NoSuchElementException();
                    int toReturn = i;
                    do i++; 
                    while (i < edges.length && edges[v][i] != Double.POSITIVE_INFINITY);
                    return toReturn;
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
       return edges[u][v] != Double.POSITIVE_INFINITY;
   }

   public Iterable<WeightedEdge> edges() {

       class EdgeIt implements Iterator<WeightedEdge> {

           int i, j;

           EdgeIt() {
               i = 0;
               j = 0;
               while ((i < edges.length && j < edges.length) && 
                       edges[i][j] == Double.POSITIVE_INFINITY)
                   advance();
           }
           
           private void advance() {
               if (j >= edges.length) {
                   if (i < edges.length) {
                       i++;
                       if (isDirected) j = 0;
                       else j = i+1;
                   }
               }
               else 
                   j++;
           }

           public boolean hasNext() {
            return j < edges.length;
           }

           public WeightedEdge next() {
               if (! hasNext()) throw new NoSuchElementException();
               WeightedEdge toReturn = new WeightedEdge(i, j, edges[i][j]);
               do advance(); while ((i < edges.length && j < edges.length) && 
                       edges[i][j] == Double.POSITIVE_INFINITY);
               return toReturn;
           }

           public void remove() {
               throw new UnsupportedOperationException();
           }
           
       };
       
       return new Iterable<WeightedEdge>() {
        public Iterator<WeightedEdge> iterator() {
            return new EdgeIt();
        }
       };
   }


   /**
    * Report the weight of the edge from u to v, if any. 
    * If v is not adjacent to u, this returns infinity.
    * For undirected graphs, this relationship is
    * symmetric and so this method returns the same
    * result whatever order the parameters are given.
    */
   public double weight(int u, int v) {
       return edges[u][v];
   }

   public int numEdges() {
       return numEdges;
   }

    
    
}
