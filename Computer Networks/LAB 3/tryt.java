import java.util.Scanner;

public class tryt {
    public static void hmsend(String data){
        char hm[] = new char[22];
        StringBuilder sb = new StringBuilder(data);
        sb.reverse();
        data = sb.toString();
        int dataindex = 0;
        for(int i=0;i<22;i++){
            if(i==1||i==2||i==4||i==8||i==16){
                hm[i] = 'p';
            }else{
                if(dataindex<data.length()){
                    hm[i]=data.charAt(dataindex);
                    dataindex++;
                }
            }
        }

        for(int i=21;i>0;i--){
            System.out.print(hm[i]+" ");
        }
        System.out.println();
        hm[1] = (char) (((hm[3]^hm[5]^hm[7]^hm[9]^hm[11]^hm[13]^hm[15]^hm[17]^hm[19]^hm[21])&1)+'0');

        System.out.println("p0 = "+hm[0]);

        hm[2] = (char)(((hm[3]^hm[6]^hm[7]^hm[10]^hm[11]^hm[14]^hm[15]^hm[18]^hm[19]^hm[21])&1)+'0');

        System.out.println("p1 = "+hm[1]);

        hm[4] = (char) (((hm[5] ^ hm[6] ^ hm[7] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]^hm[20])& 1) + '0');
        System.out.println("p4: "+ hm[4]);

        hm[8] = (char) (((hm[9] ^ hm[10] ^ hm[11] ^ hm[12] ^ hm[13] ^ hm[14] ^ hm[15]) & 1) + '0');
        System.out.println("p8: "+ hm[8]);

        hm[16] = (char) (((hm[17] ^ hm[18] ^ hm[19] ^ hm[20] ^ hm[21]) & 1) + '0');
        System.out.println("p16: "+ hm[16]);
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the integer string: ");
        String input = sc.nextLine();
        hmsend(input);
    }
}