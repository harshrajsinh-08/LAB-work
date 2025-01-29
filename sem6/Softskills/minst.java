
import java.util.Stack;

class minst{
    Stack <Integer> stack;
    Stack <Integer> mins;
    public minst(){
        stack = new Stack<>();
        mins = new Stack<>();
    }
    public void push(int element){
        stack.push(element);
        if(mins.isEmpty()|| element<=mins.peek()){
            mins.push(element);
        }
    }
    public void pop(){
        if(!stack.isEmpty()){
            int popped = stack.pop();
            if(popped == mins.peek()){
                mins.pop();
            }
        }
    }
    public int top(){
        if(!stack.isEmpty()){
            return stack.peek();
        }
        return -1;
    }
    public int getmin(){
        if(!mins.isEmpty()){
            return mins.peek();
        }
        return -1;
    }

    public static void main(String[] args) {
        minst stack = new minst();
        stack.push(1);
        stack.push(10);
        stack.push(12);
        stack.push(2);
        System.out.println("min is: "+stack.getmin());
    }
}
