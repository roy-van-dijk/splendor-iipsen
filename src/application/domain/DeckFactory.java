package application.domain;

import java.io.IOException;
import java.util.Stack;

import application.services.CardsReader;
import application.services.NoblesReader;

/**
 * A factory for creating Deck objects.
 *
 * @author Sanchez
 */
public class DeckFactory {
	private static CardsReader cardsReader;
	private static NoblesReader noblesReader;
	
	private static DeckFactory deckFactory; 
	
	/**
	 * Gets the single instance of DeckFactory.
	 *
	 * @return DeckFactory
	 */
	public static DeckFactory getInstance()
	{
		if(deckFactory == null)
			return new DeckFactory();
		return deckFactory;
	}

	/**
	 * Instantiates a new deck factory.
	 */
	private DeckFactory() {
		try {
			cardsReader = new CardsReader();
			noblesReader = new NoblesReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Deck object.
	 *
	 * @param level
	 * @return CardDeck
	 */
	public CardDeck createCardDeck(CardLevel level)
	{
		Stack<Card> cards = cardsReader.getCards(level);
		
		CardDeck deck = new CardDeck(cards, level);
		return deck;
	}
	
	/**
	 * Creates a new Deck object.
	 *
	 * @return NobleDeck
	 */
	public NobleDeck createNobleDeck()
	{
		Stack<Noble> nobles = noblesReader.getNobles();
		
		NobleDeck deck = new NobleDeck(nobles);
		return deck;
	}
}
