package application.domain;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Stack;

// TODO: Re-check docs

/**
 * The Class NobleDeck.
 *
 * @author Sanchez
 * TODO: Review its need. It may be better to just have a Stack of nobles in PlayingField
 */
public class NobleDeck extends UnicastRemoteObject {

	private Stack<Noble> nobles;
	
	/**
	 * Instantiates a new noble deck.
	 *
	 * @param nobles
	 * @throws RemoteException
	 */
	public NobleDeck(Stack<Noble> nobles) throws RemoteException
	{
		this.nobles = nobles;	
	}
	
	/**
	 * Adds a Noble to nobles.
	 *
	 * @param noble
	 */
	public void add(Noble noble)
	{
		nobles.push(noble);
	}
	
	/**
	 * Gives the first Noble of nobles.
	 *
	 * @return Noble
	 */
	public Noble top()
	{
		return nobles.peek();
	}
	
	/**
	 * Pull. //TODO add description
	 *
	 * @return Noble
	 */
	public Noble pull()
	{
		return nobles.pop();
	}

	/**
	 * Gets the all.
	 *
	 * @return Stack<Noble>
	 */
	public Stack<Noble> getAll() {
		return nobles;
	}
}
