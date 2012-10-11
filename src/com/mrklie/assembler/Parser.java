package com.mrklie.assembler;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


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
	
	public enum CommandType {
		A_COMMAND,
		C_COMMAND,
		L_COMMAND
	}
	
	public Parser(String text) {
		sbt = new SymbolTable();
		parse(text);
	}
	
	public void advance() {
		if( hasMoreCommands() ) {
			command = data.remove(0);
		} else {
			throw new NoSuchElementException();
		}		
	}
	
	private void parse(String text) {	
		data = new LinkedList<String>();
		Collections.addAll(data, format(text));
		data.removeAll(Arrays.asList("", null));

		prepareLabels();
		prepareSymbols();		
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
					sbt.addEntry("@"+s.substring(1, s.length() - 1), i);
					toRemove.add(s);
				}				
			} else {
				i++;
			}		
		}
		data.removeAll(toRemove);
	}
	
	public String getCommand() {
		return command;
	}
	
	public boolean hasMoreCommands() {
		return !data.isEmpty();
	}
	
	// @TODO Do match better
	public CommandType commandType(String s) {
		if( s.startsWith("@")) {
			return CommandType.A_COMMAND;
		} else if( s.contains("=") ) {
			return CommandType.C_COMMAND;
		} else if( s.matches("\\(.+\\)")) {
			return CommandType.L_COMMAND;
		} else {
			throw new InvalidParameterException();
		}		
	}
	
	public String symbol(String s) {
		if( commandType(s).equals(CommandType.L_COMMAND) ) {
			return s.substring(1, s.length() - 1);
		} else if( commandType(s).equals(CommandType.A_COMMAND)  ) {
			return s.substring(1, s.length());
		}		
		else {
			throw new InvalidParameterException();
		}
	}
	
	public String dest(String s) {
		if( commandType(s).equals(CommandType.C_COMMAND) ) {
			return s.substring(1, s.length() - 1);
		} else {
			throw new InvalidParameterException();
		}
	}
	
	public String comp(String s) {
		return null;
	}
	
	public String jump(String s) {
		return null;
	}

	@Override
	public String toString() {
		return "Parser [data=" + data + ", command=" + command + ", sbt=" + sbt + "]";
	}
}
