package model.map.hash;

import model.map.BKMap;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BKHashMapTest {

    @Ignore
    @Test
    public void test() {
        BKMap<String, Integer> map = new BKHashMap<>();
        Integer put = map.put("1", 1);
        assertEquals(null, put);

        Integer put1 = map.put("1", 5);
        assertEquals(1, put1.intValue());

        Integer remove = map.remove("1");
        assertEquals(5, remove.intValue());

        Integer remove1 = map.remove("1");
        assertEquals(null, remove1);
    }

    @Test
    public void test2() {
        BKMap<String, Integer> map = new BKHashMap<>();
        Integer put = map.put("Aa", 1);
        assertEquals(null, put);

        Integer put1 = map.put("BB", 5);
        assertEquals(null, put1);

        Integer remove = map.remove("Aa");
        assertEquals(1, remove.intValue());

        Integer remove1 = map.remove("Aa");
        assertEquals(null, remove1);
    }

}