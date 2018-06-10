package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Sanchez
 *
 */
public interface PlayerSlot extends Remote {
	
	public boolean isReady() throws RemoteException;
	public void setReady(boolean isReady) throws RemoteException;

	public Player getCurrentPlayer() throws RemoteException;
	public void setCurrentPlayer(Player player) throws RemoteException;
	
	public Player getPreviousPlayer() throws RemoteException;
	public void setPreviousPlayer(Player player) throws RemoteException;
	
	public boolean isAvailable() throws RemoteException;
	public void empty() throws RemoteException;
}
