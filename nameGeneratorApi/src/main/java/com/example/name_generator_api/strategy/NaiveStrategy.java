package com.example.name_generator_api.strategy;

import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.ListOfEmptyException;
import com.example.name_generator_api.model.NameList;

public class NaiveStrategy extends NameGenerationStrategy {
	public NaiveStrategy() {
		super(
				"naive",
				"Nombre de lettres fixe (moy des mots), pas de relation entre les différentes lettres"
		);
	}
	
	public final String name = "naive";
	public String description = "Nombre de lettres fixe (moy des mots), pas de relation entre les différentes lettres";
	
	@Override
	public String generateName(NameList list) throws EmptyListException, ListOfEmptyException {
		if (list.size() == 0) throw new EmptyListException();
		Boolean emptyStringInList = true;
		while (emptyStringInList) {
			emptyStringInList = list.remove("");
		}
		if (list.size() == 0) throw new ListOfEmptyException();
		
		//set the length of the word
		int length = 0;
		for (int i=0; i < list.size(); i++) {
			length += list.get(i).length();
		}
		length = Math.ceilDiv(length, list.size());
		
		//create the word
		String word = "";
		int i=0;
		while (i < length) {
			String temp = list.get((int) Math.floor(Math.random()*list.size()));
			if (temp.length() >= length) {
				word += temp.charAt(i);
				i+=1;
			}
		}			
		return word;
	}
}
