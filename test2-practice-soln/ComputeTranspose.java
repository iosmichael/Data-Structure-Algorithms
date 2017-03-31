package q5graph;

/**
 * ComputeTranspose
 * 
 * Placeholder class for the static method computeTranspose() which
 * compute the transpose of a given directed graph.
 * 
 * CSCI 345
 * Test 2 Practice Problem 5.
 */
public class ComputeTranspose {

    /**
     * Compute the transpose graph of the given directed graph g.
     * That is, build a new graph with the same number of
     * vertices as g but only those edges that do not exist
     * in g.
     * @param g The directed graph to compute the transpose of
     * @return The transpose of the given graph
     */
    public static AdjListGraph computeTranspose(AdjListGraph g) {
        AdjListGraph.ALGBuilder builder = new AdjListGraph.ALGBuilder(g.numVertices());
        for (int v = 0; v < g.numVertices(); v++) 
            for (int u : g.adjacents(v))
                builder.connect(u, v);
        return builder.getGraph();
                
    }
}
