package org.example.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NaiveForkJoinTaskFabonacci implements Callable<Integer> {

    private int value  ;

    private ExecutorService executorService;

    public NaiveForkJoinTaskFabonacci(int value , ExecutorService executorService){

        this.value = value ;
        this.executorService = executorService;
    }
    @Override
    public Integer call() throws Exception {

        if(this.value <= 1){
            return this.value ;
        }

        NaiveForkJoinTaskFabonacci  f1 = new NaiveForkJoinTaskFabonacci(this.value-1 , this.executorService);
        NaiveForkJoinTaskFabonacci  f2 = new NaiveForkJoinTaskFabonacci(this.value-2 , this.executorService);

        Future<Integer> n1 = this.executorService.submit(f1);
        Future<Integer> n2 = this.executorService.submit(f2);

        return n1.get() + n2.get() ;

    }

    public static void main(String[] args) throws Exception{

        //
        // When the fixed is used tasks are blocked after it run out of threads
        // cached can keep running
        //
        ExecutorService  executorService = Executors.newCachedThreadPool();
        // executorService = Executors.newFixedThreadPool(2);
        NaiveForkJoinTaskFabonacci  f = new NaiveForkJoinTaskFabonacci(10 , executorService);
        int result = executorService.submit(f).get();
        System.out.println(result);
        executorService.shutdown();
    }
}
