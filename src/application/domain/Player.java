package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.views.PlayerView;
/**
 * 
 * @author Sanchez
 *
 */
public interface Player extends Remote {
	/**
	 * @param cardRow
	 * @param card
	 * @throws RemoteException
	 * void
	 */
	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException;
	/**
	 * 
	 * @param tokens
	 * @param field
	 * @throws RemoteException
	 * void
	 */
	public void returnTokensToField(List<Token> tokens, PlayingField field) throws RemoteException;
	/**
	 * 
	 * @param o
	 * @throws RemoteException
	 * void
	 */
	public void addObserver(PlayerObserver o) throws RemoteException;
	
	//public void removeObserver(PlayerObserver o) throws RemoteException;
	
	/**
	 * 
	 * @param card
	 * @throws RemoteException
	 * void
	 */
	public void addSelectableCardFromReserve(Card card) throws RemoteException;
	
	/**
	 * 
	 * @param card
	 * @throws RemoteException
	 * void
	 */
	public void addCard(Card card) throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Card>
	 */
	public List<Card> getSelectableCardsFromReserve() throws RemoteException;
	/**
	 * 
	 * @throws RemoteException
	 * void
	 */
	public void findSelectableCardsFromReserve() throws RemoteException;
	/**
	 * 
	 * @param costs
	 * @return
	 * @throws RemoteException
	 * boolean
	 */
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * String
	 */
	public String getName() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Card>
	 */
	public List<Card> getReservedCards() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Card>
	 */
	public List<Card> getOwnedCards() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Noble>
	 */
	public List<Noble> getOwnedNobles() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * List<Token>
	 */
	public List<Token> getTokens() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * int
	 */
	public int getPrestige() throws RemoteException;
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * Map<Gem,Integer>
	 */
	public Map<Gem, Integer> getTokensGemCount() throws RemoteException;
	/**
	 * 
	 * @param token
	 * @throws RemoteException
	 * void
	 */
	public void debugAddToken(Token token) throws RemoteException;

	/**
	 * 
	 * @return
	 * @throws RemoteException
	 * Map<Gem,Integer>
	 */
	public Map<Gem, Integer> getBonus() throws RemoteException;
	
	public void addNoble(Noble noble) throws RemoteException;
	/**
	 * 
	 * @param card
	 * void
	 * @return 
	 */
	public void addReserverveCard(Card card) throws RemoteException;

}
