package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An asynchronous update interface for receiving notifications
 * about Deck information as the Deck is constructed.
 *
 * @param <T> the generic type
 */
public interface DeckObserver<T> extends Remote 
{
	
	/**
	 * This method is called when information about an Deck
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param deck
	 * @throws RemoteException
	 */
	public void modelChanged(T deck) throws RemoteException;
}
