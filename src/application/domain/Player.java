package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Sanchez
 *
 */
public interface Player extends Remote {

	public void reserveCardFromField(CardRowImpl cardRowImpl, Card card);

	public String getName();
	
	public List<Card> getReservedCards();
	
	public void addReservedCard(Card card);
	
	public void purchaseCard(Card card);
	
	public List<Card> getOwnedCards();
	public List<Noble> getOwnedNobles();

	public TokenList getTokenList();
	
	public void addToken(Token token);
	
	public void removeToken(Token token);
	
	public int getPrestige();
	
	

}
