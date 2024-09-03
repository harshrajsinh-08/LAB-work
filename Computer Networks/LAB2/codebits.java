import java.util.Arrays;

public class codebits {

    public static void main(String[] args) {
        System.out.println("\nThe code bits are as follows: ");
        int[][] Ht = {
            {1, 0, 1},
            {1, 1, 1},
            {1, 1, 0},
            {0, 1, 1},
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };

        int[][] H = transpose(Ht);
        int q = H.length;
        int n = H[0].length;
        int k = n - q;

        int[][] Pt = new int[q][k];
        for (int i = 0; i < q; i++) {
            for (int j = 0; j < k; j++) {
                Pt[i][j] = H[i][j];
            }
        }

        int[][] P = transpose(Pt);

        for (int m1 = 0; m1 < 2; m1++) {
            for (int m2 = 0; m2 < 2; m2++) {
                for (int m3 = 0; m3 < 2; m3++) {
                    for (int m4 = 0; m4 < 2; m4++) {
                        int[] m = {m1, m2, m3, m4}; // Vector with 4 elements
                        int[] q1 = matrixMultiply(m, P);
                        standardize(q1);
                        System.out.println(Arrays.toString(q1));
                    }
                }
            }
        }
    }
    public static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }
    public static int[] matrixMultiply(int[] vector, int[][] matrix) {
        int[] result = new int[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }
        return result;
    }
    public static void standardize(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] %= 2;
        }
    }
}import java.util.Arrays;

public class codebits {

    public static void main(String[] args) {
        System.out.println("\nThe code bits are as follows: ");
        int[][] Ht = {
            {1, 0, 1},
            {1, 1, 1},
            {1, 1, 0},
            {0, 1, 1},
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };

        int[][] H = transpose(Ht);
        int q = H.length;
        int n = H[0].length;
        int k = n - q;

        int[][] Pt = new int[q][k];
        for (int i = 0; i < q; i++) {
            for (int j = 0; j < k; j++) {
                Pt[i][j] = H[i][j];
            }
        }

        int[][] P = transpose(Pt);

        // Calculate and print code bits
        for (int m1 = 0; m1 < 2; m1++) {
            for (int m2 = 0; m2 < 2; m2++) {
                for (int m3 = 0; m3 < 2; m3++) {
                    for (int m4 = 0; m4 < 2; m4++) {
                        int[] m = {m1, m2, m3, m4}; // Vector with 4 elements
                        int[] q1 = matrixMultiply(m, P);
                        standardize(q1);
                        System.out.println("Code bits: " + Arrays.toString(q1));
                        
                        // Calculate and print Hamming distance for each pair
                        for (int i = 0; i < q1.length - 1; i++) {
                            for (int j = i + 1; j < q1.length; j++) {
                                int hammingDistance = hammingDistance(q1[i], q1[j]);
                                System.out.println("Hamming distance between " + q1[i] + " and " + q1[j] + ": " + hammingDistance);
                            }
                        }
                    }
                }
            }
        }
    }

    public static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] transposed = new int[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }

    public static int[] matrixMultiply(int[] vector, int[][] matrix) {
        int[] result = new int[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += vector[j] * matrix[j][i];
            }
        }
        return result;
    }

    public static void standardize(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            vector[i] %= 2;
        }
    }

    // Method to calculate Hamming distance between two integers
    public static int hammingDistance(int a, int b) {
        int x = a ^ b; // XOR to find differing bits
        int distance = 0;
        while (x > 0) {
            distance += x & 1; // Count the number of 1s in the XOR result
            x >>= 1;
        }
        return distance;
    }
}