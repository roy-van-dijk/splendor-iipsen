package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameImpl implements Game {
	
	private int currentPlayerIdx;
	private int roundNr;
	
	private ArrayList<GameObserver> observers;
	private int observerIndex;
	
	private List<Player> players;
	
	private PlayingField playingField;
	
	/*
	 * The Game object knows how many players there are
	 * Therefore the game object determines how many tokens and nobles there need to be created. 
	 * This information is then passed onto the playingField, which generates those tokens and nobles.
	 */

	public GameImpl() {
		this.players = new ArrayList<Player>();
		this.observers = new ArrayList<GameObserver>();
		
		Test_Create4Players();
		currentPlayerIdx = 1; // First opponent (because 0 = Player in SP)
		
		this.playingField = new PlayingField(players.size());
	}
	
	@Override
	public void reserveCardFromField(CardRow cardRow, Card card) {
		System.out.println(cardRow.getCardDeck().getAll().size());
		cardRow.removeCard(card);
		Player currentPlayer = players.get(currentPlayerIdx);
		currentPlayer.addReservedCard(card);
		
		System.out.printf("%s has taken the card: %s", currentPlayer.getName() ,card.toString());
		this.notifyObservers();
	}
	
	public void notifyObservers() {
		for (GameObserver observer : observers) {
			observer.modelChanged(this);
		}
	}
	
	public void addObserver(GameObserver observer) {
		// NextObserver
		this.observers.add(observer);
		this.notifyObservers(); // Added bonus of triggering initialization for the recently-added observer. 
	}
	
	private void Test_Create4Players()
	{
		this.addPlayer(new Player("Bob"));
		this.addPlayer(new Player("Michael"));
		this.addPlayer(new Player("Peter"));
		this.addPlayer(new Player("Martin"));
	}
	
	public void addPlayer(Player player)
	{
		players.add(player);
	}

	public int getCurrentPlayerIdx() {
		return currentPlayerIdx;
	}

	public int getRoundNr() {
		return roundNr;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public PlayingField getPlayingField() {
		return playingField;
	}
	
}
