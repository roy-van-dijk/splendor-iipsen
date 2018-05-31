package application.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * @author Sanchez
 *
 */
public class PlayingFieldImpl implements PlayingField {
	
	private final static int TOKEN_BASE_AMOUNT = 7;
	private final static int NOBLE_BASE_AMOUNT = 5;
	
	
	private List<CardRowImpl> cardRows;
	private List<Noble> nobles;
	
	private TokenList tokenList;
	private DeckFactory deckFactory;

	public PlayingFieldImpl(int playerCount) {
		this.nobles = new ArrayList<>();
		this.cardRows = new ArrayList<>();
		this.tokenList = new TokenList();
		this.deckFactory = new DeckFactory();

		this.createTokens(tokensAmount(playerCount));
		this.createNobles(noblesAmount(playerCount));
		this.createCardRows();
	}

	private void createTokens(int baseAmount)
	{
		for(Gem gem : Gem.values())
		{
			int amount = baseAmount;
			
			if(gem.equals(Gem.JOKER)) 
			{
				amount = 5;
			}
		
			for(int i = 0; i < amount; i++)
			{
				tokenList.add(new TokenImpl(gem));
			}
		}
	}
	
	private void createNobles(int amount)
	{
		NobleDeck deck = deckFactory.createNobleDeck();
		Collections.shuffle(deck.getAll());
		
		for(int i = 0; i < amount; i++)
		{
			nobles.add(deck.getAll().pop());
		}
	}
	
	private void createCardRows()
	{
		// Create 3 card rows (including decks)
		for(CardLevel level : CardLevel.values())
		{
			CardDeck deck = deckFactory.createCardDeck(level);
			Collections.shuffle(deck.getAll());
			cardRows.add(new CardRowImpl(deck));
		}
	}
	
	
	private int noblesAmount(int playerCount)
	{
		int amount;
		
		switch(playerCount) {
			case 2: { amount = NOBLE_BASE_AMOUNT - 2; } break;
			case 3: { amount = NOBLE_BASE_AMOUNT - 1; } break;
			default: { amount = NOBLE_BASE_AMOUNT; }
		}
		
		return amount;
	}
	
	private int tokensAmount(int playerCount)
	{
		int amount;
		
		switch(playerCount) {
			case 2: { amount = TOKEN_BASE_AMOUNT - 3; } break;
			case 3: { amount = TOKEN_BASE_AMOUNT - 2; } break;
			default: { amount = TOKEN_BASE_AMOUNT; }
		}
		
		return amount;
	}
	

	public List<CardRowImpl> getCardRows() {
		return cardRows;
	}

	public List<Noble> getNobles() {
		return nobles;
	}
	
	public LinkedHashMap<Gem, Integer> getTokenGemCount()
	{
		return tokenList.getTokenGemCount();
	}

	public void addToken(Token token) {
		tokenList.add(token);
		
	}

	public void removeToken(Token token) {
		tokenList.remove(token);
		
	}

}
