package application.domain;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * The Class NobleImpl.
 *
 * @author Sanchez
 */
public class NobleImpl extends UnicastRemoteObject implements Noble, Serializable {
	private int prestigeValue;
	private String illustration; 
	private Map<Gem, Integer> requirements;
	
	/**
	 * Instantiates a new noble impl.
	 *
	 * @param prestigeValue
	 * @param illustration
	 * @param requirements
	 * @throws RemoteException
	 */
	public NobleImpl(int prestigeValue, String illustration, Map<Gem, Integer> requirements) throws RemoteException {
		this.prestigeValue = prestigeValue;
		this.illustration = illustration;
		this.requirements = requirements;
	}

	/* (non-Javadoc)
	 * @see application.domain.Noble#getPrestigeValue()
	 */
	public int getPrestigeValue() throws RemoteException {
		return prestigeValue;
	}

	/* (non-Javadoc)
	 * @see application.domain.Noble#getRequirements()
	 */
	public Map<Gem, Integer> getRequirements() throws RemoteException {
		return requirements;
	}

	/* (non-Javadoc)
	 * @see application.domain.Noble#getIllustration()
	 */
	public String getIllustration() throws RemoteException {
		return illustration;
	}
	
	
}
