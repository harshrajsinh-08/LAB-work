import java.io.*;
import java.lang.*;
import java.util.*;

class djik {
    static final int V = 9;
    int mindist(int dist[], Boolean sptSet[]){
        int min = Integer.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < V; v++){
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }
    public void printarr(int dist[]){
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < V; i++){
            System.out.println(i + " \t " + dist[i]);
        }
    }
    public void dijkstra(int graph[][], int src){
        int dist[] = new int[V];
        Boolean sptSet[] = new Boolean[V];
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        dist[src] = 0;
        for (int count = 0; count < V - 1; count++) {
            int u = mindist(dist, sptSet);
            sptSet[u] = true;
            for (int v = 0; v < V; v++){
                if (!sptSet[v] && graph[u][v] != 0
                    && dist[u] != Integer.MAX_VALUE
                    && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
            }
        }
        System.out.println();
        printarr(dist);
    }
    public static void main(String[] args){
        int graph[][]
            = new int[][] { { 0, 4, 0, 0, 0, 0, 0, 8, 0 },
                            { 4, 0, 8, 0, 0, 0, 0, 11, 0 },
                            { 0, 8, 0, 7, 0, 4, 0, 0, 2 },
                            { 0, 0, 7, 0, 9, 14, 0, 0, 0 },
                            { 0, 0, 0, 9, 0, 10, 0, 0, 0 },
                            { 0, 0, 4, 14, 10, 0, 2, 0, 0 },
                            { 0, 0, 0, 0, 0, 2, 0, 1, 6 },
                            { 8, 11, 0, 0, 0, 0, 1, 0, 7 },
                            { 0, 0, 2, 0, 0, 0, 6, 7, 0 } };
        djik t = new djik();
        t.dijkstra(graph, 0);
    }
}
//HARSHRAJSINH ZALA 22BCE2238
