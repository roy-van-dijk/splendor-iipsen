package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EmptyStackException;

import application.views.CardRowView;



/**
 * @author Sanchez
 * This class acts like how you would expect CardDeck to act. This class makes sense as a View but it makes less sense as a model.
 * TODO: Consider pros/cons of merging it with CardDeck
 */
public class CardRowImpl implements Serializable, CardRow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7939191665883088567L;

	private static final int MAX_OPEN_CARDS = 4;
	
	private CardDeck cardDeckImpl;
	private CardImpl[] cardSlots;
	private ArrayList<CardRowObserver> observers;
	
	
	public CardRowImpl(CardDeck cardDeckImpl) {
		this.cardDeckImpl = cardDeckImpl;
		this.cardSlots = new CardImpl[MAX_OPEN_CARDS];
		this.observers = new ArrayList<>();
		
		try {
			fillCardSlots();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeCard(Card card) throws RemoteException
	{
		for(int i = 0; i < cardSlots.length; i++)
		{
			if(cardSlots[i].equals(card))
			{
				cardSlots[i] = null;
				fillCardSlots();
			}
		}
		this.notifyObservers();
	}
	
	
	private void fillCardSlots() throws RemoteException
	{
		for(int pos = 0; pos < cardSlots.length; pos++)
		{
			// Check if slot is empty
			if(cardSlots[pos] == null)
			{
				try {
					CardImpl cardFromDeck = (CardImpl) cardDeckImpl.pull();
					cardSlots[pos] = cardFromDeck;
				} 
				catch (EmptyStackException e)
				{
					System.out.println("The deck contains no more cards to pull, leaving card slot empty.");
				}
			}
		}
		this.notifyObservers();
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
	
	private void notifyObservers() throws RemoteException {
		for (CardRowObserver co : observers) {
			co.modelChanged(this);
		}
	}

	public void addObserver(CardRowObserver observer) throws RemoteException {
		observers.add(observer);
		this.notifyObservers();
	}

}
