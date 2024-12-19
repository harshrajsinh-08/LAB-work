import java.util.*;

class bellmanford {
    int vertices;

    bellmanford(int vertices) {
        this.vertices = vertices;
    }

    void bellmanFord(int[][] graph, int src) {
        int V = vertices;
        int[] dist = new int[V];

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;

        for (int i = 1; i < V; i++) {
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }
        }

        for (int u = 0; u < V; u++) {
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    System.out.println("Graph contains negative weight cycle");
                    return;
                }
            }
        }

        printArr(dist, V);
    }

    void printArr(int[] dist, int V) {
        System.out.println("Vertex Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + "\t\t" + dist[i]);
    }

    public static void main(String[] args) {
        int V = 5;
        bellmanford bf = new bellmanford(V);

        int[][] graph = {
            { 0, -1, 4, 0, 0 },
            { 0, 0, 3, 2, 2 },
            { 0, 0, 0, 0, 0 },
            { 0, 1, 5, 0, 0 },
            { 0, 0, 0, -3, 0 }
        };

        bf.bellmanFord(graph, 0);
    }
}
