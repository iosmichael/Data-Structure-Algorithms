package exercises;

/**
 * Exercise1.java
 * 
 * Class to hold method performing binary search on an array of Comparables.
 * 
 * CSCI 345
 * Spring 2016
 */
public class Exercise1 {

    /**
     * Find the the location of the given item, if any, using a logarithmic
     * number of comparisons.
     * @param array The array in which to search, assumed to be sorted if not null
     * @param item The item for which to search
     * @return The location of the item, if it is there, or -1 if it does not exist
     */
    public static <T extends Comparable<T>> int binarySearch(T[] array, T item) {
    	//middle
    	if(array == null) return -1;
    	if(array.length==0) return -1; //empty array exception
    	return binarySearchHelper(0,array.length,array,item);
    }
    
    
    public static <T extends Comparable<T>> int binarySearchHelper(int i,int j,T[] array, T item) {
    	if(j - i == 1){
    		//base case
    		if(item.compareTo(array[i])==0) return i;
    		return -1;
    	}
    	if(j - i == 0){
    		return -1;
    	}
    	int middle = (i+j)/2;
    	int comp = item.compareTo(array[middle]);
    	if(comp > 0){
    		//search right
    		return binarySearchHelper(middle+1,j,array,item);
    	}else if(comp < 0){
    		//search left
    		return binarySearchHelper(i,middle,array,item);
    	}else{
    		return middle;
    	}
    }
}
