package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
/**
 * 
 * @author Kees
 *
 */
public class Turn implements Serializable {
	
	
	private List<Noble> nobles;
	private Card boughtCard;
	private Card reservedCard;
	private TokenList tokenList;
	private Player player;
	
	private MoveType moveType;
	
	public Turn() 
	{
		this.nobles = new ArrayList<>();
		this.tokenList = new TokenList();
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
	
	public TokenList getTokenList() 
	{
		return tokenList;
	}
	/**
	 * Clear the hand of the current player
	 * @throws RemoteException 
	 */
	public void emptyHand() throws RemoteException 
	{
		nobles.clear();
		tokenList.getAll().clear();
		reservedCard = null;
		boughtCard = null;
		System.out.printf("%s has emptied his hand \n", player.getName());
	}

	public MoveType getMoveType() 
	{
		return moveType;
	}

	public void setMoveType(MoveType moveType) 
	{
		this.moveType = moveType;
	}
	
}
