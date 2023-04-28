package org.example.lock;

public class SynchronizedIsReentant {

    public static void main(String args[]){

        Object lock = new Object();

        synchronized (lock){

            System.out.println("Enter the first level");

            synchronized (lock){
                System.out.println(" Enter the second level");
            }

        }


    }
}
