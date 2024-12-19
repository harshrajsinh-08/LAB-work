%{
#include <stdio.h>

int char_count = 0;    // Total characters
int word_count = 0;    // Total words
int line_count = 0;    // Total lines
int space_count = 0;   // Total whitespace characters
%}

%%

[a-zA-Z0-9_]+ { 
    char_count += yyleng;  // Add length of the word
    word_count++;          // Increment word count
}

[\n] {
    line_count++;     // Count newlines
    char_count++;     // Newline is also a character
}

[ \t] {
    char_count++;     // Count spaces and tabs
    space_count++;    // Track whitespace separately
}

. {
    char_count++;     // Count any other characters
}

%%

int main() {
    printf("Enter text (press Ctrl+D when done):\n");
    yylex();
    
    // Print statistics
    printf("\nText Statistics:\n");
    printf("----------------\n");
    printf("Characters (including whitespace): %d\n", char_count);
    printf("Characters (excluding whitespace): %d\n", char_count - space_count - line_count);
    printf("Words: %d\n", word_count);
    printf("Lines: %d\n", line_count);
    printf("Whitespace characters: %d\n", space_count + line_count);
    
    return 0;
}

int yywrap() {
    return 1;
}