/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author user
 */
public class aThread extends Thread{
    public static volatile int count;
    private ArrayList<Token> tokens;
    private String arr;
    aThread(String arr, ArrayList<Token> tokens) {
        this.arr = arr;
    }
    
    @Override
    public void run() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(arr.split(" ")));
        list.parallelStream().forEach(e -> {
            if(e.contains(".")) {
                ++count;
            }

        });
        System.out.println(count);
    }

    //method untuk 
}
