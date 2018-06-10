package application.domain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Sanchez
 *
 */
public class PlayerSlotImpl extends UnicastRemoteObject implements PlayerSlot {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isReady;
	private Player currentPlayer;
	private Player previousPlayer;
	
	public PlayerSlotImpl() throws RemoteException {
		currentPlayer = null;
		previousPlayer = null;
		
		isReady = false;
	}

	public boolean isReady() throws RemoteException {
		return isReady;
	}

	public void setReady(boolean isReady) throws RemoteException {
		this.isReady = isReady;
	}

	public Player getCurrentPlayer() throws RemoteException {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) throws RemoteException {
		this.currentPlayer = currentPlayer;
	}

	public Player getPreviousPlayer() throws RemoteException {
		return previousPlayer;
	}

	public void setPreviousPlayer(Player previousPlayer) throws RemoteException {
		this.previousPlayer = previousPlayer;
	}

	public void empty() throws RemoteException {
		this.currentPlayer = null;
		this.isReady = false;
	}
	
	public boolean isAvailable() throws RemoteException {
		return this.currentPlayer == null;
	}
}
