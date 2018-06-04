package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerSlot extends Remote {
	
	public boolean isReady() throws RemoteException;

	public void setReady(boolean isReady) throws RemoteException;

	public Player getPlayer() throws RemoteException;

	public void setPlayer(Player player) throws RemoteException;
}
