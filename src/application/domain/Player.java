package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Player that can play the game
 * 
 * @author Sanchez
 *
 */
public interface Player extends Remote {
	/**
	 * Reserves a card from the field and puts it in the player's reserved card
	 * inventory
	 * 
	 * @param cardRow
	 * @param card
	 * @throws RemoteException
	 * @return void
	 */
	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException;

	/**
	 * Lets a player return tokens to the field when their owned tokens exceed a
	 * total of 10
	 * 
	 * @param tokens
	 * @param field
	 * @throws RemoteException
	 * @return void
	 */
	public void returnTokensToField(List<Token> tokens, PlayingField field) throws RemoteException;

	/**
	 * Adds an observer to the player which other classes can listen to
	 * 
	 * @param o
	 * @throws RemoteException
	 * @return void
	 */
	public void addObserver(PlayerObserver o) throws RemoteException;

	/**
	 * Removes an observer from the player
	 * 
	 * @param o
	 * @throws RemoteException
	 * @return void
	 */
	// public void removeObserver(PlayerObserver o) throws RemoteException;

	/**
	 * Adds a reserved card to the selectableCards list
	 * 
	 * @param card
	 * @throws RemoteException
	 * @return void
	 */
	public void addSelectableCardFromReserve(Card card) throws RemoteException;

	/**
	 * Adds a card to the player's owned card list
	 * 
	 * @param card
	 * @throws RemoteException
	 * @return void
	 */
	public void addCard(Card card) throws RemoteException;

	/**
	 * 
	 * Gets a list of all selectable (affordable) cards that are in the player's
	 * reserve
	 * 
	 * @throws RemoteException
	 * @return List<Card>
	 */
	public List<Card> getSelectableCardsFromReserve() throws RemoteException;

	/**
	 * 
	 * Finds all the selectable (affordable) cards that are in the player's reserve
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void findSelectableCardsFromReserve() throws RemoteException;

	/**
	 * 
	 * Determines if a player can afford a card
	 * 
	 * @param costs
	 * @throws RemoteException
	 * @return boolean
	 */
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException;

	/**
	 *
	 * Returns the name of the player
	 * 
	 * 
	 * @throws RemoteException
	 * @return String
	 */
	public String getName() throws RemoteException;

	/**
	 * 
	 * Gets all the cards that are in the player's reserve
	 * 
	 * @throws RemoteException
	 * @return List<Card>
	 */
	public List<Card> getReservedCards() throws RemoteException;

	/**
	 * 
	 * Gets all the cards that the player owns
	 * 
	 * 
	 * @throws RemoteException
	 * @return List<Card>
	 */
	public List<Card> getOwnedCards() throws RemoteException;

	/**
	 * 
	 * Gets all the nobles that the player owns
	 * 
	 * @throws RemoteException
	 * @return List<Noble>
	 */
	public List<Noble> getOwnedNobles() throws RemoteException;

	/**
	 * Gets all the nobles that the player owns
	 * 
	 * @throws RemoteException
	 * @return List<Token>
	 */
	public List<Token> getTokens() throws RemoteException;

	/**
	 * Gets the current prestige the player has
	 * 
	 * @throws RemoteException
	 * @return int
	 */
	public int getPrestige() throws RemoteException;

	/**
	 * Gets all the values of the tokens a player owns
	 * 
	 * @throws RemoteException
	 * @return Map<Gem,Integer>
	 */
	public Map<Gem, Integer> getTokensGemCount() throws RemoteException;

	/**
	 * Adds a token to the player's owned tokens (used for debugging)
	 * 
	 * @param token
	 * @throws RemoteException
	 * @return void
	 */
	public void debugAddToken(Token token) throws RemoteException;

	/**
	 * Gets the current bonus (dicount) a player is benefiting from
	 * 
	 * 
	 * @throws RemoteException
	 * @return Map<Gem,Integer>
	 */
	public Map<Gem, Integer> getBonus() throws RemoteException;

	/**
	 * Adds a noble to the player's owned nobles list
	 * 
	 * @param noble
	 * @throws RemoteException
	 * @return void
	 */
	public void addNoble(Noble noble) throws RemoteException;

	/**
	 * Adds a reserved card to the player's reserved card list
	 * 
	 * @param card
	 * @return void
	 */
	public void addReserverveCard(Card card) throws RemoteException;

	/**
	 * Adds a token to the player's owned tokens
	 * 
	 * @param token
	 * @throws RemoteException
	 * @return void
	 */
	public void addToken(Token token) throws RemoteException;

	/**
	 * Updates the player's view
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void updatePlayerView() throws RemoteException;

	public void clearSelectableCards() throws RemoteException;

}
