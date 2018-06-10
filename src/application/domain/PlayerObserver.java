package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An asynchronous update interface for receiving notifications
 * about Player information as the Player is constructed.
 */
public interface PlayerObserver extends Remote {
	
	/**
	 * This method is called when information about an Player
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param player
	 * @throws RemoteException
	 */
	public void modelChanged(Player player) throws RemoteException;
}
