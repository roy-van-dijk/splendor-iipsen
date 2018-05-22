package application.domain;

import java.io.IOException;
import java.util.Stack;

import application.services.CardsReader;

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
	
	public CardDeck createCardDeck(CardLevel level)
	{
		Stack<Card> cards = cardsReader.getCards(level);
		
		CardDeck deck = new CardDeck(cards, level);
		return deck;
	}
	
}
