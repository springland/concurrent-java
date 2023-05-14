package org.example.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveFabonacci  extends RecursiveTask<Integer> {

    private int value ;
    RecursiveFabonacci(int value){
        this.value = value ;
    }
    @Override
    protected Integer compute() {

        if(this.value <= 1){
            return this.value ;
        }


        RecursiveFabonacci  n1 = new RecursiveFabonacci(this.value -1);
        RecursiveFabonacci  n2 = new RecursiveFabonacci(this.value -2);

        n1.fork();
        n2.fork();

        return n1.join() + n2.join();

    }


    public static void main(String[] args){
        RecursiveFabonacci  fabonacci = new RecursiveFabonacci(10);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool = new ForkJoinPool(2);
        System.out.println(pool.invoke(fabonacci));

    }
}
