#include <iostream>
#include <cctype>
#include <string>
#include <vector>

using namespace std;
enum Token{
    KEYWORD,
    IDENTIFIER,
    NUMBER,
    OPERATOR,
};
struct Tokeninfo{
    Token type;
    string value;
};

bool isKeyword(const string &str){
    vector<string> keywords;
    keywords.push_back("if");
    keywords.push_back("else");
    keywords.push_back("while");
    keywords.push_back("for");
    keywords.push_back("int");
    keywords.push_back("float");

    for(int i=0;i<keywords.size();++i){
        if(str==keywords[i]){
            return true;
        }
    }
    return false;
}

vector<Tokeninfo> lexanal(const string &input){
    vector<Tokeninfo> tokens;
    string currentToken;
    size_t i=0;

    while(i<input.length()){
        if(isspace(input[i])){
            ++i;
            continue;
        }
        if(isdigit(input[i])){
            currentToken="";
            while(i<input.length() && isdigit(input[i])){
                currentToken+=input[i];
                ++i;
            }
            Tokeninfo token;
            token.type=NUMBER;
            token.value=currentToken;
            tokens.push_back(token);
            continue;
        }
    }
}
