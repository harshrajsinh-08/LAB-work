all: compiler

compiler: parser.tab.c lex.yy.c
	gcc -Wall -Wno-unused-function -o compiler parser.tab.c lex.yy.c -ll

parser.tab.c: parser.y
	bison -d parser.y

lex.yy.c: lexer.l
	flex lexer.l

run: compiler
	./compiler

clean:
	rm -f compiler parser.tab.c parser.tab.h lex.yy.c