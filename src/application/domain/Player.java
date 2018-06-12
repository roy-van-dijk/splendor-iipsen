package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Player that can play the game.
 *
 * @author Sanchez
 */
public interface Player extends Remote {
	
	/**
	 * Reserves a card from the field and puts it in the player's reserved card
	 * inventory.
	 *
	 * @param cardRow
	 * @param card
	 * @throws RemoteException
	 */
	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException;

	/**
	 * Lets a player return tokens to the field when their owned tokens exceed a
	 * total of 10.
	 *
	 * @param tokens
	 * @param field
	 * @throws RemoteException
	 */
	public void returnTokensToField(List<Token> tokens, PlayingField field) throws RemoteException;

	/**
	 * Adds an observer to the player which other classes can listen to.
	 *
	 * @param o the PlayerObserver
	 * @throws RemoteException
	 */
	public void addObserver(PlayerObserver o) throws RemoteException;

	
	// public void removeObserver(PlayerObserver o) throws RemoteException;

	/**
	 * Adds a card to the player's owned card list
	 * 
	 * @param card
	 * @throws RemoteException
	 */
	public void addCard(Card card) throws RemoteException;

	/**
	 * Gets a list of all selectable (affordable) cards that are in the player's
	 * reserve.
	 *
	 * @return List<Card>
	 * @throws RemoteException
	 */
	public List<Card> getSelectableCardsFromReserve() throws RemoteException;

	/**
	 * Finds all the selectable (affordable) cards that are in the player's reserve.
	 *
	 * @throws RemoteException
	 */
	public void findSelectableCardsFromReserve() throws RemoteException;

	/**
	 * Determines if a player can afford a card.
	 *
	 * @param costs
	 * @return boolean
	 * @throws RemoteException
	 */
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException;

	/**
	 * Returns the name of the player.
	 *
	 * @return String
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
	
	/**
	 * Sets the name.
	 *
	 * @param name 
	 * @throws RemoteException
	 */
	public void setName(String name) throws RemoteException;


	/**
	 * Gets all the cards that are in the player's reserve.
	 *
	 * @return List<Card>
	 * @throws RemoteException
	 */
	public List<Card> getReservedCards() throws RemoteException;

	/**
	 * Gets all the cards that the player owns.
	 *
	 * @return List<Card>
	 * @throws RemoteException 
	 */
	public List<Card> getOwnedCards() throws RemoteException;

	/**
	 * Gets all the nobles that the player owns.
	 *
	 * @return List<Noble>
	 * @throws RemoteException
	 */
	public List<Noble> getOwnedNobles() throws RemoteException;

	/**
	 * Gets all the tokens that the player owns.
	 *
	 * @return List<Token>
	 * @throws RemoteException
	 */
	public List<Token> getTokens() throws RemoteException;

	/**
	 * Gets the current prestige the player has.
	 *
	 * @return int
	 * @throws RemoteException
	 */
	public int getPrestige() throws RemoteException;

	/**
	 * Gets all the values of the tokens a player owns.
	 *
	 * @return Map<Gem,Integer>
	 * @throws RemoteException
	 */
	public Map<Gem, Integer> getTokensGemCount() throws RemoteException;

	/**
	 * Adds a token to the player's owned tokens (used for debugging).
	 *
	 * @param token the token
	 * @throws RemoteException
	 */
	public void debugAddToken(Token token) throws RemoteException;

	/**
	 * Gets the current bonus (dicount) a player is benefiting from.
	 *
	 * @return Map<Gem,Integer>
	 * @throws RemoteException
	 */
	public Map<Gem, Integer> getBonus() throws RemoteException;

	/**
	 * Adds a noble to the player's owned nobles list.
	 *
	 * @param noble the noble
	 * @throws RemoteException
	 */
	public void addNoble(Noble noble) throws RemoteException;

	/**
	 * Adds a reserved card to the player's reserved card list.
	 *
	 * @param card the card
	 * @throws RemoteException
	 */
	public void addReservedCard(Card card) throws RemoteException;

	/**
	 * Adds a token to the player's owned tokens.
	 *
	 * @param token the token
	 * @throws RemoteException
	 */
	public void addToken(Token token) throws RemoteException;

	/**
	 * Updates the player's view.
	 *
	 * @throws RemoteException
	 */
	public void updatePlayerView() throws RemoteException;

	/**
	 * Clear selectable cards.
	 *
	 * @throws RemoteException
	 */
	public void clearSelectableCards() throws RemoteException;

}
