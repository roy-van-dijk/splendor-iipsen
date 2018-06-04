package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CardRow extends Remote {

	public void removeCard(Card card) throws RemoteException;
	
	public void addObserver(CardRowObserver observer) throws RemoteException;

	public Card[] getCardSlots() throws RemoteException;

	public CardDeck getCardDeck() throws RemoteException;
}
