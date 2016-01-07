package model;

import model.map.BKMap;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Math.*;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.DAYS;

public class BKHashMapFactoryTest {
    int CAPACITY = 2048; //mean % cpcty != 0 (!!!)
    int MEAN = 100000;
    int THREAD_POOL_SIZE = 5;
    int REMOVE_PUT_NUMBER = 500000;

    BKHashMapFactory hashMapFactory;

    @Before
    public void setUp() throws Exception {
        hashMapFactory = new BKHashMapFactory(CAPACITY);
    }

    @Test
    public void makeTest() throws Exception{
        performTest(hashMapFactory.javaConcurrent());

        performTest(hashMapFactory.javaSynchronized());

        performTest(hashMapFactory.fineGrained());

        performTest(hashMapFactory.synchronizeed());

        performTest(hashMapFactory.globalLock());

    }

    public long performTest(final BKMap<String, Integer> bkMap) throws InterruptedException {
        return performTest(bkMap, true);
    }

    public long performTest(final BKMap<String, Integer> bkMap, boolean innerMsg) throws InterruptedException {
        if (innerMsg) System.out.println("Test started for: " + bkMap.getClass());
        long averageTime = 0;
        int loops = 5;
        //int[] puts = new int[2*MEAN+1];


        for (int i = 0; i < loops; i++) {

            long startTime = System.nanoTime();
            ExecutorService executors = newFixedThreadPool(THREAD_POOL_SIZE);

            for (int j = 0; j < THREAD_POOL_SIZE; j++) { //we will run 500k remove/put for #times = #threads; just for good averaging;
                executors.execute(() -> {
                    for (int i1 = 0; i1 < REMOVE_PUT_NUMBER; i1++) {
                        Integer randomParam = (int) ceil(random() * 2 *MEAN); //time grows lineary with groth of MEAN

                        // Retrieve value. We are not using it anywhere
                        Integer paramValue = bkMap.remove(String.valueOf(randomParam));
                        //if (paramValue!=null) puts[randomParam]--;

                        // Put value
                        Integer put = bkMap.put(String.valueOf(randomParam), randomParam);
                        //if (put==null) puts[randomParam]++;
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
        //long count = Arrays.stream(puts).filter(i -> i == 0).count();
        //System.out.println("Count of numbers not rolled = " + count);
        System.out.println("For " + bkMap.getClass() + " the average time is " + averageTime / loops + " ms");
        System.out.println("Size is = " + bkMap.size() + "\n \n"); //the size expected must be \approx 2*MEAN
        return averageTime/loops;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //here goes the test to find out better value of executionTime;
    // averaging out effect of JVM opyimizations
    @Ignore
    @Test
    public void makeBigTest() throws Exception{
        performBestTimeTest(hashMapFactory.synchronizeed());

        performBestTimeTest(hashMapFactory.globalLock());

        performBestTimeTest(hashMapFactory.fineGrained());
    }

    public void performBestTimeTest(final BKMap<String, Integer> bkMap) throws InterruptedException {
        System.out.println("Test started for: " + bkMap.getClass());
        long bestTime = Long.MAX_VALUE;
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

    ///////////////////////////////////////////////////////////////////////////////
    //here goes utility code to test bucketLoadings for Capacity and Mean settings;
    // bucket-defining code is 1-by-1 as in our HashMap implementation
    @Ignore
    @Test
    public void loadingsTest() {
        int[] loa = bucketLoadings();

        int c = 0;
        for (int i : loa) if (i!=0) c++;
        System.out.println(c);
        System.out.println("yo");
    }

    public int[] bucketLoadings(){
        Integer numb;
        int[] loadings = new int[CAPACITY];
        for (int i = 0; i < MEAN; i++) {
            numb = i;
            int bucketNumber = getBucketNumber(numb);
            loadings[bucketNumber]++;
        }
        return loadings;
    }

    private int getBucketNumber(Object key) {
        return getBucketNumber(hash(key));
    }

    private int getBucketNumber(int hash) {
        return hash & (CAPACITY - 1);
    }

    static final int hash(Object key) {
        return (key == null) ? 0 : fromCode(key);
    }

    private static int fromCode(Object key) {
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }
}