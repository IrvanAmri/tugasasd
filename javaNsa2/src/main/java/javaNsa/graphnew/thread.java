/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaNsa.graphnew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author user
 */
public class thread extends Thread{
    int source, n, destination;
    List<Edge> edges;
    thread(List<Edge> edges, int source, int n, int destination) {
        this.edges = edges;
        this.source = source;
        this.n = n;
        this.destination=destination;
    }
    
    static void getPath(int parent[], int vertex, List<Integer> path)
    {
        if (vertex < 0) {
            return;
        }
 
        getPath(parent, parent[vertex], path);
        path.add(vertex);
    }
 
    // Function to run the Bellman–Ford algorithm from a given source
    @Override
    public void run()
    {
        // distance[] and parent[] stores the shortest path
        // (least cost/path) information
        int distance[] = new int[n];
        int parent[] = new int[n];
 
        // initialize `distance[]` and `parent[]`. Initially, all vertices
        // except source vertex weight INFINITY and no parent
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;
 
        Arrays.fill(parent, -1);
 
        // relaxation step (run V-1 times)
        
        edges.parallelStream().forEach(e -> {
            int u = e.source;
            int v = e.dest;
            int w = e.weight;
            
            if (distance[u] != Integer.MAX_VALUE && distance[u] + w < distance[v])
                {
                    // update distance to the new lower value
                    distance[v] = distance[u] + w;
 
                    // set v's parent as `u`
                    parent[v] = u;
                }
        });
        
        if (destination != source && distance[destination] < Integer.MAX_VALUE) {
            List<Integer> path = new ArrayList<>();
            getPath(parent, destination, path);
            System.out.println("The distance of vertex " + destination + " from vertex " +
                    source + " is " + distance[destination] + ". Its path is " + path);
        } else {
            System.out.println("There's no viable route from "+source+" to "+destination);
        }
    }
}
