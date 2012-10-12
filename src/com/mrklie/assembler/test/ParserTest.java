package com.mrklie.assembler.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mrklie.assembler.Parser;
import com.mrklie.assembler.Parser.CommandType;

public class ParserTest extends TestCase {
	
	private Parser parser;
	private static String asmAdd;
	private static String asmMax;
	
	private static String init(String fileName) throws IOException {
		Path file = FileSystems.getDefault().getPath("asm" + System.getProperty("file.separator") + fileName);
		byte[] fileArray;
		
		fileArray = Files.readAllBytes(file);

		return new String(fileArray);
	}

	@Before
	public void setUp() throws Exception {
		asmAdd = init("Add.asm");
		asmMax = init("Max.asm");
	}

	@After
	public void tearDown() throws Exception {
		parser = null;
	}

	@Test(expected = NoSuchElementException.class)  
	public final void testAdvance() {		
		parser = new Parser(asmAdd);
		
		parser.advance();
		assertEquals("@2", parser.getCommand());
		
		parser.advance();
		assertEquals("D=A", parser.getCommand());
		
		parser.advance();
		assertEquals("@3", parser.getCommand());
		
		parser.advance();
		assertEquals("D=D+A", parser.getCommand());
		
		parser.advance();
		assertEquals("@0", parser.getCommand());
		
		parser.advance();
		assertEquals("M=D", parser.getCommand());		
		
		assertFalse(parser.hasMoreCommands( ));
	}

	@Test
	public final void testHasMoreCommands() {
		parser = new Parser(asmAdd);
		
		assertTrue(parser.hasMoreCommands( ));
		parser.advance();
		assertTrue(parser.hasMoreCommands( ));
		
		parser.advance();
		assertTrue(parser.hasMoreCommands( ));
		
		parser.advance();
		assertTrue(parser.hasMoreCommands( ));
		
		parser.advance();
		assertTrue(parser.hasMoreCommands( ));
		
		parser.advance();
		assertTrue(parser.hasMoreCommands( ));
		
		parser.advance();		
		assertFalse(parser.hasMoreCommands( ));
	}

	@Test
	public final void testCommandType() {
		parser = new Parser(asmAdd);
		
		parser.advance();
		assertEquals(CommandType.A_COMMAND, Parser.commandType(parser.getCommand()));
		
		parser.advance();
		assertEquals(CommandType.C_COMMAND, Parser.commandType(parser.getCommand()));
		
		parser.advance();
		assertEquals(CommandType.A_COMMAND, Parser.commandType(parser.getCommand()));
		
		parser.advance();
		assertEquals(CommandType.C_COMMAND, Parser.commandType(parser.getCommand()));
		
		parser.advance();
		assertEquals(CommandType.A_COMMAND, Parser.commandType(parser.getCommand()));
		
		parser.advance();
		assertEquals(CommandType.C_COMMAND, Parser.commandType(parser.getCommand()));
	}

	@Test
	public final void testSymbol() {
		parser = new Parser(asmAdd);		
		parser.advance();
		assertEquals("2", Parser.symbol(parser.getCommand()));		
		parser.advance();
		parser.advance();
		assertEquals("3", Parser.symbol(parser.getCommand()));
		parser.advance();
		parser.advance();
		assertEquals("0", Parser.symbol(parser.getCommand()));		
	}

	@Test
	public final void testDest() {
		parser = new Parser(asmAdd);
		parser.advance();
		parser.advance();
		assertEquals("D", Parser.dest(parser.getCommand()) );		
		parser.advance();		
		parser.advance();
		assertEquals("D", Parser.dest(parser.getCommand()) );		
		parser.advance();		
		parser.advance();
		assertEquals("M", Parser.dest(parser.getCommand()) );
	}

	@Test
	public final void testComp() {
		parser = new Parser(asmMax);
		parser.advance();
		parser.advance();
		assertEquals("M", Parser.comp(parser.getCommand()) );		
		parser.advance();
		parser.advance();
		assertEquals("D-M", Parser.comp(parser.getCommand()) );		
		parser.advance();		
		parser.advance();
		assertEquals("D", Parser.comp(parser.getCommand()) );		
		parser.advance();		
		parser.advance();
		assertEquals("M", Parser.comp(parser.getCommand()) );
		parser.advance();		
		parser.advance();
		assertEquals("0", Parser.comp(parser.getCommand()) );
		parser.advance();		
		parser.advance();
		assertEquals("M", Parser.comp(parser.getCommand()) );
		parser.advance();		
		parser.advance();
		assertEquals("D", Parser.comp(parser.getCommand()) );
		parser.advance();		
		parser.advance();
		assertEquals("0", Parser.comp(parser.getCommand()) );
	}

	@Test
	public final void testJump() {
		parser = new Parser(asmMax);
		parser.advance();
		parser.advance();
		parser.advance();
		parser.advance();
		parser.advance();		
		parser.advance();
		assertEquals("JGT", Parser.jump(parser.getCommand()) );
		parser.advance();		
		parser.advance();
		parser.advance();		
		parser.advance();
		assertEquals("JMP", Parser.jump(parser.getCommand()) );
		parser.advance();		
		parser.advance();
		parser.advance();		
		parser.advance();
		parser.advance();		
		parser.advance();
	}

}
