package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	private transient List<PlayerObserver> observers;
	
	private TokenList tokenList;
	
	
	public PlayerImpl(String name) throws RemoteException 
	{
		this.name = name;
		
		this.reservedCards = new ArrayList<>();
		this.ownedCards = new ArrayList<>();
		this.ownedNobles = new ArrayList<>();
		this.observers = new ArrayList<>();
		
		this.tokenList = new TokenList();
	}
	

	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException 
	{
		// TODO (low priority): Make reservedTokens class that incorporates this business rule
		if(this.getReservedCards().size() < 3) // Business rule: max 3 reserved cards
		{
			cardRow.removeCard(card);
			reservedCards.add(card);
			
			System.out.printf("%s has taken the card: %s", this.getName() ,card.toString());
			this.notifyObservers();
		}
	}
	

	
	public void purchaseCardFromField(CardRow cardRow, Card card) throws RemoteException
	{
		if(this.canAffordCard(card.getCosts()))
		{
			cardRow.removeCard(card);
			ownedCards.add(card);
			
			System.out.printf("%s has taken the card: %s", this.getName() ,card.toString());
			this.notifyObservers();
		}
	}
	
	private boolean canAffordCard(Map<Gem, Integer> costs)
	{
		Map<Gem, Integer> gemsCount = tokenList.getTokenGemCount();
		
		for(Map.Entry<Gem, Integer> cost : costs.entrySet())
		{
			if(!gemsCount.containsKey(cost.getKey())) return false; // Player does not even have the right tokens.
			if(gemsCount.get(cost.getKey()) < cost.getValue()) return false; // Insufficient funds
		}
		return true;
	}
	
	public String getName() 
	{
		System.out.printf("Getting name of: %s\n", name);
		return name;
	}
	
	public List<Card> getReservedCards() 
	{
		return reservedCards;
	}
	public List<Card> getOwnedCards() 
	{
		return ownedCards;
	}
	
	public List<Noble> getOwnedNobles() 
	{
		return ownedNobles;
	}
	
	public List<Token> getTokens() throws RemoteException {
		return tokenList.getAll();
	}

	public Map<Gem, Integer> getTokensGemCount() throws RemoteException {
		return tokenList.getTokenGemCount();
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
	
	public void returnTokensToField(List<Token> tokens, PlayingField field) throws RemoteException
	{
		for(Token token : tokens)
		{
			tokenList.remove(token);
		}
		field.addTokens(tokens);
		this.notifyObservers();
	}
	
	private void notifyObservers() throws RemoteException
	{
		for(PlayerObserver o : observers)
		{
			o.modelChanged(this);
		}
	}
	
	public void addObserver(PlayerObserver o) throws RemoteException 
	{
		observers.add(o);
		this.notifyObservers();
	}


	@Override
	public void debugAddToken(Token token) throws RemoteException {
		this.tokenList.add(token);
	}


}
