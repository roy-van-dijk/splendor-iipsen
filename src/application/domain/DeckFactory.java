package application.domain;

import java.io.IOException;
import java.util.Stack;

import application.services.CardsReader;

/**
 * @author Sanchez
 *
 */
public class DeckFactory {
	private CardsReader cardsReader;
	// TODO: add NoblesReader

	public DeckFactory() {
		
		try {
			cardsReader = new CardsReader();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CardDeckImpl createCardDeck(CardLevel level)
	{
		Stack<Card> cards = cardsReader.getCards(level);
		
		CardDeckImpl deck = new CardDeckImpl(cards, level);
		return deck;
	}
	
}
