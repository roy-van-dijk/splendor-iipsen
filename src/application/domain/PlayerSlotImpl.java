package application.domain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PlayerSlotImpl extends UnicastRemoteObject implements PlayerSlot {
	private boolean isReady;
	private Player player;
	
	public PlayerSlotImpl() throws RemoteException {
		player = null;
		isReady = false;
	}

	public boolean isReady() throws RemoteException {
		return isReady;
	}

	public void setReady(boolean isReady) throws RemoteException {
		this.isReady = isReady;
	}

	public Player getPlayer() throws RemoteException {
		return player;
	}

	public void setPlayer(Player player) throws RemoteException {
		this.player = player;
	}
}
