package model;

import model.map.naive.BKMap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Math.*;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.DAYS;

public class BKHashMapFactoryTest {
    int THREAD_POOL_SIZE = 5;
    int MEAN = 100000;
    int REMOVE_PUT_NUMBER = 500000;

    BKHashMapFactory hashMapFactory;

    @Before
    public void setUp() throws Exception {
        //hashMapFactory = BKHashMapFactory.init();
        hashMapFactory = new BKHashMapFactory(2048);
    }


    //todo : NOTE !!! With ALL THREE naive implementations of Map  averageTime getting better as we go testing;
    @Test
    public void makeTest() throws Exception{
        //performTest(new BKSimpleMap<>(), true);

        performTest(hashMapFactory.synchronizeed(), true);

        performTest(hashMapFactory.globalLock(), true);

        //performTest(hashMapFactory.fineGrained(), true);
    }

    @Ignore
    @Test
    public void makeBigTest() throws Exception{
        performBestTimeTest(hashMapFactory.synchronizeed());

        performBestTimeTest(hashMapFactory.globalLock());

        performBestTimeTest(hashMapFactory.fineGrained());
    }


    public void performBestTimeTest(final BKMap<String, Integer> bkMap) throws InterruptedException {
        System.out.println("Test started for: " + bkMap.getClass());
        long bestTime = 99999999;
        long averageTime = 0;
        int loops = 10;
        int sucLoops = 0;
        for (int i = 0; i < loops; i++) {
                long thisTime = performTest(bkMap, false);
                bestTime = min(bestTime, thisTime);
                averageTime += thisTime;
                sucLoops++;
        }

        if (sucLoops>0) {
            System.out.println("\n TOTAL " + bkMap.getClass() + " the AVERAGE time is " + averageTime / sucLoops + " ms");
            System.out.println("\n TOTAL " + bkMap.getClass() + " the BEST time is " + bestTime + " ms\n");
        }

    }


    public long performTest(final BKMap<String, Integer> bkMap, boolean innerMsg) throws InterruptedException {
        if (innerMsg) System.out.println("Test started for: " + bkMap.getClass());
        long averageTime = 0;
        int loops = 5;
        for (int i = 0; i < loops; i++) {

            long startTime = System.nanoTime();
            ExecutorService executors = newFixedThreadPool(THREAD_POOL_SIZE);

            for (int j = 0; j < THREAD_POOL_SIZE; j++) { //we will run 500k remove/put for #times = #threads; just for good averaging;
                executors.execute(() -> {
                    for (int i1 = 0; i1 < REMOVE_PUT_NUMBER; i1++) {
                        Integer randomParam = (int) ceil(random() * 2 *MEAN); //time grows lineary with groth of MEAN

                        // Retrieve value. We are not using it anywhere
                        Integer paramValue = bkMap.remove(String.valueOf(randomParam));

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
            if (innerMsg) System.out.println((THREAD_POOL_SIZE * REMOVE_PUT_NUMBER / 1000) +  "K entries added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + bkMap.getClass() + " the average time is " + averageTime / loops + " ms\n");
        System.out.println("Size is = " + bkMap.size()); //the size expected must be \approx 2*MEAN
        return averageTime/loops;
    }
}