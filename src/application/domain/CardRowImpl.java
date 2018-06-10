package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;


/**
 * The Class CardRowImpl.
 *
 * @author Sanchez
 * This class acts like how you would expect CardDeck to act. This class makes sense as a View but it makes less sense as a model.
 * TODO: Consider pros/cons of merging it with CardDeck
 */
public class CardRowImpl implements Serializable, CardRow {
	
	private static final long serialVersionUID = 7939191665883088567L;

	private static final int MAX_OPEN_CARDS = 4;
	
	private CardDeck cardDeck;
	private CardImpl[] cardSlots;
	private transient ArrayList<CardRowObserver> observers;

	private List<Card> selectableCards;
	
	/**
	 * Instantiates a new card row impl.
	 *
	 * @param cardDeckImpl
	 */
	public CardRowImpl(CardDeck cardDeckImpl) {
		this.cardDeck = cardDeckImpl;
		this.cardSlots = new CardImpl[MAX_OPEN_CARDS];
		this.observers = new ArrayList<>();
		this.selectableCards = new ArrayList<>();
		
		try {
			fillCardSlots();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#removeCard(application.domain.Card)
	 */
	public void removeCard(Card card) throws RemoteException
	{
		for(int i = 0; i < cardSlots.length; i++)
		{
			if(cardSlots[i].equals(card))
			{
				cardSlots[i] = null;
			}
		}
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
					CardImpl cardFromDeck = (CardImpl) cardDeck.pull();
					cardSlots[pos] = cardFromDeck;
				} 
				catch (EmptyStackException e)
				{
					System.out.println("The deck contains no more cards to pull, leaving card slot empty.");
				}
			}
		}
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#addCardToTemp(application.domain.CardRow, application.domain.Card, application.domain.TempHand)
	 */
	@Override
	public void addCardToTemp(CardRow cardRow, Card card, TempHand tempHand) throws RemoteException {	
		MoveType moveType = tempHand.getMoveType();
		if(moveType == MoveType.PURCHASE_CARD) {
			tempHand.selectCardToBuy(card);
		} else if(moveType == MoveType.RESERVE_CARD) {
			tempHand.selectCardToReserve(card);
		}
	}
	

	/* (non-Javadoc)
	 * @see application.domain.CardRow#getCardDeck()
	 */
	public CardDeck getCardDeck() {
		return cardDeck;
	}

	/**
	 * Gets the card slots.
	 *
	 * @return Returns all card slots, containing either a Card object or a null. See comment @ CardRowView.buildUI() for a problem description.
	 */
	public Card[] getCardSlots() {
		return cardSlots;
	}
	
	/**
	 * Notify observers.
	 *
	 * @throws RemoteException
	 */
	private void notifyObservers() throws RemoteException {
		for (CardRowObserver co : observers) {
			co.modelChanged(this);
		}
	}

	/* (non-Javadoc)
	 * @see application.domain.CardRow#addObserver(application.domain.CardRowObserver)
	 */
	public void addObserver(CardRowObserver observer) throws RemoteException {
		observers.add(observer);
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#findSelectableCards(application.domain.MoveType, application.domain.Player)
	 */
	@Override
	public void findSelectableCards(MoveType moveType, Player player) throws RemoteException {
		this.clearSelectableCards();
		if(moveType == MoveType.PURCHASE_CARD) {
			for(Card card : cardSlots) {
				if(player.canAffordCard(card.getCosts())) {
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
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#clearSelectableCards()
	 */
	@Override
	public void clearSelectableCards() throws RemoteException {
		cardDeck.clearSelectable();
		cardDeck.clearSelection();
		selectableCards.clear();
		this.notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardRow#getSelectableCards()
	 */
	@Override
	public List<Card> getSelectableCards() {
		return selectableCards;
	}

	/* (non-Javadoc)
	 * @see application.domain.CardRow#updateView()
	 */
	@Override
	public void updateView() throws RemoteException {
		this.notifyObservers();	
	}
	
}
