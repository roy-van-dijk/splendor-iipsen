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

	/**
	 * Gets the prestige value the noble provides
	 * 
	 * @return int prestigeValue
	 * @throws RemoteException
	 */
	public int getPrestigeValue() throws RemoteException;

	/**
	 * Gets the requirements a player has to meet in order for the noble to visit
	 * 
	 * @return Map<Gem, Integer> requirements
	 * @throws RemoteException
	 */
	public Map<Gem, Integer> getRequirements() throws RemoteException;

	/**
	 * Gets the file name of the noble illustration
	 * 
	 * @return String illustration
	 * @throws RemoteException
	 */
	public String getIllustration() throws RemoteException;

}
