package com.example.name_generator_api.exception;

public class BadArgsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadArgsException(String[][] args, String[][] expectedArgs) {
		super("Bad args in function. Got: " + args + "Expected: " + expectedArgs);
	}
}
