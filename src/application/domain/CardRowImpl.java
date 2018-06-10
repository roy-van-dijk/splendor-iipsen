package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.EmptyStackException;




/**
 * @author Sanchez
 * This class acts like how you would expect CardDeck to act. This class makes sense as a View but it makes less sense as a model.
 * TODO: Consider pros/cons of merging it with CardDeck
 */
public class CardRowImpl extends UnicastRemoteObject implements Serializable, CardRow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7939191665883088567L;

	private static final int MAX_OPEN_CARDS = 4;
	
	private CardDeck cardDeckImpl;
	private CardImpl[] cardSlots;
	private transient ArrayList<CardRowObserver> observers;
	
	
	public CardRowImpl(CardDeck cardDeckImpl) throws RemoteException {
		this.cardDeckImpl = cardDeckImpl;
		this.cardSlots = new CardImpl[MAX_OPEN_CARDS];
		this.observers = new ArrayList<>();
		
		this.initializeCardSlots();
	}
	
	public void removeCard(Card card) throws RemoteException
	{
		boolean debugCardFound = false;
		for(int i = 0; i < cardSlots.length; i++)
		{
			if(cardSlots[i].equals(card)) // won't work with RMI because - Left: CardImpls, Right: ProxyRefs to Card
			{
				cardSlots[i] = null;
				debugCardFound = true;
				fillCardSlots();
			}
		}
		System.out.println("[DEBUG] CardRowImpl::removeCard()::Card to be removed found? " + debugCardFound);
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
	
	public synchronized void addObserver(CardRowObserver observer) throws RemoteException {
		observers.add(observer);
		this.notifyObservers();
	}


	private synchronized void notifyObservers() throws RemoteException {
		System.out.println("[DEBUG] CardRowImpl::notifyObservers()::Notifying all CardRow observers of change");
		for (CardRowObserver co : observers) {
			co.modelChanged(this);
		}
	}
	
	private void initializeCardSlots() throws RemoteException
	{
		this.fillCardSlots();
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
	}
}
