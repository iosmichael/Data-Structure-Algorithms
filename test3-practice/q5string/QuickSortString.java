package q5string; 

/**
 * QuickSortString
 * 
 * Placeholder for a method performing quicksort on strings.
 * 
 * CSCI 345
 * Test 3 Practice Problem 5.
 */
public class QuickSortString {

 // sort the array of strings
    public static void quicksortStrings(String[] array) {
        quicksortStringsR(array, 0, array.length, 0);
    }

    /**
     * Sort the given range of strings, assumed identical up to
     * character 0
     * @param array - The array to sort (a portion of)
     * @param start - the beginning (inclusive) of the range to sort
     * @param stop - the ending (exclusive) of the range to sort
     * @param c - the character position in the strings on which to sort
     * PRECONDITION: All strings in this range are identical up to character c
     */
    public static void quicksortStringsR(String[] array, int start, int stop, int c) {
    
    }
}
