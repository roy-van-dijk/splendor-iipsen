package application.domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.services.SaveGameDAO;
import application.views.GameView;

/**
 * @author Sanchez
 *
 */
public class GameImpl extends UnicastRemoteObject implements Game, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2852281344739846301L;
	
	private GameState gameState;
	
	private PlayingFieldImpl playingField;
	
	private int currentPlayerIdx;
	private int roundNr;
	private int maxPlayers;

	private List<Player> players; // Contains a list of PlayerImpl on server
	
	//private transient List<GameObserver> observers;
	private transient Map<GameObserver, Player> observers;
	
	public GameImpl(int maxPlayers) throws RemoteException {
		this.maxPlayers = maxPlayers;
		
		this.roundNr = 0;
		this.currentPlayerIdx = -1;
		
		this.players = new ArrayList<Player>();
		this.observers = new LinkedHashMap<GameObserver, Player>();
		
		Test_Create4Players();
		
		this.playingField = new PlayingFieldImpl(this.maxPlayers);
	}
	
	/*public Player getPlayer(GameObserver o) throws RemoteException
	{
		return playersMap.get(o);
	}*/

	
	public void nextTurn() throws RemoteException
	{
		System.out.println("Next turn started");
		currentPlayerIdx++;
		if(currentPlayerIdx >= players.size() || currentPlayerIdx < 0) {
			currentPlayerIdx = 0;
		}
		
		try {
			playingField.newTurn();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Current player ID = " + currentPlayerIdx);
		this.notifyObservers();
	}
	
	public boolean isDisabled(GameObserver o) throws RemoteException
	{
		Player player = observers.get(o); // THIS IS STILL A PROXY REFERENCE BECAUSE ADDOBSERVER ADDS A PROXY
/*		System.out.printf("Checking if %s is disabled, current player = %s\n", player.getName(), this.getCurrentPlayer().getName());
		System.out.printf("Checking if %s is disabled, current player = %s\n", player, this.getCurrentPlayer());
		System.out.println("is equal: " + (this.getCurrentPlayer().equals(player)));*/
		// Equals check won't work probably if one of the players is a PlayerImpl instead of a proxy ref to a Player
		return !(player.getName().equals(this.getCurrentPlayer().getName())); // Checking for name as a workaround for the proxy-ref problem.
	}
	
	public void saveGame() throws RemoteException
	{
		try {
			SaveGameDAO.getInstance().saveGameToFile(this);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void Test_Create4Players()
	{
		try {
			this.players.add(new PlayerImpl("Bob"));
			
			PlayerImpl player = new PlayerImpl("Michael");
			for(int i = 0; i < 13; i++)
			{
				Gem[] allGems = Gem.values();
				int randomIdx = (int) (Math.random() * allGems.length);
				player.debugAddToken(new TokenImpl(allGems[randomIdx]));
			}
			this.players.add(player);
			this.players.add(new PlayerImpl("Peter"));
			this.players.add(new PlayerImpl("Martin"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findSelectableCards() throws RemoteException {
		this.getCurrentPlayer().findSelectableCardsFromReserve();
		playingField.findSelectableCardsFromField();
	}

	public int getCurrentPlayerIdx() throws RemoteException {
		return currentPlayerIdx;
	}

	public int getRoundNr() throws RemoteException {
		return roundNr;
	}

	public List<Player> getPlayers() throws RemoteException {
		return players;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public PlayingField getPlayingField() throws RemoteException {
		return playingField;
	}

	
	public Player getCurrentPlayer() throws RemoteException {
		return players.get(currentPlayerIdx);
	}

	public int getMaxPlayers() throws RemoteException {
		return maxPlayers;
	}

	public GameState getGameState() throws RemoteException {
		return gameState;
	}
	
	private synchronized void notifyObservers() throws RemoteException
	{
		System.out.println("[DEBUG] GameImpl::notifyObservers()::Notifying all game observers of change");
		for(GameObserver o : observers.keySet())
		{
			o.modelChanged(this);
		}
	}

	@Override
	public synchronized void addObserver(GameObserver o, Player player) throws RemoteException {
		this.observers.put(o, player);
		this.notifyObservers();
	}

	@Override
	public synchronized void removeObserver(GameObserver o) throws RemoteException {
		this.observers.remove(o);
		this.notifyObservers();
	}

	@Override
	public void cleanUpTurn() throws RemoteException {
		playingField.getTempHand().emptyHand();
	}

}
