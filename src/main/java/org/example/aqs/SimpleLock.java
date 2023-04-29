package org.example.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SimpleLock  implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer {

        public boolean tryAcquire(int acquires){

            assert acquires == 1;

            if(compareAndSetState(0  , 1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true ;
            }

            return false ;

        }

        public boolean tryRelease(int releases){

            assert releases == 1;
            if(!isHeldExclusively()){
                return false ;
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true ;
        }


        public boolean isHeldExclusively(){

            return getExclusiveOwnerThread() == Thread.currentThread() ;
        }


        public Condition  newCondition(){
            return new ConditionObject();
        }

    }

    Sync sync = new Sync();


    @Override
    public void lock() {

        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {

        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {

        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }





}
