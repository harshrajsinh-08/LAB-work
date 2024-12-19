# Define the Lex and C output file names
LEX_FILE = run.l
C_FILE = lex.yy.c
EXEC = word_counter

# Lex and GCC commands
LEX = lex
CC = gcc

# Default target to run everything
all: $(EXEC)

# Rule to generate the C file from the Lex file
$(C_FILE): $(LEX_FILE)
	$(LEX) $(LEX_FILE)

# Rule to compile the C code and generate the executable
$(EXEC): $(C_FILE)
	$(CC) $(C_FILE) -o $(EXEC)

# Clean up generated files
clean:
	rm -f $(C_FILE) $(EXEC) *~ lex.yy.c

# Run the program
run: $(EXEC)
	./$(EXEC)

.PHONY: all clean run