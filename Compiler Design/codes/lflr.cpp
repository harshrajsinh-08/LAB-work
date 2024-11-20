#include <iostream>
#include <vector>
#include <string>
#include <map>

using namespace std;

// Function to eliminate left recursion
void eliminateLeftRecursion(map<string, vector<vector<string> > >& grammar) {
    map<string, vector<vector<string> > >::iterator it;
    for (it = grammar.begin(); it != grammar.end(); ++it) {
        string nonTerminal = it->first;
        vector<vector<string> >& productions = it->second;
        
        vector<vector<string> > directRecursion, noRecursion;

        // Separate productions with direct recursion and without recursion
        for (size_t i = 0; i < productions.size(); ++i) {
            vector<string> production = productions[i];
            if (production[0] == nonTerminal) {
                directRecursion.push_back(production);
            } else {
                noRecursion.push_back(production);
            }
        }

        // Process direct recursion
        for (size_t i = 0; i < directRecursion.size(); ++i) {
            vector<string>& prod = directRecursion[i];
            prod.erase(prod.begin());
        }

        // Create new non-terminal for recursive productions
        string newNonTerminal = nonTerminal + "'";
        vector<vector<string> > newProds;

        // Add productions for the new non-terminal
        for (size_t i = 0; i < noRecursion.size(); ++i) {
            vector<string> prod = noRecursion[i];
            prod.push_back(newNonTerminal);
            newProds.push_back(prod);
        }

        // Add productions for the new non-terminal
        for (size_t i = 0; i < directRecursion.size(); ++i) {
            vector<string>& prod = directRecursion[i];
            prod.push_back(newNonTerminal);
            newProds.push_back(prod);
        }

        // Add epsilon production for the new non-terminal
        vector<string> epsilon;
        epsilon.push_back("ε");
        newProds.push_back(epsilon);

        // Update grammar with the new productions
        grammar[nonTerminal] = noRecursion;
        grammar[newNonTerminal] = newProds;
    }
}

// Function to perform left factoring
void leftFactor(map<string, vector<vector<string> > >& grammar) {
    map<string, vector<vector<string> > >::iterator it;
    for (it = grammar.begin(); it != grammar.end(); ++it) {
        string nonTerminal = it->first;
        vector<vector<string> >& productions = it->second;

        map<string, vector<vector<string> > > factoredProductions;

        // Find common prefixes in productions
        for (size_t i = 0; i < productions.size(); ++i) {
            vector<string> prod = productions[i];
            string prefix = prod[0]; // Assuming we're factoring on the first symbol

            // Add the production to the corresponding prefix group
            factoredProductions[prefix].push_back(prod);
        }

        // Construct new productions
        map<string, vector<vector<string> > >::iterator factoredIt;
        for (factoredIt = factoredProductions.begin(); factoredIt != factoredProductions.end(); ++factoredIt) {
            vector<vector<string> >& factoredProds = factoredIt->second;
            vector<vector<string> > newFactoredProds;

            // Create new productions for factored rules
            for (size_t i = 0; i < factoredProds.size(); ++i) {
                vector<string> prod = factoredProds[i];
                string newNonTerminal = nonTerminal + "'";
                prod.push_back(newNonTerminal);
                newFactoredProds.push_back(prod);
            }

            // Add epsilon production for the new non-terminal
            vector<string> epsilon;
            epsilon.push_back("ε");
            newFactoredProds.push_back(epsilon);

            // Update grammar
            grammar[nonTerminal] = newFactoredProds;
        }
    }
}

// Function to display grammar
void displayGrammar(map<string, vector<vector<string> > >& grammar) {
    map<string, vector<vector<string> > >::iterator it;
    for (it = grammar.begin(); it != grammar.end(); ++it) {
        cout << it->first << " -> ";
        for (size_t i = 0; i < it->second.size(); ++i) {
            vector<string>& prod = it->second[i];
            for (size_t j = 0; j < prod.size(); ++j) {
                cout << prod[j] << " ";
            }
            cout << "| ";
        }
        cout << endl;
    }
}

int main() {
    // Example grammar with left recursion and left factoring
    map<string, vector<vector<string> > > grammar;
    vector<vector<string> > prodA;
    
    // Replace initializer list with explicit push_back calls
    vector<string> prodA1;
    prodA1.push_back("A");
    prodA1.push_back("α");
    prodA.push_back(prodA1);
    
    vector<string> prodA2;
    prodA2.push_back("β");
    prodA.push_back(prodA2);
    
    grammar["A"] = prodA;

    vector<vector<string> > prodB;
    
    vector<string> prodB1;
    prodB1.push_back("b");
    prodB.push_back(prodB1);
    
    grammar["B"] = prodB;

    cout << "Original Grammar:" << endl;
    displayGrammar(grammar);

    eliminateLeftRecursion(grammar);
    cout << "\nGrammar after Eliminating Left Recursion:" << endl;
    displayGrammar(grammar);

    leftFactor(grammar);
    cout << "\nGrammar after Left Factoring:" << endl;
    displayGrammar(grammar);

    return 0;
}