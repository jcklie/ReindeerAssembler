'''
Created on Sep 29, 2012

@author: Jan-Christoph Klie
'''

import ply.yacc as yacc
import ply.lex as lex 
import hack_tokens
from hack_tokens import tokens

start = 'hack'

def p_hack(p): 
    'hack : element hack'
    p[0] = [p[1]] + p[2]
        
def p_hack_empty(p):
    'hack : '
    p[0] = [ ]
    
"""
    Label
"""
def p_element_label(p):
    'element : LABEL'
    p[0] = ('L_COMMAND', p[1])
    
"""
    A-Instruction
"""
        
def p_element_ainstruction(p):
    'element : ainstruction'
    p[0] = ('A_COMMAND', p[1])
    
def p_ainstruction_symbol(p):
    'ainstruction : SYMBOL'
    p[0] = ('SYMBOL',p[1])
    
def p_ainstruction_constant(p):
    'ainstruction : CONSTANT'
    p[0] = ('CONSTANT',p[1])
    
"""
    C-Instruction
"""
    
def p_element_cinstruction(p):
    'element : cinstruction'
    p[0] = ('C_COMMAND', p[1])
    
def p_cinstruction_assignment(p):
    'cinstruction : assignment'
    p[0] = ('ASSIGNMENT', p[1])
    
def p_assignment(p):
    'assignment : destination EQUAL term'
    p[0] = (p[1], p[3])
    
def p_destination(p):
    'destination : REGISTER destination'
    p[0] = [p[1]] + p[2]
    
def p_destination_empty(p):
    'destination : '
    p[0] = [ ]
    
def p_term_plus(p):
    'term : REGISTER PLUS REGISTER'
    p[0] = ('TERM', p[1], 'PLUS', p[3])
    
def p_term_minus(p):
    'term : REGISTER MINUS REGISTER'
    p[0] = ('TERM', (p[1], 'MINUS', p[3]))
    
def p_term_lone(p):
    'term : REGISTER'
    p[0] = ('REGISTER', p[1])
    
"""
    Jump Instruction
"""

def p_element_jumpinstruction(p):
    'element : jumpinstruction'
    p[0] = ('J_COMMAND', p[1])
    
def p_jumpinstruction_register(p):
    'jumpinstruction : REGISTER SEMICOLON JUMP'
    p[0] = ('REGISTER', ( p[1], p[3]))
    
def p_jumpinstruction_constant(p):
    'jumpinstruction : NUMBER SEMICOLON JUMP'
    p[0] = ('NUMBER', ( p[1], p[3]))
    
"""
    Error
"""
    
def p_error(p):
    print "Syntax error in input!"
        
if __name__ == '__main__':
    with open('../test/max/Max.asm', 'r') as f:
        s = f.read()

    lexer = lex.lex(module=hack_tokens) 
    lexer.input(s)
    
    parser = yacc.yacc() 
    parse_tree = parser.parse(s,lexer=lexer) 
    for e in parse_tree:
        print(e)