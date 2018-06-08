package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Alexander
 *
 */
public interface PlayingField extends Remote {
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<CardRow>
	 */
	public List<CardRow> getCardRows() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Noble>
	 */
	public List<Noble> getNobles() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * LinkedHashMap<Gem,Integer>
	 */
	public LinkedHashMap<Gem, Integer> getTokenGemCount() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Gem>
	 */
	public List<Gem> getSelectableTokens() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * TempHand
	 */
	public TempHand getTempHand() throws RemoteException;
	/**
	 * 
	 * @throws RemoteException
	 * void
	 */
	public void findSelectableCardsFromField() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Card>
	 */
	public List<Card> getSelectableCardsFromField() throws RemoteException; 
	/**
	 * 
	 * @param gemType
	 * @throws RemoteException
	 * void
	 */
	public void addTokenToTemp(Gem gemType) throws RemoteException;
	/**
	 * 
	 * @param observer
	 * @throws RemoteException
	 * void
	 */
	public void addObserver(PlayingFieldObserver observer) throws RemoteException;
	/**
	 * 
	 * @param tokens
	 * @throws RemoteException
	 * void
	 */
	public void addTokens(List<Token> tokens) throws RemoteException;
	/**
	 * 
	 * @throws RemoteException
	 * void
	 */
	public void setTokensSelectable() throws RemoteException;
	/**
	 * 
	 * @throws RemoteException
	 * void
	 */
	public void newTurn() throws RemoteException;
	/**
	 * 
	 * @param noble
	 * @throws RemoteException
	 * void
	 */
	public void removeNoble(Noble noble) throws RemoteException;
	/**
	 * 
	 * @param card
	 * void
	 */
	public void removeCard(Card card) throws RemoteException;
	/**
	 * 
	 * @param tokens
	 * @throws RemoteException
	 * void
	 */
	public void removeTokens(List<Token> tokens) throws RemoteException;
	
	public TokenList getTokenList() throws RemoteException;
	
	public void removeToken(Token token) throws RemoteException;
	
	public void setDeckSelected(CardRow cardRow) throws RemoteException;


}
