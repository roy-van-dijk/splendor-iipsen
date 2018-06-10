package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The playing field is the top right of the game. It is filled with card rows,
 * nobles, decks, and tokens
 * 
 * @author Alexander
 *
 */
public interface PlayingField extends Remote {

	/**
	 * Gets all card rows that are currently on the field
	 * 
	 * @throws RemoteException
	 * @return List<CardRow>
	 */
	public List<CardRow> getCardRows() throws RemoteException;

	/**
	 * Gets all nobles currently on the field
	 * 
	 * @throws RemoteException
	 * @return List<Noble>
	 */
	public List<Noble> getNobles() throws RemoteException;

	/**
	 * Gets the amount of each gem token currently on the field
	 * 
	 * @throws RemoteException
	 * @return LinkedHashMap<Gem,Integer>
	 */
	public LinkedHashMap<Gem, Integer> getTokenGemCount() throws RemoteException;

	/**
	 * Gets all the selectable (affordable) tokens currently on the field
	 * 
	 * @throws RemoteException
	 * @return List<Gem>
	 */
	public List<Gem> getSelectableTokens() throws RemoteException;

	/**
	 * Gets the player's temporary hard
	 * 
	 * @throws RemoteException
	 * @return TempHand
	 */
	public TempHand getTempHand() throws RemoteException;

	/**
	 * Finds all selectable (affordable) cards currently on the field
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void findSelectableCardsFromField() throws RemoteException;

	/**
	 * Gets all selectable (affordable) cards currently on the field
	 * 
	 * @throws RemoteException
	 * @return List<Card>
	 */
	public List<Card> getSelectableCardsFromField() throws RemoteException;

	/**
	 * Adds the passed token to the player's temporary hand
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void addTokenToTemp(Gem gemType) throws RemoteException;

	/**
	 * Adds an observer to the playing field that other classes can listen to
	 * 
	 * @param observer
	 * @throws RemoteException
	 * @return void
	 */
	public void addObserver(PlayingFieldObserver observer) throws RemoteException;

	/**
	 * Adds the passed tokens to the playing field
	 * 
	 * @param tokens
	 * @throws RemoteException
	 * @return void
	 */
	public void addTokens(List<Token> tokens) throws RemoteException;

	/**
	 * Marks selectable (affordable) tokens
	 * 
	 * @param moveType
	 * @throws RemoteException
	 * @return void
	 */
	public void setTokensSelectable(MoveType moveType) throws RemoteException;

	/**
	 * Switches the current turn to that of the next player
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void newTurn() throws RemoteException;

	/**
	 * Removes the passed noble from the field
	 * 
	 * @param noble
	 * @throws RemoteException
	 * @return void
	 */
	public void removeNoble(Noble noble) throws RemoteException;

	/**
	 * Removes the passed card from the field
	 * 
	 * @param card
	 * @return void
	 */
	public void removeCard(Card card) throws RemoteException;

	/**
	 * Removes the passed tokens from the field
	 * 
	 * @param tokens
	 * @throws RemoteException
	 * @return void
	 */
	public void removeTokens(List<Token> tokens) throws RemoteException;

	/**
	 * Gets a list of all tokens currently on the field
	 * 
	 * @throws RemoteException
	 * @return TokenList
	 */
	public TokenList getTokenList() throws RemoteException;

	/**
	 * Removes the passed token from the field
	 * 
	 * @param token
	 * @throws RemoteException
	 */
	public void removeToken(Token token) throws RemoteException;

	/**
	 * Marks a deck as selected
	 * 
	 * @param cardRow
	 * @throws RemoteException
	 * @return void
	 */
	public void setDeckSelected(CardRow cardRow) throws RemoteException;

	/**
	 * Unmarks a deck as selected
	 * 
	 * @throws RemoteException
	 * @return void
	 */
	public void setDeckDeselected() throws RemoteException;

}
