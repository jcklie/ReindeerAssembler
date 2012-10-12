package com.mrklie.assembler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.mrklie.assembler.util.PathValidator;

/**
 * Does the job.
 * 
 * @author Jan-Christoph Klie
 */
public class Assembler {
	
	@Parameter(names = {"-i", "--input"}, description = "Specifies the path to input folder (there lays the files to treat).", validateWith = PathValidator.class, required = true)
	private String inputFolder;
	
	@Parameter(names = {"-o", "--output"}, description = "Specifies the path to the output folder (if a save option has been chosen, files will be saved there) .", validateWith = PathValidator.class, required = true)
	private String outputFolder;
	
	@Parameter(names = "--help", help = true)
	private boolean help;
	
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
	
	private void proceed() throws IOException {
		Path pathIn = Paths.get(inputFolder);
		Path pathOut = Paths.get(outputFolder);
		
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

	public static void main(String[] args) throws IOException {		
		Assembler assembler = new Assembler();
		
		try {
			JCommander jc = new JCommander(assembler, args);

			if (assembler.help) {
				jc.usage();
			} else {
				assembler.proceed();
			}
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
		}		
	}

}
