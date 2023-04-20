package org.example.jmm.visbility;

public class ShortWaitMakesItVisible {
    private static  boolean loop = true;


    public static void main(String args[]) throws Exception{


        Thread loopThread = new Thread(
                () -> {
                    int count = 0 ;
                    System.out.println("Loop thread started");
                    while(loop) {

                        count = count +1 ;
                        shortWait(1000000);
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

    public static void shortWait(long ns){
        long start = System.nanoTime();
        while(System.nanoTime() - start < ns){

        }

    }
}
