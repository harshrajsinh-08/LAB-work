import java.util.*;
public class so{
    public static void sortQ(Queue<Integer> q){
        int  n = q.size();
        for(int i=0;i<n;i++){
            int minIndex = -1;
            int minval = Integer.MAX_VALUE;

            for(int j=0;j<n;j++){
                int curr = q.poll();
                if(curr<minval && j<(n-i)){
                    minval = curr;
                    minIndex = j;
                }
                q.add(curr);
            }
            for(int j=0;j<n;j++){
                int curr = q.poll();
                if(j!=minIndex){
                    q.add(curr);
                }
            }
            q.add(minval);
        }
    }
}