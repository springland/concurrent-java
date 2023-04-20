package org.example.jmm.visbility;

import java.util.concurrent.locks.LockSupport;

public class PermitMakesItVisible {
    private static  boolean loop = true;


    public static void main(String args[]) throws Exception{


        Thread loopThread = new Thread(
                () -> {
                    int count = 0 ;
                    System.out.println("Loop thread started");
                    while(loop) {

                        count = count +1 ;

                        // Should be a thread context switch
                        LockSupport.unpark(Thread.currentThread());;
                    }

                    System.out.println(" loop Thread completed");
                }
        );

        Thread updateThread = new Thread(
                () -> {
                    System.out.println(" reset loop to false");
                    loop = false ;
                }
        );

        loopThread.start();
        Thread.sleep(1000);

        updateThread.start();
    }
}
