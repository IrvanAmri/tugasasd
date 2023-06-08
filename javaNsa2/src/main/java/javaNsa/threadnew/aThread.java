/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa.threadnew;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class aThread extends Thread{
    private String arr;
    aThread(String arr) {
        this.arr = arr;
    }
    
    @Override
    public void run() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(arr.split("\\s")));
        list.parallelStream().forEach(e -> {
            if(e.contains("."))
                token.incSentence();
            else 
                token.inc(e);
        });
    }
}
