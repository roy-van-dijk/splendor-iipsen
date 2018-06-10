package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import application.domain.LobbyImpl.LobbyStates;

/**
 * A lobby for players to be able to join.
 *
 * @author Sanchez
 */
public interface Lobby extends Remote {
	
	/**
	 * Adds the observer.
	 *
	 * @param o the LobbyObserver
	 * @throws RemoteException
	 */
	public void addObserver(LobbyObserver o) throws RemoteException;
	
	/**
	 * Removes the observer.
	 *
	 * @param o the LobbyObserver
	 * @throws RemoteException
	 */
	public void removeObserver(LobbyObserver o) throws RemoteException;
	
	/**
	 * Creates the player.
	 *
	 * @param playerName 
	 * @throws RemoteException
	 */
	public void createPlayer(String playerName) throws RemoteException;
	
	/**
	 * Disconnect player.
	 *
	 * @param player
	 * @throws RemoteException
	 */
	public void disconnectPlayer(Player player) throws RemoteException;
	
	/**
	 * Gets the assigned players.
	 *
	 * @return the assigned players
	 * @throws RemoteException
	 */
	public Map<Integer, PlayerSlot> getAssignedPlayers() throws RemoteException;
	
	/**
	 * Gets the unassigned players.
	 *
	 * @return the unassigned players
	 * @throws RemoteException
	 */
	public List<Player> getUnassignedPlayers() throws RemoteException;
	
	/**
	 * Gets the max players.
	 *
	 * @return the max players
	 * @throws RemoteException
	 */
	public int getMaxPlayers() throws RemoteException;
	
	/**
	 * Gets the host IP.
	 *
	 * @return the host IP
	 * @throws RemoteException
	 */
	public String getHostIP() throws RemoteException;

	/**
	 * Gets the game.
	 *
	 * @return the game
	 * @throws RemoteException
	 */
	public Game getGame() throws RemoteException;
	
	/**
	 * Gets the lobby state.
	 *
	 * @return LobbyStates
	 * @throws RemoteException
	 */
	public LobbyStates getLobbyState() throws RemoteException;
}
