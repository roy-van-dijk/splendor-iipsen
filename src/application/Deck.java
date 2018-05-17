package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Deck {
	
	private CardLevel level;
	private Stack<Card> cards;
	
	public Deck(Stack<Card> cards)
	{
		this.cards = cards;		
	}
	
	public void addCard(Card card)
	{
		cards.push(card);
	}
	
	public Card takeCard(Card card)
	{
		return cards.pop();
	}
}
