package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

/**
 * So far a Deck seems to just wrap around a List. Seems almost useless.
 * 
 * @author Sanchez
 */

public interface Deck<T>
{
	public void add(T item) throws RemoteException;
	public T pull() throws RemoteException;
	public T top() throws RemoteException;
	public Stack<T> getAll() throws RemoteException;
	
	public void addObserver(T observer) throws RemoteException;
}
