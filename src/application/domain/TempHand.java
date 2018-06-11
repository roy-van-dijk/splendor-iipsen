package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Interface TempHand.
 */
public interface TempHand extends Remote {
	
	/**
	 * Update player.
	 *
	 * @param player
	 * @throws RemoteException
	 */
	public void updatePlayer(Player player) throws RemoteException;
	
	/**
	 * Select card to buy.
	 *
	 * @param card
	 * @throws RemoteException
	 */
	public void selectCardToBuy(Card card) throws RemoteException;
	
	/**
	 * Select card to reserve.
	 *
	 * @param card
	 * @throws RemoteException
	 */
	public void selectCardToReserve(Card card) throws RemoteException;
	
	/**
	 * Gets the bought card.
	 *
	 * @return Card
	 * @throws RemoteException
	 */
	public Card getBoughtCard() throws RemoteException;
	
	/**
	 * Gets the reserved card.
	 *
	 * @return Card
	 * @throws RemoteException
	 */
	public Card getReservedCard() throws RemoteException;
	
	/**
	 * Sets the token list.
	 *
	 * @param tokenList
	 * @throws RemoteException
	 */
	public void setTokenList(TokenList tokenList) throws RemoteException;
	
	/**
	 * Gets the token list.
	 *
	 * @return TokenList
	 * @throws RemoteException
	 */
	public TokenList getTokenList() throws RemoteException;
	
	/**
	 * Adds the token.
	 *
	 * @param token
	 * @throws RemoteException
	 */
	public void addToken(Token token) throws RemoteException;
	
	/**
	 * Gets the selected tokens count.
	 *
	 * @return the selected tokens count
	 * @throws RemoteException
	 */
	public int getSelectedTokensCount() throws RemoteException;
	
	/**
	 * Gets the selected gem types.
	 *
	 * @return List<Gem>
	 * @throws RemoteException
	 */
	public List<Gem> getSelectedGemTypes() throws RemoteException;
	
	
	/**
	 * Clear the hand of the current player.
	 *
	 * @throws RemoteException
	 */
	public void emptyHand() throws RemoteException;

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 * @throws RemoteException
	 */
	public boolean isEmpty() throws RemoteException;
	
	/**
	 * Gets the move type.
	 *
	 * @return MoveType
	 * @throws RemoteException
	 */
	public MoveType getMoveType() throws RemoteException;

	/**
	 * Sets the move type.
	 *
	 * @param moveType
	 * @throws RemoteException
	 */
	public void setMoveType(MoveType moveType) throws RemoteException;
	
	/**
	 * Gets the player.
	 *
	 * @return Player
	 * @throws RemoteException
	 */
	public Player getPlayer() throws RemoteException;
}
