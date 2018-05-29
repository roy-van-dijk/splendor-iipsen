package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class LobbyImpl implements Lobby {

	private final static int DEFAULT_MAX_PLAYERS = 4;
	
	private List<Player> assignedPlayers;
	private List<Player> unassignedPlayers;
	private List<LobbyObserver> observers;
	
	
	private int maxPlayers;
	
	public LobbyImpl(int maxPlayers) {
		this.maxPlayers = maxPlayers;
		
		this.observers = new ArrayList<LobbyObserver>();
		this.assignedPlayers = new ArrayList<Player>();
		this.unassignedPlayers = new ArrayList<Player>();
	}
	
	public LobbyImpl() {
		this(DEFAULT_MAX_PLAYERS);
	}

	public void addObserver(LobbyObserver o) throws RemoteException {
		observers.add(o);
		this.notifyObservers();
	}

	public void removeObserver(LobbyObserver o) throws RemoteException {
		observers.remove(o);
		this.notifyObservers();
	}
	
	public void connectPlayer(Player player) throws RemoteException {
		unassignedPlayers.add(player);
		this.notifyObservers();
	}

	public void disconnectPlayer(Player player) throws RemoteException {
		unassignedPlayers.remove(player);
		assignedPlayers.remove(player);
		this.notifyObservers();
	}

	private void notifyObservers() throws RemoteException
	{
		for(LobbyObserver o : observers)
		{
			o.modelChanged(this);
		}
	}

	public List<Player> getAssignedPlayers() throws RemoteException {
		return assignedPlayers;
	}

	public List<Player> getUnassignedPlayers() throws RemoteException {
		return unassignedPlayers;
	}

	@Override
	public int getMaxPlayers() throws RemoteException {
		return maxPlayers;
	}
	
}
