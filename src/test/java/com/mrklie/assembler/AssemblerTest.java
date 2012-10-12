package com.mrklie.assembler;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import junit.framework.TestCase;

public class AssemblerTest extends TestCase {
	
	private Parser p;
	
	private static String init(String fileName) throws IOException {
		Path file = FileSystems.getDefault().getPath("asm" + System.getProperty("file.separator") + fileName);
		byte[] fileArray;
		
		fileArray = Files.readAllBytes(file);

		return new String(fileArray);
	}
	
	public void tearDown() throws Exception {
		p = null;
	}

	public final void testAdd() throws IOException {
		String asm = init("Add.asm");
		String hack = init("Add.hack");
		assertEquals(hack, Assembler.exec(asm));
	}
	
	public final void testFill() throws IOException {
		String asm = init("Fill.asm");
		String hack = init("Fill.hack");
		assertEquals(hack, Assembler.exec(asm));
	}
	
	public final void testMax() throws IOException {
		String asm = init("Max.asm");
		String hack = init("Max.hack");
		assertEquals(hack, Assembler.exec(asm));
	}
	
	public final void testPong() throws IOException {
		String asm = init("Pong.asm");
		String hack = init("Pong.hack");
		assertEquals(hack, Assembler.exec(asm));
	}
	
	public final void testRect() throws IOException {
		String asm = init("Rect.asm");
		String hack = init("Rect.hack");
		assertEquals(hack, Assembler.exec(asm));
	}
}
