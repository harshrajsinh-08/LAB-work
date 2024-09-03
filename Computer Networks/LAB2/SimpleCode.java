import java.util.Arrays;

public class SimpleCode {

    public static void main(String[] args) {
        int[][] ht = {
            {1, 0, 1},
            {1, 1, 1},
            {1, 1, 0},
            {0, 1, 1},
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };

        int[][] h = transpose(ht);
        int rows = h.length;
        int cols = h[0].length;
        int k = cols - rows;
        int[][] pt = new int[rows][k];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < k; j++) {
                pt[i][j] = h[i][j];
            }
        }

        int[][] p = transpose(pt);

        int[] lastVector = null;

        for (int a = 0; a < 2; a++) {
            for (int b = 0; b < 2; b++) {
                for (int c = 0; c < 2; c++) {
                    for (int d = 0; d < 2; d++) {
                        int[] m = {a, b, c, d};
                        int[] result = multiply(m, p);
                        mod2(result);
                        System.out.println("Input: " + Arrays.toString(m) + " => Output: " + Arrays.toString(result));

                        if (lastVector != null) {
                            int distance = hammingDist(lastVector, result);
                            System.out.println("Hamming distance with previous: " + distance);
                        }

                        lastVector = result;
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

    public static int[] multiply(int[] vec, int[][] matrix) {
        int[] result = new int[matrix[0].length];
        for (int i = 0; i < matrix[0].length; i++) {
            result[i] = 0;
            for (int j = 0; j < vec.length; j++) {
                result[i] += vec[j] * matrix[j][i];
            }
        }
        return result;
    }

    public static void mod2(int[] vec) {
        for (int i = 0; i < vec.length; i++) {
            vec[i] = vec[i] % 2;
        }
    }

    public static int hammingDist(int[] vec1, int[] vec2) {
        int dist = 0;
        for (int i = 0; i < vec1.length; i++) {
            if (vec1[i] != vec2[i]) {
                dist++;
            }
        }
        return dist;
    }
}