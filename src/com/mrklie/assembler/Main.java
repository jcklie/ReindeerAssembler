package com.mrklie.assembler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Does the job.
 * 
 * @author Jan-Christoph Klie
 */
public class Main {
	
	private static Path path = Paths.get("/home/jan-christoph/git/ReindeerAssembler/asm/Rect.asm");

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
		
		String line = null;
		StringBuilder sb = new StringBuilder();

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		
		Parser p = new Parser(sb.toString());
		System.out.println(p.getCommand());
		
		
	}

}
