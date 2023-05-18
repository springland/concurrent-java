package org.example.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReenterantLock {

    private class Sync extends AbstractQueuedSynchronizer {

        protected boolean tryAcquire(int arg){


            if(isHeldExclusively()){
                int state = this.getState();
                return compareAndSetState(state , state+arg);
            }
            else{
                if( compareAndSetState(0  , arg)) {
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true ;
                }{
                    return false ;
                }
            }

        }

        protected boolean tryRelease(int arg){

            if(!isHeldExclusively()){
                throw new IllegalMonitorStateException();
            }

            int state = this.getState();
            setState(state - arg);
            state = this.getState();
            if(state == 0){
                setExclusiveOwnerThread(null);
                return true ;
            }
            else {
                return false;
            }
        }

        protected boolean isHeldExclusively(){

            return this.getExclusiveOwnerThread() == Thread.currentThread();
        }
    }

    private Sync sync = new Sync();

    public void lock() {

        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);

    }

    public static void main(String args[]){

        ReenterantLock lock = new ReenterantLock();

        Thread t1 = new Thread(
                () -> {

                    lock.lock();

                    try{

                            Thread.sleep(2000L);
                    } catch (InterruptedException e) {

                    }
                    finally {
                        lock.unlock();
                    }
                }

        );
        t1.start();

        lock.lock();

        lock.lock();

        lock.unlock();;

        lock.unlock();




    }
}
