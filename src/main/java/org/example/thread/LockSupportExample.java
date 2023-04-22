package org.example.thread;

import java.util.concurrent.locks.LockSupport;

public class LockSupportExample {

    //
    // A few advantages compare to wait/notify
    //  1. unpark can specify Thread target to issue permit  , Object.notify does not support it
    //  2. It does not need synchronized to enclose it
    //  3. unpark can be issued in advance. notfiy/wait does not support it , we cannot call notify earlier and expect later wait is not blocked
    //
    public static void main(String args[]) throws Exception{

        Thread  t1 =  new Thread(
                () -> {
                    System.out.println("  Thread 1 started");

                    LockSupport.park();

                    System.out.println(" Thread 1 completed");
                }
        );
        t1.start();
        System.out.println(" Main thread sleep 2 seconds");
        Thread.sleep(2000);
        System.out.println(" Unparked");
        LockSupport.unpark(t1);


    }
}
