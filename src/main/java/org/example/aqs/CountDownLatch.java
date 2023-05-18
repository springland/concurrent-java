package org.example.aqs;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class CountDownLatch {

    private class Sync extends AbstractQueuedSynchronizer {

        public Sync(int count){
            setState(count);
        }

        protected boolean isHeldExclusively(){
            return false ;
        }

        protected int tryAcquireShared(int arg) {

            if(getState() > 0) return -1 ;

            return 1 ;
        }

        protected boolean tryReleaseShared(int arg){
            int state = getState();
            return compareAndSetState(state , state - arg);
        }



    }

    private Sync sync ;
    public CountDownLatch(int count){

        sync = new Sync(count);
    }
    public void await() {

        sync.acquireShared(1);
    }

    public void countDown() {
        sync.releaseShared(1);
    }

    public static void main(String args[]) throws InterruptedException {

        CountDownLatch  latch = new CountDownLatch(3);
        for(int index = 0 ; index < 3 ; index++){
            Thread t = new Thread(
                    () -> {

                        String name = Thread.currentThread().getName() ;
                        System.out.println( name + " awaits");
                        latch.await();

                        System.out.println( name + " continue");
                    },
                    "Thread " + index
            );
            t.start();
        }

        Thread.sleep(1000L);
        latch.countDown();
        System.out.println("Count down");
        Thread.sleep(1000L);
        latch.countDown();
        System.out.println("Count down");
        Thread.sleep(1000L);
        latch.countDown();
        System.out.println("Count down");



    }

}
