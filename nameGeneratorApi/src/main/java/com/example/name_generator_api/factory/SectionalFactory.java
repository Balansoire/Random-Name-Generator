package com.example.name_generator_api.factory;

import com.example.name_generator_api.exception.BadArgsException;
import com.example.name_generator_api.strategy.SectionalStrategy;

public class SectionalFactory implements GeneratorFactory {
	
	private static SectionalFactory INSTANCE;
	
	private SectionalFactory() {}
	
	public static SectionalFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SectionalFactory();
		}
		return INSTANCE;
	}

	public SectionalStrategy generate(String[][] args) throws BadArgsException {
		String[][] expectedArgs = {{"sectionSize", "int"}};
		if (args == null || !args[0][0].equals("sectionSize") || !isInteger(args[0][1]) || Integer.parseInt(args[0][1]) <= 0) throw new BadArgsException(args, expectedArgs);
		int sectionSize = Integer.parseInt(args[0][1]);
		
		return new SectionalStrategy(sectionSize);
	}
	
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
