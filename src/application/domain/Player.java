package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import application.views.PlayerView;
/**
 * 
 * @author Sanchez
 *
 */
public interface Player extends Remote {

	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException;

	public String getName() throws RemoteException;
	
	public List<Card> getReservedCards() throws RemoteException;
	
	public void addReservedCard(Card card) throws RemoteException;
	
	public List<Card> getOwnedCards() throws RemoteException;
	
	public List<Noble> getOwnedNobles() throws RemoteException;


	public TokenList getTokenList() throws RemoteException;
	

	public int getPrestige() throws RemoteException;
	
	public void addObserver(PlayerObserver o) throws RemoteException;


	public void removeToken(Token token) throws RemoteException;


}
