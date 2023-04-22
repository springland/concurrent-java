package org.example.thread;

public class GracefulShutdown {

     static enum  Status {
         NORMAL ,
         RECOVER ,
         CANCEL
     }
    //
    // If there is no interruption count increases from 0 to 9
    // when an interruption happened rollback to 0 if count is less than 5
    // or complete the transaction to increase it to 9
    //

    public static final int THRESHOLD = 5;
    public static void main(String args[]) throws  Exception{





        Thread t1 = new Thread(
                () -> {

                    Status status = Status.NORMAL;

                    int count = 0;

                    while (count >= 0 && count < 10) {

                        System.out.println(" count = " + count );
                        // cannot use isInterupted , the interrupt flag is kept
                        // Then the below Thread.sleep does not sleep

                        if(Thread.currentThread().interrupted()){
                            switch (status)
                            {
                                case NORMAL  :
                                    if(count >= THRESHOLD){
                                        status = Status.RECOVER;
                                        System.out.println(" Cannot cancel , Roll forward");
                                    }
                                    else{
                                        status = Status.CANCEL;
                                        System.out.println(" Roll back");
                                    }
                                    break;
                                case RECOVER:
                                case CANCEL:
                                    break;
                            }

                        }

                        switch(status) {
                            case CANCEL:
                                count -- ;
                                break;
                            case RECOVER:
                            case NORMAL:
                                count ++ ;
                            break;
                        }


                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                    }
                }

        );

        t1.start();

        //noInterruption();
        //interruptAferThresholdToComplete(t1);
        interruptBeforeThresholdToCancel(t1);
        t1.join();

    }

    public static void noInterruption() {


    }

    public static void interruptAferThresholdToComplete(Thread t) throws Exception{

        Thread.sleep(5500);
        System.out.println("Interrupt");
        t.interrupt();

    }

    public static void interruptBeforeThresholdToCancel(Thread t) throws Exception {
        Thread.sleep(3000);
        System.out.println("Interrupt");
        t.interrupt();

    }
}
