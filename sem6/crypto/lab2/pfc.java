import java.util.*;

class Playfair {
    String key;
    String plainText;
    char[][] matrix = new char[5][5];

    public Playfair(String key, String plainText) {
        this.key = key.toLowerCase();
        this.plainText = plainText.toLowerCase();
    }
    public void cleanPlayFairKey() {
        LinkedHashSet<Character> set = new LinkedHashSet<>();
        StringBuilder newKey = new StringBuilder();

        for (char c : key.toCharArray()) {
            if (c == 'j') c = 'i';
            set.add(c);
        }
        for (char c : set) {
            newKey.append(c);
        }
        key = newKey.toString();
    }
    public void generateCipherKey() {
        Set<Character> set = new HashSet<>();
        for (char c : key.toCharArray()) {
            if (c != 'j') set.add(c);
        }
        StringBuilder tempKey = new StringBuilder(key);
        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'j') continue;
            if (!set.contains(c)) tempKey.append(c);
        }
        for (int i = 0, idx = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = tempKey.charAt(idx++);
            }
        }
        System.out.println("Playfair Cipher Key Matrix:");
        for (int i = 0; i < 5; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }
    public String formatPlainText() {
        StringBuilder message = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {
            char current = plainText.charAt(i) == 'j' ? 'i' : plainText.charAt(i);
            message.append(current);

            if (i + 1 < plainText.length() && current == plainText.charAt(i + 1)) {
                message.append('x');
            }
        }
        if (message.length() % 2 == 1) {
            message.append('x');
        }
        return message.toString();
    }
    public String[] formPairs(String message) {
        int len = message.length();
        String[] pairs = new String[len / 2];

        for (int i = 0, cnt = 0; i < len / 2; i++) {
            pairs[i] = message.substring(cnt, cnt += 2);
        }
        return pairs;
    }
    public int[] getCharPos(char ch) {
        int[] keyPos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == ch) {
                    keyPos[0] = i;
                    keyPos[1] = j;
                    return keyPos;
                }
            }
        }
        return keyPos;
    }
    public String encryptMessage() {
        String message = formatPlainText();
        String[] msgPairs = formPairs(message);
        StringBuilder encText = new StringBuilder();

        for (String pair : msgPairs) {
            char ch1 = pair.charAt(0);
            char ch2 = pair.charAt(1);

            int[] ch1Pos = getCharPos(ch1);
            int[] ch2Pos = getCharPos(ch2);

            if (ch1Pos[0] == ch2Pos[0]) { // Same row
                ch1Pos[1] = (ch1Pos[1] + 1) % 5;
                ch2Pos[1] = (ch2Pos[1] + 1) % 5;
            } else if (ch1Pos[1] == ch2Pos[1]) { // Same column
                ch1Pos[0] = (ch1Pos[0] + 1) % 5;
                ch2Pos[0] = (ch2Pos[0] + 1) % 5;
            } else { // Rectangle
                int temp = ch1Pos[1];
                ch1Pos[1] = ch2Pos[1];
                ch2Pos[1] = temp;
            }

            encText.append(matrix[ch1Pos[0]][ch1Pos[1]])
                   .append(matrix[ch2Pos[0]][ch2Pos[1]]);
        }
        return encText.toString();
    }
}

public class pfc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key= sc.nextLine();
        System.out.println();
        System.out.print("Enter the plaintext: ");
        String plaintext = sc.nextLine();

        System.out.println("Key: " + key);
        System.out.println("PlainText: " + plaintext);

        Playfair pfc = new Playfair(key, plaintext);
        pfc.cleanPlayFairKey();
        pfc.generateCipherKey();

        String encText = pfc.encryptMessage();
        System.out.println("Cipher Text is: " + encText);
    }
}