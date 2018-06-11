package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents a token
 * 
 * @author Sanchez
 */
public interface Token extends Remote {

	/**
	 * Gets the gem type this token is associated with.
	 *
	 * @return Gem
	 * @throws RemoteException
	 */
	public Gem getGemType() throws RemoteException;

}
