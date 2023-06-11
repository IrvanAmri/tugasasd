package tokenizer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import lombok.Getter;

@Getter
public class TokenList {
    private Dictionary<String, Integer> tokenList;

    public TokenList() {
        this.tokenList = new Hashtable<>();
    }

    public void addToken(String token) {
        if (this.tokenList.get(token) == null) {
            this.tokenList.put(token, 1);
        } else {
            this.tokenList.put(token, this.tokenList.get(token) + 1);
        }
    }

    public void mergerToken(TokenList tokenList) {
        Enumeration<String> keysB = tokenList.getTokenList().keys();
        while(keysB.hasMoreElements()) {
            String key = keysB.nextElement();
            if(this.tokenList.get(key) == null){
                this.tokenList.put(key, tokenList.getTokenList().get(key));
            } else {
                this.tokenList.put(key, this.tokenList.get(key) + tokenList.getTokenList().get(key));
            }
        }
    }

    public void printToken() {
        Enumeration<String> keys = this.tokenList.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " : " + this.tokenList.get(key));
        }
    }

    public void writeToTxt(FileWriter fileWriter) throws IOException{
        BufferedWriter writer = new BufferedWriter(fileWriter);

        Enumeration<String> keys = this.tokenList.keys();
        long numbering = 1l;
        long totalWord = 0l;
        int currentMostFrequent = 0;
        String currentMostFrequentToken = "";
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            totalWord += this.tokenList.get(key);
            try {
                writer.write(numbering++ + ". "+ key + " : " + this.tokenList.get(key));
                writer.newLine();
            } catch (Exception e) {
                System.out.println(e);
            }

            // calculate most frequent token
            if(this.tokenList.get(key) > currentMostFrequent) {
                currentMostFrequent = this.tokenList.get(key);
                currentMostFrequentToken = key;
            }
        }
        writer.newLine();
        writer.write("total word: " + totalWord);
        writer.newLine();
        writer.write("total token: " + --numbering);
        writer.newLine();
        writer.write("Most frequent token: '" + currentMostFrequentToken + "' : " + currentMostFrequent);
        writer.close();
    }
}
