package org.example.threadsafe;

import java.util.concurrent.atomic.LongAdder;

public class LongAdderExample {

    static LongAdder adder  = new LongAdder();

    public static void main(String args[]) throws Exception{

        long start = System.currentTimeMillis();
        int len = 100;
        Thread[] threads = new Thread[len];
        for(int threadIndex = 0 ; threadIndex < len ; threadIndex++){

            Thread t = new Thread(
                    () -> {

                        for( int index = 0 ; index < 10000 ; index++){
                            adder.add(1);
                        }

                    }

            );
            t.start();
            threads[threadIndex] = t ;

        }

        for(int index = 0 ; index < len ; index++){
            threads[index].join();
        }

        System.out.println("sum is " + adder.sum() + " took " + (System.currentTimeMillis() - start) + " ms" );
    }

}
