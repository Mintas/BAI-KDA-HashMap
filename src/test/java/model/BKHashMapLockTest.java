package model;

import org.junit.Before;
import org.junit.Test;

public class BKHashMapLockTest {
    int CAPACITY = 2048; //mean % cpcty != 0 (!!!);  2048 works fine; 4096 better; 8192; 16384 works cool; 32768
    int MEAN = 100000;
    int THREAD_POOL_SIZE = 2;
    int REMOVE_PUT_NUMBER = 500000;

    BKHashMapFactory hashMapFactory;
    BKHashMapFactoryTest test;

    @Before
    public void setUp() throws Exception {
        test = new BKHashMapFactoryTest();
    }

    @Test
    public void perfomanceTest() throws Exception{
        //test.makeTest(); //2 threads first - heating jit;
        System.out.println("\n\n\n\n\n\n \t REAL 2 THREADS");
        test.makeTest();

        test.THREAD_POOL_SIZE = 3;
        System.out.println("\n\n\n\n\n\n \t REAL 3 THREADS");
        test.makeTest();

        test.THREAD_POOL_SIZE = 4;
        System.out.println("\n\n\n\n\n\n \t REAL 4 THREADS");
        test.makeTest();

        test.THREAD_POOL_SIZE = 5;
        System.out.println("\n\n\n\n\n\n \t REAL 5 THREADS");
        test.makeTest();
    }
}