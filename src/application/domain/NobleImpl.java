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
	private int illustrationID; // TODO: Replace with string read from nobles.csv instead. 
	private Map<Gem, Integer> requirements;
	
	public NobleImpl(int prestigeValue, int illustrationID, Map<Gem, Integer> requirements) {
		this.prestigeValue = prestigeValue;
		this.illustrationID = illustrationID;
		this.requirements = requirements;
	}

	public int getPrestigeValue() throws RemoteException {
		return prestigeValue;
	}

	public Map<Gem, Integer> getRequirements() throws RemoteException {
		return requirements;
	}

	public int getIllustrationID() throws RemoteException {
		return illustrationID;
	}
	
	
}
