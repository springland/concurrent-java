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




    public static void main(String args[]) throws  Exception{
        SimpleLock  lock = new SimpleLock();

        for(int index = 0 ; index < 5 ; index++ ) {
            Thread t = new Thread(
                    () -> {

                        try {

                            System.out.println(Thread.currentThread().getName() + " is acquiring lock");
                            lock.lock();
                            System.out.println(Thread.currentThread().getName() + " acquired lock successfully");
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {

                        } finally {


                            lock.unlock();
                            System.out.println(Thread.currentThread().getName() + " release lock successfully");
                        }
                    },

                    "Thread " + index

            );


            t.start();
        }
//        Thread.sleep(5000);
    //    t1.start();

        Thread.sleep(3000);
        lock.unlock();
        Thread.sleep(10000);



    }

}
