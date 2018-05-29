package application.domain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Sanchez
 *
 */
public class PlayerImpl implements Player {
	
	private String name;
	
	private List<Card> reservedCards;
	private List<Card> ownedCards;
	private List<Noble> ownedNobles;
	
	private TokenList tokenList;
	
	
	public PlayerImpl(String name) {
		this.name = name;
		
		this.reservedCards = new ArrayList<>();
		this.ownedCards = new ArrayList<>();
		this.ownedNobles = new ArrayList<>();
		
		this.tokenList = new TokenList();
	}
	
	@Override
	public void reserveCardFromField(CardRowImpl cardRowImpl, Card card) {

		if(this.getReservedCards().size() < 3) // Business rule: max 3 reserved cards
		{
			cardRowImpl.removeCard(card);
			this.addReservedCard(card);
			
			System.out.printf("%s has taken the card: %s", this.getName() ,card.toString());
			//this.notifyObservers();
		}
	}
	
	
	
	
	public String getName() {
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
	
	public int getPrestige()
	{
		int prestige = 0;
		
		for(Card ownedCard : ownedCards)
		{
			try {
				prestige += ownedCard.getPrestigeValue();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(Noble ownedNoble : ownedNobles)
		{
			try {
				prestige += ownedNoble.getPrestigeValue();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return prestige;
	}

}
