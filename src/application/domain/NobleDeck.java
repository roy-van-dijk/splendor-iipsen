package application.domain;

import java.util.List;
import java.util.Stack;

/**
 * @author Sanchez
 *
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
}
