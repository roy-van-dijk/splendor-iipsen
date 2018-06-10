package application.domain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Class PlayerSlotImpl.
 */
public class PlayerSlotImpl extends UnicastRemoteObject implements PlayerSlot {
	private boolean isReady;
	private Player player;
	
	/**
	 * Instantiates a new player slot impl.
	 *
	 * @throws RemoteException
	 */
	public PlayerSlotImpl() throws RemoteException {
		player = null;
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
	 * @see application.domain.PlayerSlot#getPlayer()
	 */
	public Player getPlayer() throws RemoteException {
		return player;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayerSlot#setPlayer(application.domain.Player)
	 */
	public void setPlayer(Player player) throws RemoteException {
		this.player = player;
	}
}
