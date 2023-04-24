package org.example.threadsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

// Use AtomicStampedReference
public class ABAAddressed {

    public static void main(String args[]){

        AtomicStampedReference<Integer>  atomicStampedReference = new AtomicStampedReference<>(2 , 1);

        Thread t1 = new Thread(
                () -> {
                    int value = atomicStampedReference.getReference();
                    int stamp = atomicStampedReference.getStamp();

                    System.out.println(" Thread 1 read value " + value);

                    LockSupport.parkNanos(1000000000L);
                    if(atomicStampedReference.compareAndSet(value , 3 , stamp , stamp+1)){
                        System.out.println( " Thread 1 updates value from " + value + " to 3");
                    }
                    else{
                        System.out.println("Thread 1 update fail");
                    }
                }
        );

        Thread t2 = new Thread(
                () -> {
                    int value = atomicStampedReference.getReference();
                    int stamp = atomicStampedReference.getStamp();
                    int original = value ;

                    System.out.println(" Thread 2 get value " + value);
                    if(atomicStampedReference.compareAndSet(value , 1 , stamp , stamp+1)){
                        System.out.println(" Thread 2 change value from " + value + " to 1");
                    }
                    else{
                        System.out.println("Thread 2 change value fail");
                    }

                    value = atomicStampedReference.getReference();
                    stamp = atomicStampedReference.getStamp();
                    if(atomicStampedReference.compareAndSet(value , 1 , stamp , stamp+1)){
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
