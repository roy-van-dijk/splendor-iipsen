package application.domain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerSlotImpl.
 * @author Sanchez
 *
 */
public class PlayerSlotImpl extends UnicastRemoteObject implements PlayerSlot {
	
	private static final long serialVersionUID = 1L;
	private boolean isReady;
	private Player currentPlayer;
	private Player previousPlayer;
	
	/**
	 * Instantiates a new player slot impl.
	 *
	 * @throws RemoteException
	 */
	public PlayerSlotImpl() throws RemoteException {
		currentPlayer = null;
		previousPlayer = null;
		
		isReady = false;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#isReady()
	 */
	public boolean isReady() throws RemoteException {
		return isReady;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#setReady(boolean)
	 */
	public void setReady(boolean isReady) throws RemoteException {
		this.isReady = isReady;
	}


	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#getCurrentPlayer()
	 */
	public Player getCurrentPlayer() throws RemoteException {
		return currentPlayer;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#setCurrentPlayer(application.domain.Player)
	 */
	public void setCurrentPlayer(Player currentPlayer) throws RemoteException {
		this.currentPlayer = currentPlayer;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#getPreviousPlayer()
	 */
	public Player getPreviousPlayer() throws RemoteException {
		return previousPlayer;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#setPreviousPlayer(application.domain.Player)
	 */
	public void setPreviousPlayer(Player previousPlayer) throws RemoteException {
		this.previousPlayer = previousPlayer;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#empty()
	 */
	public void empty() throws RemoteException {
		this.currentPlayer = null;
		this.isReady = false;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#isAvailable()
	 */
	public boolean isAvailable() throws RemoteException {
		return this.currentPlayer == null;
	}
}
