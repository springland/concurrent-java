package org.example.threadsafe;

import org.example.jmm.UnsafeFactory;
import sun.misc.Unsafe;



// 8 ms
// This is causing cpu wasting
// and ABA issue
public class CASExample {

    public static int sum ;

    public static void main(String args[]) throws Exception{


        Unsafe unsafe = UnsafeFactory.getUnsafe();
        long offset = unsafe.staticFieldOffset(CASExample.class.getField("sum"));
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[10];

        for(int threadIndex = 0 ; threadIndex < 10 ; threadIndex++){

            Thread t = new Thread(
                    () -> {
                        for( int index = 0 ; index < 10000 ; index++){

                                while( unsafe.compareAndSwapInt(CASExample.class , offset , sum , sum+1 ) == false){
                                    // very interesting
                                    // if this is commonted out it takes 22~24ms

                                    // I think I got it , System.out.println causes a memory fence
                                    // Then sum is upddated when System.out.println is called , finally compareAndSwapInt moves forward
                                    // we can use CAS with volatile to remove System.out
                                    System.out.println(" Update fail");
                                }
                        }

                    }

            );
            t.start();
            threads[threadIndex] = t ;

        }

        for(int index = 0 ; index < 10 ; index++){
            threads[index].join();
        }

        System.out.println("sum is " + sum + " took " + (System.currentTimeMillis() - start) + " ms" );
    }

}

