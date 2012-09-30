'''
Created on Sep 29, 2012

@author: Jan-Christoph Klie
'''

import ply.lex as lex 

tokens = [
    "EQUAL",
    "SEMICOLON",
    "PLUS",
    "MINUS",
    "CONSTANT",
    "LABEL",
    "SYMBOL",
    "JUMP",
    "REGISTER",
    "NUMBER"
]

"""
    Tokens
"""

t_EQUAL = r'='
t_SEMICOLON = r';'
t_PLUS = r'\+'
t_MINUS = r'-'
t_JUMP = r'NULL|JGT|JEQ|JGE|JLT|JNE|JLE|JMP'
t_REGISTER = 'A|D|M'
t_NUMBER = '[0-9]+'

def t_LABEL(t):
    r'\([a-zA-Z_\.$:][a-zA-Z_\.$:0-9]*\)'
    t.value = t.value[1:-1]
    return t

def t_CONSTANT(t):
    r'@[0-9]+'
    t.value = t.value.lstrip('@')
    return t

def t_SYMBOL(t):
    r'@[a-zA-Z_\.$:][a-zA-Z_\.$:0-9]*'
    t.value = t.value.lstrip('@')
    return t

def t_comment(t):
    r'\/\/.+'

# Ignored characters 

t_ignore = ' \t\v\r' 

def t_newline(t): 
    r'\n+' 
    t.lexer.lineno += t.value.count("\n") 
    
def t_error(t):
    print("Illegal character '%s'" % t.value[0]) 
    t.lexer.skip(1)
    
if __name__ == '__main__':
    with open('../test/max/Max.asm', 'r') as f:
        s = f.read()
    lexer = lex.lex()
    lexer.input(s)
    for tok in iter(lexer.token, None):
        print repr(tok.type), repr(tok.value)
