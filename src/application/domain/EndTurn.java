package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


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
	
	public boolean returningTokens() throws RemoteException;
}
