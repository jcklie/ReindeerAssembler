'''
Created on Sep 29, 2012

@author: Jan-Christoph Klie
'''

import ply.yacc as yacc
import ply.lex as lex 
import hack_tokens
import hack_grammar

class LabelError(Exception):
    """ Thrown if a label already exists in the labels dictionary. """
    def __init__(self, value):
        self.value = value
        
    def __str__(self):
        return repr(self.value)

class SymbolList():
    
    def __init__(self, tree):
        self.next_address = 16
        self.symbols = {}
        
        self.prepare(tree)
    
    def lookup(self, key):
        if  key in self.symbols:
            return self.symbols[key]
        else:
            self.symbols[key] = self.next_address
            self.next_address += 1

    def prepare(self, tree):
        line = 0
        for instr in tree:
            i = instr[0]
            if i == 'A_COMMAND' or i == 'C_COMMAND' or i == 'J_COMMAND':
                line += 1
            elif i == 'L_COMMAND':
                label = instr[1]
                if label not in self.symbols:
                    self.symbols[label] = line+1
                else:
                    raise LabelError( 'Label "{0}"already exists in this program!'.format(i) )
    
def interprete(tree, symbols):
    code = ""
    for instr in tree:
        if instr[0] == 'A_COMMAND':
            cmd = instr[1]
            val = symbols.lookup(cmd[1]) if cmd[0] == 'SYMBOL' else cmd[1]
            code = code + a_cmd(val) + '\n' 
        elif instr[0] == 'C_COMMAND':
            cmd = instr[1]
            
            
            dest = set(cmd[0])
           
            code = code + c_cmd(val) + '\n' 
            print(cmd)
        elif instr[0] == 'J_COMMAND':
            pass
        elif instr[0] == 'L_COMMAND':
            pass
        else:
            print("ERROR", instr[0])
    return code
            
def to_bin(d):
    return bin(d)[2:].zfill(16)
            
def a_cmd(c):
    return to_bin(int(c))

def c_cmd(c):
    return "0"

if __name__ == '__main__':
    with open('../test/max/Max.asm', 'r') as f:
        s = f.read()

    lexer = lex.lex(module=hack_tokens) 
    lexer.input(s)
    
    parser = yacc.yacc(module=hack_grammar) 
    parse_tree = parser.parse(s,lexer=lexer)
    
    symbols = SymbolList(parse_tree)
    
    code = interprete(parse_tree, symbols )
    print(code)