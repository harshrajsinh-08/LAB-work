# Define the compiler and flags
CC = gcc
CFLAGS = -Wall -ll

# Define file names
LEX_FILE = expr.l
BISON_FILE = expr.y
BISON_HEADER = expr.tab.h
BISON_SOURCE = expr.tab.c
LEX_SOURCE = lex.yy.c
OUTPUT = infix_converter

# Default rule
all: $(OUTPUT)

# Rule to build the final executable
$(OUTPUT): $(BISON_SOURCE) $(LEX_SOURCE)
	$(CC) $(BISON_SOURCE) $(LEX_SOURCE) -o $(OUTPUT) $(CFLAGS)

# Rule to generate Bison output files
$(BISON_SOURCE) $(BISON_HEADER): $(BISON_FILE)
	bison -d $(BISON_FILE)

# Rule to generate Lex output file
$(LEX_SOURCE): $(LEX_FILE) $(BISON_HEADER)
	flex $(LEX_FILE)

# Clean up generated files
clean:
	rm -f $(BISON_SOURCE) $(BISON_HEADER) $(LEX_SOURCE) $(OUTPUT)

# Run the program
run: $(OUTPUT)
	./$(OUTPUT)
