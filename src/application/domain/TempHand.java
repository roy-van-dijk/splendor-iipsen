package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TempHand extends Remote {
	
	public void updatePlayer(Player player) throws RemoteException;
	
	public void selectCardToBuy(Card card) throws RemoteException;
	
	public void selectCardToReserve(Card card) throws RemoteException;
	
	public Card getBoughtCard() throws RemoteException;
	
	public Card getReservedCard() throws RemoteException;
	
	public void setTokenList(TokenList tokenList) throws RemoteException;
	
	public TokenList getTokenList() throws RemoteException;
	
	public void addToken(Token token) throws RemoteException;
	
	public int getSelectedTokensCount() throws RemoteException;
	
	/**
	 * 
	 * @return
	 * List<Gem>
	 */
	public List<Gem> getSelectedGemTypes() throws RemoteException;
	
	
	/**
	 * Clear the hand of the current player
	 * @throws RemoteException 
	 */
	public void emptyHand() throws RemoteException;

	public boolean isEmpty() throws RemoteException;
	
	public MoveType getMoveType() throws RemoteException;

	public void setMoveType(MoveType moveType) throws RemoteException;
	
	public Player getPlayer() throws RemoteException;
}
