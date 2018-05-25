package application.domain;


import java.util.Stack;

/**
 * @author Sanchez
 *
 */
public class CardDeck implements Deck<Card> {
	
	private Stack<Card> cards;
	private CardLevel level; // Probably useless
	
	public CardDeck(Stack<Card> cards, CardLevel level)
	{
		this.cards = cards;		
		this.level = level;
	}
	
	public void add(Card card)
	{
		cards.push(card);
	}
	
	public Card top()
	{
		return cards.peek();
	}
	
	public Card pull()
	{
		return cards.pop();
	}

	public Stack<Card> getAll() {
		return cards;
	}
}
