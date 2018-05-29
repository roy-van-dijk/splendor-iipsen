package application.domain;

import java.rmi.RemoteException;
import java.util.Map;
/**
 * 
 * @author Sanchez
 *
 */
public class NobleImpl implements Noble {
	private int prestigeValue;
	private String illustration; 
	private Map<Gem, Integer> requirements;
	
	public NobleImpl(int prestigeValue, String illustration, Map<Gem, Integer> requirements) {
		this.prestigeValue = prestigeValue;
		this.illustration = illustration;
		this.requirements = requirements;
	}

	public int getPrestigeValue() throws RemoteException {
		return prestigeValue;
	}

	public Map<Gem, Integer> getRequirements() throws RemoteException {
		return requirements;
	}

	public String getIllustration() throws RemoteException {
		return illustration;
	}
	
	
}
