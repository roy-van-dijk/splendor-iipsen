package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;


// TODO: Auto-generated Javadoc
/**
 * The Interface EndTurn.
 */
public interface EndTurn extends Remote {

	/**
	 * End turn.
	 *
	 * @throws RemoteException the remote exception
	 */
	public void endTurn() throws RemoteException;
	
	/**
	 * Returning tokens.
	 *
	 * @return true, if successful
	 * @throws RemoteException
	 */
	public boolean returningTokens() throws RemoteException;
}
