package application.domain;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

// TODO: Auto-generated Javadoc
/**
 * The Class CardDeckImpl.
 *
 * @author Sanchez
 */
public class CardDeckImpl extends UnicastRemoteObject implements CardDeck, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5422878514985364368L;
	
	
	private Stack<Card> cards;
	private CardLevel level;
	private boolean isSelectable;
	private boolean isSelected;
	
	/**
	 * Instantiates a new card deck impl.
	 *
	 * @param cards the cards
	 * @param level the level
	 * @throws RemoteException the remote exception
	 */
	public CardDeckImpl(Stack<Card> cards, CardLevel level) throws RemoteException
	{
		this.cards = cards;		
		this.level = level;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#getLevel()
	 */
	public CardLevel getLevel()
	{
		return level;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#add(application.domain.Card)
	 */
	public void add(Card card)
	{
		cards.push(card);
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#top()
	 */
	public Card top()
	{
		return cards.peek();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#pull()
	 */
	public Card pull()
	{
		return cards.pop();
	}

	/* (non-Javadoc)
	 * @see application.domain.CardDeck#getAll()
	 */
	public Stack<Card> getAll() {
		return cards;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#isSelectable()
	 */
	public boolean isSelectable() {
		return isSelectable;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#setSelectable()
	 */
	public void setSelectable() {
		isSelectable = true;
		isSelected = false;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#clearSelectable()
	 */
	public void clearSelectable() {
		isSelectable = false;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#isSelected()
	 */
	public boolean isSelected() {
		return isSelected;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#setSelected()
	 */
	public void setSelected() {
		isSelected = true;
		isSelectable = false;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.CardDeck#clearSelection()
	 */
	public void clearSelection() {
		isSelected = false;
	}
}
