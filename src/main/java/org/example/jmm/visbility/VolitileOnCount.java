package org.example.jmm.visbility;

public class VolitileOnCount {

    private static  boolean loop = true;

    private static volatile  int count = 0;
    public static void main(String args[]) throws Exception{


        Thread loopThread = new Thread(
                () -> {
                    System.out.println("Loop thread started");
                    while(loop) {

                        count = count +1 ;
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
