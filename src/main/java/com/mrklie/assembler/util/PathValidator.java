package com.mrklie.assembler.util;

import java.io.File;
import java.io.IOException;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class PathValidator implements IParameterValidator {

	@Override
	public void validate(String name, String value) throws ParameterException {
		File f = new File(value);

		try {
			f.getCanonicalPath();
		} catch (IOException e) {
			throw new ParameterException("Parameter " + name + " should be a valid file location! Found: " + value);
		}
	}

}