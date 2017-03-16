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
        
        // The initial places for each value (once we calculate them)
        int[] nextPlace = new int[maxVal+1];
        // C. Determine the initial next place for each value
        nextPlace[0] = 0;
        int offset = 0;
        for(int i = 0; i<counts.length; i++){
        	nextPlace[i] = offset; 
        	offset = offset + counts[i];
        }
        
        // The auxiliary array into which to sort the array
        T[] aux = (T[]) new Object[array.length];
        // D. Sort the items into aux
        // ...
        for(int i = 0; i<aux.length; i++){
        	int item = toInt.v(array[i]);
        	int in = nextPlace[item];
        	int out;
        	if (item == maxVal){
        		out = aux.length;
        	}else{
        		out = nextPlace[item+1];
        	}
        	for(int n = in; n<out; n++){
        		aux[n] = array[i];
        	}
        }
        // E. move them back to array
        for(int i = 0; i<array.length; i++){
        	array[i] = aux[i];
        }
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
                    // ...
                    return (item / rp)%r;
                }
            });
            rPow *= r;
        }
    }
    


}