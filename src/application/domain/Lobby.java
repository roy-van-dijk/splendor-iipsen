package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import application.domain.LobbyImpl.LobbyStates;
import application.util.LobbyFullException;
import application.util.NameTakenException;

// TODO: Auto-generated Javadoc
/**
 * A lobby for players to be able to join.
 *
 * @author Sanchez
 */
public interface Lobby extends Remote {
	
	/**
	 * Leave lobby.
	 *
	 * @param o the LobbyObserver
	 * @throws RemoteException
	 */
	public void leaveLobby(LobbyObserver o) throws RemoteException;
	
	/**
	 * Creates the player.
	 *
	 * @param o the LobbyObservver
	 * @param playerName
	 * @throws RemoteException
	 */
	public void createPlayer(LobbyObserver o, String playerName) throws RemoteException;
	
	/**
	 * Disconnect player.
	 *
	 * @param o the LobbyObserver
	 * @throws RemoteException
	 */
	public void disconnectPlayer(LobbyObserver o) throws RemoteException;
	
	/**
	 * Ready player.
	 *
	 * @param o the LobbyObserver
	 * @throws RemoteException
	 */
	public void readyPlayer(LobbyObserver o) throws RemoteException;
	
	/**
	 * Unassign player.
	 *
	 * @param o the LobbyObserver
	 * @throws RemoteException 
	 */
	public void unassignPlayer(LobbyObserver o) throws RemoteException;
	
	/**
	 * Select slot.
	 *
	 * @param o the LobbyObserver
	 * @param slotIdx the PlayerSlot
	 * @throws RemoteException 
	 */
	public void selectSlot(LobbyObserver o, int slotIdx) throws RemoteException;
	
	/**
	 * Checks if is assigned.
	 *
	 * @param o the LobbbyObserver
	 * @return true, if is assigned
	 * @throws RemoteException 
	 */
	public boolean isAssigned(LobbyObserver o) throws RemoteException;

	/**
	 * Checks if is ready.
	 *
	 * @param o the LobbbyObserver
	 * @return true, if is ready
	 * @throws RemoteException 
	 */
	public boolean isReady(LobbyObserver o) throws RemoteException;
	
	/**
	 * Gets the assigned player.
	 *
	 * @param o the LobbyObserver
	 * @return Player
	 * @throws RemoteException
	 */
	public Player getAssignedPlayer(LobbyObserver o) throws RemoteException;
	
	
	/**
	 * Gets the assigned player slots.
	 *
	 * @return List<PlayerSlot>
	 * @throws RemoteException
	 */
	public List<PlayerSlot> getAssignedPlayerSlots() throws RemoteException;
	
	/**
	 * Gets the assignable slots.
	 *
	 * @return List<Player>
	 * @throws RemoteException
	 */
	public List<PlayerSlot> getAssignableSlots() throws RemoteException;
	
	/**
	 * Gets the unassigned players.
	 *
	 * @return List<Player>
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
	
	/**
	 * Checks if is full.
	 *
	 * @return true, if is full
	 * @throws RemoteException
	 * @throws LobbyFullException
	 */
	public boolean isFull() throws RemoteException, LobbyFullException;

	/**
	 * Checks if a player with the given name already exists in the lobby.
	 * Ignores case.
	 *
	 * @return true, if is full
	 * @throws RemoteException
	 * @throws NameTakenException
	 */
	public boolean isNameTaken(String name) throws RemoteException, NameTakenException; 
	
}
