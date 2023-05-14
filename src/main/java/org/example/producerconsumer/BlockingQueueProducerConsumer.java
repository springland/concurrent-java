package org.example.producerconsumer;

import java.util.concurrent.*;

public class BlockingQueueProducerConsumer {

    static class Producer implements Runnable {

        String name ;
        BlockingQueue<String> queue ;
        public Producer(String name , BlockingQueue<String> queue){
            this.name = name ;
            this.queue = queue ;
        }
        @Override
        public void run() {

            for(int index = 0 ; index < 50 ; index++){

                String item =  " item " + index + " from " + name ;
                try {
                    this.queue.put(item);
                    System.out.println(name + " produces item " + index);
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {

                }

            }
        }
    }

    static class Consumer implements  Runnable {

        String name ;
        BlockingQueue<String> queue ;
        public Consumer(String name , BlockingQueue<String> queue)
        {
            this.name = name ;
            this.queue = queue ;

        }
        @Override
        public void run() {

            for(int index = 0 ; index < 100 ; index++){
                try {
                    String item =this.queue.take();
                    System.out.println(name + " consumes " + item);

                    Thread.sleep(1000L);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public static void main(String args[]){

        int consumerNum = 1;

        int producerNum = 2;



        ExecutorService executorService = Executors.newCachedThreadPool();
        BlockingQueue<String>  blockingQueue = new ArrayBlockingQueue<String>(10);

        for(int index = 0 ; index < consumerNum ; index++){
            executorService.submit(new Consumer(" Consumer " + index , blockingQueue));
        }

        for(int index = 0 ; index < producerNum ; index++){
            executorService.submit(new Producer(" Producer " +index , blockingQueue));
        }


    }
}
