package com.mrklie.assembler;

import java.util.HashMap;
import java.util.Map;


/**
 * Translates Hack assembly language mnemonics into binary codes.
 * 
 * @author Jan-Christoph Klie
 */
public class Code {
	
	private static Map<String, String> dest;
	private static Map<String, String> comp;
	private static Map<String, String> jump;
	
	static {		
		dest = new HashMap<String, String>();
		comp = new HashMap<String, String>();
		jump = new HashMap<String, String>();
		
		/*
		 * (when a=0)
		 */
		comp.put("0", "0101010");
		comp.put("1", "0111111");
		comp.put("-1", "0111010");
		comp.put("D", "0001100");
		comp.put("A", "0110000");
		comp.put("!D", "0001101");
		comp.put("!A", "0110001");
		comp.put("-D", "0001111");
		comp.put("-A", "0110011");
		comp.put("D+1", "0011111");
		comp.put("A+1", "0110111");
		comp.put("D-1", "0001110");
		comp.put("A-1", "0110010");
		comp.put("D+A", "0000010");
		comp.put("D-A", "0010011");
		comp.put("A-D", "0000111");
		comp.put("D&A", "0000000");
		comp.put("D|A", "0010101");	
		
		/*
		 * (when a=1)
		 */		
		comp.put("M", "1110000");
		comp.put("!M", "1110001");
		comp.put("-M", "1110011");
		comp.put("M+1", "1110111");
		comp.put("M-1", "1110010");
		comp.put("D+M", "1000010");
		comp.put("D-M", "1010011");
		comp.put("M-D", "1000111");
		comp.put("D&M", "1000000");
		comp.put("D|M", "1010101");	
		
		/*
		 * Dest
		 */
		dest.put("null", "000");
		dest.put("M", "001");
		dest.put("D", "010");
		dest.put("MD", "011");
		dest.put("A", "100");
		dest.put("AM", "101");
		dest.put("AD", "110");
		dest.put("AMD", "111");
		
		/*
		 * Jmp
		 */		
		jump.put("null", "000");
		jump.put("JGT", "001");
		jump.put("JEQ", "010");
		jump.put("JGE", "011");
		jump.put("JLT", "100");
		jump.put("JNE", "101");
		jump.put("JLE", "110");
		jump.put("JMP", "111");		
	}

	/**
	 * Returns the binary code of the dest mnemonic.
	 * @return
	 */
	public static String dest(String s) {
		if( !dest.containsKey(s)) {
			return "000";
		}
		return dest.get(s);
	}

	/**
	 * Returns the binary code of the comp mnemonic.
	 * @return
	 */
	public static String comp(String s) {
		if( !comp.containsKey(s)) {
			return "0000000";
		}
		return comp.get(s);
	}

	/**
	 * Returns the binary code of the jump mnemonic.
	 * @return
	 */
	public static String jump(String s) {
		if( !jump.containsKey(s)) {
			return "000";
		}
		return jump.get(s);
	}

}
