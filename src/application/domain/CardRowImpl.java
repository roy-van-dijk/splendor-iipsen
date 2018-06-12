package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import application.util.Logger;
import application.util.Logger.Verbosity;


// TODO: Auto-generated Javadoc
/**
 * The Class CardRowImpl.
 *
 * @author Sanchez
 */
public class CardRowImpl extends UnicastRemoteObject implements Reinitializable, Serializable, CardRow {
	
	private static final long serialVersionUID = 7939191665883088567L;

	private static final int MAX_OPEN_CARDS = 4;
	
	private CardDeck cardDeck;
	private Card[] cardSlots;
	private transient ArrayList<CardRowObserver> observers;

	private List<Card> selectableCards;
	
	private int index; // TODO if enough time: Replace with CardLevel instead.
	

	/**
	 * Instantiates a new card row impl.
	 *
	 * @param cardDeck
	 * @param index
	 * @throws RemoteException
	 */
	public CardRowImpl(CardDeck cardDeck, int index) throws RemoteException {
		this.cardDeck = cardDeck;
		this.index = index;

		this.cardSlots = new Card[MAX_OPEN_CARDS];
		this.observers = new ArrayList<>();
		this.selectableCards = new ArrayList<>();
		
		this.initializeCardSlots();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#removeCard(application.domain.Card)
	 */
	public void removeCard(Card card) throws RemoteException
	{
		boolean debugCardFound = false;
		for(int i = 0; i < cardSlots.length; i++)
		{
			if(cardSlots[i].equals(card)) // won't work with RMI because - Left: CardImpls, Right: ProxyRefs to Card
			{
				cardSlots[i] = null;
				debugCardFound = true;
				fillCardSlots();
			}
		}
		System.out.println("[DEBUG] CardRowImpl::removeCard()::Card to be removed found? " + debugCardFound);
		this.notifyObservers();
	}
	

	/* (non-Javadoc)
	 * @see application.domain.CardRow#getCardDeck()
	 */
	public CardDeck getCardDeck() throws RemoteException {
		return cardDeck;
	}

	/**
	 * Gets the card slots.
	 *
	 * @return Returns all card slots, containing either a Card object or a null. See comment @ CardRowView.buildUI() for a problem description.
	 * @throws RemoteException the remote exception
	 */
	public Card[] getCardSlots() throws RemoteException {
		return cardSlots;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#addObserver(application.domain.CardRowObserver)
	 */
	public synchronized void addObserver(CardRowObserver observer) throws RemoteException {
		observers.add(observer);
		this.notifyObservers();
	}


	/**
	 * Notify observers.
	 *
	 * @throws RemoteException
	 */
	private synchronized void notifyObservers() throws RemoteException {
		Logger.log("CardRowImpl::notifyObservers()::Notifying all CardRow observers of change", Verbosity.DEBUG);
		for (CardRowObserver co : observers) {
			co.modelChanged(this);
		}
	}
	
	/**
	 * Initialize card slots.
	 *
	 * @throws RemoteException
	 */
	private void initializeCardSlots() throws RemoteException
	{
		this.fillCardSlots();
	}
	
	/**
	 * Fill card slots.
	 *
	 * @throws RemoteException
	 */
	private void fillCardSlots() throws RemoteException
	{
		for(int pos = 0; pos < cardSlots.length; pos++)
		{
			// Check if slot is empty
			if(cardSlots[pos] == null)
			{
				try {
					Card cardFromDeck = cardDeck.pull();
					cardSlots[pos] = cardFromDeck;
				} 
				catch (EmptyStackException e)
				{
					System.out.println("The deck contains no more cards to pull, leaving card slot empty.");
				}
			}
		}

	}

	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#findSelectableCards(application.domain.MoveType, application.domain.Player)
	 */
	@Override
	public void findSelectableCards(MoveType moveType, Player player) throws RemoteException {
		this.clearAll();
		Logger.log("CardRowImpl::findSelectableCards::Checking selectable cards for player " + player.getName(), Verbosity.DEBUG);
		Logger.log("CardRowImpl::findSelectableCards::moveType = " + moveType, Verbosity.DEBUG);
		if(moveType == MoveType.PURCHASE_CARD) {
			for(Card card : cardSlots) {
				if(player.canAffordCard(card.getCosts())) {
					Logger.log("CardRowImpl::findSelectableCards::Player can afford a card", Verbosity.DEBUG);
					selectableCards.add(card);
				}
			}
		} else if(moveType == MoveType.RESERVE_CARD){
			for(Card card : cardSlots) {
				selectableCards.add(card);
			}
			cardDeck.setSelectable();
		}
		this.notifyObservers();
	}
	

	/**
	 * Clear all.
	 *
	 * @throws RemoteException
	 */
	private void clearAll() throws RemoteException
	{
		cardDeck.clearSelectable();
		cardDeck.clearSelection();
		selectableCards.clear();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#clearSelectableCards()
	 */
	@Override
	public void clearSelectableCards() throws RemoteException {
		this.clearAll();
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#getSelectableCards()
	 */
	@Override
	public List<Card> getSelectableCards() throws RemoteException {
		return selectableCards;
	}

	// Why not make notifyObservers public?
	/* (non-Javadoc)
	 * @see application.domain.CardRow#updateView()
	 */
	@Override
	public void updateView() throws RemoteException {
		this.notifyObservers();	
	}

	/* (non-Javadoc)
	 * @see application.domain.CardRow#getIndex()
	 */
	@Override
	public int getIndex() throws RemoteException {
		return index;
	}
	
	@Override
	public void reinitializeObservers() {
		this.observers = new ArrayList<>();
	}
}
