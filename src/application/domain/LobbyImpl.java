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
 * The Class LobbyImpl.
 *
 * @author Sanchez
 */
public class LobbyImpl extends UnicastRemoteObject implements Lobby {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The Enum LobbyStates.
	 */
	public enum LobbyStates { WAITING, STARTED_GAME };
	
	private LobbyStates lobbyState; 
	
	private Map<Integer, PlayerSlot> assignedPlayerSlots;
	private List<Player> unassignedPlayers;
	private List<LobbyObserver> observers;
	
	private String hostIP;
	private int maxPlayers;
	
	private GameImpl game;
	
	/**
	 * Instantiates a new lobby impl.
	 *
	 * @param game
	 * @throws RemoteException
	 */
	public LobbyImpl(GameImpl game) throws RemoteException {
		this.game = game;
		this.maxPlayers = game.getMaxPlayers();
		this.lobbyState = LobbyStates.WAITING;
		
		try {
			this.hostIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		this.observers = new ArrayList<>();
		this.assignedPlayerSlots = new HashMap<>();
		this.unassignedPlayers = new ArrayList<>();
		
		this.createPlayerSlots();
	}
	
	/**
	 * Creates the player slots.
	 *
	 * @throws RemoteException
	 */
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
	
	/**
	 * Start game.
	 *
	 * @throws RemoteException
	 */
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
	
	/**
	 * Ready player.
	 *
	 * @param slotIdx
	 * @throws RemoteException
	 */
	public void readyPlayer(int slotIdx) throws RemoteException
	{
		assignedPlayerSlots.get(slotIdx).setReady(true);
		checkReadyPlayers();
		this.notifyObservers();
	}
	
	// selectSlot function may have to keep track of previousSlot (and then restore that slot to its former glory)
	
	/**
	 * Check ready players.
	 *
	 * @throws RemoteException
	 */
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

	/* (non-Javadoc)
	 * @see application.domain.Lobby#addObserver(application.domain.LobbyObserver)
	 */
	public void addObserver(LobbyObserver o) throws RemoteException {
		observers.add(o);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Lobby#removeObserver(application.domain.LobbyObserver)
	 */
	public void removeObserver(LobbyObserver o) throws RemoteException {
		observers.remove(o);
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Lobby#createPlayer(java.lang.String)
	 */
	public void createPlayer(String playerName) throws RemoteException {
		System.out.printf("Player %s has connected to the lobby.\n", playerName);
		PlayerImpl player = new PlayerImpl(playerName);
		unassignedPlayers.add(player);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Lobby#disconnectPlayer(application.domain.Player)
	 */
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

	/**
	 * Notify observers.
	 *
	 * @throws RemoteException
	 */
	private void notifyObservers() throws RemoteException
	{
		System.out.println("Notifying lobby observers of change");
		for(LobbyObserver o : observers)
		{
			o.modelChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.Lobby#getAssignedPlayers()
	 */
	public Map<Integer, PlayerSlot> getAssignedPlayers() throws RemoteException {
		return assignedPlayerSlots;
	}

	/* (non-Javadoc)
	 * @see application.domain.Lobby#getUnassignedPlayers()
	 */
	public List<Player> getUnassignedPlayers() throws RemoteException {
		return unassignedPlayers;
	}

	/* (non-Javadoc)
	 * @see application.domain.Lobby#getMaxPlayers()
	 */
	public int getMaxPlayers() throws RemoteException {
		return maxPlayers;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Lobby#getHostIP()
	 */
	public String getHostIP() throws RemoteException {
		return hostIP;
	}

	/* (non-Javadoc)
	 * @see application.domain.Lobby#getGame()
	 */
	public GameImpl getGame() throws RemoteException {
		return game;//(Game) UnicastRemoteObject.exportObject(game, 1099);
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Lobby#getLobbyState()
	 */
	public LobbyStates getLobbyState() throws RemoteException {
		return lobbyState;
	}
	
}
