package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class PlayingFieldImpl.
 *
 * @author Sanchez
 */
public class PlayingFieldImpl extends UnicastRemoteObject implements Reinitializable, PlayingField, Serializable {
	
	private static final long serialVersionUID = 6248798862229804286L;
	
	
	private final static int TOKEN_BASE_AMOUNT = 7;
	private final static int NOBLE_BASE_AMOUNT = 5;
	
	
	private List<CardRow> cardRows;
	private List<Noble> nobles;
	
	private List<Gem> selectableTokens;

	private TokenList tokenList;
	private TempHand tempHand;
	
	private transient List<PlayingFieldObserver> observers;


	

	/**
	 * Instantiates a new playing field impl.
	 *
	 * @param playerCount
	 * @throws RemoteException
	 */
	public PlayingFieldImpl(int playerCount) throws RemoteException {
		this.nobles = new ArrayList<>();
		this.cardRows = new ArrayList<>();
		this.tokenList = new TokenList();
		this.selectableTokens = new ArrayList<>();
		this.observers = new ArrayList<>();
		
		this.createTokens(tokensAmount(playerCount));
		this.createNobles(noblesAmount(playerCount));
		this.createCardRows();
		
		this.tempHand = new TempHandImpl();
	}
	
	/**
	 * Creates the tokens.
	 *
	 * @param baseAmount
	 * @throws RemoteException
	 */
	private void createTokens(int baseAmount) throws RemoteException
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
	
	/**
	 * Creates the nobles.
	 *
	 * @param amount
	 * @throws RemoteException
	 */
	private void createNobles(int amount) throws RemoteException
	{
		NobleDeck deck = DeckFactory.getInstance().createNobleDeck();
		Collections.shuffle(deck.getAll());
		
		for(int i = 0; i < amount; i++)
		{
			nobles.add(deck.getAll().pop());
		}
	}
	
	/**
	 * Creates the card rows.
	 *
	 * @throws RemoteException
	 */
	private void createCardRows() throws RemoteException
	{
		// Create 3 card rows (including decks), starting from level 3 to level 1.
		
		List<CardLevel> cardLevels = Arrays.asList(CardLevel.values());
		Collections.reverse(cardLevels);
		
		for(int i = 0; i < cardLevels.size(); i++)
		{
			CardLevel level = cardLevels.get(i);
			CardDeck deck = DeckFactory.getInstance().createCardDeck(level);
			Collections.shuffle(deck.getAll());
			
			cardRows.add(new CardRowImpl(deck, i));
		}
	}
	
	
	/**
	 * Nobles amount.
	 *
	 * @param playerCount
	 * @return the int
	 */
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
	
	/**
	 * Tokens amount.
	 *
	 * @param playerCount
	 * @return the int
	 */
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

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#getCardRows()
	 */
	public List<CardRow> getCardRows() throws RemoteException {
		return cardRows;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#getNobles()
	 */
	public List<Noble> getNobles() throws RemoteException {
		return nobles;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#getTokenGemCount()
	 */
	public LinkedHashMap<Gem, Integer> getTokenGemCount() throws RemoteException
	{
		return tokenList.getTokenGemCount();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#findSelectableCardsFromField()
	 */
	public void findSelectableCardsFromField() throws RemoteException {
		for(CardRow cardRow : cardRows) {
			cardRow.findSelectableCards(tempHand.getMoveType(), tempHand.getPlayer());
		}
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#setTokensSelectable(application.domain.MoveType)
	 */
	public void setTokensSelectable(MoveType moveType) throws RemoteException
	{
		selectableTokens.clear();
		
		tempHand.setMoveType(moveType);
		
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

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#getSelectableTokens()
	 */
	public List<Gem> getSelectableTokens() throws RemoteException {
		return selectableTokens;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#getTokenList()
	 */
	public TokenList getTokenList() throws RemoteException {
		return tokenList;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#getTempHand()
	 */
	public TempHand getTempHand() throws RemoteException {
		return tempHand;
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#addTokenToTemp(application.domain.Gem)
	 */
	@Override
	public void addTokenToTemp(Gem gemType) throws RemoteException {
		tempHand.addToken(new TokenImpl(gemType));
		if(tempHand.getMoveType() == MoveType.TAKE_TWO_TOKENS) {
			tempHand.addToken(new TokenImpl(gemType));
		}
		this.notifyObservers();
	}
	
	/**
	 * Notify observers.
	 *
	 * @throws RemoteException the remote exception
	 */
	private synchronized void notifyObservers() throws RemoteException
	{
		for(PlayingFieldObserver o : observers)
		{
			o.modelChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#addObserver(application.domain.PlayingFieldObserver)
	 */
	@Override
	public synchronized void addObserver(PlayingFieldObserver observer) throws RemoteException {
		this.observers.add(observer);
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#addTokens(java.util.List)
	 */
	public void addTokens(List<Token> tokens) throws RemoteException
	{
		for(Token token : tokens)
		{
			tokenList.add(token);
		}
		this.notifyObservers();
	}

	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#removeToken(application.domain.Token)
	 */
	public void removeToken(Token token) throws RemoteException {
		tokenList.remove(token);
		this.notifyObservers();
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#removeCard(application.domain.Card)
	 */
	public void removeCard(Card card) throws RemoteException{
		for(CardRow cardRow : cardRows) {
			cardRow.removeCard(card);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#newTurn()
	 */
	public void newTurn() throws RemoteException {
		selectableTokens.clear();
		this.notifyObservers();	
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#removeNoble(application.domain.Noble)
	 */
	public void removeNoble(Noble noble) throws RemoteException {
		nobles.remove(noble);
		
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#removeTokens(java.util.List)
	 */
	@Override
	public void removeTokens(List<Token> tokens) throws RemoteException {
		for(Token token : tokens)
		{
			tokenList.remove(token);
		}
		this.notifyObservers();
		
	}

	/* (non-Javadoc)
	 * @see application.domain.PlayingField#setDeckSelected(application.domain.CardRow)
	 */
	@Override
	public void setDeckSelected(CardRow selectedCardRow) throws RemoteException {
		for(CardRow cardRow : cardRows) {
			if(cardRow != selectedCardRow) {
				cardRow.getCardDeck().setSelectable();
			}
		}
		selectedCardRow.getCardDeck().setSelected();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.PlayingField#setDeckDeselected()
	 */
	@Override
	public void setDeckDeselected() throws RemoteException {
		for(CardRow cardRow : cardRows) {
			cardRow.getCardDeck().setSelectable();
			cardRow.getCardDeck().top().setReservedFromDeck(false);
		}
	}
	
	@Override
	public void reinitializeObservers() {
		this.observers = new ArrayList<>();
		for(int i = 0; i < cardRows.size(); i++)
		{
			CardRowImpl cardRow = (CardRowImpl) this.cardRows.get(i);
			cardRow.reinitializeObservers();
		}
	}
}
