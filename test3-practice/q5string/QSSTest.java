package q5string;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

public class QSSTest {

    
    private boolean isSorted(String[] array) {
        boolean sorted = true;
        for (int i = 0; sorted && i < array.length - 1; i++)
            sorted &= array[i].compareTo(array[i+1]) <= 0;
        return sorted;
    }
    
    private boolean testArray(String[] original) {
        HashSet<String> contents = new HashSet<String>();
        for (String s : original) 
            contents.add(s);
        QuickSortString.quicksortStrings(original);
        boolean ok = isSorted(original);
        for (int i = 0; ok && i < original.length; i++)
            ok &= contents.remove(original[i]);
        return ok;
        
    }
    
    @Test
    public void testTLWs() {
        assertTrue(testArray(new String[]{"VRY", "MPZ", "WYR", "BXY", "CBD", "PLH", "WOA", 
                "AUS", "PHU", "RFI", "QNZ", "MCO", "HTS", "IZQ", "MAJ"}));
        
    }

    @Test
    public void testNames() {
        assertTrue(testArray(new String[]{"CONSTANCE", "HELEN", "JUSTIN", "JON", "JOHN", "CONSTANTIUS",
                "HELENA", "HELLA", "JONATHAN", "CONSTANTINE", "JUSTINIAN",
                "CLEMENS", "HEROD", "ANN", "JOHANA", "JUSTINIANUS",
                "CONSTANTINUS", "ELLEN", "ELAINE", "ELLIE", "ELLA",
                "HERODIAS", "HERODIAN", "JOHNA", "ANNALISE", "ANNETTE",
                "ANNIKA", "CLEMENT", "CAESAR", "AUGUSTUS", "ANNE", "ANNMARIE",
                "JUSTINMARTYR", "JOHNBAPTIST", "ANNIE", "ANNA", "CAESARION",
                "CAESAREA", "AUGUSTA", "ANNMARGARET", "AUGUST", "AUGUSTINE",
                "CLEMENZO", "JONATHANIAN", "CAESARINA", "AUGUSTINUS", "CONSTANS",
                "HELENE" }));
    }
    
}
