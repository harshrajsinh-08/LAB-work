#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX 100
typedef struct {
    char op;
    char arg1[5];
    char arg2[5];
    char result[5];
} Quadruple;

Quadruple quadruples[MAX];
char stack[MAX];
int top = -1;
int temp_count = 1;
int reg_count = 0;
int quad_index = 0;
void push(char c) {
    stack[++top] = c;
}
char pop() {
    return stack[top--];
}
int precedence(char c) {
    if (c == '*' || c == '/') return 2;
    if (c == '+' || c == '-') return 1;
    return 0;
}
int isOp(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/';
}
void Postfix(char infix[], char postfix[]) {
    int i = 0, k = 0;
    char c;
    while ((c = infix[i++]) != '\0') {
        if (isalnum(c)) {
            postfix[k++] = c;
        } else if (c == '(') {
            push(c);
        } else if (c == ')') {
            while (top != -1 && stack[top] != '(') {
                postfix[k++] = pop();
            }
            pop(); 
        } else if (isOp(c)) {
            while (top != -1 && precedence(stack[top]) >= precedence(c)) {
                postfix[k++] = pop();
            }
            push(c);
        }
    }
    while (top != -1) {
        postfix[k++] = pop();
    }
    postfix[k] = '\0';
}
void quad(char postfix[]) {
    char op1[MAX], op2[MAX];
    char temp[5];
    int i = 0;

    while (postfix[i] != '\0') {
        if (isalnum(postfix[i])) {
            char str[2] = {postfix[i], '\0'};
            push(postfix[i]);
        } else if (isOp(postfix[i])) {
            op2[0] = pop();
            op1[0] = pop();
            op2[1] = op1[1] = '\0';
            sprintf(temp, "t%d", temp_count++);
            Quadruple q;
            q.op = postfix[i];
            strcpy(q.arg1, op1);
            strcpy(q.arg2, op2);
            strcpy(q.result, temp);
            quadruples[quad_index++] = q;
            push(temp[0]);
        }
        i++;
    }
    printf("\nQuadruple Table:\n");
    printf("Op Arg1 Arg2 Result\n");
    for (i = 0; i < quad_index; i++) {
        printf("%c   %s   %s   %s\n", quadruples[i].op, quadruples[i].arg1, quadruples[i].arg2, quadruples[i].result);
    }
}
void machinecode() {
    int reg = 0;
    printf("\nMachine Code:\n");

    for (int i = 0; i < quad_index; i++) {
        Quadruple q = quadruples[i];

        printf("MOV R%d, %s\n", reg, q.arg1);
        if (q.op == '+')
            printf("ADD R%d, %s\n", reg, q.arg2);
        else if (q.op == '-')
            printf("SUB R%d, %s\n", reg, q.arg2);
        else if (q.op == '*')
            printf("MUL R%d, %s\n", reg, q.arg2);
        else if (q.op == '/')
            printf("DIV R%d, %s\n", reg, q.arg2);

        printf("MOV %s, R%d\n", q.result, reg);
        reg++;
    }
}

int main() {
    char infix[MAX], postfix[MAX];

    printf("Enter an infix expression: ");
    scanf("%s", infix);

    Postfix(infix, postfix);

    printf("Postfix expression: %s\n", postfix);

    quad(postfix); 
    machinecode();

    return 0;
}
