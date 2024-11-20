#include <iostream>
#include <cctype>
#include <string>
#include <vector>

using namespace std;

enum Token
{
    KEYWORD,
    IDENTIFIER,
    NUMBER,
    OPERATOR,
    END_OF_FILE,
    INVALID
};

struct TokenInfo
{
    Token type;
    string value;
};

// Check if a string is a keyword
bool isKeyword(const string &str)
{
    vector<string> keywords;
    keywords.push_back("if");
    keywords.push_back("else");
    keywords.push_back("while");
    keywords.push_back("for");
    keywords.push_back("int");
    keywords.push_back("float");

    for (int i = 0; i < keywords.size(); ++i)
    {
        if (str == keywords[i])
        {
            return true;
        }
    }
    return false;
}

// Lexical analyzer function to tokenize input string
vector<TokenInfo> lexicalAnalyzer(const string &input)
{
    vector<TokenInfo> tokens;
    string currentToken;
    size_t i = 0;

    while (i < input.length())
    {
        if (isspace(input[i]))
        {
            // Skip white spaces
            ++i;
            continue;
        }

        // Handle digits (numbers)
        if (isdigit(input[i]))
        {
            currentToken = "";
            while (i < input.length() && isdigit(input[i]))
            {
                currentToken += input[i];
                ++i;
            }
            TokenInfo token;
            token.type = NUMBER;
            token.value = currentToken;
            tokens.push_back(token);
            continue;
        }

        // Handle alphabets (identifiers or keywords)
        if (isalpha(input[i]))
        {
            currentToken = "";
            while (i < input.length() && (isalnum(input[i]) || input[i] == '_'))
            {
                currentToken += input[i];
                ++i;
            }
            TokenInfo token;
            if (isKeyword(currentToken))
            {
                token.type = KEYWORD;
            }
            else
            {
                token.type = IDENTIFIER;
            }
            token.value = currentToken;
            tokens.push_back(token);
            continue;
        }

        // Handle operators
        // Handle operators
        if (input[i] == '+' || input[i] == '-' || input[i] == '*' || input[i] == '/' || input[i] == '=')
        {
            currentToken = input[i];
            TokenInfo token;
            token.type = OPERATOR;
            token.value = currentToken;
            tokens.push_back(token);
            ++i;
            continue;
        }

        // Handle invalid characters
        currentToken = string(1, input[i]);
        TokenInfo token;
        token.type = INVALID;
        token.value = currentToken;
        tokens.push_back(token);
        ++i;
    }

    TokenInfo token;
    token.type = END_OF_FILE;
    token.value = "";
    tokens.push_back(token);
    return tokens;
}

// Print tokens
void printTokens(const vector<TokenInfo> &tokens)
{
    for (int i = 0; i < tokens.size(); ++i)
    {
        string tokenType;
        switch (tokens[i].type)
        {
        case KEYWORD:
            tokenType = "Keyword";
            break;
        case IDENTIFIER:
            tokenType = "Identifier";
            break;
        case NUMBER:
            tokenType = "Number";
            break;
        case OPERATOR:
            tokenType = "Operator";
            break;
        case END_OF_FILE:
            tokenType = "End of File";
            break;
        case INVALID:
            tokenType = "Invalid";
            break;
        }
        cout << tokenType << ": " << tokens[i].value << endl;
    }
}

int main()
{
    string input;
    cout << "Enter a string to tokenize: ";
    getline(cin, input);

    vector<TokenInfo> tokens = lexicalAnalyzer(input);
    printTokens(tokens);

    return 0;
}