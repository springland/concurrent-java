package org.example.threadsafe;

import org.example.jmm.UnsafeFactory;
import sun.misc.Unsafe;

import java.util.concurrent.locks.LockSupport;

public class CASWithVolatile {

    public static volatile  int sum ;

    public static void main(String args[]) throws Exception{


        Unsafe unsafe = UnsafeFactory.getUnsafe();
        long offset = unsafe.staticFieldOffset(CASWithVolatile.class.getField("sum"));
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[10];

        for(int threadIndex = 0 ; threadIndex < 10 ; threadIndex++){

            Thread t = new Thread(
                    () -> {

                        for( int index = 0 ; index < 10000 ; index++){

                            while( unsafe.compareAndSwapInt(CASWithVolatile.class , offset , sum , sum+1 ) == false){
                                // Actually updating sum is done via unsafe
                                // it does not access sum variable directly
                                // then the volatile does not work
                                LockSupport.unpark(Thread.currentThread());
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
