package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface CardRow extends Remote {

	public void removeCard(Card card) throws RemoteException;
	
	public void addObserver(CardRowObserver observer) throws RemoteException;

	public Card[] getCardSlots() throws RemoteException;

	public CardDeck getCardDeck() throws RemoteException;
	
	public void findSelectableCards(Player player) throws RemoteException;
	
	public List<Card> getSelectableCards();

	public void addCardToTemp(CardRow cardRow, Card card, TempHand tempHand) throws RemoteException;

	public void updateView() throws RemoteException;
}
