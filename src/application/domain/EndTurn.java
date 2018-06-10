package application.domain;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * The Interface EndTurn.
 */
public interface EndTurn {

	/**
	 * End turn.
	 *
	 * @throws RemoteException the remote exception
	 */
	void endTurn() throws RemoteException;

}
