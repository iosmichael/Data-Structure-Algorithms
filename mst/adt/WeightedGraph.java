package adt;

/**
 * Graph
 * 
 * Interface to define the external operations specific
 * to a weighted graph.
 * 
 * Edges are represented as value-objects
 * Vertices are known by this interface only by number.
 * If the graph has V vertices, those vertices are labeled
 * 0 through V-1.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 16, 2015
 */
public interface WeightedGraph extends Graph {

    public static class WeightedEdge implements Comparable<WeightedEdge> {
        public final int first, second;
        public final double weight;
        public WeightedEdge(int first, int second, double weight) {
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
        public String toString() {
            return "(" + first + "," + second + ";" + weight + ")";
        }
        public int compareTo(WeightedEdge other) {
            return Double.compare(weight, other.weight);
        }
        public boolean equals(Object other) {
            if (! (other instanceof WeightedEdge)) return false;
            WeightedEdge o = (WeightedEdge) other;
            return (first == o.first && second == o.second) ||
                    (first == o.second && second == o.first);
        }
        @Override
        public int hashCode() {
            // can't do anything fancier, needs to be consistent with equals
            return first + second;
        }
    }

    /**
     * Iterate through the edges in this graph.
     */
    Iterable<WeightedEdge> edges();

    /**
     * Report the weight of the edge from u to v, if any. 
     * If v is not adjacent to u, this returns infinity.
    * For undirected graphs, this relationship is
     * symmetric and so this method returns the same
     * result whatever order the parameters are given.
     */
    double weight(int u, int v);

}
