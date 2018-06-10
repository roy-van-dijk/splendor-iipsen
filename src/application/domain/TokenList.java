package application.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
// TODO: Re-check the JavaDoc

/**
 * The Class TokenList.
 *
 * @author Sanchez
 */
public class TokenList implements Serializable {
	private List<Token> tokens;

	/**
	 * Instantiates a new token list.
	 */
	public TokenList() {
		this.tokens = new ArrayList<Token>();
	}
	
	/**
	 * Instantiates a new token list.
	 *
	 * @param tokens
	 */
	public TokenList(List<Token> tokens)
	{
		this.tokens = tokens;
	}

	/**
	 * Adds a Token to the tokens 
	 *
	 * @param token
	 */
	public void add(Token token) {
		tokens.add(token);
	}
	
	/**
	 * Removes an token from tokens
	 *
	 * @param token
	 */
	public void remove(Token token) {
		for(Token t : tokens) {
			if(t.getGemType() == token.getGemType()) {
			tokens.remove(t); return;
		}
		}
	}
	
	/**
	 * Gets all the tokens 
	 *
	 * @return tokens as a List type
	 */
	public List<Token> getAll() {
		return tokens;
	}
	
	/**
	 * TODO: Consider making this part of a view rather than an a model.
	 *
	 * @return LinkedHashMap - a Map containing gems and their amount of occurrences in this TokenList
	 */
	public LinkedHashMap<Gem, Integer> getTokenGemCount() {
		LinkedHashMap<Gem, Integer> gemsCount = new LinkedHashMap<Gem, Integer>();
		
		// Initialize map
		for(Gem gemType : Gem.values())
		{
			gemsCount.put(gemType, 0);
		}
		
		// Count occurrences
		for(Token token : tokens)
		{
			gemsCount.put(token.getGemType(), gemsCount.get(token.getGemType()) + 1);
		}
		return gemsCount;
		
	}
	
	
}
