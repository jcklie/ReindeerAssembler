package com.mrklie.assembler;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps a correspondence between symbolic labels and numeric addresses.
 * 
 * @author Jan-Christoph Klie
 */
public class SymbolTable {
	
	private Map<String, Integer> map;
	private int address;
	
	public SymbolTable() {
		address = 16;
		map = new HashMap<String, Integer>();
		addDefaults();
	}
	
	private void addDefaults() {
		/*
		 * Predefined pointers:
		 * The symbols SP, LCL, ARG, THIS, and THAT are predefined 
		 * to refer to RAM addresses 0 to 4, respectively.
		 */
		
		map.put("@SP", 0);
		map.put("@LCL", 1);
		map.put("@ARG", 2);
		map.put("@THIS", 3);
		map.put("@THAT", 4);
		
		/*
		 * Virtual registers
		 * To simplify assembly programming, the symbols R0 to R15 are 
		 * predefined to refer to RAM addresses 0 to 15, respectively.
		 */
		
		map.put("@R0", 0);
		map.put("@R1", 1);
		map.put("@R2", 2);
		map.put("@R3", 3);
		map.put("@R4", 4);
		map.put("@R5", 5);
		map.put("@R6", 6);
		map.put("@R7", 7);
		map.put("@R8", 8);
		map.put("@R9", 9);
		map.put("@R10", 10);
		map.put("@R11", 11);
		map.put("@R12", 12);
		map.put("@R13", 13);
		map.put("@R14", 14);
		map.put("@R15", 15);
		
		/*
		 * Predefined symbols
		 */
		map.put("@SCREEN", 16384);
		map.put("@KBD", 24576);
	}
	
	public void addEntry(String symbol) {
		map.put(symbol, address);
		address++;
	}
	
	public void addEntry(String symbol, int address) {
		map.put(symbol, address);
	}
	
	public boolean contains(String symbol) {
		return map.containsKey(symbol);
	}
	
	public Integer getAddress(String symbol) {
		return map.get(symbol);
	}
	
	@Override
	public String toString() {
		return map.toString();
	}

}
