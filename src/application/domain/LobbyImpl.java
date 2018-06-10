package application.domain;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import application.util.Util;

/**
 * @author Sanchez
 *
 */
public class LobbyImpl extends UnicastRemoteObject implements Lobby {

	private static final long serialVersionUID = 1L;
	
	private static final int MIN_PLAYERS_TO_START = 1;
	
	public enum LobbyStates { WAITING, STARTED_GAME, CLOSING };
	
	private LobbyStates lobbyState; 
	
	private LobbyObserver host;
	
	private GameImpl game;
	
	private String hostIP;
	private int maxPlayers;
	
	private Registry registry;

	private Map<Integer, PlayerSlot> assignableSlots; // for slot indices
	
	// Observers
	private Map<LobbyObserver, PlayerSlot> assignedPlayersMap; // for assigned players
	private Map<LobbyObserver, Player> playersMap; // for unassigned & assigned players
	
	
	public LobbyImpl(GameImpl game) throws RemoteException {
		this.game = game;
		this.maxPlayers = game.getMaxPlayers();
		this.lobbyState = LobbyStates.WAITING;
		
		try {
			this.hostIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		this.assignableSlots = new LinkedHashMap<>();
		this.assignedPlayersMap = new LinkedHashMap<>();
		this.playersMap = new LinkedHashMap<>();
		
		this.createPlayerSlots();
	}
	
	private void disconnectAllPlayers() throws RemoteException
	{
		System.out.println("[DEBUG] LobbyImpl::disconnectAllPlayers()::Disconnecting all observers.");
		for(LobbyObserver o : playersMap.keySet())
		{
			o.disconnect(lobbyState);
		}
		playersMap.clear();
		assignedPlayersMap.clear();
	}
	
	public void leaveLobby(LobbyObserver o) throws RemoteException {
		if(host.equals(o)) // Only host can terminate the lobby
		{
			this.terminateLobby();
		} else {
			this.clearPlayerSlot(o); // Clear previous slot
			o.disconnect(lobbyState); // Update view
			this.assignedPlayersMap.remove(o); // Unassign player from slot
			this.playersMap.remove(o); // Remove observer
			this.notifyObservers();
		}
	}
	
	private void terminateLobby() throws RemoteException
	{
		System.out.println("[DEBUG] LobbyImpl::terminateLobby()::Terminating server.");
		
		this.lobbyState = LobbyStates.CLOSING;
		this.disconnectAllPlayers();
		
		UnicastRemoteObject.unexportObject(this, true);
		try {
			this.registry.unbind("Lobby");
		} catch (NotBoundException e) {
			System.out.println("[DEBUG] LobbyImpl::terminateLobby()::Something went wrong with lobby termination. Lobby was not bound in the first place.");
			e.printStackTrace();
		}
		UnicastRemoteObject.unexportObject(this.registry, true);
		System.out.println("[DEBUG] LobbyImpl::terminateLobby()::Server terminated.");
	}
	
	private void createPlayerSlots() throws RemoteException
	{
		List<Player> previousPlayers = game.getPlayers();
		for(int i = 0; i < this.maxPlayers; i++)
		{
			PlayerSlotImpl slot = new PlayerSlotImpl();
			if(previousPlayers.get(i) != null)
			{
				slot.setPreviousPlayer(previousPlayers.get(i));
			}
			this.assignableSlots.put(i, slot); 
		}
	}
	
	public Player getAssignedPlayer(LobbyObserver o) throws RemoteException
	{
		return assignedPlayersMap.get(o).getCurrentPlayer();
	}
	
	private void startGame() throws RemoteException
	{
		System.out.println("[DEBUG] LobbyImpl::startGame()::Setting up players");
		ArrayList<Player> players = new ArrayList<Player>();
		for(PlayerSlot slot : assignableSlots.values())
		{
			if(slot.getCurrentPlayer() != null)
			{
				Player player = slot.getCurrentPlayer();
				
				// If a previous player is overwritten, use the previous player's data and simply change his display name;
				if(slot.getPreviousPlayer() != null)
				{
					player = slot.getPreviousPlayer(); // Our player is the current player
					player.setName(slot.getCurrentPlayer().getName());
					slot.setCurrentPlayer(player);
				}
				players.add(player);
			}
		}
		this.game.setPlayers(players);
		this.lobbyState = LobbyStates.STARTED_GAME;
		this.game.nextTurn();
	}
	
	public void readyPlayer(LobbyObserver o) throws RemoteException
	{
		this.assignedPlayersMap.get(o).setReady(true);
		
		this.checkReadyPlayers();
		this.notifyObservers();
	}
	
	private void clearPlayerSlot(LobbyObserver o) throws RemoteException
	{
		if(assignedPlayersMap.containsKey(o))
		{
			assignedPlayersMap.get(o).empty();
		}
	}
	
	public void unassignPlayer(LobbyObserver o) throws RemoteException
	{
		this.clearPlayerSlot(o); // Clear previous slot
		this.assignedPlayersMap.remove(o); // Unassign player from slot
		System.out.println("[DEBUG] LobbyImpl::unassignPlayer()::Unassigned player.");
		
		this.notifyObservers();
	}
	
	public void selectSlot(LobbyObserver o, PlayerSlot slot) throws RemoteException
	{
		if(!slot.isAvailable()) return;
		
		this.clearPlayerSlot(o); // Clear previous slot
		this.assignedPlayersMap.put(o, slot); // Change to new slot
		slot.setCurrentPlayer(playersMap.get(o)); // Update new slot
		
		this.notifyObservers();
	}
	
	private void checkReadyPlayers() throws RemoteException
	{
		boolean allReady = true;
		if(assignedPlayersMap.size() != playersMap.size()) return; // Not all connected players are assigned to a slot.
			
		for(PlayerSlot slot : assignedPlayersMap.values()) {
			if(!slot.isReady()) allReady = false;
		}
		
		if(allReady && assignedPlayersMap.size() >= MIN_PLAYERS_TO_START)
		{
			System.out.println("[DEBUG] LobbyImpl::checkReadyPlayers()::Starting game");
			this.startGame();
		}
	}
	
	// Probably would've done this onGameStart but this is the way its defined in the technical design.
	public void createPlayer(LobbyObserver o, String playerName) throws RemoteException {
		System.out.printf("[DEBUG] LobbyImpl::createPlayer()::Player '%s' has connected to the lobby!\n", playerName);
		
		PlayerImpl player = new PlayerImpl(playerName);
		this.playersMap.put(o, player);
		
		// Set first person to connect as host.
		if(this.host == null)
		{
			this.host = o;
		}
		
		this.notifyObservers();
	}
	
	public void disconnectPlayer(LobbyObserver o) throws RemoteException {
		this.clearPlayerSlot(o);
		assignedPlayersMap.remove(o);
		playersMap.remove(o);
		this.notifyObservers();
	}

	private void notifyObservers() throws RemoteException
	{
		System.out.println("[DEBUG] LobbyImpl::notifyObservers()::Notifying all lobby observers of change");
		for(LobbyObserver o : playersMap.keySet())
		{
			o.modelChanged(this);
		}
	}
	
	public boolean isAssigned(LobbyObserver o) throws RemoteException {
		return assignedPlayersMap.containsKey(o);
	}
	
	public List<PlayerSlot> getAssignedPlayerSlots() throws RemoteException {
		return new ArrayList<PlayerSlot>(assignedPlayersMap.values());
	}

	public List<PlayerSlot> getAssignableSlots() {
		return new ArrayList<PlayerSlot>(assignableSlots.values());
	}

	public List<Player> getUnassignedPlayers() throws RemoteException {
		List<Player> unassignedPlayers = new ArrayList<Player>();
		
		for(LobbyObserver o : playersMap.keySet())
		{
			if(!assignedPlayersMap.containsKey(o))
			{
				unassignedPlayers.add(playersMap.get(o));
			}
		}
		return unassignedPlayers;
	}

	public int getMaxPlayers() throws RemoteException {
		return maxPlayers;
	}
	
	public String getHostIP() throws RemoteException {
		return hostIP;
	}

	public GameImpl getGame() throws RemoteException {
		return game;//(Game) UnicastRemoteObject.exportObject(game, 1099);
	}
	
	public LobbyStates getLobbyState() throws RemoteException {
		return lobbyState;
	}
/*
	public Registry getRegistry() {
		return registry;
	}
*/
	public void setRegistry(Registry registry) {
		this.registry = registry;
	}


}
