#ifndef EXPR_H
#define EXPR_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LEN 100

typedef struct {
    char postfix[MAX_LEN];
    char prefix[MAX_LEN];
} Result;

#endif // EXPR_H
