package com.example.name_generator_api.exception;

public class NotImplementedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotImplementedException(String name) {
		super("Name: " + name + " not Implemented");
	}
}
