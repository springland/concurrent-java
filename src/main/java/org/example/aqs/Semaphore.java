package org.example.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class Semaphore {

    private class Sync extends AbstractQueuedSynchronizer {

        public Sync(int permits){
            setState(permits);
        }
        protected boolean isHeldExclusively() {
           return false;
        }
        protected int tryAcquireShared(int arg){

            int state = getState();
            if(state >= arg){
                if(compareAndSetState(state , state - arg)){

                    return state - arg ;
                }
            }

            return -1 ;

        }

        protected boolean tryReleaseShared(int arg){

            int state = getState();
            return compareAndSetState(state , state + arg);
        }

    }


    private Sync sync ;


    public Semaphore(int permits){
        sync = new Sync(permits);
    }


    public void acquire() {

        sync.acquireShared(1);
    }

    public void release() {
        sync.releaseShared(1);
    }

    public static void main(String args[])throws Exception{

        Semaphore  semaphore = new Semaphore(2);
        Thread t1 = new Thread(
                () -> {

                    semaphore.acquire();
                    System.out.println("t1 semaphore acquired");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    semaphore.release();
                    System.out.println("t1 semaphore released");
                }
        ) ;

        Thread t2 = new Thread(
                () -> {
                    semaphore.acquire();
                    System.out.println("t2 semaphore acquired");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    semaphore.release();
                    System.out.println("t2 semaphore released");

                }
        ) ;

        t1.start();
        t2.start();


        Thread.sleep(1000);

        semaphore.acquire();
        System.out.println(" Main thread semaphore acquired");

        semaphore.release();

        System.out.println(" Main thread semaphore released");
    }
}
