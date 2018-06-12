package application.domain;

import java.util.List;

import java.rmi.Remote;
import java.rmi.RemoteException;

// TODO: Auto-generated Javadoc
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
	 * @param player
	 * @throws RemoteException
	 */
	public void addObserver(GameObserver o, Player player) throws RemoteException;
	
	/**
	 * Removes the observer.
	 *
	 * @param o the GameObserver
	 * @throws RemoteException
	 */
	public void removeObserver(GameObserver o) throws RemoteException;
	
	/**
	 * Notify observers.
	 *
	 * @throws RemoteException
	 */
	public void notifyObservers() throws RemoteException;
	
	/**
	 * Save the game.
	 *
	 * @throws RemoteException
	 */
	public void saveGame() throws RemoteException;
	
	/**
	 * Find selectable cards.
	 *
	 * @param moveType 
	 * @throws RemoteException
	 */
	public void findSelectableCards(MoveType moveType) throws RemoteException;
	
	/**
	 * Gets the current player idx.
	 *
	 * @return the current player idx
	 * @throws RemoteException
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
	 * @return List<Player>
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
	 * Clean up turn.
	 *
	 * @throws RemoteException
	 */
	public void cleanUpTurn() throws RemoteException;
	
	/**
	 * Checks if is disabled.
	 *
	 * @param o the GameObserver
	 * @return true, if is disabled
	 * @throws RemoteException
	 */
	public boolean isDisabled(GameObserver o) throws RemoteException;

	/**
	 * Reserve card from deck.
	 *
	 * @param cardRowIdx
	 * @throws RemoteException
	 */
	public void reserveCardFromDeck(int cardRowIdx) throws RemoteException;
	
	/**
	 * Adds the card to temp from field.
	 *
	 * @param cardRowIdx
	 * @param cardIdx
	 * @throws RemoteException
	 */
	public void addCardToTempFromField(int cardRowIdx, int cardIdx) throws RemoteException;
	
	/**
	 * Adds the card to temp from reserve.
	 *
	 * @param cardIdx
	 * @throws RemoteException
	 */
	public void addCardToTempFromReserve(int cardIdx) throws RemoteException;

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

	/**
	 * Sets the tokens selectable.
	 *
	 * @param moveType
	 * @throws RemoteException
	 */
	public void setTokensSelectable(MoveType moveType) throws RemoteException;

	/**
	 * Adds the token to temp.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public void addTokenToTemp(Gem gemType) throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public boolean reserveCardInventoryFull() throws RemoteException;
	/**
	 * close the curent running game
	 * @throws RemoteException
	 */
	public void terminateGame() throws RemoteException;
	/**
	 * winner of the game
	 * @param winningPlayer
	 * @throws RemoteException
	 */
	public void playerHasWon(Player winningPlayer) throws RemoteException;
	/**
	 * get the winning player
	 * @return
	 * @throws RemoteException
	 */
	public Player getWinningPlayer() throws RemoteException;
	
	public boolean anyCardsPurchasable() throws RemoteException;

}
