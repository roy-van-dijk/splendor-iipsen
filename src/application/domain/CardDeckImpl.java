package application.domain;


import java.util.Stack;

public class CardDeckImpl implements Deck<Card> {
	
	private Stack<Card> cards;
	private CardLevel level; // Probably useless
	
	public CardDeckImpl(Stack<Card> cards, CardLevel level)
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

	@Override
	public void addObserver(Card observer) {
		
	}
}
