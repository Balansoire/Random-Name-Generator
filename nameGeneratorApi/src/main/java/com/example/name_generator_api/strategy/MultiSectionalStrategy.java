package com.example.name_generator_api.strategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.name_generator_api.exception.EmptyListException;
import com.example.name_generator_api.exception.ListOfEmptyException;
import com.example.name_generator_api.model.NameList;

public class MultiSectionalStrategy extends NameGenerationStrategy {
	public MultiSectionalStrategy() {
		super("naive", "Nombre de lettres fixe (moy des mots), pas de relation entre les différentes lettres");
		// TODO Auto-generated constructor stub
	}

	private NameList currentList;
	private ArrayList<ArrayList<Token>> currentTokenLists;
	private ArrayList<ArrayList<Token>> currentFirstTokenLists;
	
	@Override
	public String generateName(NameList list) throws EmptyListException, ListOfEmptyException {
		if (list.size() == 0) throw new EmptyListException();
		Boolean emptyStringInList = true;
		while (emptyStringInList) {
			emptyStringInList = list.remove("");
		}
		if (list.size() == 0) throw new ListOfEmptyException();
		
		// Initialize lists if they are not
		if (list != currentList || 
			this.currentTokenLists == null || 
			this.currentFirstTokenLists == null
		) createTokenLists(list);
		
		// Generate word size
		Random random = new Random();
		double avgLetters = 0;
		double stdDevLetters = 0;
		int firstTokenMaxLength = currentFirstTokenLists.size();
		for (String word : list) {
			avgLetters += word.length();
		}
		avgLetters = (long) avgLetters / (long) list.size();
		for (String word : list) {
			stdDevLetters += Math.pow((long) word.length() - (long) avgLetters, 2);
		}
		stdDevLetters = Math.sqrt(stdDevLetters / list.size());
		int wordLength = (int) Math.round(random.nextGaussian(avgLetters, stdDevLetters));
		wordLength = Math.max(wordLength, firstTokenMaxLength);
		
		// Choose first token
		ArrayList<Token> selectedFirstTokenList = currentFirstTokenLists.get(random.nextInt(firstTokenMaxLength));
		int tokenSum = 0;
		for (Token token: selectedFirstTokenList) tokenSum += token.getAppearences();
		int selectedTokenWeight = random.nextInt(tokenSum);
		String word = getRandomToken(selectedFirstTokenList, random).getToken();
		String currentWord = "";
		String previousWord;
		// Choose next tokens
		int tokenMaxLength = currentTokenLists.size();
		
		int maxAttempts = 1000;
		int attempts = 0;
		while (word.length() < wordLength && attempts < maxAttempts) {
			attempts += 1;
			int tokenLength = random.nextInt(2, tokenMaxLength + 1);
			
			previousWord = currentWord;
			currentWord = word;
			
			String tokenStart;
			if (word.length() < tokenLength) tokenStart = word;
			else tokenStart = word.substring(word.length() - tokenLength, word.length());
			
			ArrayList<Token> filteredTokens = (ArrayList<Token>) currentTokenLists.get(tokenLength - 1)
																 .stream()
																 .filter(token -> token.token.startsWith(tokenStart))
																 .collect(Collectors.toList());
			if (filteredTokens.isEmpty()) continue;
			Token selectedToken = getRandomToken(filteredTokens, random);
			
			int toAdd = selectedToken.token.length() - tokenStart.length();
			if (toAdd > 0) {
			    word += selectedToken.token.substring(tokenStart.length());
			}
		}
		return word;
	}
	
	public Token getRandomToken(ArrayList<Token> tokenList, Random random) {
		int total = 0;
		for (Token token: tokenList) {
			total += token.appearances;
		}

		int target = random.nextInt(Math.max(total,  1));
		int currentSum = 0;
		for (Token token: tokenList) {
			currentSum += token.appearances;
			if (currentSum >= target) {
				return token;
			}
		}
		return tokenList.getLast();
	}
	
	public void createTokenLists(NameList list) {
		int maxTokenSize = Integer.MAX_VALUE;
		for (String elt: list) {
			maxTokenSize = Math.min(elt.length(), maxTokenSize); // ensures there will not be 
		}
		
		ArrayList<ArrayList<Token>> tokenLists = new ArrayList<ArrayList<Token>>();
		ArrayList<ArrayList<Token>> firstTokenLists = new ArrayList<ArrayList<Token>>();
		
		for (int size = 2; size < maxTokenSize; size++) {
			ArrayList<Token> tokenList = new ArrayList<Token>();
			ArrayList<Token> firstTokenList = new ArrayList<Token>();
			
			for (String elt: list) {
				incrementTokenList(elt.substring(0, size), firstTokenList);
				for (int i=0; i<= elt.length() - size; i++) {
					incrementTokenList(elt.substring(i, size + i), tokenList);
				}
			}
			tokenLists.add(tokenList);
			firstTokenLists.add(firstTokenList);
		}
		this.currentTokenLists = tokenLists;
		this.currentFirstTokenLists = firstTokenLists;
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
	
	public String toString() {
		return "NamegenerationStrategy: " + name;
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
