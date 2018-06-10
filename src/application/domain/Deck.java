package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

// TODO: Re-check JavaDoc
/**
 * So far a Deck seems to just wrap around a List. Seems almost useless.
 * TODO: consider splitting Deck into CardDeck and NobleDeck for separate observers.
 *
 * @author Sanchez
 * @param <T> the generic type
 */

public interface Deck<T> extends Remote
{
	
	/**
	 * Adds the.
	 *
	 * @param item the item
	 * @throws RemoteException the remote exception
	 */
	public void add(T item) throws RemoteException;
	
	/**
	 * Pull.
	 *
	 * @return the t
	 * @throws RemoteException the remote exception
	 */
	public T pull() throws RemoteException;
	
	/**
	 * Top.
	 *
	 * @return the t
	 * @throws RemoteException the remote exception
	 */
	public T top() throws RemoteException;
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 * @throws RemoteException the remote exception
	 */
	public Stack<T> getAll() throws RemoteException;
}
