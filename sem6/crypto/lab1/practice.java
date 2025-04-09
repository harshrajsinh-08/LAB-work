public class practice{
    public static String encrypt(String pt,int[][] kmat,int matsize){
        pt = pt.toUpperCase().replaceAll("[^A-Z]","");
        while(pt.length()%matsize!=0){
            pt+='X';
        }
        StringBuilder ct = new StringBuilder();
        for(int i=0;i<pt.length();i+=matsize){
            int[] res = new int[matsize];
            for(int j=0;j<matsize;j++){
                res[j] = pt.charAt(i+j)-'A';
            }

            for(int row=0;row<matsize;row++){
                int sum = 0;
                for(int col=0;col<matsize;col++){
                    sum += kmat[row][col]*res[col];
                }
                ct.append((char)((sum%26)+'A'));
            }

        }
        return ct.toString();
    }
}