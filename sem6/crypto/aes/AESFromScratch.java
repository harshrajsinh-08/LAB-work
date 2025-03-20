public class AESFromScratch {

    private int Nk;  // Key size in words (number of 32-bit words in the key)
    private int Nb = 4;  // Block size in words (always 4 for AES)
    private int Nr;  // Number of rounds, depends on key size

    private byte[][] state = new byte[4][Nb]; // State array

    private byte[][] keySchedule; // Expanded key
    static byte[][] sbox = {
            {(byte)0x63, (byte)0x7c, (byte)0x77, (byte)0x7b, (byte)0xf2, (byte)0x6b, (byte)0x6f, (byte)0xc5, (byte)0x30, (byte)0x01, (byte)0x67, (byte)0x2b, (byte)0xfe, (byte)0xd7, (byte)0xab, (byte)0x76},
            {(byte)0xca, (byte)0x82, (byte)0xc9, (byte)0x7d, (byte)0xfa, (byte)0x59, (byte)0x47, (byte)0xf0, (byte)0xad, (byte)0xd4, (byte)0xa2, (byte)0xaf, (byte)0x9c, (byte)0xa4, (byte)0x72, (byte)0xc0},
            {(byte)0xb7, (byte)0xfd, (byte)0x93, (byte)0x26, (byte)0x36, (byte)0x3f, (byte)0xf7, (byte)0xcc, (byte)0x34, (byte)0xa5, (byte)0xe5, (byte)0xf1, (byte)0x71, (byte)0xd8, (byte)0x31, (byte)0x15},
            {(byte)0x04, (byte)0xc7, (byte)0x23, (byte)0xc3, (byte)0x18, (byte)0x96, (byte)0x05, (byte)0x9a, (byte)0x07, (byte)0x12, (byte)0x80, (byte)0xe2, (byte)0xeb, (byte)0x27, (byte)0xb2, (byte)0x75},
            {(byte)0x09, (byte)0x83, (byte)0x2c, (byte)0x1a, (byte)0x1b, (byte)0x6e, (byte)0x5a, (byte)0xa0, (byte)0x52, (byte)0x3b, (byte)0xd6, (byte)0xb3, (byte)0x29, (byte)0xe3, (byte)0x2f, (byte)0x84},
            {(byte)0x53, (byte)0xd1, (byte)0x00, (byte)0xed, (byte)0x20, (byte)0xfc, (byte)0xb1, (byte)0x5b, (byte)0x6a, (byte)0xcb, (byte)0xbe, (byte)0x39, (byte)0x4a, (byte)0x4c, (byte)0x58, (byte)0xcf},
            {(byte)0xd0, (byte)0xef, (byte)0xaa, (byte)0xfb, (byte)0x43, (byte)0x4d, (byte)0x33, (byte)0x85, (byte)0x45, (byte)0xf9, (byte)0x02, (byte)0x7f, (byte)0x50, (byte)0x3c, (byte)0x9f, (byte)0xa8},
            {(byte)0x51, (byte)0xa3, (byte)0x40, (byte)0x8f, (byte)0x92, (byte)0x9d, (byte)0x38, (byte)0xf5, (byte)0xbc, (byte)0xb6, (byte)0xda, (byte)0x21, (byte)0x10, (byte)0xff, (byte)0xf3, (byte)0xd2},
            {(byte)0xcd, (byte)0x0c, (byte)0x13, (byte)0xec, (byte)0x5f, (byte)0x97, (byte)0x44, (byte)0x17, (byte)0xc4, (byte)0xa7, (byte)0x7e, (byte)0x3d, (byte)0x64, (byte)0x5d, (byte)0x19, (byte)0x73},
            {(byte)0x60, (byte)0x81, (byte)0x4f, (byte)0xdc, (byte)0x22, (byte)0x2a, (byte)0x90, (byte)0x88, (byte)0x46, (byte)0xee, (byte)0xb8, (byte)0x14, (byte)0xde, (byte)0x5e, (byte)0x0b, (byte)0xdb},
            {(byte)0xe0, (byte)0x32, (byte)0x3a, (byte)0x0a, (byte)0x49, (byte)0x06, (byte)0x24, (byte)0x5c, (byte)0xc2, (byte)0xd3, (byte)0xac, (byte)0x62, (byte)0x91, (byte)0x95, (byte)0xe4, (byte)0x79},
            {(byte)0xe7, (byte)0xc8, (byte)0x37, (byte)0x6d, (byte)0x8d, (byte)0xd5, (byte)0x4e, (byte)0xa9, (byte)0x6c, (byte)0x56, (byte)0xf4, (byte)0xea, (byte)0x65, (byte)0x7a, (byte)0xae, (byte)0x08},
            {(byte)0xba, (byte)0x78, (byte)0x25, (byte)0x2e, (byte)0x1c, (byte)0xa6, (byte)0xb4, (byte)0xc6, (byte)0xe8, (byte)0xdd, (byte)0x74, (byte)0x1f, (byte)0x4b, (byte)0xbd, (byte)0x8b, (byte)0x8a},
            {(byte)0x70, (byte)0x3e, (byte)0xb5, (byte)0x66, (byte)0x48, (byte)0x03, (byte)0xf6, (byte)0x0e, (byte)0x61, (byte)0x35, (byte)0x57, (byte)0xb9, (byte)0x86, (byte)0xc1, (byte)0x1d, (byte)0x9e},
            {(byte)0xe1, (byte)0xf8, (byte)0x98, (byte)0x11, (byte)0x69, (byte)0xd9, (byte)0x8e, (byte)0x94, (byte)0x9b, (byte)0x1e, (byte)0x87, (byte)0xe9, (byte)0xce, (byte)0x55, (byte)0x28, (byte)0xdf},
            {(byte)0x8c, (byte)0xa1, (byte)0x89, (byte)0x0d, (byte)0xbf, (byte)0xe6, (byte)0x42, (byte)0x68, (byte)0x41, (byte)0x99, (byte)0x2d, (byte)0x0f, (byte)0xb0, (byte)0x54, (byte)0xbb, (byte)0x16}
    };

    static int[][] invsbox = {
            {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
            {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
            {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
            {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
            {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
            {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
            {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
            {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
            {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
            {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
            {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
            {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
            {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
            {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}
    };

    // Round constant (provided in the prompt)
    static int[] rcon = {0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
            0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
            0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
            0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
            0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
            0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
            0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
            0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
            0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
            0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
            0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
            0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
            0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb};

    static int[][] galois = 
            {{0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}};


    public AESFromScratch(int keySize) {
        if (keySize == 128) {
            Nk = 4;
            Nr = 10;
        } else if (keySize == 192) {
            Nk = 6;
            Nr = 12;
        } else if (keySize == 256) {
            Nk = 8;
            Nr = 14;
        } else {
            throw new IllegalArgumentException("Invalid key size.  Must be 128, 192, or 256.");
        }
    }

    private byte[] padInput(byte[] input) {
        int paddingLength = 16 - (input.length % 16);
        if (paddingLength == 0) {
            paddingLength = 16; // Pad with a full block if already a multiple of 16
        }
        byte[] paddedInput = new byte[input.length + paddingLength];
        System.arraycopy(input, 0, paddedInput, 0, input.length);

        // PKCS7 padding: pad with the number of padding bytes
        for (int i = input.length; i < paddedInput.length; i++) {
            paddedInput[i] = (byte) paddingLength;
        }
        return paddedInput;
    }

    private byte[] unpadInput(byte[] paddedInput) {
        int paddingLength = paddedInput[paddedInput.length - 1] & 0xFF;

        // Check for corrupted padding
        if (paddingLength > 16 || paddingLength <= 0) {
            return paddedInput; // Invalid padding, return original (or throw exception)
        }

        int newLength = paddedInput.length - paddingLength;
        byte[] unpadded = new byte[newLength];
        System.arraycopy(paddedInput, 0, unpadded, 0, newLength);
        return unpadded;
    }

    public byte[] encrypt(byte[] input, byte[] key) {
        // Pad the input to be multiple of 16 bytes
        byte[] paddedInput = padInput(input);
        byte[] output = new byte[paddedInput.length];

        keyExpansion(key);

        // Process each 16-byte block
        for (int i = 0; i < paddedInput.length; i += 16) {
            byte[] block = new byte[16];
            System.arraycopy(paddedInput, i, block, 0, 16);
            byte[] encryptedBlock = encryptBlock(block, key);
            System.arraycopy(encryptedBlock, 0, output, i, 16);
        }

        return output;
    }

    private byte[] encryptBlock(byte[] input, byte[] key) {
        if (input.length != 16) {
            throw new IllegalArgumentException("Block length must be 128 bits");
        }

        // Copy input to state array
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = input[i + 4 * j];
            }
        }

        addRoundKey(0);

        for (int round = 1; round < Nr; round++) {
            subBytes();
            shiftRows();
            mixColumns();
            addRoundKey(round);
        }

        subBytes();
        shiftRows();
        addRoundKey(Nr);

        byte[] output = new byte[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                output[i + 4 * j] = state[i][j];
            }
        }

        return output;
    }

    public byte[] decrypt(byte[] input, byte[] key) {
        if (input.length % 16 != 0) {
            throw new IllegalArgumentException("Input length must be multiple of 128 bits");
        }

        keyExpansion(key);
        byte[] output = new byte[input.length];

        // Process each 16-byte block
        for (int i = 0; i < input.length; i += 16) {
            byte[] block = new byte[16];
            System.arraycopy(input, i, block, 0, 16);
            byte[] decryptedBlock = decryptBlock(block, key);
            System.arraycopy(decryptedBlock, 0, output, i, 16);
        }

        // Remove padding from the final result
        return unpadInput(output);
    }

    private byte[] decryptBlock(byte[] input, byte[] key) {
        if (input.length != 16) {
            throw new IllegalArgumentException("Block length must be 128 bits");
        }

        // Copy input to state array
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = input[i + 4 * j];
            }
        }

        addRoundKey(Nr);

        for (int round = Nr - 1; round > 0; round--) {
            invShiftRows();
            invSubBytes();
            addRoundKey(round);
            invMixColumns();
        }

        invShiftRows();
        invSubBytes();
        addRoundKey(0);

        byte[] output = new byte[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                output[i + 4 * j] = state[i][j];
            }
        }

        return output;
    }

    // Key Expansion
    private void keyExpansion(byte[] key) {
        int Nb = 4;
        int Nk;
        int Nr;

        if (key.length == 16) {
            Nk = 4;
            Nr = 10;
        } else if (key.length == 24) {
            Nk = 6;
            Nr = 12;
        } else if (key.length == 32) {
            Nk = 8;
            Nr = 14;
        } else {
            throw new IllegalArgumentException("Key length must be 16, 24, or 32 bytes for AES-128, AES-192, or AES-256, respectively.");
        }

        keySchedule = new byte[Nb * (Nr + 1)][4];
        byte[] temp = new byte[4];

        // The first Nk words are the key itself.
        for (int i = 0; i < Nk; i++) {
            for (int j = 0; j < 4; j++) {
                keySchedule[i][j] = key[i * 4 + j];
            }
        }

        // All other words w[i] are computed recursively based on w[i-1] and w[i-Nk].
        for (int i = Nk; i < Nb * (Nr + 1); i++) {
            for (int j = 0; j < 4; j++) {
                temp[j] = keySchedule[i - 1][j];
            }
            if (i % Nk == 0) {
                temp = subWord(rotWord(temp));
                temp[0] ^= (byte) rcon[i / Nk];
            } else if (Nk > 6 && i % Nk == 4) {
                temp = subWord(temp);
            }
            for (int j = 0; j < 4; j++) {
                keySchedule[i][j] = (byte) (keySchedule[i - Nk][j] ^ temp[j]);
            }
        }
    }

    private byte[] subWord(byte[] word) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = sbox[((word[i] & 0xFF) >>> 4)][word[i] & 0x0F];
        }
        return result;
    }

    private byte[] rotWord(byte[] word) {
        byte[] result = new byte[4];
        result[0] = word[1];
        result[1] = word[2];
        result[2] = word[3];
        result[3] = word[0];
        return result;
    }

    // SubBytes
    private void subBytes() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = sbox[((state[i][j] & 0xFF) >>> 4)][state[i][j] & 0x0F];
            }
        }
    }

    // InvSubBytes
    private void invSubBytes() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = (byte) invsbox[((state[i][j] & 0xFF) >>> 4)][state[i][j] & 0x0F];
            }
        }
    }

    // ShiftRows
    private void shiftRows() {
        byte temp;

        // Row 1
        temp = state[1][0];
        state[1][0] = state[1][1];
        state[1][1] = state[1][2];
        state[1][2] = state[1][3];
        state[1][3] = temp;

        // Row 2
        temp = state[2][0];
        state[2][0] = state[2][2];
        state[2][2] = temp;
        temp = state[2][1];
        state[2][1] = state[2][3];
        state[2][3] = temp;

        // Row 3
        temp = state[3][3];
        state[3][3] = state[3][2];
        state[3][2] = state[3][1];
        state[3][1] = state[3][0];
        state[3][0] = temp;
    }

    // InvShiftRows
    private void invShiftRows() {
        byte temp;

        // Row 1
        temp = state[1][3];
        state[1][3] = state[1][2];
        state[1][2] = state[1][1];
        state[1][1] = state[1][0];
        state[1][0] = temp;

        // Row 2
        temp = state[2][0];
        state[2][0] = state[2][2];
        state[2][2] = temp;
        temp = state[2][1];
        state[2][1] = state[2][3];
        state[2][3] = temp;

        // Row 3
        temp = state[3][0];
        state[3][0] = state[3][1];
        state[3][1] = state[3][2];
        state[3][2] = state[3][3];
        state[3][3] = temp;
    }

    // MixColumns
    private void mixColumns() {
        byte[] column = new byte[4];
        for (int c = 0; c < 4; c++) {
            for (int i = 0; i < 4; i++) {
                column[i] = state[i][c];
            }
            byte[] newColumn = mixColumn(column);
            for (int i = 0; i < 4; i++) {
                state[i][c] = newColumn[i];
            }
        }
    }

    private byte[] mixColumn(byte[] column) {
        byte[] newColumn = new byte[4];
        for (int i = 0; i < 4; i++) {
            newColumn[i] = (byte) (galoisMultiply(galois[i][0], column[0]) ^
                    galoisMultiply(galois[i][1], column[1]) ^
                    galoisMultiply(galois[i][2], column[2]) ^
                    galoisMultiply(galois[i][3], column[3]));
        }
        return newColumn;
    }


    // InvMixColumns
    private void invMixColumns() {
        byte[] column = new byte[4];
        for (int c = 0; c < 4; c++) {
            for (int i = 0; i < 4; i++) {
                column[i] = state[i][c];
            }
            byte[] newColumn = invMixColumn(column);
            for (int i = 0; i < 4; i++) {
                state[i][c] = newColumn[i];
            }
        }
    }

    private byte[] invMixColumn(byte[] column) {
        int[][] invGalois = {
                {0x0e, 0x0b, 0x0d, 0x09},
                {0x09, 0x0e, 0x0b, 0x0d},
                {0x0d, 0x09, 0x0e, 0x0b},
                {0x0b, 0x0d, 0x09, 0x0e}
        };
        byte[] newColumn = new byte[4];
        for (int i = 0; i < 4; i++) {
            newColumn[i] = (byte) (galoisMultiply(invGalois[i][0], column[0]) ^
                    galoisMultiply(invGalois[i][1], column[1]) ^
                    galoisMultiply(invGalois[i][2], column[2]) ^
                    galoisMultiply(invGalois[i][3], column[3]));
        }
        return newColumn;
    }

    private int galoisMultiply(int a, int b) {
        int p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) {
                p ^= a;
            }
            boolean highBitSet = (
                (a & 0x80) != 0);
            a <<= 1;
            if (highBitSet) {
                a ^= 0x11b; // AES prime polynomial
            }
            b >>>= 1;
        }
        return p & 0xFF;
    }

    // AddRoundKey
    private void addRoundKey(int round) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] ^= keySchedule[round * Nb + j][i];
            }
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // Example key (16 bytes for AES-128)
        byte[] key = new byte[16];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) i;
        }

        // Test string of any length
        String message = "Hello, AES World! This is a longer message to test padding.";
        byte[] input = message.getBytes();

        AESFromScratch aes = new AESFromScratch(128);

        // Encrypt
        byte[] encrypted = aes.encrypt(input, key);
        System.out.println("Original: " + message);
        System.out.println("Encrypted (hex): " + bytesToHex(encrypted));

        // Decrypt
        byte[] decrypted = aes.decrypt(encrypted, key);
        System.out.println("Decrypted: " + new String(decrypted));
    }
}

