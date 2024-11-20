#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>
using namespace std;

// Define a Symbol structure
struct Symbol {
    string name;  // Name of the symbol (e.g., variable or function)
    string type;  // Data type of the symbol (e.g., int, float)
    string scope; // Scope of the symbol (e.g., local, global)
    int memoryLocation; // Memory location (simulated for simplicity)

    // Constructor to initialize a symbol
    Symbol(string n, string t, string s, int m) 
        : name(n), type(t), scope(s), memoryLocation(m) {}
};

// Symbol Table class to store and manage symbols
class SymbolTable {
private:
    vector<Symbol> symbols;  // List to store the symbols
    unordered_map<string, int> symbolMap; // A map for quick look-up (name -> index)

public:
    // Function to insert a new symbol into the table
    void insertSymbol(const string& name, const string& type, const string& scope) {
        // Generate a memory location (simple simulation using table size)
        int memoryLocation = symbols.size();

        // Check if the symbol already exists in the symbol table
        if (symbolMap.find(name) != symbolMap.end()) {
            cout << "Error: Symbol " << name << " already exists!" << endl;
            return;
        }

        // Create a new symbol
        Symbol newSymbol(name, type, scope, memoryLocation);
        
        // Add the new symbol to the symbol table
        symbols.push_back(newSymbol);
        symbolMap[name] = symbols.size() - 1;  // Map the symbol's name to its index
    }

    // Function to look up a symbol by its name
    bool lookupSymbol(const string& name) {
        if (symbolMap.find(name) != symbolMap.end()) {
            int index = symbolMap[name];
            cout << "Symbol Found: " << symbols[index].name << ", Type: " 
                 << symbols[index].type << ", Scope: " 
                 << symbols[index].scope << ", Memory Location: " 
                 << symbols[index].memoryLocation << endl;
            return true;
        }
        return false;
    }

    // Function to display all symbols in the table
    void displaySymbols() {
        if (symbols.empty()) {
            cout << "No symbols in the table." << endl;
            return;
        }
        
        cout << "Symbol Table: " << endl;
        
        // Replace range-based for loop with traditional for loop
        for (size_t i = 0; i < symbols.size(); ++i) {
            const Symbol& symbol = symbols[i];
            cout << "Name: " << symbol.name << ", Type: " << symbol.type
                 << ", Scope: " << symbol.scope << ", Memory Location: " 
                 << symbol.memoryLocation << endl;
        }
    }
};

// Main function to demonstrate symbol table usage
int main() {
    SymbolTable table;

    // Insert some symbols into the symbol table
    table.insertSymbol("x", "int", "local");
    table.insertSymbol("y", "float", "local");
    table.insertSymbol("z", "int", "global");

    // Lookup a symbol
    table.lookupSymbol("x");
    table.lookupSymbol("z");

    // Display all symbols
    table.displaySymbols();

    // Try inserting a symbol with an existing name
    table.insertSymbol("x", "float", "global");

    return 0;
}