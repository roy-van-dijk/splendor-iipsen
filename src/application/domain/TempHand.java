package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

//TODO javadoc Re-check
/**
 *  
 *
 * @author Kees
 */
///Turn is tijdelijk hand
// TODO: give Turn a remote interface.
public class TempHand implements Serializable {
	
	private Card boughtCard;
	private Card reservedCard;
	private TokenList tokenList;
	private Player player;
	
	private MoveType moveType;

	private int selectedTokensCount;
	private List<Gem> selectedGemTypes;
	
	/**
	 * Instantiates a new temp hand.
	 */
	public TempHand() 
	{
		this.selectedGemTypes = new ArrayList<>();
		this.tokenList = new TokenList();
	}
	
	/**
	 * Update player.
	 *
	 * @param player
	 */
	public void updatePlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Select card to buy.
	 *
	 * @param card
	 */
	public void selectCardToBuy(Card card) {
		boughtCard = card;
	}
	
	/**
	 * Select card to reserve.
	 *
	 * @param card
	 */
	public void selectCardToReserve(Card card) {
		reservedCard = card;
	}
	
	/**
	 * Gets the bought card.
	 *
	 * @return Card
	 */
	public Card getBoughtCard() {
		return boughtCard;
	}
	
	/**
	 * Gets the reserved card.
	 *
	 * @return Card the reserved card
	 */
	public Card getReservedCard() {
		return reservedCard;
	}
	
	/**
	 * Sets the token list.
	 *
	 * @param tokenList the new token list
	 */
	public void setTokenList(TokenList tokenList) {
		this.tokenList = tokenList;
	}
	
	/**
	 * Gets the token list.
	 *
	 * @return tokenlist
	 */
	public TokenList getTokenList() {
		return tokenList;
	}
	
	/**
	 * Adds the token to tokenlist.
	 *
	 * @param token
	 */
	public void addToken(Token token) {
		selectedTokensCount++;
		tokenList.add(token);
		selectedGemTypes.add(token.getGemType());
	}
	
	/**
	 * Gets the selected tokens count.
	 *
	 * @return the selected tokens count
	 */
	public int getSelectedTokensCount() {
		return selectedTokensCount;
	}
	
	/**
	 * Gets the selected gem types.
	 *
	 * @return List<Gem>
	 */
	public List<Gem> getSelectedGemTypes() {
		return selectedGemTypes;
	}
	
	/**
	 * Clear the hand of the current player.
	 *
	 * @throws RemoteException
	 */
	public void emptyHand() throws RemoteException 
	{
		selectedGemTypes.clear();
		tokenList.getAll().clear();
		reservedCard = null;
		boughtCard = null;
		selectedTokensCount = 0;
		//System.out.printf("%s has emptied his hand \n", player.getName());
	}

	/**
	 * Gets the move type.
	 *
	 * @return MoveType
	 */
	public MoveType getMoveType() 
	{
		return moveType;
	}

	/**
	 * Sets the move type.
	 *
	 * @param moveType the new move type
	 */
	public void setMoveType(MoveType moveType) 
	{
		this.moveType = moveType;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}
	
}
