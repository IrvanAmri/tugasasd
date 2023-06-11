/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa.graphnew;

/**
 *
 * @author user
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
 
class BF
{
    public static void main(String[] args)
    {
        /*
        // List of graph edges as per the above diagram
        List<Edge> edges = Arrays.asList(
                // (x, y, w) —> edge from `x` to `y` having weight `w`
                new Edge(0, 1, 1), new Edge(0, 2, 4), new Edge(1, 2, 3),
                new Edge(1, 3, 2), new Edge(1, 4, 2), new Edge(3, 2, 5),
                new Edge(3, 1, 1), new Edge(4, 3, 3)
        );
        */
        
        List<Edge> edges = new ArrayList<>();
        
        ArrayList<String> file = null; 
        try {
            String content = new String(Files.readAllBytes(Paths.get("edge.txt")));
            file = new ArrayList<>(Arrays.asList(content.split("\\s")));
        } catch (IOException ex) {
            
        }
        
        file.parallelStream().forEach(f -> {
            ArrayList<String> edge = new ArrayList<>(Arrays.asList(f.split(",")));
            int a = Integer.valueOf(edge.get(0));
            int b = Integer.valueOf(edge.get(1));
            int c = Integer.valueOf(edge.get(2));
            
            edges.add(new Edge(a,b,c));
        });
        
        
 
        // set the maximum number of nodes in the graph
        int n = 5;
 
        // run the Bellman–Ford algorithm from every node
        thread t0 = new thread(edges, 0, n, 3);
        t0.start();
        thread t1 = new thread(edges, 1, n, 2);
        t1.start();
        thread t2 = new thread(edges, 2, n, 4);
        t2.start();
        thread t3 = new thread(edges, 3, n, 1);
        t3.start();
        thread t4 = new thread(edges, 4, n, 0);
        t4.start();
    }
}
