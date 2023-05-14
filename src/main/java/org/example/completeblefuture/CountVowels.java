package org.example.completeblefuture;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CountVowels {

    public static void main(String args[]) throws  Exception{


        int vowels =  countVoewls("abcdefg");

        System.out.println(vowels);

        vowels =  countVoewls("abc");
        System.out.println(vowels);
    }

    public static int countVowels(char[] chs){
        Set<Character> vowels = new HashSet(List.of('a' , 'e' , 'i' , 'o' , 'u')) ;

        int count = 0 ;
        for( char ch : chs) {
            if (vowels.contains(ch)) {
                count++;
            }
        }
        return count ;

    }
    public static int countVoewls(String str) throws  Exception{

        int vowelsCount = 0;



        vowelsCount = CompletableFuture.supplyAsync(
                () -> str
        ).thenApply(
                s-> s.toLowerCase()
        ).thenApply(
                s -> s.toCharArray()
        ).thenApply(
                chs -> countVowels(chs)
        ).thenApply(
                c ->{
                  if( c%2 != 0){
                      throw new IllegalArgumentException("does not supprt odd");
                  }
                  return c ;
                }
        ).exceptionally(
                e -> {
                    System.out.println( "Get ex" + e.getMessage());
                    return -1 ;
                }
        ).get();



        return vowelsCount ;
    }
}
