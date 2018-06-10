package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import application.domain.LobbyImpl.LobbyStates;


public interface Lobby extends Remote {
	
	public void leaveLobby(LobbyObserver o) throws RemoteException;
	
	public void createPlayer(LobbyObserver o, String playerName) throws RemoteException;
	public void disconnectPlayer(LobbyObserver o) throws RemoteException;
	
	public void readyPlayer(LobbyObserver o) throws RemoteException;
	public void unassignPlayer(LobbyObserver o) throws RemoteException;
	public void selectSlot(LobbyObserver o, PlayerSlot slot) throws RemoteException;
	
	public boolean isAssigned(LobbyObserver o) throws RemoteException;

	public Player getAssignedPlayer(LobbyObserver o) throws RemoteException;
	
	
	public List<PlayerSlot> getAssignedPlayerSlots() throws RemoteException;
	public List<PlayerSlot> getAssignableSlots() throws RemoteException;
	public List<Player> getUnassignedPlayers() throws RemoteException;
	
	
	public int getMaxPlayers() throws RemoteException;
	
	public String getHostIP() throws RemoteException;

	public Game getGame() throws RemoteException;
	
	public LobbyStates getLobbyState() throws RemoteException;
	
}
