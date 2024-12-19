import java.util.*;

public class djikstra {
    private static final int NO_PARENT = -1;

    public static void dijkstra(int[][] adjacencyMatrix, int startVertex) {
        int nVertices = adjacencyMatrix.length; // Fixed to dynamically get the length
        int[] shortestDistances = new int[nVertices];
        boolean[] added = new boolean[nVertices];
        
        // Initialize distances and added array
        for (int i = 0; i < nVertices; i++) {
            shortestDistances[i] = Integer.MAX_VALUE;
            added[i] = false;
        }
        shortestDistances[startVertex] = 0;
        int[] parents = new int[nVertices];
        parents[startVertex] = NO_PARENT;
        
        // Find shortest path for all vertices
        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            added[nearestVertex] = true;
            
            // Update distances
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
        printSolution(startVertex, shortestDistances, parents);
    }

    private static void printSolution(int startVertex, int[] distances, int[] parents) {
        int nVertices = distances.length;
        System.out.println("Vertex\t Distance\tPath");
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            if (vertexIndex != startVertex) {
                System.out.print("\n" + startVertex + " -> ");
                System.out.print(vertexIndex + " \t\t ");
                System.out.print(distances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents);
            }
        }
    }

    private static void printPath(int currentVertex, int[] parents) {
        if (currentVertex == NO_PARENT) {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
    }

    public static void main(String[] args) {
        int[][] adjacencyMatrix = {
            { 0,4,0,0,0,0,8,0 },
            { 4,0,8,0,0,0,0,11,0 },
            { 0,0,7,0,9,15,0,0,0 },
            { 0,0,0,9,0,10,0,0,0 },
            { 0,0,4,15,10,0,2,0,0 },
            { 0,0,0,0,0,2,0,1,6 },
            { 8,11,0,0,0,0,1,0,11}
        };
        dijkstra(adjacencyMatrix, 0);
    }
}
