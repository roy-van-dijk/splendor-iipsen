package application.domain;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import application.util.Util;

/**
 * @author Sanchez
 *
 */
public class LobbyImpl extends UnicastRemoteObject implements Lobby, Serializable {

	private static final long serialVersionUID = 1L;
	public enum LobbyStates { WAITING, STARTED_GAME };
	
	private LobbyStates lobbyState; 
	
	private Map<Integer, PlayerSlot> assignedPlayerSlots;
	private List<Player> unassignedPlayers;
	private List<LobbyObserver> observers;
	
	private String hostIP;
	private int maxPlayers;
	
	private GameImpl game;
	
	public LobbyImpl(GameImpl game) throws RemoteException {
		this.game = game;
		this.maxPlayers = game.getMaxPlayers();
		this.lobbyState = LobbyStates.WAITING;
		
		try {
			this.hostIP  = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		this.observers = new ArrayList<>();
		this.assignedPlayerSlots = new HashMap<>();
		this.unassignedPlayers = new ArrayList<>();
		
		this.createPlayerSlots();
	}
	
	private void createPlayerSlots() throws RemoteException
	{
		List<Player> previousPlayers = game.getPlayers();
		for(int i = 0; i < this.maxPlayers; i++)
		{
			PlayerSlotImpl slot = new PlayerSlotImpl();
			if(previousPlayers.get(i) != null)
			{
				slot.setPlayer(previousPlayers.get(i)); // TODO: Won't work. Can be overwritten.  Game.getPlayers() has history.
				// Also, how to tell difference between historic Player and actual new Player?
			}
			assignedPlayerSlots.put(i, slot); 
		}
	}
	
	private void startGame() throws RemoteException
	{
		List<Player> players = new ArrayList<Player>();
		for(PlayerSlot slot : assignedPlayerSlots.values())
		{
			players.add(slot.getPlayer());
		}
		game.setPlayers(players); // TODO: what about pre-existing players?
		lobbyState = LobbyStates.STARTED_GAME;
	}
	
	public void readyPlayer(int slotIdx) throws RemoteException
	{
		assignedPlayerSlots.get(slotIdx).setReady(true);
		checkReadyPlayers();
		this.notifyObservers();
	}
	
	// selectSlot function may have to keep track of previousSlot (and then restore that slot to its former glory)
	
	private void checkReadyPlayers() throws RemoteException
	{
		boolean allReady = true;
		for(PlayerSlot slot : assignedPlayerSlots.values()) {
			if(!slot.isReady()) allReady = false;
		}
		
		if(allReady)
		{
			startGame();
		}
	}

	public void addObserver(LobbyObserver o) throws RemoteException {
		observers.add(o);
		this.notifyObservers();
	}

	public void removeObserver(LobbyObserver o) throws RemoteException {
		observers.remove(o);
		this.notifyObservers();
	}
	
	public void createPlayer(String playerName) throws RemoteException {
		System.out.printf("Player %s has connected to the lobby.\n", playerName);
		PlayerImpl player = new PlayerImpl(playerName);
		unassignedPlayers.add(player);
		this.notifyObservers();
	}

	public void disconnectPlayer(Player player) throws RemoteException {
		unassignedPlayers.remove(player);
		
		// Remove player from assigned player slot
		Iterator<Entry<Integer, PlayerSlot>> it = assignedPlayerSlots.entrySet().iterator();
		while (it.hasNext()) {
			if(it.next().getValue().getPlayer().equals(player))
			{
				it.remove();
				break;
			}
		}
		this.notifyObservers();
	}

	private void notifyObservers() throws RemoteException
	{
		System.out.println("Notifying lobby observers of change");
		for(LobbyObserver o : observers)
		{
			o.modelChanged(this);
		}
	}

	public Map<Integer, PlayerSlot> getAssignedPlayers() throws RemoteException {
		return assignedPlayerSlots;
	}

	public List<Player> getUnassignedPlayers() throws RemoteException {
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
	
}
