package org.example.threadsafe;

import java.util.concurrent.atomic.AtomicLong;

// 6 ms
public class AtomicLongExample {

    public static AtomicLong sum = new AtomicLong() ;

    public static void main(String args[]) throws Exception{

        long start = System.currentTimeMillis();
        int len = 1000;
        Thread[] threads = new Thread[len];
        for(int threadIndex = 0 ; threadIndex < len ; threadIndex++){

            Thread t = new Thread(
                    () -> {

                        for( int index = 0 ; index < 10000 ; index++){
                            sum.getAndIncrement();
                        }

                    }

            );
            t.start();
            threads[threadIndex] = t ;

        }

        for(int index = 0 ; index < len; index++){
            threads[index].join();
        }

        System.out.println("sum is " + sum + " took " + (System.currentTimeMillis() - start) + " ms" );
    }

}
