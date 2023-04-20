package org.example.jmm.visbility;

public class YieldMakesItVisible {

    private static  boolean loop = true;


    public static void main(String args[]) throws Exception{


        Thread loopThread = new Thread(
                () -> {
                    int count = 0 ;
                    System.out.println("Loop thread started");
                    while(loop) {

                        count = count +1 ;
                        Thread.yield();
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
