package org.example.lock;

import org.openjdk.jol.info.ClassLayout;

public class ThinLock {

    public static void main(String args[]){


        Object lock = new Object();
        ClassLayout layout = ClassLayout.parseInstance(lock);
        System.out.println("Before Sync");
        System.out.println(layout.toPrintable());


        synchronized (lock){
            System.out.println("After Sync");
            System.out.println(layout.toPrintable());
        }

        synchronized (lock){
            System.out.println("After Sync 2");
            System.out.println(layout.toPrintable());
        }

    }
}
