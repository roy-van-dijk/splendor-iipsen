package application.domain;


import java.io.Serializable;
import java.util.Stack;

/**
 * The Class CardDeck.
 *
 * @author Sanchez
 */
public class CardDeck implements Deck<Card>, Serializable {
	
	private Stack<Card> cards;
	private CardLevel level;
	private boolean isSelectable;
	private boolean isSelected;
	
	/**
	 * Instantiates a new card deck.
	 *
	 * @param cards
	 * @param level
	 */
	public CardDeck(Stack<Card> cards, CardLevel level)
	{
		this.cards = cards;		
		this.level = level;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return CardLevel
	 */
	public CardLevel getLevel()
	{
		return level;
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Deck#add(java.lang.Object)
	 */
	public void add(Card card)
	{
		cards.push(card);
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Deck#top()
	 */
	public Card top()
	{
		return cards.peek();
	}
	
	/* (non-Javadoc)
	 * @see application.domain.Deck#pull()
	 */
	public Card pull()
	{
		return cards.pop();
	}

	/* (non-Javadoc)
	 * @see application.domain.Deck#getAll()
	 */
	public Stack<Card> getAll() {
		return cards;
	}
	
	/**
	 * Checks if is selectable.
	 *
	 * @return true, if is selectable
	 */
	public boolean isSelectable() {
		return isSelectable;
	}
	
	/**
	 * Sets the selectable.
	 */
	public void setSelectable() {
		isSelectable = true;
		isSelected = false;
	}
	
	/**
	 * Clear selectable.
	 */
	public void clearSelectable() {
		isSelectable = false;
	}
	
	/**
	 * Checks if is selected.
	 *
	 * @return true, if is selected
	 */
	public boolean isSelected() {
		return isSelected;
	}
	
	/**
	 * Sets the selected.
	 */
	public void setSelected() {
		isSelected = true;
		isSelectable = false;
	}
	
	/**
	 * Clear selection.
	 */
	public void clearSelection() {
		isSelected = false;
	}
}
