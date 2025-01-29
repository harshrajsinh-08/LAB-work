import java.util.Stack;

public class stackperm {
    public static boolean istrue(int[] original,int[] target){
        Stack<Integer> stack = new Stack<>();
        int i=0;
        for(int num:original){
            stack.push(num);
            while(!stack.isEmpty()&& stack.peek() == target[i]){
                stack.pop();
                i++;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        int[] original = {1, 2, 3};
        int[] target = {2,1,3};
        boolean result = istrue(original, target);
        System.out.println("Is it a stack permutation? " + result);
    }
}