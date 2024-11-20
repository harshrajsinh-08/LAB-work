#include <iostream>
#include <string>
#include <vector>
#include <map>

using namespace std;

class IntermediateCodeGenerator {
public:
    IntermediateCodeGenerator() : tempCounter(0) {}

    // Generate a fresh temporary variable name
    string generateTemp() {
        return "t" + to_string(tempCounter++);
    }

    // Convert a simple expression into 3-address code
    void generateCode(const string& expression) {
        vector<string> postfix = infixToPostfix(expression);
        vector<string> stack;

        for (size_t i = 0; i < postfix.size(); i++) {
            string token = postfix[i];
            if (isOperator(token)) {
                // Pop two operands from the stack
                string operand2 = stack.back(); stack.pop_back();
                string operand1 = stack.back(); stack.pop_back();
                
                // Generate a temporary variable
                string temp = generateTemp();
                
                // Emit the intermediate code for the operation
                string code = temp + " = " + operand1 + " " + token + " " + operand2;
                codeList.push_back(code);
                
                // Push the temporary variable back onto the stack
                stack.push_back(temp);
            } else {
                // Push the operand onto the stack
                stack.push_back(token);
            }
        }
    }

    // Print the intermediate code (3-address code)
    void printCode() {
        for (size_t i = 0; i < codeList.size(); i++) {
            cout << codeList[i] << endl;
        }
    }

private:
    int tempCounter;
    vector<string> codeList;

    // Check if the token is an operator
    bool isOperator(const string& token) {
        return (token == "+" || token == "-" || token == "*" || token == "/");
    }

    // Convert infix expression to postfix using the Shunting Yard algorithm
    vector<string> infixToPostfix(const string& expression) {
        vector<string> result;
        vector<string> stack;
        string current;

        for (size_t i = 0; i < expression.size(); i++) {
            char c = expression[i];

            if (isdigit(c) || isalpha(c)) {
                // If the character is a number or a variable, add it to the output
                current += c;
                if (i == expression.size() - 1 || !isalnum(expression[i + 1])) {
                    result.push_back(current);
                    current.clear();
                }
            } else if (c == '(') {
                stack.push_back("(");
            } else if (c == ')') {
                while (!stack.empty() && stack.back() != "(") {
                    result.push_back(stack.back());
                    stack.pop_back();
                }
                stack.pop_back(); // Remove '(' from the stack
            } else if (isOperator(string(1, c))) {
                while (!stack.empty() && precedence(stack.back()) >= precedence(string(1, c))) {
                    result.push_back(stack.back());
                    stack.pop_back();
                }
                stack.push_back(string(1, c));
            }
        }

        // Pop all the remaining operators from the stack
        while (!stack.empty()) {
            result.push_back(stack.back());
            stack.pop_back();
        }

        return result;
    }

    // Get precedence of operators
    int precedence(const string& op) {
        if (op == "+" || op == "-") return 1;
        if (op == "*" || op == "/") return 2;
        return 0;
    }
};

int main() {
    string expression = "(a + b) * (c - d)";

    IntermediateCodeGenerator icg;
    icg.generateCode(expression);
    
    cout << "Generated Intermediate Code (3-Address Code):\n";
    icg.printCode();

    return 0;
}