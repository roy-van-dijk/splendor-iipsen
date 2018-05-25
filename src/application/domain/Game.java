package application.domain;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Sanchez
 *
 */
public interface Game {
	
	public void addPlayer(Player player);

	public int getCurrentPlayerIdx();

	public int getRoundNr();

	public List<Player> getPlayers();

	public PlayingField getPlayingField();
	
	public void reserveCardFromField(CardRow cardRow, Card card);
	
	
	public void addObserver(GameObserver observer);
}
