package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import application.util.Logger;
import application.util.Logger.Verbosity;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerImpl.
 *
 * @author Sanchez
 */
public class PlayerImpl extends UnicastRemoteObject implements Reinitializable, Player, Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private List<Card> reservedCards; // TODO: Probably replace Card with CardImpl
	private List<Card> ownedCards;
	private List<Noble> ownedNobles;

	private List<Card> selectableCards;

	private transient List<PlayerObserver> observers;
	
	private TokenList tokenList;
	
	/* (non-Javadoc)
	 * @see application.domain.Player#getSelectableCardsFromReserve()
	 */
	public List<Card> getSelectableCardsFromReserve() {
		return selectableCards;
	}
	
	/**
	 * Instantiates a new player impl.
	 *
	 * @param name the name
	 * @throws RemoteException the remote exception
	 */
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
			
			System.out.printf("[DEBUG] PlayerImpl::reserveCardFromField()::Player %s has taken the card with costs: %s\n", this.getName() ,card.getCosts());
			this.notifyObservers();
		}


	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#updatePlayerView()
	 */
	@Override
	public void updatePlayerView() throws RemoteException {
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#findSelectableCardsFromReserve()
	 */
	public void findSelectableCardsFromReserve() throws RemoteException {
		this.clearSelectableCards();
		for(Card card : this.getReservedCards()) {
			if(this.canAffordCard(card.getCosts())) {
				selectableCards.add(card);
			}
		}
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#clearSelectableCards()
	 */
	@Override
	public void clearSelectableCards() throws RemoteException {
		selectableCards.clear();
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#canAffordCard(java.util.Map)
	 */
	public boolean canAffordCard(Map<Gem, Integer> costs) throws RemoteException
	{
		Logger.log("PlayerImpl::canAffordCard::Player == " + this.getName(), Verbosity.DEBUG);
		Map<Gem, Integer> gemsCount = tokenList.getTokenGemCount();
		int jokersLeft = gemsCount.get(Gem.JOKER);
		
		//this can be made more readable (if there is time) by making a few funcitons.
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
	
	/* (non-Javadoc)
	 * @see application.domain.Player#getBonus()
	 */
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

	/* (non-Javadoc)
	 * @see application.domain.Player#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException{
		this.name = name;
	}
	

	/* (non-Javadoc)
	 * @see application.domain.Player#getName()
	 */
	public String getName() throws RemoteException
	{
		//System.out.printf("Getting name of: %s\n", name);
		return name;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#getReservedCards()
	 */
	public List<Card> getReservedCards() throws RemoteException
	{
		return reservedCards;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#getOwnedCards()
	 */
	public List<Card> getOwnedCards() throws RemoteException
	{
		return ownedCards;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#getOwnedNobles()
	 */
	public List<Noble> getOwnedNobles() throws RemoteException
	{
		return ownedNobles;
	}

	/* (non-Javadoc)
	 * @see application.domain.Player#addNoble(application.domain.Noble)
	 */
	public void addNoble(Noble noble) throws RemoteException {
		ownedNobles.add(noble);
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#getTokens()
	 */
	public List<Token> getTokens() throws RemoteException {
		return tokenList.getAll();
	}

	/* (non-Javadoc)
	 * @see application.domain.Player#getTokensGemCount()
	 */
	public Map<Gem, Integer> getTokensGemCount() throws RemoteException {
		return tokenList.getTokenGemCount();
	}

	/* (non-Javadoc)
	 * @see application.domain.Player#getPrestige()
	 */
	public int getPrestige() throws RemoteException
	{
		int prestige = 14;
		
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
	
	/* (non-Javadoc)
	 * @see application.domain.Player#returnTokensToField(java.util.List, application.domain.PlayingField)
	 */
	public void returnTokensToField(List<Token> tokens, PlayingField field) throws RemoteException
	{
		for(Token token : tokens)
		{
			tokenList.remove(token);
		}
		field.addTokens(tokens);
		this.notifyObservers();
	}
	
	/**
	 * Notify observers.
	 *
	 * @throws RemoteException the remote exception
	 */
	private synchronized void notifyObservers() throws RemoteException
	{
		for(PlayerObserver o : observers)
		{
			o.modelChanged(this);
		}
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Player#addObserver(application.domain.PlayerObserver)
	 */
	public synchronized void addObserver(PlayerObserver o) throws RemoteException 
	{
		observers.add(o);
		this.notifyObservers();
	}


	/* (non-Javadoc)
	 * @see application.domain.Player#debugAddToken(application.domain.Token)
	 */
	@Override
	public void debugAddToken(Token token) throws RemoteException {
		this.tokenList.add(token);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.Player#addCard(application.domain.Card)
	 */
	@Override
	public void addCard(Card card) throws RemoteException {
		this.ownedCards.add(card);
		
	}


	/* (non-Javadoc)
	 * @see application.domain.Player#addReserverveCard(application.domain.Card)
	 */
	@Override
	public void addReservedCard(Card card) throws RemoteException {
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

	/* (non-Javadoc)
	 * @see application.domain.Reinitializable#reinitializeObservers()
	 */
	@Override
	public void reinitializeObservers() {
		this.observers = new ArrayList<>();
	}
}
