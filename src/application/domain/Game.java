package application.domain;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Sanchez
 *
 */
public interface Game extends Remote {

	public void nextTurn() throws RemoteException;
	
	public void addObserver(GameObserver o) throws RemoteException;
	public void removeObserver(GameObserver o) throws RemoteException;
	
	public void saveGame() throws RemoteException;
	
	public void findSelectableCards() throws RemoteException;
	
	public int getCurrentPlayerIdx() throws RemoteException;
	
	/**
	 * Convenience function
	 */
	public Player getCurrentPlayer() throws RemoteException;

	public int getRoundNr() throws RemoteException;

	public List<Player> getPlayers() throws RemoteException;

	public PlayingField getPlayingField() throws RemoteException;

	public void cleanUpTurn() throws RemoteException;

}
