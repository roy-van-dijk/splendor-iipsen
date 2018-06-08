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
	 * 
	 * @return
	 * @throws RemoteException
	 * Player
	 */
	public Player getCurrentPlayer() throws RemoteException;

	public int getRoundNr() throws RemoteException;

	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Player>
	 */
	public List<Player> getPlayers() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * PlayingField
	 */
	public PlayingField getPlayingField() throws RemoteException;
	
	public void updatePlayingFieldCardsAndPlayerView() throws RemoteException;

	public void addCardToTempFromReserve(Card card) throws RemoteException;

	public EndTurn getEndTurn() throws RemoteException;

	public void cleanUpSelections() throws RemoteException;

}
