package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface PlayerSlot.
 * @author Sanchez
 *
 */
public interface PlayerSlot extends Remote {
	
	/**
	 * Checks if is ready.
	 *
	 * @return true, if is ready
	 * @throws RemoteException
	 */
	public boolean isReady() throws RemoteException;
	
	/**
	 * Sets the ready.
	 *
	 * @param isReady
	 * @throws RemoteException
	 */
	public void setReady(boolean isReady) throws RemoteException;

	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 * @throws RemoteException
	 */
	public Player getCurrentPlayer() throws RemoteException;
	
	/**
	 * Sets the current player.
	 *
	 * @param player the new current player
	 * @throws RemoteException
	 */
	public void setCurrentPlayer(Player player) throws RemoteException;
	
	/**
	 * Gets the previous player.
	 *
	 * @return the previous player
	 * @throws RemoteException
	 */
	public Player getPreviousPlayer() throws RemoteException;
	
	/**
	 * Sets the previous player.
	 *
	 * @param player the new previous player
	 * @throws RemoteException
	 */
	public void setPreviousPlayer(Player player) throws RemoteException;
	
	/**
	 * Checks if is available.
	 *
	 * @return true, if is available
	 * @throws RemoteException
	 */
	public boolean isAvailable() throws RemoteException;
	
	/**
	 * Empty the PlayerSlot.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void empty() throws RemoteException;
}
