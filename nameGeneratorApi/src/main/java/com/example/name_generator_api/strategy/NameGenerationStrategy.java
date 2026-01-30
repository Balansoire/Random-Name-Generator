package com.example.name_generator_api.strategy;

import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.model.NameList;

public abstract class NameGenerationStrategy {
	protected NameGenerationStrategy(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public final String name;
	public final String description;
	public abstract String generateName(NameList list) throws EmptyListException;
	public String toString() {
		return "NamegenerationStrategy: " + name;
	}

}
