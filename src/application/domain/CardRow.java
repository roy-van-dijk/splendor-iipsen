package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardRow extends Remote {
	/**
	 * 
	 * @param card
	 * @throws RemoteException
	 * void
	 */
	public void removeCard(Card card) throws RemoteException;
	/**
	 * 
	 * @param observer
	 * @throws RemoteException
	 * void
	 */
	public void addObserver(CardRowObserver observer) throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * Card[]
	 */
	public Card[] getCardSlots() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * CardDeck
	 */
	public CardDeck getCardDeck() throws RemoteException;
}
