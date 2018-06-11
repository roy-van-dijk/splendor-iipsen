package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An asynchronous update interface for receiving notifications
 * about PlayingField information as the PlayingField is constructed.
 */
public interface PlayingFieldObserver extends Remote {
	
	/**
	 * This method is called when information about an PlayingField
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param playingField
	 * @throws RemoteException
	 */
	public void modelChanged(PlayingField playingField) throws RemoteException;
}
