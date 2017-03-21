package sorts;

public class Sorts {

	
    /**
     * Interface for objects that determine a positive integer
     * for objects of a given type so that those objects
     * may be sorted by those integers.
     */
    public static interface ToInteger<T> {
        /**
         * What integer should we use for sorting purposes for
         * the given item?
         */
        int v(T item);
    }

    /**
     * Sort the given array using counting sort.
     * @param array The array (or anything) to sort, assumed non-null and
     * with no null elements.
     * @param toInt A means of determining a number (for sorting
     * purposes) for the items in the array
     */
    public static <T> void countingSort(T[] array, ToInteger<T> toInt) {
        
        // The maximum value in the array (once we calculate it)
        int maxVal = 0;
        // A. Find the maximum value in the array
        for(int i = 0; i<array.length;i++){
        	if(maxVal < toInt.v(array[i])){
        		maxVal = toInt.v(array[i]);
        	}
        }
        
        // The occurrences of each value (once we calculate them)
        int[] counts = new int[maxVal+1];
        // B. tabulate the occurrences of each value
        for(int i = 0; i<array.length; i++){
        	int n = toInt.v(array[i]);
        	//index is the value
        	//array value at certain index is the count
        	counts[n]++;
        }
        printArray(counts,"counts");
        
        // The initial places for each value (once we calculate them)
        int[] nextPlace = new int[maxVal+1];
        // C. Determine the initial next place for each value
        nextPlace[0] = counts[0];
        for(int i = 1; i<counts.length; i++){
        	nextPlace[i] = nextPlace[i-1] + counts[i];
        }
        printArray(nextPlace,"nextPlace");
        // The auxiliary array into which to sort the array
        T[] aux = (T[]) new Object[array.length];
        // D. Sort the items into aux
        //This is very important!!!!!! have to be backward sorting!!!
        for(int i = array.length - 1; i>=0; i--){
        	int item = toInt.v(array[i]);
        	int in = --nextPlace[item];
        	aux[in] = array[i];
        }

        // E. move them back to array
        String finOutput= "final: [";
        for(int i = 0; i<array.length; i++){
        	array[i] = aux[i];
        	finOutput += (array[i].toString()) + ", ";
        }
        System.out.println(finOutput + "]");
    }
    
    private static void printArray(int[] array, String title){
    	String output= title+": [";
    	for(int i = 0; i < array.length; i++){
    		output += array[i]+", ";
    	}
    	output += "]";
    	System.out.println(output);
    }

    /**
     * Sort the given array using radix sort with the given radix.
     * @param array The array to sort
     * @param r The radix to use (must be greater than 1)
     */
    public static void radixSort(Integer[] array, final int r) {
        assert r > 1;

        // The maximum number of digits for any number in array
        // (once we calculate it)
        int maxNumDigits = 0;  

        // calculate the number of digits
        for (int i = 0; i < array.length; i++){
            int numDigits = (int) Math.ceil(Math.log(array[i])/Math.log(r));
            if (numDigits > maxNumDigits)
                maxNumDigits = numDigits;
        }
        
        // sort by each digit
        int rPow = 1;
        for (int i = 0; i < maxNumDigits; i++) {
            // "Final" version of rPow that we can use inside an anonymous inner class
            final int rp = rPow;
            countingSort(array, new ToInteger<Integer>() {
                public int v(Integer item) {
                    return (int)(item / rp) % r;
                }
            });
            rPow *= r;
        }
    }
    


}