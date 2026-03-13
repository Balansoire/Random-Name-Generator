package com.example.name_generator_api.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.example.name_generator_api.model.NameList;
import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.ListOfEmptyException;

public class SectionalStrategy extends NameGenerationStrategy {
	public SectionalStrategy(int sectionSize) {
		super(
				"sectional",
				"A strategy to create word with tokens generated out of parts of the words of the list"
		);
		this.sectionSize = sectionSize;
	}
	
	public final String name = "sectional";
	public final String description = "A strategy to create word with tokens generated out of parts of the words of the list";
	private int sectionSize = 3;
	private ArrayList<String> currentList;
	private ArrayList<Token> currentFirstTokenList;
	private ArrayList<Token> currentTokenList;
	
	public int getSectionSize() {
		return this.sectionSize;
	}

	@Override
	public String generateName(NameList list) throws EmptyListException, ListOfEmptyException {
		if (list.size() == 0) throw new EmptyListException();
		Boolean emptyStringInList = true;
		while (emptyStringInList) {
			emptyStringInList = list.remove("");
		}
		if (list.size() == 0) throw new ListOfEmptyException();
			
		//check if token list is generated
		if (this.currentTokenList == null || this.currentFirstTokenList == null || (this.currentList == null || this.currentList != list)) createTokenLists(list);
		
		//generate word length
		double avgLetters = 0;
		double stdDevLetters = 0;
		for (String word : list) {
			avgLetters += word.length();
		}
		avgLetters = (long) avgLetters / (long) list.size();
		for (String word : list) {
			stdDevLetters += Math.pow((long) word.length() - (long) avgLetters, 2);
		}
		stdDevLetters = Math.sqrt(stdDevLetters / list.size());
		Random random = new Random();
		int wordLength = (int) Math.round(random.nextGaussian(avgLetters, stdDevLetters));
		wordLength = Math.max(wordLength, sectionSize);

		//chose the first token of the generated word
		String finalWord = selectFromTokenList(this.currentFirstTokenList);

		//choose the next letters
		for (int i = sectionSize + 1; i <= wordLength; i++) {
			ArrayList<Token> charOptions = new ArrayList<>();
			String tokenStart = finalWord.substring(i - sectionSize);
			for (Token t : this.currentTokenList) if (t.getToken().startsWith(tokenStart)) {
				incrementTokenList(t.getToken(), charOptions);
			}
			String selectedToken = selectFromTokenList(charOptions);
			if (selectedToken == "") break;
			finalWord += selectedToken.charAt(sectionSize - 1);
		}
		
		return finalWord;
	}

	private void createTokenLists(NameList list) {
		ArrayList<Token> firstTokenList = new ArrayList<>();
		ArrayList<Token> tokenList = new ArrayList<>();
			
		//slice the words of the list into tokens
		for (String word : list) {
			if (word.length() < sectionSize) continue;
			
			for (int i = 0; i <= word.length() - sectionSize; i++) {
				String token = word.substring(i, i+sectionSize);
				incrementTokenList(token, tokenList);
				if (i == 0) incrementTokenList(token, firstTokenList);
			}
		}
		if (tokenList.size() == 0) {
			this.currentList = list;
			this.currentFirstTokenList = firstTokenList;
			this.currentTokenList = tokenList;
			return;
		}
		
		//sort the list by appearances
		Collections.sort(firstTokenList);
		Collections.sort(tokenList);
		this.currentList = list;
		this.currentFirstTokenList = firstTokenList;
		this.currentTokenList = tokenList;
		return;
		
	}

	public void incrementTokenList(String token, ArrayList<Token> tokenList) {
		boolean missing = true;
		for (Token k : tokenList) {
			if (token.equals(k.getToken())) {
				missing = false;
				k.addAppearence();
				break;
			}
		}
		if (missing) tokenList.add(new Token(token));
	}
	
	public String selectFromTokenList(ArrayList<Token> tokenList) {
		if (tokenList == null || tokenList.size() == 0) return "";
		
		Random random = new Random();
		int totalTokens = 0;
		for (Token t : tokenList) {
			totalTokens += t.getAppearences();
		}
		boolean selected = false;
		int i = 0;
		while (selected == false) {
			double rand = random.nextDouble();
			double ratio = (double)  tokenList.get(i).getAppearences() / totalTokens;
			if (ratio >= rand) {
				selected = true;
			} else {
				i += 1;
				if (i == tokenList.size()) i = 0;
			}
		}
		Token selectedToken = tokenList.get(i);
		return selectedToken.getToken();
	}
	
	public String toString() {
		return "NamegenerationStrategy: " + name + " section size: " + sectionSize;
	}
	
	private class Token implements Comparable<Token>{
		public Token(String token) {
			this.token = token;
			this.appearances = 1;
		}
		
		private String token;
		private int appearances;
		
		public String getToken() {
			return token;
		}
		
		public int getAppearences() {
			return this.appearances;
		}
		
		public void addAppearence() {
			this.appearances += 1;
		}
		
		@Override
		public int compareTo(Token token) {
			if (appearances < token.appearances) return -1;
			if (appearances > token.appearances) return 1;
			return 0;
		}
		
		public String toString() {
			return "[Token: " + this.token + ", " + this.appearances + "]";
		}
	}

}
