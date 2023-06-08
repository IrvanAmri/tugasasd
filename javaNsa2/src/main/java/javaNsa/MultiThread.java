/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author user
 */
public class MultiThread {
    // private static ArrayList<Token> tokens = new ArrayList<>();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Dictionary<String, Integer> tokens = new Hashtable<>();
        ArrayList<Integer> sentenceCount = new ArrayList<Integer>();
        ArrayList<String> paragraphs = new ArrayList<>();
        
        try {
            String content = new String(Files.readAllBytes(Paths.get("D:/kuliah(Amri)/Sem 6/Algoritma dan Struktur Data/Periode 2/tugasDuaNsa/test.txt")))
                    .toLowerCase()
                    .replaceAll("[^a-zA-Z0-9\\s.]", "")
                    .replaceAll("[.]", " .");
            paragraphs = new ArrayList<>(Arrays.asList(content.split("\n")));
        } catch (IOException ex) {
            Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE, "error bro", ex);
        }
        
        paragraphs.parallelStream().forEachOrdered(p -> {
            synchronized(sentenceCount) {
                ArrayList<String> sentences = new ArrayList<String>(Arrays.asList(p.split(".")));
                sentenceCount.add(sentences.size());
                sentences.parallelStream().forEach(s -> {
                    synchronized(tokens) {
                        ArrayList<String> words = new ArrayList<String>(Arrays.asList(p.split(" ")));
                        
                        words.parallelStream().forEach(w -> {
                            if (tokens.get(w) != null) {
                                tokens.put(w, tokens.get(w)+1);
                            } else {
                                tokens.put(w, 1);
                            }
                        });
                    }
                });
            }
        });

        int totalSentenceCount = sentenceCount.stream().reduce(0, (subtotal, element) -> subtotal + element);
        System.out.println("Sentence count : " + totalSentenceCount);
        // Arrays.asList(tokens).stream().forEach(t -> {
        //     System.out.println(t.keys() + " : " + t.elements());
        // });
        // for (Map.Entry<String, Integer> entry : token.entrySet()) {
        //     String key = entry.getKey();
        //     Object val = entry.getValue();
        // }
        System.out.println("word count");
        while(tokens.elements().hasMoreElements()){
            System.out.println(tokens.keys().nextElement() + " : " + tokens.elements().nextElement());
        }
        // for (int i = 0; i < paragraphs.size(); i++) {
        //     // parallelStream
            
        //     aThread t = new aThread(paragraphs.get(i));
        //     t.start();
        // }
    }

    // private static void tokenizer(ArrayList<String> paragraphs){
    // paragraphs.stream().forEach(word -> {
    // List<Token> tokenFound = tokens.stream().filter(token ->
    // token.getWord().equals(word)).collect(Collectors.toList());
    // if(tokenFound.isEmpty()){
    // Token token = new Token();
    // token.setWord(word);
    // token.setCount(1);
    // tokens.add(token);
    // }else{
    // tokenFound.get(0).setCount(tokenFound.get(0).getCount()+1);
    // }
    //     });

}
