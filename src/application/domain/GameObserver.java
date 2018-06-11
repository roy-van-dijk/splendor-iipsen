package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.views.Disableable;

/**
 * An asynchronous update interface for receiving notifications
 * about Game information as the Game is constructed.
 */
public interface GameObserver extends Remote {
	
	/**
	 * This method is called when information about an Game
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param game
	 * @throws RemoteException
	 */
	public void modelChanged(Game game) throws RemoteException;

	public void disconnect(GameState gameState) throws RemoteException;

}
