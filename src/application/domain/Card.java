package application.domain;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * 
 * @author Sanchez
 *
 */
public interface Card extends Remote {
	public int getPrestigeValue() throws RemoteException;

	public String getIllustration() throws RemoteException;

	public Gem getBonusGem() throws RemoteException;

	public Map<Gem, Integer> getCosts() throws RemoteException;

	public CardLevel getLevel() throws RemoteException;
	
	public boolean isReservedFromDeck() throws RemoteException;

	public void setReservedFromDeck(boolean reservedFromDeck) throws RemoteException;

}
