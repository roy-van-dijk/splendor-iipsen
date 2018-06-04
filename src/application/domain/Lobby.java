package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import application.domain.LobbyImpl.LobbyStates;


public interface Lobby extends Remote {
	
	public void addObserver(LobbyObserver o) throws RemoteException;
	
	public void removeObserver(LobbyObserver o) throws RemoteException;
	
	public void createPlayer(String playerName) throws RemoteException;
	
	public void disconnectPlayer(Player player) throws RemoteException;
	
	public Map<Integer, PlayerSlot> getAssignedPlayers() throws RemoteException;
	
	public List<Player> getUnassignedPlayers() throws RemoteException;
	
	public int getMaxPlayers() throws RemoteException;
	
	public String getHostIP() throws RemoteException;

	public Game getGame() throws RemoteException;
	
	public LobbyStates getLobbyState() throws RemoteException;
}
