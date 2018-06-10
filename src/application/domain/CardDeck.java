package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

/**
 * 
 * @author Sanchez
 */

public interface CardDeck extends Remote
{
	public void add(Card card) throws RemoteException;
	public Card pull() throws RemoteException;
	public Card top() throws RemoteException;
	public Stack<Card> getAll() throws RemoteException;
	
	public CardLevel getLevel() throws RemoteException;
	
	public boolean isSelectable() throws RemoteException;
	
	public void setSelectable() throws RemoteException;
	
	public void clearSelectable() throws RemoteException;
	
	public boolean isSelected() throws RemoteException;
	
	public void setSelected() throws RemoteException;
	
	public void clearSelection() throws RemoteException;
}
