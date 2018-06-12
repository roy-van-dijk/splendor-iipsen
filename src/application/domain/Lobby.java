package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import application.domain.LobbyImpl.LobbyStates;
import application.util.LobbyFullException;

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
	 * @throws RemoteException the remote exception
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
	 * @throws RemoteException the remote exception
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
	 * @return List<Player
	 * @throws RemoteException the remote exception
	 */
	public List<PlayerSlot> getAssignableSlots() throws RemoteException;
	
	/**
	 * Gets the unassigned players.
	 *
	 * @return List<Player>
	 * @throws RemoteException the remote exception
	 */
	public List<Player> getUnassignedPlayers() throws RemoteException;
	
	
	/**
	 * Gets the max players.
	 *
	 * @return the max players
	 * @throws RemoteException the remote exception
	 */
	public int getMaxPlayers() throws RemoteException;
	
	/**
	 * Gets the host IP.
	 *
	 * @return the host IP
	 * @throws RemoteException the remote exception
	 */
	public String getHostIP() throws RemoteException;

	/**
	 * Gets the game.
	 *
	 * @return the game
	 * @throws RemoteException the remote exception
	 */
	public Game getGame() throws RemoteException;
	
	/**
	 * Gets the lobby state.
	 *
	 * @return LobbyStates
	 * @throws RemoteException the remote exception
	 */
	public LobbyStates getLobbyState() throws RemoteException;
	
	public boolean isFull() throws RemoteException, LobbyFullException;
	
}
