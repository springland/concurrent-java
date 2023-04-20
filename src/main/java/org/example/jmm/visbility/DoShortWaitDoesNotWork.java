package org.example.jmm.visbility;

public class DoShortWaitDoesNotWork {

    private static  boolean loop = true;


    public static void main(String args[]) throws Exception{


        Thread loopThread = new Thread(
                () -> {
                    int count = 0 ;
                    System.out.println("Loop thread started");
                    while(loop) {

                        count = count +1 ;

                        long start = System.nanoTime();
                        do{

                        }
                        while(System.nanoTime() - start < 2000000);

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
