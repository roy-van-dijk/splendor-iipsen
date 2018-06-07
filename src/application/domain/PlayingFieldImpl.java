package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Sanchez
 *
 */
public class PlayingFieldImpl extends UnicastRemoteObject implements PlayingField, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6248798862229804286L;
	
	
	private final static int TOKEN_BASE_AMOUNT = 7;
	private final static int NOBLE_BASE_AMOUNT = 5;
	
	
	private List<CardRow> cardRows;
	private List<Noble> nobles;
	
	private List<Gem> selectableTokens;

	private TokenList tokenList;
	private TempHand tempHand;
	
	private transient List<PlayingFieldObserver> observers;


	

	public PlayingFieldImpl(int playerCount) throws RemoteException {
		this.nobles = new ArrayList<>();
		this.cardRows = new ArrayList<>();
		this.tokenList = new TokenList();
		this.selectableTokens = new ArrayList<>();
		this.observers = new ArrayList<>();
		
		this.createTokens(tokensAmount(playerCount));
		this.createNobles(noblesAmount(playerCount));
		this.createCardRows();
		
		this.tempHand = new TempHand();
	}

	private void createTokens(int baseAmount)
	{
		for(Gem gem : Gem.values())
		{
			int amount = baseAmount;
			
			if(gem.equals(Gem.JOKER)) 
			{
				amount = 5;
			}
		
			for(int i = 0; i < amount; i++)
			{
				tokenList.add(new TokenImpl(gem));
			}
		}
	}
	
	private void createNobles(int amount)
	{
		NobleDeck deck = DeckFactory.getInstance().createNobleDeck();
		Collections.shuffle(deck.getAll());
		
		for(int i = 0; i < amount; i++)
		{
			nobles.add(deck.getAll().pop());
		}
	}
	
	private void createCardRows()
	{
		// Create 3 card rows (including decks)
		for(CardLevel level : CardLevel.values())
		{
			CardDeck deck = DeckFactory.getInstance().createCardDeck(level);
			Collections.shuffle(deck.getAll());
			cardRows.add(new CardRowImpl(deck));
		}
	}
	
	
	private int noblesAmount(int playerCount)
	{
		int amount;
		
		switch(playerCount) {
			case 2: { amount = NOBLE_BASE_AMOUNT - 2; } break;
			case 3: { amount = NOBLE_BASE_AMOUNT - 1; } break;
			default: { amount = NOBLE_BASE_AMOUNT; }
		}
		
		return amount;
	}
	
	private int tokensAmount(int playerCount)
	{
		int amount;
		
		switch(playerCount) {
			case 2: { amount = TOKEN_BASE_AMOUNT - 3; } break;
			case 3: { amount = TOKEN_BASE_AMOUNT - 2; } break;
			default: { amount = TOKEN_BASE_AMOUNT; }
		}
		
		return amount;
	}

	public List<CardRow> getCardRows() {
		return cardRows;
	}

	public List<Noble> getNobles() {
		return nobles;
	}
	
	public LinkedHashMap<Gem, Integer> getTokenGemCount()
	{
		return tokenList.getTokenGemCount();
	}
	
	public void findSelectableCardsFromField() throws RemoteException {
		for(CardRow cardRow : cardRows) {
			cardRow.findSelectableCards(tempHand.getPlayer());
		}
	}
	
	public void setTokensSelectable() throws RemoteException
	{
		selectableTokens.clear();
		
		MoveType moveType = tempHand.getMoveType();
		
		LinkedHashMap<Gem, Integer> gemsCount = tokenList.getTokenGemCount();
		for(Map.Entry<Gem, Integer> gemCount : gemsCount.entrySet())
		{
			if(gemCount.getKey() != Gem.JOKER) {
				if(moveType == MoveType.TAKE_TWO_TOKENS && gemCount.getValue() >= 4) {
					selectableTokens.add(gemCount.getKey());
				} 
				else if(moveType == MoveType.TAKE_THREE_TOKENS && gemCount.getValue() > 0) 
				{
					selectableTokens.add(gemCount.getKey());
				}
			}
		}
		this.notifyObservers();
	}

	public List<Gem> getSelectableTokens() {
		return selectableTokens;
	}

	public TempHand getTempHand() {
		return tempHand;
	}

	@Override
	public void addTokenToTemp(Gem gemType) throws RemoteException {
		tempHand.addToken(new TokenImpl(gemType));
		if(tempHand.getMoveType() == MoveType.TAKE_TWO_TOKENS) {
			tempHand.addToken(new TokenImpl(gemType));		
		}
		this.notifyObservers();
	}
	
	private void notifyObservers() throws RemoteException
	{
		for(PlayingFieldObserver o : observers)
		{
			o.modelChanged(this);
		}
	}

	@Override
	public void addObserver(PlayingFieldObserver observer) throws RemoteException {
		this.observers.add(observer);
		this.notifyObservers();
	}
	
	public void addTokens(List<Token> tokens) throws RemoteException
	{
		for(Token token : tokens)
		{
			tokenList.add(token);
		}
		this.notifyObservers();
	}

	public void removeToken(Token token) {
		tokenList.remove(token);
		
	}

	public void newTurn() throws RemoteException {
		this.notifyObservers();	
	}

}
