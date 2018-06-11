package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

/**
 * The Interface CardDeck.
 *
 * @author Sanchez
 */
public interface CardDeck extends Remote
{
	
	/**
	 * Adds the.
	 *
	 * @param card the card
	 * @throws RemoteException
	 */
	public void add(Card card) throws RemoteException;
	
	/**
	 * Pull.
	 *
	 * @return the card
	 * @throws RemoteException
	 */
	public Card pull() throws RemoteException;
	
	/**
	 * Top.
	 *
	 * @return Card
	 * @throws RemoteException
	 */
	public Card top() throws RemoteException;
	
	/**
	 * Gets the all.
	 *
	 * @return Stack<Card>
	 * @throws RemoteException
	 */
	public Stack<Card> getAll() throws RemoteException;
	
	/**
	 * Gets the level.
	 *
	 * @return CardLevel
	 * @throws RemoteException
	 */
	public CardLevel getLevel() throws RemoteException;
	
	/**
	 * Checks if is selectable.
	 *
	 * @return true, if is selectable
	 * @throws RemoteException
	 */
	public boolean isSelectable() throws RemoteException;
	
	/**
	 * Sets the selectable.
	 *
	 * @throws RemoteException
	 */
	public void setSelectable() throws RemoteException;
	
	/**
	 * Clear selectable.
	 *
	 * @throws RemoteException
	 */
	public void clearSelectable() throws RemoteException;
	
	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 * @throws RemoteException
	 */
	public boolean isSelected() throws RemoteException;
	
	/**
	 * Sets the selected.
	 *
	 * @throws RemoteException
	 */
	public void setSelected() throws RemoteException;
	
	/**
	 * Clear selection.
	 *
	 * @throws RemoteException
	 */
	public void clearSelection() throws RemoteException;
}
