package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sanchez
 *
 */
public class GameImpl implements Game {
	
	private int currentPlayerIdx;
	private int roundNr;
	
	private List<Player> players;
	
	private PlayingField playingField;
	
	private Turn turn;
	
	/*
	 * The Game object knows how many players there are
	 * Therefore the game object determines how many tokens and nobles there need to be created. 
	 * This information is then passed onto the playingField, which generates those tokens and nobles.
	 */

	public GameImpl(List<Player> players) {
		this.players = new ArrayList<Player>(); // TODO: replace with: this.players = players;
		
		Test_Create4Players();
		
		this.roundNr = 0;
		this.currentPlayerIdx = 1; // TODO: First opponent starts first for now (1 because 0 = Player in SP)
		

		this.playingField = new PlayingFieldImpl(this.players.size());

		this.turn = new Turn(getPlayers().get(currentPlayerIdx));
	}
	
	public void nextTurn()
	{
		currentPlayerIdx++;
		if(currentPlayerIdx >= players.size())
			currentPlayerIdx = 0;
	}


	private void Test_Create4Players()
	{
		this.players.add(new PlayerImpl("Bob"));
		this.players.add(new PlayerImpl("Michael"));
		this.players.add(new PlayerImpl("Peter"));
		this.players.add(new PlayerImpl("Martin"));
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

	
	public Player getCurrentPlayer() {
		return players.get(currentPlayerIdx);
	}
	
	public Turn getTurn() {
		return this.turn;
	}
	
}
