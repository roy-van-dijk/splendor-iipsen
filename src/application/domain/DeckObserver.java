package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DeckObserver<T> extends Remote 
{
	public void modelChanged(T deck) throws RemoteException;
}
