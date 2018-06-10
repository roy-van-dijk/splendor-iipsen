package application.domain;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface Game.
 *
 * @author Sanchez
 */
public interface Game extends Remote {

	/**
	 * Next turn.
	 *
	 * @throws RemoteException
	 */
	public void nextTurn() throws RemoteException;
	
	/**
	 * Adds the observer.
	 *
	 * @param o the GameObserver
	 * @throws RemoteException
	 */
	public void addObserver(GameObserver o) throws RemoteException;
	
	/**
	 * Removes the observer.
	 *
	 * @param o the GameObserver
	 * @throws RemoteException
	 */
	public void removeObserver(GameObserver o) throws RemoteException;
	
	/**
	 * Save the game.
	 *
	 * @throws RemoteException
	 */
	public void saveGame() throws RemoteException;
	
	/**
	 * Find selectable cards.
	 *
	 * @throws RemoteException
	 */
	public void findSelectableCards() throws RemoteException;
	
	/**
	 * Gets the current player idx.
	 *
	 * @return the current player idx
	 * @throws RemoteException the remote exception
	 */
	public int getCurrentPlayerIdx() throws RemoteException;
	
	/**
	 * Gets the current player.
	 *
	 * @return Player
	 * @throws RemoteException
	 */
	public Player getCurrentPlayer() throws RemoteException;

	/**
	 * Gets the round nr.
	 *
	 * @return the round nr
	 * @throws RemoteException
	 */
	public int getRoundNr() throws RemoteException;

	/**
	 * Gets the players.
	 *
	 * @return the players
	 * @throws RemoteException
	 */
	public List<Player> getPlayers() throws RemoteException;
	
	/**
	 * Gets the playing field.
	 *
	 * @return PlayingField
	 * @throws RemoteException
	 */
	public PlayingField getPlayingField() throws RemoteException;

	/**
	 * Adds the card to temp from reserve.
	 *
	 * @param card
	 * @throws RemoteException
	 */
	public void addCardToTempFromReserve(Card card) throws RemoteException;

	/**
	 * Gets the end turn.
	 *
	 * @return EndTurn
	 * @throws RemoteException
	 */
	public EndTurn getEndTurn() throws RemoteException;

	/**
	 * Clean up selections.
	 *
	 * @throws RemoteException
	 */
	public void cleanUpSelections() throws RemoteException;

	/**
	 * Update playing field and player view.
	 *
	 * @throws RemoteException
	 */
	public void updatePlayingFieldAndPlayerView() throws RemoteException;

}
