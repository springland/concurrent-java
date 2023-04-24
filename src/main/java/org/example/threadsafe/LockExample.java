package org.example.threadsafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 15 ms
public class LockExample {

    // 15 ms
    public static volatile  int sum  ;

    public static void main(String args[]) throws Exception{

        ReentrantLock  lock = new ReentrantLock();
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[10];
        for(int threadIndex = 0 ; threadIndex < 10 ; threadIndex++){

            Thread t = new Thread(
                    () -> {

                        for( int index = 0 ; index < 10000 ; index++){
                            try{
                                lock.lock();
                                sum++;
                            }
                            finally{
                                lock.unlock();
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
