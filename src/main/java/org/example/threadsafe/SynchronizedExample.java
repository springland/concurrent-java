package org.example.threadsafe;

 // 22 ms
public class SynchronizedExample {


    public static volatile  int sum  ;

    public static void main(String args[]) throws Exception{


        Object lock = new Object() ;
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[10];
        for(int threadIndex = 0 ; threadIndex < 10 ; threadIndex++){

            Thread t = new Thread(
                    () -> {

                            for( int index = 0 ; index < 10000 ; index++){
                                synchronized (lock) {
                                    sum++;
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
