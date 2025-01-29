import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

public class sortq {

    private static void sortQueue(Queue<Integer> queue) {
        int n = queue.size();
        int arr[] =new int[n];
        for(int i=0;i<n;i++){
            arr[i]=queue.poll();
        }
        Arrays.sort(arr);
        queue.clear();
        for(int i=0;i<n;i++){
            queue.add(arr[i]);
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(10);
        queue.add(7);
        queue.add(2);
        queue.add(8);
        queue.add(6);

        sortQueue(queue);
    }
}