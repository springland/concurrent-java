package org.example.threadsafe;

public class VolitileIsNotAtomic {
    public static volatile  int sum  ;

    public static void main(String args[]) throws Exception{

        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[10];
        for(int threadIndex = 0 ; threadIndex < 10 ; threadIndex++){

            Thread t = new Thread(
                    () -> {

                        for( int index = 0 ; index < 10000 ; index++){
                            // the ++ is not atomic operation
                            sum++;
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
