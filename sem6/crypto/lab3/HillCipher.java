import java.util.Scanner;

public class HillCipher {

    public static String encrypt(String plaintext, int[][] keyMatrix, int matrixSize) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        while (plaintext.length() % matrixSize != 0) {
            plaintext += 'X';
        }

        StringBuilder cipherText = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += matrixSize) {
            int[] vector = new int[matrixSize];

            for (int j = 0; j < matrixSize; j++) {
                vector[j] = plaintext.charAt(i + j) - 'A';
            }

            for (int row = 0; row < matrixSize; row++) {
                int sum = 0;
                for (int col = 0; col < matrixSize; col++) {
                    sum += keyMatrix[row][col] * vector[col];
                }
                cipherText.append((char) ((sum % 26) + 'A'));
            }
        }

        return cipherText.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Hill Cipher Program ===");

        System.out.print("Enter the size of the key matrix (e.g., 2 for 2x2): ");
        int matrixSize = scanner.nextInt();
        int[][] keyMatrix = new int[matrixSize][matrixSize];

        System.out.println("\nEnter the key matrix (space-separated values row by row):");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }
        scanner.nextLine();

        System.out.print("\nEnter the text to encrypt: ");
        String plaintext = scanner.nextLine();

        String encryptedText = encrypt(plaintext, keyMatrix, matrixSize);
        System.out.println("\nEncrypted Text: " + encryptedText);

        System.out.println("\n=== Program Completed ===");

        scanner.close();
    }
}
