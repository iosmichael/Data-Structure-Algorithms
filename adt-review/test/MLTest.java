package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import impl.MapList;
import impl.ArrayMap;

public class MLTest extends ListTest {

    protected void reset() {
        testList = new MapList<String>(new ArrayMap<Integer, String>());
    }
    
}
