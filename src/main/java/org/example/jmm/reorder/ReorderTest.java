package org.example.jmm.reorder;

public class ReorderTest {

    static int x , y , a , b ;

    public static void main(String[] args) throws  Exception{

        int index = 0 ;
        for(index = 0;true; index++){

            a = 0 ; b = 0 ; x = 0 ; y = 0;

            Thread t1 = new Thread(
                    () -> {

                        x = 1 ;
                        a = y ;
                    }
            );

            Thread t2 = new Thread(
                    () -> {
                        y = 1 ;
                        b = x;
                    }
            );

            t1.start();
            shortWait(1000);
            t2.start();

            t1.join();
            t2.join();
            System.out.println( " index = " + index + " a = " + a + " b = "+ b);

            if(a == 0 && b == 0){
                break;
            }
        }
    }

    public static void shortWait(long ns){
        long start = System.nanoTime();
        while(System.nanoTime() - start < ns){

        }

    }
}
