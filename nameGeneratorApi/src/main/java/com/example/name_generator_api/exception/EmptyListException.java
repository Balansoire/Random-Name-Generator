package com.example.name_generator_api.exception;

public class EmptyListException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmptyListException() {
		super("Trying to generate a word from an empty list");
	}

}
