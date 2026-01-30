package com.example.name_generator_api.service;

import java.util.ArrayList;

import com.example.name_generator_api.exception.BadArgsException;
import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.NotImplementedException;
import com.example.name_generator_api.factory.SectionalFactory;
import com.example.name_generator_api.model.NameList;
import com.example.name_generator_api.strategy.NaiveStrategy;
import com.example.name_generator_api.strategy.NameGenerationStrategy;
import com.example.name_generator_api.strategy.SectionalStrategy;

public class NameGeneratorService {
	
	private NameGeneratorService() {
		this.instances = new ArrayList<>();
	}
	
	public static NameGeneratorService INSTANCE;
	
	public static NameGeneratorService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NameGeneratorService();
		}
		return INSTANCE;
	}

	private ArrayList<NameGenerationStrategy> instances;

	public String generateName(String strategyName, String[][] strategyArgs, NameList list) throws EmptyListException, NotImplementedException, BadArgsException {
		boolean exists = false;
		switch(strategyName) {
		case "naive":
			for (NameGenerationStrategy strategy : instances) {
				if (strategy.name.equals("naive")) {
					exists = true;
					return strategy.generateName(list);
				}
			}
			if (!exists) {
				NameGenerationStrategy instance = new NaiveStrategy();
				instances.add(instance);
				return instance.generateName(list);
			}
		case "sectional":
			for (NameGenerationStrategy strategy : instances) {
				if (
						strategy.name.equals("sectional") && 
						strategy.getClass().equals(SectionalStrategy.class) &&
						strategyArgs != null && 
						strategyArgs[0][0].equals("sectionSize") && 
						Integer.parseInt(strategyArgs[0][1]) == ((SectionalStrategy)strategy).getSectionSize()
				) {
					exists = true;
					return strategy.generateName(list);
				}
			}
			if (!exists) {
				SectionalFactory factory = SectionalFactory.getInstance();
				NameGenerationStrategy instance = factory.generate(strategyArgs);
				System.out.println("adding sectional instance: " + instance);
				instances.add(instance);
				return instance.generateName(list);
			}
		default:
			throw new NotImplementedException(strategyName);
		}
	}
}
