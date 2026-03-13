package com.example.name_generator_api.exception;

public class ListOfEmptyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListOfEmptyException() {
		super("The list only contains empty elements");
	}
}
