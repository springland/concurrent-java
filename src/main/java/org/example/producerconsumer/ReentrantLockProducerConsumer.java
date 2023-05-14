package org.example.producerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockProducerConsumer {


    public static class MyQueue<E> {

        private ReentrantLock lock ;

        private  Condition  spaceAvailable ;

        private   Condition  itemAvailable ;

        private  Object items[] ;

        private int headIndex ;
        private int tailIndex ;

        public MyQueue(int size){
            items = new Object[size+1];
            lock = new ReentrantLock();
            spaceAvailable = lock.newCondition() ;
            itemAvailable = lock.newCondition() ;


        }
        public void put(E it) throws InterruptedException{

            lock.lock();
            try {
                while((tailIndex+1)%items.length == headIndex){

                    spaceAvailable.await();
                }

                tailIndex = (tailIndex+1)%items.length;
                items[tailIndex] = it ;
                itemAvailable.signal();
            }
            finally{
                lock.unlock();
            }
        }

        public E take() throws  InterruptedException{


            lock.lock();
            try{


                while(headIndex == tailIndex){
                    itemAvailable.await();
                }
                headIndex = (headIndex+1)%items.length;
                E item = (E) items[headIndex];
                spaceAvailable.signal();
                return item ;

            }
            finally {
                lock.unlock();
            }
        }

    }
    public static class Producer implements  Runnable{

        private MyQueue<String>  queue ;

        private String name ;

        public Producer(String name , MyQueue<String> queue){
            this.name = name ;
            this.queue = queue ;

        }


        @Override
        public void run() {

            for(int index = 0 ; index < 50 ; index ++ ){
                try {
                    String item = " Item " + index + " from " + name ;
                    queue.put(item);
                    System.out.println( name + "  produces " + " item " + index );

                } catch (InterruptedException e) {
                }
            }
        }
    }


    public static class Consumer implements  Runnable {

        private String name ;
        private MyQueue<String> queue ;


        public Consumer(String name , MyQueue<String> queue){
            this.name = name ;
            this.queue = queue ;

        }
        @Override
        public void run() {
            for(int index = 0 ; index < 100 ; index ++){

                try {
                    String item = queue.take();
                    System.out.println( name + " get " + item );

                    Thread.sleep(1000L);
                } catch (InterruptedException e) {

                }
            }

        }
    }

    public static void main(String[] args){

        ExecutorService executorService = Executors.newCachedThreadPool();

        MyQueue<String>  queue = new MyQueue<>(10);

        for(int index = 0  ; index < 2 ; index ++){

            Producer producer = new Producer(
                    "Producer " + index ,
                    queue
            )    ;
            executorService.submit(producer);
        }

        for(int index = 0 ; index < 1 ; index++){
            Consumer consumer = new Consumer(
                    "Consumer " + index ,
                    queue
            );

            executorService.submit(consumer);
        }
    }
}
