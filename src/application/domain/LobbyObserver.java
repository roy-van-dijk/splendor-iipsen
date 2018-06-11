package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

import application.domain.LobbyImpl.LobbyStates;

/**
 * An asynchronous update interface for receiving notifications
 * about Lobby information as the Lobby is constructed.
 */
public interface LobbyObserver extends Remote {

	/**
	 * This method is called when information about an Lobby
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param lobby
	 * @throws RemoteException
	 */
	public void modelChanged(Lobby lobby) throws RemoteException;
	
	/**
	 * This method is called when information about an Lobby
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param lobbyState
	 * @throws RemoteException
	 */
	public void disconnect(LobbyStates lobbyState) throws RemoteException;
}
