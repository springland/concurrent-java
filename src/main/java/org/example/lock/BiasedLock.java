package org.example.lock;


import org.openjdk.jol.info.ClassLayout;

public class BiasedLock {

    /**
     *
     * Prior to JDK 9, biased locking is only enabled after 5 seconds
     * after the VM startup. Therefore, the test is best run with
     * -XX:BiasedLockingStartupDelay=0 on JDK 8 and lower. After JDK 15,
     * biased locking is disabled by default, and this tests needs
     * -XX:+UseBiasedLocking.
     * */
    public static void main(String args[]) throws Exception{


        Object lock = new Object();

        synchronized (lock){

            ClassLayout layout = ClassLayout.parseInstance(lock);
            System.out.println(layout.toPrintable());
            Thread.sleep(1000);
        }

    }
}
