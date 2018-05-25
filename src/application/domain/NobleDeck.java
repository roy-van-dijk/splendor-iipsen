package application.domain;

import java.util.List;
import java.util.Stack;

/**
 * @author Sanchez
 * TODO: Review its need. It may be better to just have a Stack of nobles in PlayingField
 */
public class NobleDeck implements Deck<Noble> {

	private Stack<Noble> nobles;
	
	public NobleDeck(Stack<Noble> nobles)
	{
		this.nobles = nobles;	
	}
	
	public void add(Noble noble)
	{
		nobles.push(noble);
	}
	
	public Noble top()
	{
		return nobles.peek();
	}
	
	public Noble pull()
	{
		return nobles.pop();
	}

	public Stack<Noble> getAll() {
		return nobles;
	}

	@Override
	public void addObserver(Noble observer) {
		// Unused
	}
}
