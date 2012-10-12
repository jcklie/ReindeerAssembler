package com.mrklie.assembler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

/**
 * Does the job.
 * 
 * @author Jan-Christoph Klie
 */
public class Assembler {
	
	private static String zfill(String s) {
		int i =  Integer.parseInt(s);
		return String.format("%15s", Integer.toBinaryString(i)).replace(' ', '0');
	}
	
	public static String exec(String input) {
		String command, comp, dest, jump;
		StringBuilder sb = new StringBuilder();
		Parser p = new Parser(input);		
		
		while( p.hasMoreCommands() ) {
			p.advance();
			command = p.getCommand();
			switch( Parser.commandType(command) ) {
			case A_COMMAND:
				sb.append( "0" + zfill( Parser.symbol(command)) + "\n");
				break;
			case C_COMMAND:			
			case J_COMMAND:
				comp = Code.comp( Parser.comp(command));
				dest = Code.dest( Parser.dest(command));
				jump = Code.jump(Parser.jump(command));
				sb.append( "111" + comp + dest + jump + "\n");
				break;
			case L_COMMAND:
				break;
			default:
				throw new InvalidParameterException(command);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		
		Path pathIn = Paths.get("/home/jan-christoph/git/ReindeerCompute/projects/06/pong/Pong.asm");
		Path pathOut = Paths.get("/home/jan-christoph/git/ReindeerCompute/projects/06/pong/Pong.hack");
		
		BufferedReader reader = Files.newBufferedReader(pathIn, Charset.defaultCharset());
		
		String line = null;
		StringBuilder sb = new StringBuilder();

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

		FileWriter fw = new FileWriter( pathOut.toFile());
		fw.write( exec(sb.toString()));
		fw.close();
	}

}
