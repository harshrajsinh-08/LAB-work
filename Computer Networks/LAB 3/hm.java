public class hm {
    public static void hamm_sender(String data) {
        char[] hm = new char[22];
        StringBuilder sb = new StringBuilder(data);
        sb.reverse();
        data = sb.toString();

        int dataIndex = 0;

        for (int i = 1; i < 22; i++) {
            if (i == 1 || i == 2 || i == 4 || i == 8 || i == 16) {
                hm[i] = 'p';
            } else {
                if (dataIndex < data.length()) {
                    hm[i] = data.charAt(dataIndex);
                    dataIndex++;
                }
            }
        }
        for (int i = 21; i > 0; i--) {
            System.out.print(hm[i] + " ");
        }
        System.out.println();
        hm[1] = (char) (((hm[3] ^ hm[5] ^ hm[7] ^ hm[9] ^ hm[11] ^ hm[13] ^ hm[15] ^ hm[17] ^ hm[19] ^ hm[21]) & 1)+ '0');
        System.out.println("P1: " + (hm[3] + "^" + hm[5] + "^" + hm[7] + "^" + hm[9] + "^" + hm[11] + "^" + hm[13] + "^"+ hm[15] + "^" + hm[17] + "^" + hm[19] + "^" + hm[21]) + " = " + hm[1]);

        hm[2] = (char) (((hm[3] ^ hm[6] ^ hm[7] ^ hm[10] ^ hm[11] ^ hm[14] ^ hm[15] ^ hm[18] ^ hm[19] ^ hm[21]) & 1)
                + '0');
        System.out.println("P2: " + (hm[3] + "^" + hm[6] + "^" + hm[7] + "^" + hm[10] + "^" + hm[11] + "^" + hm[14]
                + "^" + hm[15] + "^" + hm[18] + "^" + hm[19] + "^" + hm[21]) + " = " + hm[2]);

        hm[4] = (char) (((hm[5] ^ hm[6] ^ hm[7] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1) + '0');
        System.out.println(
                "P4: " + (hm[5] + "^" + hm[6] + "^" + hm[7] + "^" + hm[12] + "^" + hm[13] + "^" + hm[14] + "^" + hm[15])
                        + " = " + hm[4]);

        hm[8] = (char) (((hm[9] ^ hm[10] ^ hm[11] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1) + '0');
        System.out.println("P8: "
                + (hm[9] + "^" + hm[10] + "^" + hm[11] + "^" + hm[12] + "^" + hm[13] + "^" + hm[14] + "^" + hm[15])
                + " = " + hm[8]);

        hm[16] = (char) (((hm[17] ^ hm[18] ^ hm[19] ^ hm[20] ^ hm[21]) & 1) + '0');
        System.out.println(
                "P16: " + (hm[17] + "^" + hm[18] + "^" + hm[19] + "^" + hm[20] + "^" + hm[21]) + " = " + hm[16]);
        System.out.println();

        System.out.print("Hamming array: ");
        for (int i = 21; i >= 0; i--) {
            System.out.print(hm[i] + "");
        }
        System.out.println();

        System.out.print("Data sent: ");
        for (int i = 21; i >= 0; i--) {
            System.out.print(hm[i] + "");
        }
        System.out.println();
        System.out.println("At reciever side: ");
        System.out.print("Data recieved: ");
        for (int i = 21; i >= 0; i--) {
            System.out.print(hm[i] + "");
        }
        System.out.println();
        char p1 = (char) (((hm[1]^hm[3] ^ hm[5] ^ hm[7] ^ hm[9] ^ hm[11] ^ hm[13] ^ hm[15] ^ hm[17] ^ hm[19] ^ hm[21]) & 1)+'0');
        char p2 = (char) (((hm[2]^hm[3] ^ hm[6] ^ hm[7] ^ hm[10] ^ hm[11] ^ hm[14] ^ hm[15] ^ hm[18] ^ hm[19] ^ hm[21]) & 1)+ '0');
        char p4 = (char) (((hm[4]^hm[5] ^ hm[6] ^ hm[7] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1)+ '0');
        char p8 = (char) (((hm[8]^hm[9] ^ hm[10] ^ hm[11] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1)+ '0');
        char p16 = (char) (((hm[16]^hm[17] ^ hm[18] ^ hm[19] ^ hm[20] ^ hm[21]) & 1)+ '0');

        System.out.println("P1: "+p1);
        System.out.println("P2: "+p2);
        System.out.println("P4: "+p4);
        System.out.println("P8: "+p8);
        System.out.println("P16: "+p16);

        int error = (int)(p1-'0')*1 + (int)(p2-'0')*2 + (int)(p4-'0')*4 + (int)(p8-'0')*8 + (int)(p16-'0')*16;
        if(error!=0){
            System.out.println("Error at position: "+error);
            if(hm[error]=='0'){
                hm[error] = '1';
            }else{
                hm[error] = '0';
            }
            System.out.print("Corrected data: ");
            for (int i = 21; i >= 0; i--) {
                System.out.print(hm[i] + "");
            }
            System.out.println();
        }else{
            System.out.println("No error");
        }
    }
    public static void main(String[] args) {
        String binaryData = "0010001000111000";
        System.out.println("Sender side: ");
        hamm_sender(binaryData);
    }
}