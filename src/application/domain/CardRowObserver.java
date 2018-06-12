package application.domain;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An asynchronous update interface for receiving notifications
 * about CardRow information as the CardRow is constructed.
 *
 * @author Sanchez
 */
public interface CardRowObserver extends Remote {
	
	/**
	 * This method is called when information about an CardRow
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param cardRow
	 * @throws RemoteException
	 */
	public void modelChanged(CardRow cardRow) throws RemoteException;
	
}
