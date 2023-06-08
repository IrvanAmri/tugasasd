/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa.threadnew;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class token {
    private static volatile ArrayList<String> tokens = new ArrayList<>();
    private static volatile ArrayList<Integer> count = new ArrayList<>();
    private static volatile int sentenceCount;
    
    static synchronized void inc(String str) {
        if(tokens.isEmpty()) {
            tokens.add(str);
            count.add(1);
        }
        else if(tokens.contains(str)) {
            int index = tokens.indexOf(str);
            count.set(index, count.get(index)+1);
        } else {
            tokens.add(str);
            count.add(1);
        }
    }
    
    static void getWord() {
        tokens.parallelStream().sorted().forEachOrdered(e -> {
            System.out.println(e+": "+count.get(tokens.indexOf(e)));
        });
    }
    
    static synchronized void incSentence() {
        sentenceCount++;
    }
    
    static int getSentence() {
        return sentenceCount;
    }
    
    static int getSize() {
        return tokens.size();
    }
    
    static int getSum() {
        return count.parallelStream().mapToInt(Integer::intValue).sum();
    }
    
    static void getMax() {
        int max = count.stream().max(Integer::compare).get();
        System.out.println("The most frequently used word from the document is \""+
                tokens.get(count.indexOf(max))+"\" that's used "+
                max+" number of times.");
    }
}
