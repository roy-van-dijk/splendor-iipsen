package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Sanchez
 *
 */
public class PlayerImpl extends UnicastRemoteObject implements Player, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private List<Card> reservedCards; // TODO: Probably replace Card with CardImpl
	private List<Card> ownedCards;
	private List<Noble> ownedNobles;

	private List<PlayerObserver> observers;
	
	private TokenList tokenList;
	
	
	public PlayerImpl(String name) throws RemoteException {
		this.name = name;
		
		this.reservedCards = new ArrayList<>();
		this.ownedCards = new ArrayList<>();
		this.ownedNobles = new ArrayList<>();
		this.observers = new ArrayList<>();
		
		this.tokenList = new TokenList();
	}
	

	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException {

		if(this.getReservedCards().size() < 3) // Business rule: max 3 reserved cards
		{
			cardRow.removeCard(card);
			this.addReservedCard(card);
			
			System.out.printf("%s has taken the card: %s", this.getName() ,card.toString());
			this.notifyObservers();
		}
	}
	
	
	public String getName() {
		System.out.printf("Getting name of: %s\n", name);
		return name;
	}
	
	public List<Card> getReservedCards() {
		return reservedCards;
	}
	
	public void addReservedCard(Card card)
	{
		reservedCards.add(card);
	}
	
	public List<Card> getOwnedCards() {
		return ownedCards;
	}
	
	public List<Noble> getOwnedNobles() {
		return ownedNobles;
	}

	public TokenList getTokenList()
	{
		return tokenList;
	}
	
	public int getPrestige() throws RemoteException
	{
		int prestige = 0;
		
		for(Card ownedCard : ownedCards)
		{
			prestige += ownedCard.getPrestigeValue();
		}
		
		for(Noble ownedNoble : ownedNobles)
		{
			prestige += ownedNoble.getPrestigeValue();	
		}
		
		return prestige;
	}

	private void notifyObservers() throws RemoteException
	{
		for(PlayerObserver o : observers)
		{
			o.modelChanged(this);
		}
	}
	
	public void addObserver(PlayerObserver o) throws RemoteException {
		observers.add(o);
		this.notifyObservers();
	}

}
