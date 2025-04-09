package LABFAT;
import java.util.Scanner;

public class hill {

    static int modInverse(int a,int m){
        a=(a%m+m)%m;
        for(int x=1;x<m;x++){
            if((a*x)%m == 1){
                return x;
            }
        }
        return -1;
    }
    static int[][] getMatrixInverse(int[][] key, int size) {
        int det = 0;
        if (size == 2) {
            det = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % 26;
        } else if (size == 3) {
            det = key[0][0] * (key[1][1] * key[2][2] - key[1][2] * key[2][1])
                - key[0][1] * (key[1][0] * key[2][2] - key[1][2] * key[2][0])
                + key[0][2] * (key[1][0] * key[2][1] - key[1][1] * key[2][0]);
            det %= 26;
        }

        det = (det + 26) % 26;
        int detInv = modInverse(det, 26);
        if (detInv == -1) {
            System.out.println("Inverse doesn't exist. Matrix not invertible.");
            return null;
        }

        int[][] adj = new int[size][size];

        if (size == 2) {
            adj[0][0] = key[1][1];
            adj[0][1] = -key[0][1];
            adj[1][0] = -key[1][0];
            adj[1][1] = key[0][0];
        } else if (size == 3) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int[][] minor = new int[2][2];
                    for (int mi = 0, x = 0; mi < 3; mi++) {
                        if (mi == i) continue;
                        for (int mj = 0, y = 0; mj < 3; mj++) {
                            if (mj == j) continue;
                            minor[x][y++] = key[mi][mj];
                        }
                        x++;
                    }
                    int minorDet = (minor[0][0] * minor[1][1] - minor[0][1] * minor[1][0]);
                    adj[j][i] = (int) Math.pow(-1, i + j) * minorDet;
                }
            }
        }

        int[][] inv = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                inv[i][j] = (adj[i][j] * detInv) % 26;
                if (inv[i][j] < 0)
                    inv[i][j] += 26;
            }

        return inv;
    }

    static String processMessage(String msg, int size) {
        msg = msg.toUpperCase().replaceAll("[^A-Z]", "");
        while (msg.length() % size != 0) {
            msg += "X"; // padding
        }
        return msg;
    }

    static String encrypt(String msg, int[][] key, int size) {
        msg = processMessage(msg, size);
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < msg.length(); i += size) {
            int[] vec = new int[size];
            for (int j = 0; j < size; j++)
                vec[j] = msg.charAt(i + j) - 'A';

            for (int row = 0; row < size; row++) {
                int sum = 0;
                for (int col = 0; col < size; col++) {
                    sum += key[row][col] * vec[col];
                }
                cipher.append((char) ((sum % 26) + 'A'));
            }
        }
        return cipher.toString();
    }

    static String decrypt(String cipher, int[][] key, int size) {
        int[][] inverseKey = getMatrixInverse(key, size);
        if (inverseKey == null) return "Cannot decrypt.";

        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < cipher.length(); i += size) {
            int[] vec = new int[size];
            for (int j = 0; j < size; j++)
                vec[j] = cipher.charAt(i + j) - 'A';

            for (int row = 0; row < size; row++) {
                int sum = 0;
                for (int col = 0; col < size; col++) {
                    sum += inverseKey[row][col] * vec[col];
                }
                decrypted.append((char) (((sum % 26 + 26) % 26) + 'A'));
            }
        }

        return decrypted.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter matrix size (2 or 3): ");
        int size = sc.nextInt();

        if (size != 2 && size != 3) {
            System.out.println("Only size 2 or 3 supported.");
            return;
        }

        int[][] key = new int[size][size];
        System.out.println("Enter the key matrix (" + (size * size) + " numbers):");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                key[i][j] = sc.nextInt();

        sc.nextLine();
        System.out.print("Enter the message: ");
        String message = sc.nextLine();

        String encrypted = encrypt(message, key, size);
        System.out.println("Encrypted message: " + encrypted);

        String decrypted = decrypt(encrypted, key, size);
        System.out.println("Decrypted message: " + decrypted);
    }
}