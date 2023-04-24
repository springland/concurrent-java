package org.example.threadsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ABAIssue {
    //
    // ABA issue happens a thread thought nothing was changed but the state has been updated
    //
    // Like the below example , thread2 has updated the value twice but thread1 is not aware of it

    //
    //

    public static void main(String args[]){

        AtomicInteger  atomic = new AtomicInteger(2);
        Thread t1 = new Thread(
                () -> {
                    int value = atomic.get();
                    System.out.println(" Thread 1 read value " + value);

                    LockSupport.parkNanos(1000000000L);
                    if(atomic.compareAndSet(value ,3)){
                        System.out.println( " Thread 1 updates value from " + value + " to 3");
                    }
                    else{
                        System.out.println("Thread 1 update fail");
                    }
                }
        );

        Thread t2 = new Thread(
                () -> {
                    int value = atomic.get();
                    int original = value ;
                    System.out.println(" Thread 2 get value " + value);
                    if(atomic.compareAndSet(value , 1)){
                        System.out.println(" Thread 2 change value from " + value + " to 1");
                    }
                    else{
                        System.out.println("Thread 2 change value fail");
                    }

                    value = atomic.get();
                    if(atomic.compareAndSet(value , original)){
                        System.out.println(" Thread 2 change value from " + value + " to " + original);
                    }
                    else{
                        System.out.println("Thread 2 change value fail");
                    }

                }
        );


        t1.start();
        t2.start();

    }
}
