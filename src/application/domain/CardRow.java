package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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
	
	public List<Card> getSelectableCards();

	public void addCardToTemp(CardRow cardRow, Card card, TempHand tempHand) throws RemoteException;

	public void updateView() throws RemoteException;
	
	public void clearSelectableCards() throws RemoteException;
	
	public void findSelectableCards(MoveType moveType, Player player) throws RemoteException;
	
	boolean isCardDeckSelectable();
}
