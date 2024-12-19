import java.util.Arrays;
public class bellman {
    public static void bellmanFord(int[][] g, int V, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src - 1] = 0;
        for (int i = 0; i < V - 1; i++) {
            for (int u = 0; u < V; u++) {
                for (int v = 0; v < V; v++) {
                    if (g[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + g[u][v] < dist[v]) {
                        dist[v] = dist[u] + g[u][v];
                    }
                }
            }
        }
        System.out.println("\nBellman Ford Routing Table (from src " + src + "):");
        System.out.println("Vertex\tdist from src");
        for (int i = 0; i < V; i++) {
            System.out.println((i + 1) + "\t\t" + dist[i]);
        }
    }
    public static void main(String[] args) {
        int V = 6;
        int[][] g = new int[V][V];
        g[0][1] = 6; g[1][0] = 6;
        g[0][2] = 3; g[2][0] = 3;
        g[1][3] = 7; g[3][1] = 7;
        g[2][4] = 9; g[4][2] = 9;
        g[3][5] = 8; g[5][3] = 8;
        g[4][5] = 4; g[5][4] = 4;
        g[1][2] = 2; g[2][1] = 2;
        g[3][4] = 1; g[4][3] = 1;

        bellmanFord(g, V, 1);
    }
}