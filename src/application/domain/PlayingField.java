package application.domain;

import java.util.ArrayList;
import java.util.List;

import application.services.CardsReader;
/**
 * 
 * @author Sanchez
 *
 */
public class PlayingField {
	
	private final static int TOKEN_BASE_AMOUNT = 7;
	private final static int NOBLE_BASE_AMOUNT = 5;
	
	
	private List<CardRow> cardRows;
	
	private List<Noble> nobles;
	private TokenList tokenList;
	
	//private NobleDeck noblesDeck; // Not necessary - is only used on round start.
	
	private DeckFactory deckFactory;

	public PlayingField(int playerCount) {
		this.nobles = new ArrayList<>();
		this.cardRows = new ArrayList<>();
		this.tokenList = new TokenList();
		this.deckFactory = new DeckFactory();

		this.createTokens(tokensAmount(playerCount));
		this.Test_createNobles(noblesAmount(playerCount));
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
				tokenList.add(new Token(gem));
			}
		}
	}
	
	private void Test_createNobles(int baseAmount)
	{
		// TODO: Utilize DeckFactory for this
		
		for(int i = 0; i < baseAmount; i++)
		{
			Noble noble = new Noble((int) (Math.random() * 5), (int) (Math.random() * 10), null); // read TODO above.
			nobles.add(noble);
		}
	}
	
	private void createCardRows()
	{
		// Create 3 card rows (including decks)
		for(CardLevel level : CardLevel.values())
		{
			CardDeck deck = deckFactory.createCardDeck(level);
			cardRows.add(new CardRow(deck));
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
	

	public List<CardRow> getCardRows() {
		return cardRows;
	}

	public List<Noble> getNobles() {
		return nobles;
	}
	
	public TokenList getTokenList()
	{
		return tokenList;
	}

}
