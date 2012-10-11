package com.mrklie.assembler;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Encapsulates access to the input code. Reads an assembly language command,
 * parses it, and provides convenient access to the command’s components (fields
 * and symbols). In addition, removes all white space and comments.
 * 
 * @author Jan-Christoph Klie
 */
public class Parser {
	
	private List<String> data;
	private String command;
	private SymbolTable sbt;
	
	private enum CommandType {
		A_COMMAND,
		C_COMMAND,
		L_COMMAND
	}
	
	public Parser(String text) {
		sbt = new SymbolTable();
		parse(text);
	}
	
	public void advance() {
		
	}
	
	private void parse(String text) {	
		data = new LinkedList<String>();
		Collections.addAll(data, format(text));
		data.removeAll(Arrays.asList("", null));

		prepareLabels();
		prepareSymbols();
		
		int line = 0;
		for( String s : data) {
			System.out.println(++line + "\t" + s);
		}
		
	}
	
	private String[] format(String text) {		
		text = text.replaceAll("//.*", "");
		return text.split("\\s+");
	}
	
	private void prepareSymbols() {
		for(int i = 0 ; i < data.size(); i++) {
			String s = data.get(i);
			if( s.matches("@[a-zA-Z_\\.$:][a-zA-Z_\\.$:0-9]*")) {
				if( !sbt.contains(s)) {
					sbt.addEntry(s);
				}				
				data.set(i, "@" + sbt.getAddress(s));			
			} else if (s.matches("r'\\([a-zA-Z_\\.$:][a-zA-Z_\\.$:0-9]*\\)'")) {
				data.set(i, "@" + sbt.getAddress(s));	
			}
		}
	}
	
	private void prepareLabels() {
		int i = 0;
		List<String> toRemove = new LinkedList<String>();
		for(String s : data) {			
			if (s.matches("\\([a-zA-Z_\\.$:][a-zA-Z_\\.$:0-9]*\\)")) {
				if( !sbt.contains(s)) {
					sbt.addEntry("@"+s.substring(1, s.length()), i);
					System.out.println("@"+s.substring(1, s.length() - 1) + "\t"+ i);
					toRemove.add(s);
				}				
			} else {
				i++;
			}		
		}
		data.removeAll(toRemove);
	}
	
	public boolean hasMoreCommands() {
		return true;
	}
	
	public CommandType commandType(String s) {
		switch(s) {
			
		}
		
		return null;		
	}
	
	public String symbol(String s) {
		return null;
	}
	
	public String dest(String s) {
		return null;
	}
	
	public String comp(String s) {
		return null;
	}
	
	public String jump(String s) {
		return null;
	}

}
