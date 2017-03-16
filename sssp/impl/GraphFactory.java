package impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import adt.Graph;
import adt.WeightedGraph;
import adt.WeightedGraph.WeightedEdge;

/**
 * GraphFactory
 * 
 * File format (based on files provided by Sedgwick):
 * number of vertices
 * number of edges
 * each edge: vertex vertex (weight)
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 18, 2015
 */
public class GraphFactory {

    private static Random randy = new Random();
    
    public static WeightedGraph weightedUndirectedALGraphRandom(int vertices, int edges) {

        WeightedAdjListGraph.WALGBuilder builder = 
                new WeightedAdjListGraph.WALGBuilder(vertices);
        
        for (int i = 0; i < edges; i++) {
            int first = randy.nextInt(vertices);
            int second = first + ((int) (5 * randy.nextGaussian()));
            if (second >= 0 && second < vertices && first != second)
                builder.connectUndirected(first, second, randy.nextDouble());
            else i--;
        }
        return builder.getGraph();
    }
    
    
    public static WeightedGraph weightedUndirectedAMGraphRandom(int vertices, int edges) {
        WeightedAdjMatrixGraph.WAMGBuilder builder = 
                new WeightedAdjMatrixGraph.WAMGBuilder(vertices);
 
        for (int i = 0; i < edges; i++) {
            int first = randy.nextInt(vertices);
            int second = first + ((int) (5 * randy.nextGaussian()));
            if (second >= 0 && second < vertices && first != second)
                builder.connectUndirected(first, second, randy.nextDouble());
            else i--;
        }
        return builder.getGraph();
        
    }
    
    
    public static WeightedGraph weightedUndirectedAMGraphCopy(WeightedGraph g) {
        WeightedAdjMatrixGraph.WAMGBuilder builder = 
                new WeightedAdjMatrixGraph.WAMGBuilder(g.numVertices());
        
        for (WeightedEdge e : g.edges())
            builder.connectUndirected(e.first, e.second, e.weight); 
        return builder.getGraph();
        
    }

    public static WeightedGraph weightedDirectedALGraphFromFile(String filename) {
        try {
            Scanner file = new Scanner(new File(filename));
            WeightedAdjListGraph.WALGBuilder builder = 
                    new WeightedAdjListGraph.WALGBuilder(Integer.parseInt(file.nextLine()));
            int numEdges = Integer.parseInt(file.nextLine());
            for (int i = 0; i < numEdges; i++) {
                StringTokenizer tokey = new StringTokenizer(file.nextLine());
                builder.connect(Integer.parseInt(tokey.nextToken()), 
                        Integer.parseInt(tokey.nextToken()), 
                        Double.parseDouble(tokey.nextToken()));
            }
            file.close();
            return builder.getGraph(); 
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return null;
        }
    }

    
}
