package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
/**
 * Turn is tijdelijk hand 
 * @author Kees
 *
 */
// TODO: give Turn a remote interface.
public class TempHand implements Serializable {
	
	
	private List<Noble> nobles;
	private Card boughtCard;
	private Card reservedCard;
	private TokenList tokenList;
	private Player player;
	
	private MoveType moveType;

	private int selectedTokensCount;
	private List<Gem> selectedGemTypes;
	
	public TempHand() 
	{
		this.nobles = new ArrayList<>();
		this.selectedGemTypes = new ArrayList<>();
		this.tokenList = new TokenList();
	}
	
	public void updatePlayer(Player player) {
		this.player = player;
	}
	
	public void selectNoble(Noble noble) 
	{
		nobles.add(noble);
	}
	
	public void selectCardToBuy(Card card) 
	{
		boughtCard = card;
	}
	
	public void selectCardToReserve(Card card) 
	{
		reservedCard = card;
	}
	
	public Card getBoughtCard() 
	{
		return boughtCard;
	}
	
	public Card getReservedCard() 
	{
		return reservedCard;
	}
	
	public void setTokenList(TokenList tokenList) {
		this.tokenList = tokenList;
	}
	
	public TokenList getTokenList() 
	{
		return tokenList;
	}
	
	public void addToken(Token token) {
		selectedTokensCount++;
		tokenList.add(token);
		selectedGemTypes.add(token.getGemType());
	}
	
	public int getSelectedTokensCount() {
		return selectedTokensCount;
	}
	/**
	 * 
	 * @return
	 * List<Gem>
	 */
	public List<Gem> getSelectedGemTypes() {
		return selectedGemTypes;
	}
	/**
	 * Clear the hand of the current player
	 * @throws RemoteException 
	 */
	public void emptyHand() throws RemoteException 
	{
		nobles.clear();
		selectedGemTypes.clear();
		tokenList.getAll().clear();
		reservedCard = null;
		boughtCard = null;
		selectedTokensCount = 0;
		//System.out.printf("%s has emptied his hand \n", player.getName());
	}

	public MoveType getMoveType() 
	{
		return moveType;
	}

	public void setMoveType(MoveType moveType) 
	{
		this.moveType = moveType;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
