package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import application.util.Logger;
import application.util.Logger.Verbosity;
import javafx.scene.Node;

/**
 * TempHand is tijdelijk hand .
 *
 * @author Kees
 */
// TODO: give TempHand a remote interface.
public class TempHandImpl extends UnicastRemoteObject implements Serializable, TempHand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -255317299667017490L;
	
	
	private Card boughtCard;
	private Card reservedCard;
	private TokenList tokenList;
	private Player player;
	
	private MoveType moveType;

	private int selectedTokensCount;
	private List<Gem> selectedGemTypes;
	
	/**
	 * temporary hand of player (basically selected tokens or cards).
	 *
	 * @throws RemoteException the remote exception
	 */
	public TempHandImpl() throws RemoteException
	{
		this.selectedGemTypes = new ArrayList<>();
		this.tokenList = new TokenList();
	}
	
	/**
	 * update information about player.
	 *
	 * @param player the player
	 * @throws RemoteException the remote exception
	 */
	public void updatePlayer(Player player) throws RemoteException {
		this.player = player;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#selectCardToBuy(application.domain.Card)
	 */
	public void selectCardToBuy(Card card) throws RemoteException {
		boughtCard = card;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#selectCardToReserve(application.domain.Card)
	 */
	public void selectCardToReserve(Card card) throws RemoteException {
		reservedCard = card;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#getBoughtCard()
	 */
	public Card getBoughtCard() throws RemoteException {
		return boughtCard;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#getReservedCard()
	 */
	public Card getReservedCard() throws RemoteException {
		return reservedCard;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#setTokenList(application.domain.TokenList)
	 */
	public void setTokenList(TokenList tokenList) throws RemoteException {
		this.tokenList = tokenList;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#getTokenList()
	 */
	public TokenList getTokenList() throws RemoteException {
		return tokenList;
	}
	
	/**
	 * add token to tempHand.
	 *
	 * @param token the token
	 * @throws RemoteException the remote exception
	 */
	public void addToken(Token token) throws RemoteException {
		selectedTokensCount++;
		tokenList.add(token);
		selectedGemTypes.add(token.getGemType());
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#getSelectedTokensCount()
	 */
	public int getSelectedTokensCount() throws RemoteException {
		return selectedTokensCount;
	}
	
	/**
	 * Gets the selected gem types.
	 *
	 * @return List<Gem>
	 * @throws RemoteException the remote exception
	 */
	public List<Gem> getSelectedGemTypes() throws RemoteException {
		return selectedGemTypes;
	}
	
	/**
	 * Clear the hand of the current player.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void emptyHand() throws RemoteException 
	{
		selectedGemTypes.clear();
		tokenList.getAll().clear();
		reservedCard = null;
		boughtCard = null;
		selectedTokensCount = 0;
		
		Logger.log(String.format("%s has emptied his hand \n", player.getName()), Verbosity.DEBUG);
	}
	
	/**
	 * clear selected cards and tokens.
	 *
	 * @return true, if is empty
	 * @throws RemoteException the remote exception
	 */
	public boolean isEmpty() throws RemoteException {
		return reservedCard == null && boughtCard == null && tokenList.getAll().isEmpty() && selectedGemTypes.isEmpty();
	}

	/* (non-Javadoc)
	 * @see application.domain.TempHand#getMoveType()
	 */
	public MoveType getMoveType() throws RemoteException 
	{
		return moveType;
	}

	/* (non-Javadoc)
	 * @see application.domain.TempHand#setMoveType(application.domain.MoveType)
	 */
	public void setMoveType(MoveType moveType) throws RemoteException 
	{
		this.moveType = moveType;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.TempHand#getPlayer()
	 */
	public Player getPlayer() throws RemoteException {
		return player;
	}
	
}
