package alg;

import java.util.Comparator;


/**
 * VertexRecord
 * 
 * Simple class to represent the record of a vertex
 * for use in Prim's algorithm and Dijkstra's algorithm
 * in a priority queue.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * June 24, 2015
 */
class VertexRecord {

    public static class VRComparator implements Comparator<VertexRecord>{
    	public int compare(VertexRecord u, VertexRecord v) {
    		return Double.compare(v.distance, u.distance);
    	}
    }

    /**
     * Which vertex is this?
     */
    public final int id;

    /**
     * The (current) upper bound on distance.
     */
    private double distance;

    /**
     * Plain constructor
     */
    VertexRecord(int id, double distance) {
		this.id = id;
		this.distance = distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getDistance() { return distance; }
}