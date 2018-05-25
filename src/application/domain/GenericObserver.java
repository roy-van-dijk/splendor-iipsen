package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * @author Sanchez
 *
 * @param <T>
 * TODO: Review whether this interface is needed or not.
 */
public interface GenericObserver<T> extends Remote {
	
	public void modelChanged(T model) throws RemoteException;
}
