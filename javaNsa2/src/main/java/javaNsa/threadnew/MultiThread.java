/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa.threadnew;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class MultiThread {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> paragraphs = null; 
        try {
            String content = new String(Files.readAllBytes(Paths.get("D:/kuliah(Amri)/Sem 6/Algoritma dan Struktur Data/Periode 2/tugasDuaNsa/Sorcerers Stone.txt")))
                    .toLowerCase().replaceAll("[^a-zA-Z.\\s'-]", "").replaceAll("[.]", " .");
            paragraphs = new ArrayList<>(Arrays.asList(content.split("\n")));
        } catch (IOException ex) {
            Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        paragraphs.parallelStream().forEach(p -> {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(p.split("\\s")));
            list.parallelStream().forEach(l -> {
                if(!l.equals("")) {
                    if(l.contains("."))
                        token.incSentence();
                    else 
                        token.inc(l);
                }
            });
        });
        
        token.getWord();
        System.out.println("\nThere are "+token.getSentence()+" sentences in the document.");
        System.out.println("There are "+token.getSize()+" number of unique words in the document.");
        System.out.println("There are "+token.getSum()+" words in the document.");
        token.getMax();
    }
}
