package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EmptyStackException;

import application.views.CardRowView;



/**
 * @author Sanchez
 * This class acts like how you would expect CardDeck to act. This class makes sense as a View but it makes less sense as a model.
 * TODO: Consider pros/cons of merging it with CardDeck
 */
public class CardRowImpl {
	
	private static final int MAX_OPEN_CARDS = 4;
	
	private CardDeck cardDeckImpl;
	private Card[] cardSlots;
	private ArrayList<GenericObserver<CardRowImpl>> observers = new ArrayList<GenericObserver<CardRowImpl>>();
	
	
	public CardRowImpl(CardDeck cardDeckImpl) {
		this.cardDeckImpl = cardDeckImpl;
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
	}
	
	/*
	private void notifyObservers() {
		for(Observer o : observers)
		{
			o.modelChanged(this);
		}

	}*/
	
	private void fillCardSlots()
	{
		for(int pos = 0; pos < cardSlots.length; pos++)
		{
			// Check if slot is empty
			if(cardSlots[pos] == null)
			{
				try {
					Card cardFromDeck = cardDeckImpl.pull();
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
		return cardDeckImpl;
	}

	/**
	 * @return Returns all card slots, containing either a Card object or a null. See comment @ CardRowView.buildUI() for a problem description.
	 */
	public Card[] getCardSlots() {
		return cardSlots;
	}
	
	public void notifyObservers() throws RemoteException {
		for (GenericObserver<CardRowImpl> co : observers) {
			co.modelChanged(this);
		}
	}

	public void addObserver(GenericObserver<CardRowImpl> observer) throws RemoteException {
		observers.add(observer);
		try {
			notifyObservers();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
