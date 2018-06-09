package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Row of cards that can be seen on the playing field
 * 
 * @author Sanchez
 *
 */
public interface CardRow extends Remote {

	/**
	 * Removes the passed card from the card row
	 * 
	 * @param card
	 * @throws RemoteException
	 * @return void
	 */
	public void removeCard(Card card) throws RemoteException;

	/**
	 * Adds an observer to the card row that other classes can listen to
	 * 
	 * @param observer
	 * @throws RemoteException
	 * @return void
	 */
	public void addObserver(CardRowObserver observer) throws RemoteException;

	/**
	 * Gets the current cards in this card row
	 * 
	 * @throws RemoteException
	 * @return Card[]
	 */
	public Card[] getCardSlots() throws RemoteException;

	/**
	 * Gets the card deck this card row draws cards from
	 * 
	 * @throws RemoteException
	 * @return CardDeck
	 */
	public CardDeck getCardDeck() throws RemoteException;

	/**
	 * Gets all the cards in this card row that the current player can afford
	 * 
	 * @return List<Card>
	 */
	public List<Card> getSelectableCards();

	/**
	 * Adds the passed card to the player's temporary hand
	 * 
	 * @param cardRow
	 * @param card
	 * @param tempHand
	 * @throws RemoteException
	 */
	public void addCardToTemp(CardRow cardRow, Card card, TempHand tempHand) throws RemoteException;

	/**
	 * Notifies all observers of this card row of any changes
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void updateView() throws RemoteException;

	/**
	 * Removes selectable (affordable) markings from all cards in the row
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void clearSelectableCards() throws RemoteException;

	/**
	 * Finds all selectable (affordable) cards in this row
	 * 
	 * @param moveType
	 * @param player
	 * @throws RemoteException
	 */
	public void findSelectableCards(MoveType moveType, Player player) throws RemoteException;

}
