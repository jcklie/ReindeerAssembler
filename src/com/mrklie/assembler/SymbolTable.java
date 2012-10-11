package com.mrklie.assembler;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	
	private Map<String, Integer> map;
	private int address;
	
	public SymbolTable() {
		address = 16;
		map = new HashMap<String, Integer>();
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

}
