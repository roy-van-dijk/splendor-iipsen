package application.domain;

import java.io.IOException;
import java.util.Stack;

import application.services.CardsReader;
import application.services.NoblesReader;

/**
 * @author Sanchez
 *
 */
public class DeckFactory {
	private CardsReader cardsReader;
	private NoblesReader noblesReader;
	// TODO: add NoblesReader

	public DeckFactory() {
		
		try {
			cardsReader = new CardsReader();
			noblesReader = new NoblesReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CardDeck createCardDeck(CardLevel level)
	{
		Stack<Card> cards = cardsReader.getCards(level);
		
		CardDeck deck = new CardDeck(cards, level);
		return deck;
	}
	
	public NobleDeck createNobleDeck()
	{
		Stack<Noble> nobles = noblesReader.getNobles();
		
		NobleDeck deck = new NobleDeck(nobles);
		return deck;
	}
}
