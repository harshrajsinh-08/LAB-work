#include <iostream>
#include <string>
#include <cctype>
using namespace std;

// Function prototypes
int parseExpr();
int parseTerm();
int parseFactor();
int parseNumber();

// Global variables for the input string and the current position in it
string input;
int pos = 0;

// Function to get the current character (from the input string)
char currentChar() {
    if (pos < input.size()) {
        return input[pos];
    }
    return '\0';  // Null character if we've reached the end of the input
}

// Function to consume a character (advance the position in the input string)
void consume() {
    if (pos < input.size()) {
        pos++;
    }
}

// Function to check if the current character is a digit
bool isDigit(char c) {
    return c >= '0' && c <= '9';
}

// Parse a number
int parseNumber() {
    int num = 0;
    while (isDigit(currentChar())) {
        num = num * 10 + (currentChar() - '0');
        consume();
    }
    return num;
}

// Parse a factor (a number or an expression inside parentheses)
int parseFactor() {
    if (currentChar() == '(') {
        consume();  // Consume '('
        int result = parseExpr();  // Parse the expression inside parentheses
        if (currentChar() == ')') {
            consume();  // Consume ')'
        } else {
            cerr << "Error: Expected ')'" << endl;
            exit(1);
        }
        return result;
    } else if (isDigit(currentChar())) {
        return parseNumber();  // Parse a number
    } else {
        cerr << "Error: Invalid character " << currentChar() << endl;
        exit(1);
    }
}

// Parse a term (a factor followed by * or /)
int parseTerm() {
    int result = parseFactor();  // Parse the first factor
    while (currentChar() == '*' || currentChar() == '/') {
        char op = currentChar();
        consume();  // Consume the operator
        int nextFactor = parseFactor();  // Parse the next factor
        if (op == '*') {
            result *= nextFactor;
        } else if (op == '/') {
            if (nextFactor == 0) {
                cerr << "Error: Division by zero!" << endl;
                exit(1);
            }
            result /= nextFactor;
        }
    }
    return result;
}

// Parse an expression (a term followed by + or -)
int parseExpr() {
    int result = parseTerm();  // Parse the first term
    while (currentChar() == '+' || currentChar() == '-') {
        char op = currentChar();
        consume();  // Consume the operator
        int nextTerm = parseTerm();  // Parse the next term
        if (op == '+') {
            result += nextTerm;
        } else if (op == '-') {
            result -= nextTerm;
        }
    }
    return result;
}

int main() {
    cout << "Enter an arithmetic expression: ";
    getline(cin, input);  // Read the input expression

    // Parse the expression and print the result
    int result = parseExpr();
    
    // Output the final result
    cout << "Result: " << result << endl;
    
    return 0;
}