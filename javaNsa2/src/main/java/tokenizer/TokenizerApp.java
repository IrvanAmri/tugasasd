package tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TokenizerApp {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        int numberOfPartition = 4;

        /*--- DOCUMENT PRE-PROCESSING ---*/
        System.out.println("Pre-processing document...");
        long preProcessingTime = System.currentTimeMillis();
        ArrayList<ArrayList<String>> documentPartition = documentPartitionGenerator(numberOfPartition);
        File document = new File("D:/kuliah(Amri)/Sem 6/Algoritma dan Struktur Data/Periode 2/tugasDuaNsa/test.ft.txt");
        FileInputStream documentStream = new FileInputStream(document);
        BufferedReader documentReader = new BufferedReader(new InputStreamReader(documentStream));
        long lineCounter = 0l;
        String stringLine;
        while((stringLine = documentReader.readLine()) != null) {
            if(stringLine.isEmpty()) {
                continue;
            }
            stringLine = stringLine.toLowerCase().replaceAll("[^a-zA-Z -]", " ");
            ArrayList<String> stringLineArray = new ArrayList<String>(java.util.Arrays.asList(stringLine.split(" ")));
            for (String str : stringLineArray) {
                if(str == " " || str.isEmpty() || str == null) {
                    continue;
                }
                documentPartition.get((int) (lineCounter % numberOfPartition)).add(str);
            }
            lineCounter++;
        }
        documentReader.close();
        preProcessingTime = System.currentTimeMillis() - preProcessingTime;
        System.out.println("Pre-processing document done in "+preProcessingTime+" ms.");

        /*--- PARALLEL TOKENIZATION ---*/
        System.out.println("Tokenizing document...");
        long tokenizationTime = System.currentTimeMillis();
        ArrayList<TokenList> tokenListCollection = tokenListCollectionGenerator(numberOfPartition);
        ArrayList<Thread> threadCollection = new ArrayList<Thread>();
        for(int i = 0; i < numberOfPartition; i++){
            Thread thread = parallelTokenization(documentPartition.get(i), tokenListCollection.get(i));
            threadCollection.add(thread);
            thread.setName("Thread "+i);
            thread.start();
            System.out.println("Thread "+i+" started.");
        }
        for (Thread thread : threadCollection) {
            thread.join();
        }

        // merger BELUM DAPAT secara otomatis menyesuaikan dengan jumlah partisi
        Thread merger1 = mergerTokenList(tokenListCollection.get(0), tokenListCollection.get(1));
        Thread merger2 = mergerTokenList(tokenListCollection.get(2), tokenListCollection.get(3));
        merger1.setName("Thread merge 0,1");
        merger2.setName("Thread merge 2,3");
        merger1.start();
        System.out.println("Thread "+merger1.getName()+" started.");
        merger2.start();
        System.out.println("Thread "+merger2.getName()+" started.");
        merger1.join();
        merger2.join();

        Thread mergerFinal = mergerTokenList(tokenListCollection.get(0), tokenListCollection.get(2));
        mergerFinal.setName("Thread merge 01,23");
        mergerFinal.start();
        System.out.println("Thread "+mergerFinal.getName()+" started.");
        mergerFinal.join();
        TokenList result = tokenListCollection.get(0);
        tokenizationTime = System.currentTimeMillis() - tokenizationTime;
        System.out.println("Tokenizing document done in "+tokenizationTime+" ms.");

        /*--- WRITE TO FILE ---*/
        FileWriter fileWriter = new FileWriter("D:/kuliah(Amri)/Sem 6/Algoritma dan Struktur Data/Periode 2/tugasDuaNsa/tokenizerResult.txt");
        System.out.println("Writing to file...");
        long writeToFileTime = System.currentTimeMillis();
        result.writeToTxt(fileWriter);
        writeToFileTime = System.currentTimeMillis() - writeToFileTime;
        System.out.println("Writing to file done in "+writeToFileTime+" ms.");
        System.out.println("Total time taken : "+(preProcessingTime+tokenizationTime+writeToFileTime)+" ms.");
    }

    private static ArrayList<ArrayList<String>> documentPartitionGenerator(int numberOfPartition){
        ArrayList<ArrayList<String>> documentPartition = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < numberOfPartition; i++){
            documentPartition.add(new ArrayList<String>());
        }
        return documentPartition;
    }

    private static ArrayList<TokenList> tokenListCollectionGenerator(int numberOfPartition){
        ArrayList<TokenList> tokenListCollection = new ArrayList<TokenList>();
        for(int i = 0; i < numberOfPartition; i++){
            tokenListCollection.add(new TokenList());
        }
        return tokenListCollection;
    }

    private static Thread parallelTokenization(ArrayList<String> document, TokenList tokenList) {
        Runnable runnable = () -> {
            for (String string : document) {
                tokenList.addToken(string);
            }
            System.out.println("Thread "+Thread.currentThread().getName()+" finished.");
        };
        return new Thread(runnable);
    }

    private static Thread mergerTokenList(TokenList tokenListA, TokenList tokenListB){

        Runnable runnable = () -> {
            tokenListA.mergerToken(tokenListB);
            System.out.println("Thread "+Thread.currentThread().getName()+" finished.");
        };

        return new Thread(runnable);
    }

    // TODO: create automatic merger berdasarkan jumlah partisi
}
