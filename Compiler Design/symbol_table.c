#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define max_ident_len 64
#define max_datatype_len 32

typedef struct symb
{
    char identifier[max_ident_len];
    char dataType[max_datatype_len];
    int scopelvl;
    int memaddr;
    struct symb *next;
} symb;

symb *symbTable = NULL;

symb *createsymb(const char *identifier, const char *dataType, int scopelvl, int memaddr)
{
    symb *newsymb = (symb *)malloc(sizeof(symb));
    strcpy(newsymb->identifier, identifier);
    strcpy(newsymb->dataType, dataType);
    newsymb->scopelvl = scopelvl;
    newsymb->memaddr = memaddr;
    newsymb->next = NULL;
    return newsymb;
}

void insertSymbol(const char *identifier, const char *dataType, int scopelvl, int memaddr)
{
    symb *newsymb = createsymb(identifier, dataType, scopelvl, memaddr);
    newsymb->next = symbTable;
    symbTable = newsymb;
}

symb *searchSymbol(const char *identifier)
{
    for (symb *current = symbTable; current != NULL; current = current->next)
    {
        if (strcmp(current->identifier, identifier) == 0)
        {
            return current;
        }
    }
    return NULL;
}

void display()
{
    printf("identifier\tData Type\tScope Level\tMemory Address\n");
    for (symb *current = symbTable; current != NULL; current = current->next)
    {
        printf("%s\t\t%s\t\t%d\t\t%d\n", current->identifier, current->dataType, current->scopelvl, current->memaddr);
    }
}

int main()
{
    insertSymbol("x", "int", 1, 1000);
    insertSymbol("y", "float", 1, 1004);
    insertSymbol("z", "char", 1, 1008);
    insertSymbol("a", "int", 2, 1012);
    insertSymbol("b", "float", 2, 1016);
    insertSymbol("main", "function", 0, 2000);

    display();

    printf("searching for symb 'b' ");
    printf("\n");
    symb *sym = searchSymbol("b");
    if (sym)
    {
        printf("\nsymb found: %s\ntype: %s\nscope: %d\naddress: %d", sym->identifier, sym->dataType, sym->scopelvl, sym->memaddr);
    }
    else
    {
        printf("\nsymb not found\n");
    }

    return 0;
}
