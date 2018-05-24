package application.domain;

import java.util.EmptyStackException;



/**
 * @author Sanchez
 * This class acts like how you would expect CardDeck to act. This class makes sense as a View but it makes less sense as a model.
 * TODO: Consider pros/cons of merging it with CardDeck
 */
public class CardRow {
	
	private static final int MAX_OPEN_CARDS = 4;
	
	private CardDeck cardDeck;
	private Card[] cardSlots;
	
	
	public CardRow(CardDeck cardDeck) {
		this.cardDeck = cardDeck;
		this.cardSlots = new Card[MAX_OPEN_CARDS];
		
		fillCardSlots();
	}
	
	public void removeCard(Card card)
	{
		for(int i = 0; i < cardSlots.length; i++)
		{
			if(cardSlots[i].equals(card))
			{
				cardSlots[i] = null;
				fillCardSlots();
			}
		}
		//notifyObservers();
	}/*
	
	private void notifyObservers() {
		for(Observer o : observers)
		{
			o.modelChanged(this);
		}

	}*/
	
	public void fillCardSlots()
	{
		for(int pos = 0; pos < cardSlots.length; pos++)
		{
			// Check if slot is empty
			if(cardSlots[pos] == null)
			{
				try {
					Card cardFromDeck = cardDeck.pull();
					cardSlots[pos] = cardFromDeck;
				} 
				catch (EmptyStackException e)
				{
					System.out.println("The deck contains no more cards to pull, leaving card slot empty.");
				}
			}
		}
	}

	public CardDeck getCardDeck() {
		return cardDeck;
	}

	/**
	 * @return Returns all card slots, containing either a Card object or a null. See comment @ CardRowView.buildUI() for a problem description.
	 */
	public Card[] getCardSlots() {
		return cardSlots;
	}
	
}
