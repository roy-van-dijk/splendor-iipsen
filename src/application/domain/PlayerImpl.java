package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.EmptyStackException;
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
	

	public void addreserveCardFromField(CardRow cardRow, Card card) throws RemoteException 
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
	
	@Override
	public void updatePlayerView() throws RemoteException {
		this.notifyObservers();
	}
	
	public void findSelectableCardsFromReserve() throws RemoteException {
		this.clearSelectableCards();
		for(Card card : this.getReservedCards()) {
			if(this.canAffordCard(card.getCosts())) {
				selectableCards.add(card);
			}
		}
		this.notifyObservers();
	}
	
	@Override
	public void clearSelectableCards() throws RemoteException {
		selectableCards.clear();
		this.notifyObservers();
	}
	
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException
	{
		Map<Gem, Integer> gemsCount = tokenList.getTokenGemCount();
		int jokersLeft = gemsCount.get(Gem.JOKER);
		
		for(Map.Entry<Gem, Integer> cost : costs.entrySet())
		{
			//if(!gemsCount.containsKey(cost.getKey())) return false; // Player does not even have the right tokens.
			if(gemsCount.get(cost.getKey()) < (cost.getValue() - this.getBonus().get(cost.getKey()))) {
				if((gemsCount.get(cost.getKey()) + jokersLeft) < (cost.getValue() - this.getBonus().get(cost.getKey()))) {
					return false; // Insufficient funds
				} else {
					jokersLeft += gemsCount.get(cost.getKey()) - (cost.getValue() - this.getBonus().get(cost.getKey()));
				}
			}
		}
		return true;
	}
	
	public Map<Gem, Integer> getBonus() throws RemoteException {
		LinkedHashMap<Gem, Integer> bonus = new LinkedHashMap<Gem, Integer>();	
		for(Gem gemType : Gem.values()) {
			if(gemType != Gem.JOKER) {
				bonus.put(gemType, 0);	
			}
		}
		for(Card card : this.getOwnedCards()) {
			bonus.put(card.getBonusGem(), bonus.get(card.getBonusGem()) + 1);
		}
		return bonus;
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
	
	public void addNoble(Noble noble) throws RemoteException {
		ownedNobles.add(noble);
		this.notifyObservers();
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
		this.notifyObservers();
	}

	/**
	 * 
	 */
	@Override
	public void addCard(Card card) throws RemoteException {
		this.ownedCards.add(card);
		
	}


	/* (non-Javadoc)
	 * @see application.domain.Player#reserveCardFromField(application.domain.CardRow, application.domain.Card)
	 */
	@Override
	public void reserveCardFromField(CardRow cardRow, Card card) throws RemoteException {
		// TODO (low priority): Make reservedTokens class that incorporates this business rule
		if(this.getReservedCards().size() < 3) // Business rule: max 3 reserved cards
		{
			cardRow.removeCard(card);
			reservedCards.add(card);
			
			System.out.printf("%s has taken the card with costs: %s\n", this.getName() ,card.getCosts());
			this.notifyObservers();
		}

	}
		

	/* (non-Javadoc)
	 * @see application.domain.Player#addReserverveCard(application.domain.Card)
	 */
	@Override
	public void addReserverveCard(Card card) throws RemoteException {
		this.reservedCards.add(card);
		
	}

	/* (non-Javadoc)
	 * @see application.domain.Player#addToken(application.domain.Token)
	 */
	@Override
	public void addToken(Token token) throws RemoteException {
		this.tokenList.add(token);
		this.notifyObservers();
		
	}
}
