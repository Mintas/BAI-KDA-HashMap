package model;

import model.map.naive.BKMap;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Math.ceil;
import static java.lang.Math.random;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.DAYS;
import static model.BKHashMapFactory.*;

public class BKHashMapFactoryTest {
    int THREAD_POOL_SIZE = 5;

    BKHashMapFactory hashMapFactory;

    @Before
    public void setUp() throws Exception {
        hashMapFactory = init();
    }


    //todo : NOTE !!! With ALL THREE naive implementations of Map  averageTime getting better as we go testing;
    @Test
    public void makeTest() throws Exception{
        performTest(hashMapFactory.synchronizeed());

        performTest(hashMapFactory.globalLock());

        performTest(hashMapFactory.fineGrained());
    }



    public void performTest(final BKMap<String, Integer> bkMap) throws InterruptedException {
        System.out.println("Test started for: " + bkMap.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();
            ExecutorService executors = newFixedThreadPool(THREAD_POOL_SIZE);

            for (int j = 0; j < THREAD_POOL_SIZE; j++) {
                executors.execute(() -> {
                    for (int i1 = 0; i1 < 500000; i1++) {
                        Integer randomParam = (int) ceil(random() * 550000);

                        // Retrieve value. We are not using it anywhere
                        Integer paramValue = bkMap.get(String.valueOf(randomParam));

                        // Put value
                        bkMap.put(String.valueOf(randomParam), randomParam);
                    }
                });
            }

            // Make sure executor stops
            executors.shutdown();

            // Blocks until all tasks have completed execution after a shutdown request
            executors.awaitTermination(MAX_VALUE, DAYS);

            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("500K entries added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + bkMap.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }
}