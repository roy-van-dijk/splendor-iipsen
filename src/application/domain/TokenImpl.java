package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author Sanchez
 *
 */
public class TokenImpl extends UnicastRemoteObject implements Token, Serializable {
	private Gem gemType;

	public Gem getGemType() {
		return gemType;
	}

	public TokenImpl(Gem gemType) throws RemoteException {
		this.gemType = gemType;
	}
	
	
}
