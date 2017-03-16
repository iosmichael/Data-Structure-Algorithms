package test;

import static org.junit.Assert.*;

import impl.ListBag;
import impl.MapList;
import impl.ArrayMap;

public class LBTest extends BagTest {

    protected void reset() {
        testBag = new ListBag<String>(new MapList<String>(new ArrayMap<Integer, String>()));
    }


}
