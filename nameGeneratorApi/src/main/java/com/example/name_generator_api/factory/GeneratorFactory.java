package com.example.name_generator_api.factory;

import com.example.name_generator_api.exception.BadArgsException;
import com.example.name_generator_api.strategy.NameGenerationStrategy;

public interface GeneratorFactory {
	public NameGenerationStrategy generate(String[][] args) throws BadArgsException;
}
