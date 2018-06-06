package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

	private List<Card> selectableCards;

	private transient List<PlayerObserver> observers;
	
	private TokenList tokenList;
	
	public void addSelectableCardFromReserve(Card card) {
		selectableCards.add(card);
	}
	
	public List<Card> getSelectableCardsFromReserve() {
		return selectableCards;
	}
	
	public PlayerImpl(String name) throws RemoteException 
	{
		this.name = name;
		
		this.selectableCards = new ArrayList<>();
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
			
			System.out.printf("%s has taken the card with costs: %s\n", this.getName() ,card.getCosts());
			this.notifyObservers();
		}
	}
	

	
	public void purchaseCardFromField(CardRow cardRow, Card card) throws RemoteException
	{
		if(this.canAffordCard(card.getCosts()))
		{
			cardRow.removeCard(card);
			ownedCards.add(card);
			
			System.out.printf("%s has bought the card with costs: %s\\n", this.getName(), card.getCosts());
			this.notifyObservers();
		}
	}
	
	public void findSelectableCardsFromReserve() throws RemoteException {
		selectableCards.clear();
		for(Card card : this.getReservedCards()) {
			if(this.canAffordCard(card.getCosts())) {
				this.addSelectableCardFromReserve(card);
			}
		}
		
		this.notifyObservers();
	}
	
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException
	{
		Map<Gem, Integer> gemsCount = tokenList.getTokenGemCount();
		
		for(Map.Entry<Gem, Integer> cost : costs.entrySet())
		{
			//if(!gemsCount.containsKey(cost.getKey())) return false; // Player does not even have the right tokens.
			if((gemsCount.get(cost.getKey()) + gemsCount.get(Gem.JOKER)) < (cost.getValue() - this.getDiscount().get(cost.getKey()))) return false; // Insufficient funds
		}
		return true;
	}
	
	public Map<Gem, Integer> getDiscount() throws RemoteException {
		LinkedHashMap<Gem, Integer> discount = new LinkedHashMap<Gem, Integer>();
		
		for(Gem gemType : Gem.values()) {
			discount.put(gemType, 0);
		}
		
		for(Card card : this.getOwnedCards()) {
			discount.put(card.getBonusGem(), discount.get(card.getBonusGem()) + 1);
		}
		
		return discount;
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
