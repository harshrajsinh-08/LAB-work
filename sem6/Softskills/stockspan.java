import java.util.Stack;
import java.util.Arrays;
public class stockspan{
    public int[] calspan(int[] prices){
        int n = prices.length;
        int span[] = new int[n];
        Stack <Integer> st = new Stack<>();
        st.push(0);
        span[0]=1;
        for(int i=0;i<n;i++){
            while(!st.isEmpty()&&prices[i] >= prices[st.peek()]){
                st.pop();
            }
            span[i]= st.isEmpty()?i+1:i-st.peek();
            st.push(i);
        }
        return span;
    }
    public static void main(String[] args) {
        stockspan calculator = new stockspan();
        int[] stockPrices = {100, 80, 60, 70, 60, 75,85};
        int[] spans = calculator.calspan(stockPrices);

        System.out.println("Stock Prices: " + Arrays.toString(stockPrices));
        System.out.println("Stock Spans:   " + Arrays.toString(spans));
    }
}