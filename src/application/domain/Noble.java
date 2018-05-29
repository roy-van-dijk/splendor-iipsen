package application.domain;

import java.util.Map;
/**
 * 
 * @author 	Sanchez
 * edit		Tom
 *
 */
public class Noble {
	private int prestigeValue;
	private String illustration; 
	private Map<Gem, Integer> requirements;
	
	public Noble(int prestigeValue, String illustration, Map<Gem, Integer> requirements) {
		this.prestigeValue = prestigeValue;
		this.illustration = illustration;
		this.requirements = requirements;
	}

	public int getPrestigeValue() {
		return prestigeValue;
	}

	//requirements are different from the cards requirements. 
	//nobles are bought automatically at the end of a turn with only bonesus, not tokens.
	public Map<Gem, Integer> getRequirements() {
		return requirements;
	}

	public String getIllustration() {
		return illustration;
	}
	
	
}
=======
package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
/**
 * 
 * @author Sanchez
 *
 */
public interface Noble extends Remote {
	public int getPrestigeValue() throws RemoteException;

	public Map<Gem, Integer> getRequirements() throws RemoteException;

	public int getIllustrationID() throws RemoteException;
	
}
