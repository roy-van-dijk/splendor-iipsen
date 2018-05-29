
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

	public String getIllustrationID() throws RemoteException;
	
}
	