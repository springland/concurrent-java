package org.example.completeblefuture;

import java.util.concurrent.CompletableFuture;

public class UpperCaseCharCount {


    public static void main(String args[]) throws  Exception{


        int count =  CompletableFuture.supplyAsync(
                    () -> {
                        System.out.println(" String generated");
                        return "AbcdEfg" ;
                    }

            ).thenApply(
                    t -> {
                        System.out.println(" Char counted");
                        int uppercaseCount = 0 ;
                        for( char ch : t.toCharArray()){
                            if(Character.isUpperCase(ch))
                            uppercaseCount ++;
                        }

                        return uppercaseCount ;
                    }
            ).get();


        System.out.println(count);
    }

}
