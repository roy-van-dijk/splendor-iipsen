package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import application.views.PlayerView;
/**
 * 
 * @author Sanchez
 *
 */
public interface Player extends Remote {

	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException;
	
	public void returnTokensToField(List<Token> tokens, PlayingField field) throws RemoteException;
	
	public void addObserver(PlayerObserver o) throws RemoteException;
	
	//public void removeObserver(PlayerObserver o) throws RemoteException;

	public void addSelectableCardFromReserve(Card card) throws RemoteException;
	
	public List<Card> getSelectableCardsFromReserve() throws RemoteException;
	
	public void findSelectableCardsFromReserve() throws RemoteException;
	
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException;
	
	public String getName() throws RemoteException;
	
	public List<Card> getReservedCards() throws RemoteException;
	
	public List<Card> getOwnedCards() throws RemoteException;
	
	public List<Noble> getOwnedNobles() throws RemoteException;
	
	public List<Token> getTokens() throws RemoteException;
	
	public int getPrestige() throws RemoteException;
	
	public Map<Gem, Integer> getTokensGemCount() throws RemoteException;
	
	public void debugAddToken(Token token) throws RemoteException;
	
	public Map<Gem, Integer> getDiscount() throws RemoteException;
}
