package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Class TokenImpl.
 *
 * @author Sanchez
 */
public class TokenImpl extends UnicastRemoteObject implements Token, Serializable {
	private Gem gemType;

	/* (non-Javadoc)
	 * @see application.domain.Token#getGemType()
	 */
	public Gem getGemType() {
		return gemType;
	}

	/**
	 * Instantiates a new token impl.
	 *
	 * @param gemType
	 * @throws RemoteException
	 */
	public TokenImpl(Gem gemType) throws RemoteException {
		this.gemType = gemType;
	}
	
	
}
