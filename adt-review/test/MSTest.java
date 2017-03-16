package test;

import static org.junit.Assert.*;
import impl.ArrayMap;
import impl.MapSet;

import org.junit.Before;
import org.junit.Test;

public class MSTest extends SetTest {

    protected void reset() {
        testSet = new MapSet<String>(new ArrayMap<String, String>());
    }
    
}
