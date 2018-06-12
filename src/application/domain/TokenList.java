package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
	 * remove a token
	 * @param token
	 * @throws RemoteException
	 */
	public void remove(Token token) throws RemoteException {
		for(Token t : tokens) {
			if(t.getGemType() == token.getGemType()) {
				tokens.remove(t); return;
			}
		}
	}
	
	/**
	 * Gets all the tokens 
	 *
	 * @return List<Token>
	 */
	public List<Token> getAll() {
		return tokens;
	}
	
	/**
	 * TODO: Consider making this part of a view rather than an a model
	 * @return a Map containing gems and their amount of occurrences in this TokenList
	 * @throws RemoteException 
	 */
	public LinkedHashMap<Gem, Integer> getTokenGemCount() throws RemoteException {
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
