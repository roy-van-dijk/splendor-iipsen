package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface PlayerSlot.
 */
public interface PlayerSlot extends Remote {
	
	/**
	 * Checks if is ready.
	 *
	 * @return true, if is ready
	 * @throws RemoteException the remote exception
	 */
	public boolean isReady() throws RemoteException;

	/**
	 * Sets the ready.
	 *
	 * @param isReady the new ready
	 * @throws RemoteException the remote exception
	 */
	public void setReady(boolean isReady) throws RemoteException;

	/**
	 * Gets the player.
	 *
	 * @return Player
	 * @throws RemoteException the remote exception
	 */
	public Player getPlayer() throws RemoteException;

	/**
	 * Sets the player.
	 *
	 * @param player the new player
	 * @throws RemoteException
	 */
	public void setPlayer(Player player) throws RemoteException;
}
