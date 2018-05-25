package application.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * 
 * @author Sanchez
 *
 */
public class TokenList {
	private List<Token> tokens;

	public TokenList() {
		this.tokens = new ArrayList<Token>();
	}

	public void add(Token token)
	{
		tokens.add(token);
	}
	
	public void remove(Token token)
	{
		tokens.remove(token);
	}
	
	public List<Token> getAll()
	{
		return tokens;
	}
	
	/**
	 * TODO: Consider making this part of a view rather than an a model
	 * @return a Map containing gems and their amount of occurrences in this TokenList
	 */
	public LinkedHashMap<Gem, Integer> getTokenGemCount()
	{
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
