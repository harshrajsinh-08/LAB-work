public class celebrity{
    public static int findceleb(int matrix[][]){
        int n= matrix.length;
        int top = 0;
        int bottom = n-1;
        while(top<bottom){
            if(matrix[top][bottom] == 1){
                top=top+1;
            }
            else if(matrix[bottom][top]==1){
                bottom=bottom-1;
            }
            else{
                top++;bottom--;
            }
        }
            if(top>bottom){
                return -1;
            }
            for(int i=0;i<n-1;i++){
                if(i==top) continue;
                if(matrix[top][i] == 0 && matrix[i][top]==1){
                    return top;
                }
            }
            return -1;

    }
    public static void main(String[] args) {
        int matrix[][]= {   { 0, 0, 0, 0 },
                            { 1, 0, 1, 0 },
                            { 1, 1, 0, 1 },
                            { 1, 0, 1, 0 } 
                        };
        System.out.println("celebrity is "+findceleb(matrix));
    }
}