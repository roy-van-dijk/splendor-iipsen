package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Lobby extends Remote {
	
	public void addObserver(LobbyObserver o) throws RemoteException;
	
	public void removeObserver(LobbyObserver o) throws RemoteException;
	
	public void connectPlayer(Player player) throws RemoteException;
	
	public void disconnectPlayer(Player player) throws RemoteException;
	
	public List<Player> getAssignedPlayers() throws RemoteException;
	
	public List<Player> getUnassignedPlayers() throws RemoteException;
	
	public int getMaxPlayers() throws RemoteException;
}
